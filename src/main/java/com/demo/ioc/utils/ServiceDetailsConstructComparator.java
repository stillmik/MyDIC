package com.demo.ioc.utils;

import com.demo.ioc.models.ServiceInf;

import java.util.Comparator;

public class ServiceDetailsConstructComparator implements Comparator<ServiceInf> {

    @Override
    public int compare(ServiceInf o1, ServiceInf o2) {
        return Integer.compare(o1.getTargetConstructor().getParameterCount(),o2.getTargetConstructor().getParameterCount());
    }
}
