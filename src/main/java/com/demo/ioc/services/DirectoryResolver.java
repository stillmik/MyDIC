package com.demo.ioc.services;

import com.demo.ioc.models.DirectoryInf;

public interface DirectoryResolver {

    DirectoryInf resolveDirectory(Class<?> startupClass);
}
