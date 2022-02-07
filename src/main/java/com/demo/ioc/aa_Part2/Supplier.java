package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.InstantiationException;
import com.demo.ioc.aa_Part2.Exceptions.InterfaceNotFoundException;

public interface Supplier {

    Object supply(Class<?> interfaceType) throws InterfaceNotFoundException, InstantiationException;

    void print();
}
