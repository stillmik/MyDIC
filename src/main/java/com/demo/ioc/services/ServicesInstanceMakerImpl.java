package com.demo.ioc.services;

import com.demo.ioc.config.configurations.InstantiationConfiguration;
import com.demo.ioc.exceptions.ServiceInstantiationException;
import com.demo.ioc.models.EnqueuedServiceInf;
import com.demo.ioc.models.ServiceBeanInf;
import com.demo.ioc.models.ServiceInf;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ServicesInstanceMakerImpl implements ServicesInstanceMaker {

    private static final String MAX_NUMBER_OF_ALLOWED_ITERATIONS_REACHED = "maximum number of iterations was reached";
    private final InstantiationConfiguration configuration;
    private final ObjectInstanceMaker objectInstantiationService;
    private final LinkedList<EnqueuedServiceInf> enqueuedServiceInfArray;
    private final List<Class<?>> allAvailableClasses;
    private final List<ServiceInf<?>> instantiatedServices;

    public ServicesInstanceMakerImpl(InstantiationConfiguration configuration, ObjectInstanceMaker objectInstantiationService) {
        this.configuration = configuration;
        this.objectInstantiationService = objectInstantiationService;
        this.enqueuedServiceInfArray = new LinkedList<>();
        this.allAvailableClasses = new ArrayList<>();
        instantiatedServices = new ArrayList<>();
    }

    @Override
    public List<ServiceInf<?>> instantiateServicesAndBeans(Set<ServiceInf<?>> mappedServices) throws ServiceInstantiationException {
        init(mappedServices);
        checkForMissingServices(mappedServices);

        int counter = 0;
        while (!enqueuedServiceInfArray.isEmpty()) {

            if (counter > configuration.getMaxAllowedIterations()) {
                throw new ServiceInstantiationException(MAX_NUMBER_OF_ALLOWED_ITERATIONS_REACHED);
            }

            EnqueuedServiceInf enqueuedServiceInf = this.enqueuedServiceInfArray.removeFirst();

            if(enqueuedServiceInf.isResolved()){

                ServiceInf<?> serviceInf = enqueuedServiceInf.getServiceInf();
                Object[] dependencyInstances = enqueuedServiceInf.getDependencyInstances();

                objectInstantiationService.createInstance(serviceInf,dependencyInstances);
                registerInstantiatedService(serviceInf);
                registerBeans(serviceInf);
            }else {
                enqueuedServiceInfArray.addLast(enqueuedServiceInf);
                counter++;
            }
        }
        return instantiatedServices;
    }

    private void registerBeans(ServiceInf<?> serviceInf) {
        for (Method beanMethod : serviceInf.getBeanMethods()) {
            ServiceBeanInf<?> beanInf = new ServiceBeanInf<>(beanMethod.getReturnType(),beanMethod,serviceInf);
            objectInstantiationService.createBeanInstance(beanInf);
            registerInstantiatedService(beanInf);
        }
    }

    private void registerInstantiatedService(ServiceInf<?> serviceInf) {

        if(!(serviceInf instanceof ServiceBeanInf)){
            updatedDependantServices(serviceInf);
        }

        instantiatedServices.add(serviceInf);

        for (EnqueuedServiceInf enqueuedServiceInf : enqueuedServiceInfArray) {
            if(enqueuedServiceInf.isDependencyRequired(serviceInf.getServiceType())){
                enqueuedServiceInf.addDependencyInstance(serviceInf.getInstance());
            }
        }
    }

    private void updatedDependantServices(ServiceInf<?> newService){

        for (Class<?> constParamOfNewService : newService.getTargetConstructor().getParameterTypes()) {
            for (ServiceInf<?> serviceInf : instantiatedServices) {
                if(constParamOfNewService.isAssignableFrom(serviceInf.getServiceType())){
                    serviceInf.addDependantService(newService);
                }
            }
        }
    }

    private void checkForMissingServices(Set<ServiceInf<?>> mappedServices) throws ServiceInstantiationException {
        for (ServiceInf<?> serviceInf : mappedServices) {
            for (Class<?> parameterType : serviceInf.getTargetConstructor().getParameterTypes()) {
                if(!isAssignableTypePresent(parameterType)){
                    throw new ServiceInstantiationException(String.format("could not create instance of '%s'. Parameter '%s' implementation was not found", serviceInf.getServiceType(),parameterType.getName()));
                }
            }
        }
    }

    private boolean isAssignableTypePresent(Class<?> parameter){
        for (Class<?> serviceType : allAvailableClasses) {
            if(parameter.isAssignableFrom(serviceType))
                return true;
        }
        return false;
    }

    private void init(Set<ServiceInf<?>> mappedServices) {
        enqueuedServiceInfArray.clear();
        allAvailableClasses.clear();
        instantiatedServices.clear();

        for (ServiceInf<?> serviceInf : mappedServices) {
            enqueuedServiceInfArray.add(new EnqueuedServiceInf(serviceInf));
            allAvailableClasses.add(serviceInf.getServiceType());
            allAvailableClasses.addAll(Arrays.stream(serviceInf.getBeanMethods()).map(Method::getReturnType).collect(Collectors.toList()));
        }
    }
}
