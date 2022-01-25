package com.demo.ioc.services;

import com.demo.ioc.annotations.Service;
import com.demo.ioc.config.configurations.InstantiationConfiguration;
import com.demo.ioc.exceptions.ServiceInstantiationException;
import com.demo.ioc.models.EnqueuedServiceDetails;
import com.demo.ioc.models.ServiceBeanDetails;
import com.demo.ioc.models.ServiceDetails;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ServicesInstantiationServiceImpl implements ServicesInstantiationService {

    private static final String MAX_NUMBER_OF_ALLOWED_ITERATIONS_REACHED = "maximum number of iterations was reached";
    private final InstantiationConfiguration configuration;
    private final ObjectInstantiationService instantiationService;
    private final LinkedList<EnqueuedServiceDetails> enqueuedServiceDetails;
    private final List<Class<?>> allAvailableClasses;
    private final List<ServiceDetails<?>> instantiatedServices;

    public ServicesInstantiationServiceImpl(InstantiationConfiguration configuration, ObjectInstantiationService instantiationService) {
        this.configuration = configuration;
        this.instantiationService = instantiationService;
        this.enqueuedServiceDetails = new LinkedList<>();
        this.allAvailableClasses = new ArrayList<>();
        instantiatedServices = new ArrayList<>();
    }

    @Override
    public List<ServiceDetails<?>> instantiateServicesAndBeans(Set<ServiceDetails<?>> mappedServices) throws ServiceInstantiationException {
        init(mappedServices);
        checkForMissingServices(mappedServices);

        int counter = 0;
        int maxNumberOfIterations = this.configuration.getMaxAllowedIterations();
        while (!enqueuedServiceDetails.isEmpty()) {
            if (counter > maxNumberOfIterations) {
                throw new ServiceInstantiationException(MAX_NUMBER_OF_ALLOWED_ITERATIONS_REACHED);
            }

            EnqueuedServiceDetails enqueuedServiceDetails = this.enqueuedServiceDetails.removeFirst();

            if(enqueuedServiceDetails.isResolved()){
                ServiceDetails<?> serviceDetails = enqueuedServiceDetails.getServiceDetails();
                Object[] dependencyInstances = enqueuedServiceDetails.getDependencyInstances();

                instantiationService.createInstance(serviceDetails,dependencyInstances);
                registerInstantiatedService(serviceDetails);
                registerBeans(serviceDetails);
            }else {
                this.enqueuedServiceDetails.addLast(enqueuedServiceDetails);
                counter++;
            }
        }
        return instantiatedServices;
    }

    private void registerBeans(ServiceDetails<?> serviceDetails) {
        for (Method beanMethod : serviceDetails.getBeans()) {
            ServiceBeanDetails<?> beanDetails = new ServiceBeanDetails<>(beanMethod.getReturnType(),beanMethod,serviceDetails);
            instantiationService.createBeanInstance(beanDetails);
            registerInstantiatedService(beanDetails);
        }
    }

    private void registerInstantiatedService(ServiceDetails<?> serviceDetails) {

        if(!(serviceDetails instanceof ServiceBeanDetails)){
            updatedDependantServices(serviceDetails);
        }

        instantiatedServices.add(serviceDetails);

        for (EnqueuedServiceDetails enqueuedService : enqueuedServiceDetails) {
            if(enqueuedService.isDependencyRequired(serviceDetails.getServiceType())){
                enqueuedService.addDependencyInstance(serviceDetails.getInstance());
            }
        }
    }

    private void updatedDependantServices(ServiceDetails<?> newService ){

        for (Class<?> parameterType : newService.getTargetConstructor().getParameterTypes()) {
            for (ServiceDetails<?> serviceDetails : instantiatedServices) {
                if(parameterType.isAssignableFrom(serviceDetails.getServiceType())){
                    serviceDetails.addDependantService(newService);
                }
            }
        }
    }

    private void checkForMissingServices(Set<ServiceDetails<?>> mappedServices) throws ServiceInstantiationException {
        for (ServiceDetails<?> serviceDetails : mappedServices) {
            for (Class<?> parameterType : serviceDetails.getTargetConstructor().getParameterTypes()) {
                if(!isAssignableTypePresent(parameterType)){
                    throw new ServiceInstantiationException(String.format("could not create instance of '%s'. Parameter '%s' implementation was not found", serviceDetails.getServiceType(),parameterType.getName()));
                }
            }
        }
    }

    private boolean isAssignableTypePresent(Class<?> cls){
        for (Class<?> serviceType : allAvailableClasses) {
            if(cls.isAssignableFrom(serviceType))
                return true;
        }
        return false;
    }

    private void init(Set<ServiceDetails<?>> mappedServices) {
        enqueuedServiceDetails.clear();
        allAvailableClasses.clear();
        instantiatedServices.clear();

        for (ServiceDetails<?> serviceDetails : mappedServices) {
            enqueuedServiceDetails.add(new EnqueuedServiceDetails(serviceDetails));
            allAvailableClasses.add(serviceDetails.getServiceType());
            allAvailableClasses.addAll(Arrays.stream(serviceDetails.getBeans()).map(Method::getReturnType).collect(Collectors.toList()));
        }
    }
}
