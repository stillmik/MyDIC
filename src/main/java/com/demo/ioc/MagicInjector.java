package com.demo.ioc;

import com.demo.ioc.config.MagicConfig;
import com.demo.ioc.enums.DirectoryType;
import com.demo.ioc.models.Directory;
import com.demo.ioc.services.*;

import java.util.Set;

public class MagicInjector {
    public static void main(String[] args) {
        run(MagicInjector.class);
    }

    public static void run(Class<?> startupClass){
        run(startupClass,new MagicConfig());
    }

    public static void run(Class<?> startupClass, MagicConfig magicConfig){

       /*int startPoints =100;
        double multiplier =1.036188;
        double total = 0;
        for(int i=1;i<42*3;i++){
            total += startPoints;
            System.out.println("lvl: " + i + " pts: " + startPoints + " total: " + total);
            startPoints*=multiplier;
        }*/

        Directory directory = new DirectoryResolveImpl().resolveDirectory(startupClass);
        ClassLocator classLocator;

        if(directory.getDirectoryType()== DirectoryType.JAR_FILE) {
            classLocator = new ClassLocatorForJarFiles();
        }else {
            classLocator = new ClassLocatorForDirectory();
        }

        Set<Class<?>> classes = classLocator.locateClasses(directory.getDirectory());

        System.out.println("Dir is: ");
        System.out.println(classes);
    }
}
