package com.demo.ioc;

import com.demo.ioc.annotations.PostConstruct;
import com.demo.ioc.annotations.Service;
import com.demo.ioc.annotations.StartUp;
import com.demo.ioc.config.MagicConfig;
import com.demo.ioc.enums.DirectoryType;
import com.demo.ioc.models.DirectoryInf;
import com.demo.ioc.models.ServiceInf;
import com.demo.ioc.services.*;
import com.demo.ioc.tests.OtherService;
import com.demo.ioc.tests.OtherServiceContract;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@Service
public class MagicInjector {

    private final OtherServiceContract otherService;

    public static final DependencyContainer dependencyContainer;

    static {
        dependencyContainer = new DependencyContainerImpl();
    }


    public MagicInjector(OtherService otherService) {
        this.otherService = otherService;
    }

    @PostConstruct
    public void init() {
        System.out.println("creating main class");
    }


    public static void main(String[] args) {
        run(MagicInjector.class);
    }

    public static void run(Class<?> startupClass) {//<?> is a wildcard
        run(startupClass, new MagicConfig());
    }

    @StartUp
    public void appStart() {
        System.out.println("i should be at last position");
        dependencyContainer.reload(dependencyContainer.getService(OtherServiceContract.class), true);
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

        ServicesScanner servicesScanner = new ServicesScannerImpl(magicConfig.annotations());
        ObjectInstanceMaker objectInstanceMaker = new ObjectInstanceMakerImpl();
        ServicesInstanceMaker servicesInstanceMaker = new ServicesInstanceMakerImpl(magicConfig.getInstantiations(), objectInstanceMaker);

        DirectoryInf directoryInf = new DirectoryResolveImpl().resolveDirectory(startupClass);
        System.out.println(directoryInf.getDirectoryPath());
        ClassLocator classLocator;

        if (directoryInf.getDirectoryType() == DirectoryType.JAR_FILE) {
            classLocator = new ClassLocatorForJarFiles();
        } else {
            classLocator = new ClassLocatorForDirectory();
        }

        Set<Class<?>> locatedClasses = classLocator.locateClasses(directoryInf.getDirectoryPath());

        Set<ServiceInf<?>> mappedServices = servicesScanner.mapServices(locatedClasses);
        System.out.println(mappedServices);
        List<ServiceInf<?>> serviceDetails = servicesInstanceMaker.instantiateServicesAndBeans(mappedServices);

        dependencyContainer.init(serviceDetails, objectInstanceMaker);
        runStartupMethod(startupClass);
    }

    private static void runStartupMethod(Class<?> startupClass) {
        ServiceInf<?> serviceInf = dependencyContainer.getServiceDetails(startupClass);

        for (Method declaredMethod : serviceInf.getServiceType().getDeclaredMethods()) {
            if (declaredMethod.getParameterCount() != 0 || (declaredMethod.getReturnType() != void.class && declaredMethod.getReturnType() != Void.class) || !declaredMethod.isAnnotationPresent(StartUp.class)) {
                continue;
            }

            declaredMethod.setAccessible(true);
            try {
                declaredMethod.invoke(serviceInf.getInstance());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            return;
        }
    }
}
