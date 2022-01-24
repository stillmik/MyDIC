package com.demo.ioc.services;

import com.demo.ioc.exceptions.ClassLocationException;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ClassLocatorForDirectory implements ClassLocator {

    private final Set<Class<?>> locatedClasses;

    public ClassLocatorForDirectory() {
        this.locatedClasses = new HashSet<>();
    }

    @Override
    public Set<Class<?>> locateClasses(String directory) throws ClassLocationException {
        locatedClasses.clear();

        File file = new File(directory);

        if (!file.isDirectory()) {
            throw new ClassLocationException("invalid dir :" + directory);
        }

        for(File innerFile: Objects.requireNonNull(file.listFiles())){
            try {
                scanDir(innerFile,"");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return this.locatedClasses;
    }

    private void scanDir(File file, String packageName) throws ClassNotFoundException {

        if (file.isDirectory()) {

            packageName += file.getName() + ".";
            for (File innerFile : Objects.requireNonNull(file.listFiles())) {
                scanDir(innerFile,packageName);
            }

        }else {
            if(!file.getName().endsWith(".class")){
                return;
            }

            final String className = packageName + file.getName().replace(".class","");

            locatedClasses.add(Class.forName(className));
        }
    }
}
