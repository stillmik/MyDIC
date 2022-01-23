package com.demo.ioc.services;

import com.demo.ioc.models.ServiceDetails;

import java.util.Set;

public class ServicesScanningServiceImpl implements ServicesScanningService{

    @Override
    public Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses) {
        return null;
    }
}
