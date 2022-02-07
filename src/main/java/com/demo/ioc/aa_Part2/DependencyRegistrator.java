package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;

public interface DependencyRegistrator {

    void register(Class<?> interfaceType,Class<?> classType) throws ClassRegistrationException;

    void print();

    void register(Class<?> interfaceType,Class<?>... classTypes) throws ClassRegistrationException;
}
