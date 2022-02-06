package com.demo.ioc.services;

import com.demo.ioc.models.ServiceInf;

import java.util.Set;

public interface ServicesScanner {

    Set<ServiceInf<?>> mapServices(Set<Class<?>> locatedClasses);
}
