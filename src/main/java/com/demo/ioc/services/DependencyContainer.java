package com.demo.ioc.services;

import com.demo.ioc.exceptions.AlreadyInitializedException;
import com.demo.ioc.models.ServiceInf;

import java.lang.annotation.Annotation;
import java.util.List;

public interface DependencyContainer {

    void init(List<ServiceInf<?>> servicesAndBeans, ObjectInstanceMaker instantiationService) throws AlreadyInitializedException;

    <T> void reload(ServiceInf<T> serviceDetails, boolean reloadDependantServices);

    <T> T reload(T service);

    <T> T reload(T service, boolean reloadDependantServices);

    <T> T getService(Class<T> serviceType);

    <T> ServiceInf<T> getServiceDetails(Class<T> serviceType);

    List<ServiceInf<?>> getServicesByAnnotation(Class<? extends Annotation> annotationType);

    List<Object> getAllServices();

    List<ServiceInf<?>> getAllServicesDetails();
}
