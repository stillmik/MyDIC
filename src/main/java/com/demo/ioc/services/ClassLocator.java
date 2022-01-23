package com.demo.ioc.services;

import com.demo.ioc.exceptions.ClassLocationException;
import com.demo.ioc.models.Directory;

import java.util.Set;

public interface ClassLocator {

    Set<Class<?>> locateClasses(String directory) throws ClassLocationException;
}
