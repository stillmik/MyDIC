package com.demo.ioc.services;

import com.demo.ioc.enums.DirectoryType;
import com.demo.ioc.models.Directory;

import java.io.File;

public class DirectoryResolveImpl implements DirectoryResolver{

    @Override
    public Directory resolveDirectory(Class<?> startupClass) {
        final String directory = this.getDirectory(startupClass);

        return  new Directory(directory,this.getDirectoryType(directory));
    }

    private String getDirectory(Class<?> cls){
        return  cls.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    private DirectoryType getDirectoryType(String directory){
        File file = new File(directory);

        if(!file.isDirectory() && directory.endsWith(".jar")){
            return DirectoryType.JAR_FILE;
        }

        return DirectoryType.DIRECTORY;
    }
}
