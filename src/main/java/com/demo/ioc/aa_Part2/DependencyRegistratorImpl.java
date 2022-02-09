package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;

import java.util.*;

public class DependencyRegistratorImpl implements DependencyRegistrator {

    private Map<Class<?>, ArrayList<ResolvePackich>> resolveMap;

    public DependencyRegistratorImpl() {
        this.resolveMap = new HashMap<>();
    }


    @Override
    public void register(Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!resolveMap.containsKey(interfaceType))
                resolveMap.put(interfaceType, new ArrayList<>());

            resolveMap.get(interfaceType).add(
                    new ResolvePackich(
                            interfaceType,
                            classType,
                            ImplementationConfig.ANY,
                            InstanceConfig.INSTANCE,
                            DepthConfig.T_ANY)
            );
        } else {
            throw new ClassRegistrationException("Could not register class " + classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void register(Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(interfaceType, classType);
        }
    }

    @Override
    public void register(DepthConfig depthConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!resolveMap.containsKey(interfaceType))
                resolveMap.put(interfaceType, new ArrayList<>());

            resolveMap.get(interfaceType).add(
                    new ResolvePackich(
                            interfaceType,
                            classType,
                            ImplementationConfig.ANY,
                            InstanceConfig.INSTANCE,
                            depthConfig)
            );
        } else {
            throw new ClassRegistrationException("Could not register class " + classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void register(DepthConfig depthConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(depthConfig,interfaceType, classType);
        }
    }

    @Override
    public void register(InstanceConfig instanceConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!resolveMap.containsKey(interfaceType))
                resolveMap.put(interfaceType, new ArrayList<>());

            resolveMap.get(interfaceType).add(
                    new ResolvePackich(
                            interfaceType,
                            classType,
                            ImplementationConfig.ANY,
                            instanceConfig,
                            DepthConfig.T_ANY)
            );
        } else {
            throw new ClassRegistrationException("Could not register class " + classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void register(ImplementationConfig implementationConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!resolveMap.containsKey(interfaceType))
                resolveMap.put(interfaceType, new ArrayList<>());

            resolveMap.get(interfaceType).add(
                    new ResolvePackich(
                            interfaceType,
                            classType,
                            implementationConfig,
                            InstanceConfig.INSTANCE,
                            DepthConfig.T_ANY)
            );
        } else {
            throw new ClassRegistrationException("Could not register class " + classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void register(ImplementationConfig implementationConfig, InstanceConfig instanceConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!resolveMap.containsKey(interfaceType))
                resolveMap.put(interfaceType, new ArrayList<>());

            resolveMap.get(interfaceType).add(
                    new ResolvePackich(
                            interfaceType,
                            classType,
                            implementationConfig,
                            instanceConfig,
                            DepthConfig.T_ANY)
            );
        } else {
            throw new ClassRegistrationException("Could not register class " + classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void register(InstanceConfig instanceConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(instanceConfig, interfaceType, classType);
        }
    }

    @Override
    public void register(ImplementationConfig implementationConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(implementationConfig, interfaceType, classType);
        }
    }

    @Override
    public void register(ImplementationConfig implementationConfig, InstanceConfig instanceConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(implementationConfig, instanceConfig, interfaceType, classType);
        }
    }


    @Override
    public void register(DepthConfig depthConfig, InstanceConfig instanceConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!resolveMap.containsKey(interfaceType))
                resolveMap.put(interfaceType, new ArrayList<>());

            resolveMap.get(interfaceType).add(
                    new ResolvePackich(
                            interfaceType,
                            classType,
                            ImplementationConfig.ANY,
                            instanceConfig,
                            depthConfig)
            );
        } else {
            throw new ClassRegistrationException("Could not register class " + classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void register(DepthConfig depthConfig, ImplementationConfig implementationConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!resolveMap.containsKey(interfaceType))
                resolveMap.put(interfaceType, new ArrayList<>());

            resolveMap.get(interfaceType).add(
                    new ResolvePackich(
                            interfaceType,
                            classType,
                            ImplementationConfig.ANY,
                            InstanceConfig.INSTANCE,
                            depthConfig)
            );
        } else {
            throw new ClassRegistrationException("Could not register class " + classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void register(DepthConfig depthConfig, ImplementationConfig implementationConfig, InstanceConfig instanceConfig, Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!resolveMap.containsKey(interfaceType))
                resolveMap.put(interfaceType, new ArrayList<>());

            resolveMap.get(interfaceType).add(
                    new ResolvePackich(
                            interfaceType,
                            classType,
                            implementationConfig,
                            instanceConfig,
                            depthConfig)
            );
        } else {
            throw new ClassRegistrationException("Could not register class " + classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void register(DepthConfig depthConfig, InstanceConfig instanceConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(depthConfig, instanceConfig, interfaceType, classType);
        }
    }

    @Override
    public void register(DepthConfig depthConfig, ImplementationConfig implementationConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(depthConfig, implementationConfig, interfaceType, classType);
        }
    }

    @Override
    public void register(DepthConfig depthConfig, ImplementationConfig implementationConfig, InstanceConfig instanceConfig, Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(depthConfig, implementationConfig, instanceConfig, interfaceType, classType);
        }
    }

    @Override
    public Map<Class<?>, ArrayList<ResolvePackich>> getResolveMap() {
        if (resolveMap.isEmpty())
            return null;
        return resolveMap;
    }

    private boolean registrationIsCorrect(Class<?> interfaceType, Class<?> classType) {
        return interfaceType.isAssignableFrom(classType);
    }
}
