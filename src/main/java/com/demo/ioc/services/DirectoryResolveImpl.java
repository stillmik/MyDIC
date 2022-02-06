package com.demo.ioc.services;

import com.demo.ioc.enums.DirectoryType;
import com.demo.ioc.models.DirectoryInf;

import java.io.File;

public class DirectoryResolveImpl implements DirectoryResolver{

    @Override
    public DirectoryInf resolveDirectory(Class<?> startupClass) {
        final String directoryPath = getDirectoryPath(startupClass);
        return  new DirectoryInf(directoryPath,getDirectoryType(directoryPath));
    }

    private String getDirectoryPath(Class<?> cls){
        return  cls.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    private DirectoryType getDirectoryType(String directoryPath){
        File file = new File(directoryPath);

        if(!file.isDirectory() && directoryPath.endsWith(".jar")){
            return DirectoryType.JAR_FILE;
        }

        return DirectoryType.DIRECTORY;
    }
}
