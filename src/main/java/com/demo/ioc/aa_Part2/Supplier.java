package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;
import com.demo.ioc.aa_Part2.Exceptions.InstantiationException;
import com.demo.ioc.aa_Part2.Exceptions.NotFoundException;

import java.util.List;

public interface Supplier {

    void registerCycle(Class<?> classType,int maxInstances);

    Object supply(Class<?> argumentType) throws NotFoundException, InstantiationException, ClassRegistrationException;

    Object supply(Class<?> argumentType, InstanceConfig instanceConfig) throws NotFoundException, InstantiationException, ClassRegistrationException;

    Object supply(Class<?> argumentType, ImplementationConfig implementationConfig) throws NotFoundException, InstantiationException, ClassRegistrationException;

    Object supply(Class<?> argumentType, InstanceConfig instanceConfig,ImplementationConfig implementationConfig) throws NotFoundException, InstantiationException, ClassRegistrationException;

    Object[] supplyAll(Class<?> argumentType) throws NotFoundException, InstantiationException, ClassRegistrationException;

    void print();
}
