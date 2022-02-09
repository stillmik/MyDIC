package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.InstantiationException;
import com.demo.ioc.aa_Part2.Exceptions.NotFoundException;

public interface Supplier {

    Object supply(Class<?> argumentType) throws NotFoundException, InstantiationException;

    Object supply(Class<?> argumentType, InstanceConfig instanceConfig) throws NotFoundException, InstantiationException;

    void print();
}
