package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;

import java.util.*;

public class DependencyRegistratorImpl implements DependencyRegistrator {

    private final Map<Class<?>, ArrayList<Class<?>>> registratedClasses;

    public DependencyRegistratorImpl() {
        this.registratedClasses = new HashMap<>();
    }


    @Override
    public void register(Class<?> interfaceType, Class<?> classType) throws ClassRegistrationException {
        if (registrationIsCorrect(interfaceType, classType)) {

            if (!registratedClasses.containsKey(interfaceType))
                registratedClasses.put(interfaceType, new ArrayList<>());

            registratedClasses.get(interfaceType).add(classType);
        }else {
            throw new ClassRegistrationException("Could not register class "+ classType.getName() + ". interface " + interfaceType.getName() + " is not suitable");
        }
    }

    @Override
    public void print() {
        System.out.println(registratedClasses);
    }

    @Override
    public void register(Class<?> interfaceType, Class<?>... classTypes) throws ClassRegistrationException {
        for (Class<?> classType : classTypes) {
            register(interfaceType,classType);
        }
    }

    private boolean registrationIsCorrect(Class<?> interfaceType, Class<?> classType) {
        return interfaceType.isAssignableFrom(classType);
    }
}
