package com.demo.ioc;

import com.demo.ioc.config.MagicConfig;
import com.demo.ioc.enums.DirectoryType;
import com.demo.ioc.models.Directory;
import com.demo.ioc.models.ServiceDetails;
import com.demo.ioc.services.*;

import java.util.Set;

public class MagicInjector {
    public static void main(String[] args) {
        run(MagicInjector.class);
    }

    public static void run(Class<?> startupClass){//<?> is a wildcard
        run(startupClass,new MagicConfig());
    }

    public static void run(Class<?> startupClass, MagicConfig magicConfig) {

        /*int startPoints =100;
        double multiplier =1.146;
        //double multiplier =1.036188;
        double total = 0;
        for(int i=1;i<=42;i++){
            total += startPoints;
            System.out.println("lvl: " + i + " pts: " + startPoints + " total: " + total);
            startPoints*=multiplier;
        }*/

       ServicesScanningService scanningService = new ServicesScanningServiceImpl(magicConfig.annotations());

        Directory directory = new DirectoryResolveImpl().resolveDirectory(startupClass);
        ClassLocator classLocator;

        System.out.println(directory.getDirectory());

        if(directory.getDirectoryType()== DirectoryType.JAR_FILE) {
            classLocator = new ClassLocatorForJarFiles();
        }else {
            classLocator = new ClassLocatorForDirectory();
        }

        Set<Class<?>> locatedClasses = classLocator.locateClasses(directory.getDirectory());

        Set<ServiceDetails<?>> serviceDetails = scanningService.mapServices(locatedClasses);
        System.out.println(serviceDetails);
    }
}
