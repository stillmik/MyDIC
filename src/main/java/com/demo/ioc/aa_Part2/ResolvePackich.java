package com.demo.ioc.aa_Part2;

import java.util.ArrayList;
import java.util.List;

public class ResolvePackich {
    private Class<?> interfaceType;
    private Class<?> registratedClass;
    private List<Object> constructorInstances;
    private ImplementationConfig implementationConfig;
    private InstanceConfig instanceConfig;
    private DepthConfig depthConfig;


    public ResolvePackich(Class<?> interfaceType, Class<?> classType, ImplementationConfig implementationConfig, InstanceConfig instanceConfig, DepthConfig depthConfig) {
        registratedClass = classType;
        this.interfaceType = interfaceType;
        this.implementationConfig = implementationConfig;
        this.instanceConfig = instanceConfig;
        this.depthConfig = depthConfig;
        constructorInstances = new ArrayList<>();
    }

    public void setInterfaceType(Class<?> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public void setImplementationConfig(ImplementationConfig implementationConfig) {
        this.implementationConfig = implementationConfig;
    }

    public void setInstanceConfig(InstanceConfig instanceConfig) {
        this.instanceConfig = instanceConfig;
    }

    public void setRegistratedClass(Class<?> registratedClass) {
        this.registratedClass = registratedClass;
    }


    public void setDepthConfig(DepthConfig depthConfig) {
        this.depthConfig = depthConfig;
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public Class<?> getRegistratedClass() {
        return registratedClass;
    }

    public ImplementationConfig getImplementationConfig() {
        return implementationConfig;
    }

    public InstanceConfig getInstanceConfig() {
        return instanceConfig;
    }

    public DepthConfig getDepthConfig() {
        return depthConfig;
    }

    public void addConstructorInstance(Object param) {
        constructorInstances.add(param);
    }

    public List<Object> getConstructorInstances() {
        return constructorInstances;
    }
}
