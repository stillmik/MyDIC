package com.demo.ioc.models;

import java.lang.reflect.Method;

public class ServiceBeanDetails<T> extends ServiceDetails<T> {

    private final Method originMethod;
    private final ServiceDetails<?> rootService;

    public ServiceBeanDetails(Class<T> beanType, Method originMethod,ServiceDetails<?> rootService) {
        this.originMethod=originMethod;
        this.rootService=rootService;
        setBeans(new Method[0]);
        setServiceType(beanType);
    }

    public Method getOriginMethod(){
        return originMethod;
    }

    public ServiceDetails<?> getRootService(){
        return rootService;
    }
}
