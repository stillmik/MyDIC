package com.demo.ioc.aa_Part2;

import java.util.ArrayList;
import java.util.List;

public class InterfaceResolvePackich {
    private Class<?> interfaceType;
    private List<Class<?>> registratedClasses;
    private ImplementationConfig implementationConfig;
    private InstanceConfig instanceConfig;
    private DepthConfig depthConfig;


    public InterfaceResolvePackich(Class<?> interfaceType, Class<?> classType, ImplementationConfig implementationConfig, InstanceConfig instanceConfig, DepthConfig depthConfig) {
        registratedClasses = new ArrayList<>();
        registratedClasses.add(classType);
        this.interfaceType = interfaceType;
        this.implementationConfig = implementationConfig;
        this.instanceConfig = instanceConfig;
        this.depthConfig = depthConfig;
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

    public void addRegistratedClass(Class<?> registratedClass) {
        registratedClasses.add(registratedClass);
    }


    public void setDepthConfig(DepthConfig depthConfig) {
        this.depthConfig = depthConfig;
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public List<Class<?>> getRegistratedClasses() {
        return registratedClasses;
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
}
