package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;

import java.util.ArrayList;
import java.util.Map;

public interface DependencyRegistrator {

    void register(Class<?> interfaceType,Class<?> classType) throws ClassRegistrationException;

    void register(Class<?> interfaceType,Class<?>... classTypes) throws ClassRegistrationException;

    void register(DepthConfig depthConfig, Class<?> interfaceType,Class<?> classType) throws ClassRegistrationException;

    void register(DepthConfig depthConfig,Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException;

    void register(InstanceConfig instanceConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException;

    void register(ImplementationConfig implementationConfig, Class<?> interfaceType, Class<?> classTypes) throws ClassRegistrationException;

    void register(ImplementationConfig implementationConfig, InstanceConfig second, Class<?> interfaceType, Class<?> classTypes) throws ClassRegistrationException;

    void register(InstanceConfig instanceConfig, Class<?> interfaceType, Class<?>... classType) throws ClassRegistrationException;

    void register(ImplementationConfig implementationConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException;

    void register(ImplementationConfig implementationConfig, InstanceConfig second, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException;



    void register(DepthConfig depthConfig,InstanceConfig instanceConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException;

    void register(DepthConfig depthConfig,ImplementationConfig implementationConfig, Class<?> interfaceType, Class<?> classTypes) throws ClassRegistrationException;

    void register(DepthConfig depthConfig,ImplementationConfig implementationConfig, InstanceConfig second, Class<?> interfaceType, Class<?> classTypes) throws ClassRegistrationException;

    void register(DepthConfig depthConfig,InstanceConfig instanceConfig, Class<?> interfaceType, Class<?>... classType) throws ClassRegistrationException;

    void register(DepthConfig depthConfig,ImplementationConfig implementationConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException;

    void register(DepthConfig depthConfig,ImplementationConfig implementationConfig, InstanceConfig second, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException;

    Map<Class<?>, ArrayList<InterfaceResolvePackich>> getInterfaceResolvePackich();

}
