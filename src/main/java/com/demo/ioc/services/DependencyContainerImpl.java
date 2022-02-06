package com.demo.ioc.services;

import com.demo.ioc.exceptions.AlreadyInitializedException;
import com.demo.ioc.models.ServiceBeanInf;
import com.demo.ioc.models.ServiceInf;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DependencyContainerImpl implements DependencyContainer {

    private static final String ALREADY_INITIALIZED = "Dependency container already initialized";
    private boolean isInit;
    private List<ServiceInf<?>> servicesAndBeans;
    private ObjectInstanceMaker objectInstanceMaker;

    public DependencyContainerImpl() {
        isInit = false;
    }

    @Override
    public void init(List<ServiceInf<?>> servicesAndBeans, ObjectInstanceMaker objectInstanceMaker) throws AlreadyInitializedException {

        if (isInit) {
            throw new AlreadyInitializedException(ALREADY_INITIALIZED);
        }

        this.servicesAndBeans = servicesAndBeans;
        this.objectInstanceMaker = objectInstanceMaker;

        isInit = true;
    }

    @Override
    public <T> void reload(ServiceInf<T> tServiceInf, boolean reloadDependantServices) {
        objectInstanceMaker.destroyInstance(tServiceInf);
        handleReload(tServiceInf);

        if (reloadDependantServices) {
            for (ServiceInf<?> dependantService : tServiceInf.getDependantServices()) {
                reload(dependantService, reloadDependantServices);
            }
        }
    }

    private void handleReload(ServiceInf<?> serviceDetails) {

        if (serviceDetails instanceof ServiceBeanInf) {
            objectInstanceMaker.createBeanInstance((ServiceBeanInf<?>) serviceDetails);
        } else {
            objectInstanceMaker.createInstance(serviceDetails, collectDependencies(serviceDetails));
        }


    }

    private Object[] collectDependencies(ServiceInf<?> serviceDetails) {
        Class<?>[] parameterTypes = serviceDetails.getTargetConstructor().getParameterTypes();
        Object[] dependencyInstances = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            dependencyInstances[i] = getService(parameterTypes[i]);
        }

        return dependencyInstances;
    }

    @Override
    public <T> T reload(T service) {
        return reload(service, false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T reload(T service, boolean reloadDependantServices) {
        ServiceInf<T> serviceDetails = (ServiceInf<T>) getServiceDetails(service.getClass());
        if (serviceDetails == null) {
            return null;
        }

        reload(serviceDetails,reloadDependantServices);
        return serviceDetails.getInstance();
    }

    @Override
    public <T> T getService(Class<T> serviceType) {
        ServiceInf<?> serviceDetails = this.getServiceDetails(serviceType);

        if (serviceDetails != null) {
            return (T) serviceDetails.getInstance();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ServiceInf<T> getServiceDetails(Class<T> serviceType) {
        return (ServiceInf<T>) servicesAndBeans.stream().filter(si -> serviceType.isAssignableFrom(si.getServiceType())).findFirst().orElse(null);
    }

    @Override
    public List<ServiceInf<?>> getServicesByAnnotation(Class<? extends Annotation> annotationType) {
        return this.servicesAndBeans.stream().filter(sd -> sd.getAnnotation().annotationType() == annotationType).collect(Collectors.toList());
    }

    @Override
    public List<Object> getAllServices() {
        return this.servicesAndBeans.stream().map(ServiceInf::getInstance).collect(Collectors.toList());
    }

    @Override
    public List<ServiceInf<?>> getAllServicesDetails() {
        return Collections.unmodifiableList(servicesAndBeans);
    }
}
