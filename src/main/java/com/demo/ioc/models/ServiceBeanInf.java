package com.demo.ioc.models;

import java.lang.reflect.Method;

public class ServiceBeanInf<T> extends ServiceInf<T> {

    private final Method originMethod;
    private final ServiceInf<?> rootService;

    public ServiceBeanInf(Class<T> beanType, Method originMethod, ServiceInf<?> rootService) {
        this.originMethod=originMethod;
        this.rootService=rootService;
        setBeanMethods(new Method[0]);
        setServiceType(beanType);
    }

    public Method getOriginMethod(){
        return originMethod;
    }

    public ServiceInf<?> getRootService(){
        return rootService;
    }
}
