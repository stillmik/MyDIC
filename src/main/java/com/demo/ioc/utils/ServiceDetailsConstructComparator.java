package com.demo.ioc.utils;

import com.demo.ioc.models.ServiceDetails;

import java.util.Comparator;

public class ServiceDetailsConstructComparator implements Comparator<ServiceDetails> {

    @Override
    public int compare(ServiceDetails o1, ServiceDetails o2) {
        return Integer.compare(o1.getTargetConstructor().getParameterCount(),o2.getTargetConstructor().getParameterCount());
    }
}
