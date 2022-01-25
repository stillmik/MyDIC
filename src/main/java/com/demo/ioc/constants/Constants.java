package com.demo.ioc.constants;

import com.demo.ioc.annotations.Service;

import java.lang.annotation.Annotation;

public class Constants {
    public static final String JAVA_BINARY_EXTENSION = ".class";

    public static final Class<Service> DEFAULT_LOADED_ANNOTATION = Service.class;

    public static final int MAX_NUMBER_OF_INSTANTIATION_CONFIGURATIONS = 100000;
}
