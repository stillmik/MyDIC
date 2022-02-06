package com.demo.ioc.models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceInf<T> {

    private Class<T> serviceType;
    private Annotation annotation;
    private Constructor<T> targetConstructor;
    private T instance;
    private Method postConstructorMethod;
    private Method preDestroyMethod;
    private Method[] beanMethods;
    private final List<ServiceInf<?>> dependantServices;

    public ServiceInf() {
        dependantServices = new ArrayList<>();
    }

    public ServiceInf(Class<T> serviceType, Annotation annotation, Constructor<T> targetConstructor, Method postConstructorMethod, Method preDestroyMethod, Method[] beanMethods){

        this();
        setAnnotation(annotation);
        setServiceType(serviceType);
        setTargetConstructor(targetConstructor);
        setPreDestroyMethod(preDestroyMethod);
        setPostConstructorMethod(postConstructorMethod);
        setBeanMethods(beanMethods);
    }

    public Class<T> getServiceType() {
        return serviceType;
    }

    public void setServiceType(Class<T> serviceType) {
        this.serviceType = serviceType;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Constructor<T> getTargetConstructor() {
        return targetConstructor;
    }

    public void setTargetConstructor(Constructor<T> targetConstructor) {
        this.targetConstructor = targetConstructor;
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = (T)instance;
    }

    public Method getPostConstructorMethod() {
        return postConstructorMethod;
    }

    public void setPostConstructorMethod(Method postConstructorMethod) {
        this.postConstructorMethod = postConstructorMethod;
    }

    public Method getPreDestroyMethod() {
        return preDestroyMethod;
    }

    public void setPreDestroyMethod(Method preDestroyMethod) {
        this.preDestroyMethod = preDestroyMethod;
    }

    public Method[] getBeanMethods() {
        return beanMethods;
    }

    public void setBeanMethods(Method[] beanMethods) {
        this.beanMethods = beanMethods;
    }

    public List<ServiceInf<?>> getDependantServices() {
        return Collections.unmodifiableList(dependantServices);
    }

    public void addDependantService(ServiceInf<?> serviceDetails) {
        dependantServices.add(serviceDetails);
    }

    @Override
    public int hashCode(){
        if(this.serviceType==null){
            return super.hashCode();
        }
        return this.serviceType.hashCode();
    }
}
