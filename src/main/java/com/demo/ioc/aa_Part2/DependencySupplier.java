package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.NotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class DependencySupplier implements Supplier {

    private final Map<Class<?>, ArrayList<ResolvePackich>> resolveMap;
    private final Map<Class<?>, Object> singletonObjectsMap;

    public DependencySupplier(Map<Class<?>, ArrayList<ResolvePackich>> resolveMap) {
        this.resolveMap = resolveMap;
        singletonObjectsMap = new HashMap<>();
    }

    @Override
    public Object supply(Class<?> argumentType, InstanceConfig instanceConfig) throws NotFoundException {

        if (instanceConfig == InstanceConfig.SINGLETON) {
            return supplyByInterfaceSingleton(argumentType);
        } else {
            return supplyByInterfaceNotSingleton(argumentType);
        }
    }

    @Override
    public Object supply(Class<?> argumentType) throws NotFoundException {

        return supplyByInterfaceNotSingleton(argumentType);
    }

    private Object supplyByInterfaceNotSingleton(Class<?> interfaceType) throws NotFoundException {

        ResolvePackich resolvePackich = findSuitableResolvePackich(interfaceType);
        if(resolvePackich==null){
            throw new NotFoundException("there are only singleton registrated instances");
        }
        return createNewInstance(resolvePackich);
    }

    private Object createNewInstance(ResolvePackich resolvePackich) throws NotFoundException {

        Object supplyingObject = null;
        Constructor<?> instanceConstructor = findSuitableConstructor(resolvePackich.getRegistratedClass());
        resolvePackich.getConstructorInstances().clear();
        for (Parameter parameter : instanceConstructor.getParameters()) {

            if(resolvePackich.getInstanceConfig()==InstanceConfig.SINGLETON) {
                resolvePackich.addConstructorInstance(supply(parameter.getType(),InstanceConfig.SINGLETON));
            }else {
                resolvePackich.addConstructorInstance(supply(parameter.getType()));
            }
        }

        try {
            supplyingObject = instanceConstructor.newInstance(resolvePackich.getConstructorInstances().toArray());
            System.out.println(resolvePackich.getRegistratedClass().getName() + " was successfully created");
        } catch (java.lang.InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return supplyingObject;
    }

    private Object supplyByInterfaceSingleton(Class<?> interfaceType) throws NotFoundException {

        Object supplyingObject;
        ResolvePackich resolvePackich = findSuitableResolvePackichSingleton(interfaceType);

        if (resolvePackich == null) {
            throw new NotFoundException("there is no suitable singleton packich");
        }
        if (singletonObjectsMap.containsKey(interfaceType)) {
            return singletonObjectsMap.get(interfaceType);
        }

        supplyingObject = createNewInstance(resolvePackich);
        singletonObjectsMap.put(interfaceType, supplyingObject);
        return supplyingObject;
    }



    /*private Object supplyByClass(Class<?> classType) {

        Object supplyingObject = new Object();
        ResolvePackich resolvePackich = findSuitableResolvePackichForClassType(classType);

        Class<?> registratedClass = resolvePackich.getRegistratedClass();
        Constructor<?> instanceConstructor = findSuitableConstructor(registratedClass);


        for (Parameter parameter : instanceConstructor.getParameters()) {
            resolvePackich.addConstructorInstances(supply(parameter.getType()));
        }
        try {
            supplyingObject = instanceConstructor.newInstance(resolvePackich.getConstructorInstances());
        } catch (java.lang.InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return supplyingObject;

        return null;
    }*/

    private ResolvePackich findSuitableResolvePackichForClassType(Class<?> classType) {

        return null;
    }

    private Constructor<?> findSuitableConstructor(Class<?> classType) {
        Constructor<?>[] constructors = classType.getDeclaredConstructors();

        int countOfParams = 0;
        int index = 0;
        for (int i = 0; i < constructors.length; i++) {
            if (constructors[i].getParameterCount() > countOfParams) {
                countOfParams = constructors[i].getParameterCount();
                index = i;
            }
        }
        constructors[index].setAccessible(true);

        return constructors[index];
    }

    private ResolvePackich findSuitableResolvePackich(Class<?> argumentType) throws NotFoundException {
        if (!resolveMap.containsKey(argumentType)) {
            System.out.println(argumentType);
            throw new NotFoundException("there is no suitable interface");
        }

        ArrayList<ResolvePackich> resolvePackiches = resolveMap.get(argumentType);
        for (ResolvePackich resolvePackich : resolvePackiches) {
            if (resolvePackich.getInstanceConfig() != InstanceConfig.SINGLETON) {
                System.out.println("resolvePackich: " + resolvePackich.getInstanceConfig() + " " +resolvePackich.getRegistratedClass().getName());
                return resolvePackich;
            }
        }
        return null;
    }

    private ResolvePackich findSuitableResolvePackichSingleton(Class<?> argumentType) throws NotFoundException {
        if (!resolveMap.containsKey(argumentType)) {
            throw new NotFoundException("there is no suitable interface");
        }

        ArrayList<ResolvePackich> resolvePackiches = resolveMap.get(argumentType);

        for (ResolvePackich resolvePackich : resolvePackiches) {
            if (resolvePackich.getInstanceConfig() == InstanceConfig.SINGLETON) {
                return resolvePackich;
            }
        }
        return null;
    }

    @Override
    public void print() {
        resolveMap.forEach(this::print2);
    }

    private void print2(Class<?> interfaceType, ArrayList<ResolvePackich> interfaceResolveContents) {
        System.out.print("key: " + interfaceType + " | ");
        interfaceResolveContents.forEach((v) -> System.out.printf("value: {%s, %s, %s, %s, %s} \n", v.getInterfaceType(), v.getRegistratedClass(), v.getImplementationConfig(), v.getInstanceConfig(), v.getDepthConfig()));
    }

    public Map<Class<?>, Object> getSingletonObjectsMap() {
        return singletonObjectsMap;
    }
}
