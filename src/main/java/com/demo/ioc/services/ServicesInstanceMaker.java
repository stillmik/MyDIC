package com.demo.ioc.services;

import com.demo.ioc.exceptions.ServiceInstantiationException;
import com.demo.ioc.models.ServiceInf;

import java.util.List;
import java.util.Set;

public interface ServicesInstanceMaker {
    List<ServiceInf<?>> instantiateServicesAndBeans(Set<ServiceInf<?>> mappedServices) throws ServiceInstantiationException;

}
