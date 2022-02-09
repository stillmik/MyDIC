package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;
import com.demo.ioc.aa_Part2.Exceptions.NotFoundException;
import com.demo.ioc.aa_Part2.testClasses.CycleRegistrator;
import com.demo.ioc.aa_Part2.tree.TreeNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

public class DependencySupplier implements Supplier {

    private final Map<Class<?>, ArrayList<ResolvePackich>> resolveMap;
    private final Map<Class<?>, Object> singletonObjectsMap;
    private CycleRegistrator cycleRegistrator = new CycleRegistrator(CycleRegistrator.class,3);


    @Override
    public void registerCycle(Class<?> cycleClass, int maxInstances) {
        cycleRegistrator = new CycleRegistrator(cycleClass, maxInstances);
    }

    public DependencySupplier(Map<Class<?>, ArrayList<ResolvePackich>> resolveMap) {
        this.resolveMap = resolveMap;
        singletonObjectsMap = new HashMap<>();
    }

    @Override
    public Object[] supplyAll(Class<?> argumentType) throws NotFoundException, ClassRegistrationException {
        return supplyAllByInterface(argumentType);
    }

    @Override
    public Object supply(Class<?> argumentType, InstanceConfig instanceConfig) throws NotFoundException, ClassRegistrationException {
        if (instanceConfig == InstanceConfig.SINGLETON) {
            return supplyByInterfaceSingleton(argumentType);
        } else {
            return supplyByInterfaceNotSingleton(argumentType, ImplementationConfig.ANY);
        }
    }

    @Override
    public Object supply(Class<?> argumentType, ImplementationConfig implementationConfig) throws NotFoundException, ClassRegistrationException {
        return supplyByInterfaceNotSingleton(argumentType, implementationConfig);
    }

    @Override
    public Object supply(Class<?> argumentType, InstanceConfig instanceConfig, ImplementationConfig implementationConfig) throws NotFoundException, ClassRegistrationException {
        if (instanceConfig == InstanceConfig.SINGLETON) {
            if (implementationConfig == ImplementationConfig.FIRST || implementationConfig == ImplementationConfig.SECOND) {
                throw new NotFoundException("there is no first or second instance of singleton");
            }
            return supplyByInterfaceSingleton(argumentType);
        } else {
            return supplyByInterfaceNotSingleton(argumentType, ImplementationConfig.ANY);
        }
    }

    @Override
    public Object supply(Class<?> argumentType) throws NotFoundException, ClassRegistrationException {
        return supplyByInterfaceNotSingleton(argumentType, ImplementationConfig.ANY);
    }


    private Object supplyByInterfaceNotSingleton(Class<?> interfaceType, ImplementationConfig implementationConfig) throws NotFoundException, ClassRegistrationException {

        ResolvePackich resolvePackich = findSuitableResolvePackich(interfaceType, InstanceConfig.INSTANCE, implementationConfig);
        if (resolvePackich == null) {
            throw new NotFoundException("there is on suitable instance packich");
        }
        return createNewInstance(resolvePackich);
    }

    private Object[] supplyAllByInterface(Class<?> interfaceType) throws NotFoundException, ClassRegistrationException {

        ArrayList<ResolvePackich> resolvePackiches = findAllSuitableResolvePackiches(interfaceType);
        return createAllNewInstances(resolvePackiches);
    }

    private Object[] createAllNewInstances(ArrayList<ResolvePackich> resolvePackiches) throws NotFoundException, ClassRegistrationException {

        ArrayList<Object> supplyingObjects = new ArrayList<>();
        Object supplyingObject = null;

        for (ResolvePackich resolvePackich : resolvePackiches) {

            Constructor<?> instanceConstructor = findSuitableConstructor(resolvePackich.getRegistratedClass());
            resolvePackich.getConstructorInstances().clear();
            for (Parameter parameter : instanceConstructor.getParameters()) {

                if (resolvePackich.getInstanceConfig() != InstanceConfig.SINGLETON) {
                    resolvePackich.addConstructorInstance(supply(parameter.getType()));
                }
            }
            try {
                supplyingObject = instanceConstructor.newInstance(resolvePackich.getConstructorInstances().toArray());
                System.out.println(resolvePackich.getRegistratedClass().getName() + " was successfully created");
            } catch (java.lang.InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            supplyingObjects.add(supplyingObject);
        }
        return supplyingObjects.toArray();
    }

    // TODO: ImplementationConfig parameter
    private Object supplyByInterfaceSingleton(Class<?> interfaceType) throws NotFoundException, ClassRegistrationException {

        Object supplyingObject;
        ResolvePackich resolvePackich = findSuitableResolvePackich(interfaceType, InstanceConfig.SINGLETON, ImplementationConfig.ANY);

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


    private Object createNewInstance(ResolvePackich resolvePackich) throws NotFoundException, ClassRegistrationException {

        if (cycleRegistrator.isReached()) {
            return null;
        }
        if (resolvePackich.getInterfaceType().getName().equals(cycleRegistrator.getCycleClass().getName())) {
            cycleRegistrator.incCurrentCycle();
            System.out.println("+++++++++++++++++++++");
        }

        Object supplyingObject = null;
        Constructor<?> instanceConstructor = findSuitableConstructor(resolvePackich.getRegistratedClass());
        resolvePackich.getConstructorInstances().clear();

        ArrayList<Object> constructorInstances = new ArrayList<>();
        for (Parameter parameter : instanceConstructor.getParameters()) {

            if (resolvePackich.getInstanceConfig() == InstanceConfig.SINGLETON) {

                Object supply = supply(parameter.getType(), InstanceConfig.SINGLETON);
                constructorInstances.add(supply);
                resolvePackich.addConstructorInstance(supply);
            } else {

                Object supply = supply(parameter.getType());
                constructorInstances.add(supply);
                resolvePackich.addConstructorInstance(supply);

            }
        }

        try {
            System.out.println(" res: " + resolvePackich.getRegistratedClass() + "  " + Arrays.toString(resolvePackich.getConstructorInstances().toArray()));
            supplyingObject = instanceConstructor.newInstance(constructorInstances.toArray());
            System.out.println(resolvePackich.getRegistratedClass().getName() + " was successfully created");
        } catch (java.lang.InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return supplyingObject;
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

    private ResolvePackich findSuitableResolvePackich(Class<?> argumentType, InstanceConfig instanceConfig, ImplementationConfig implementationConfig) throws NotFoundException, ClassRegistrationException {
        if (!resolveMap.containsKey(argumentType)) {
            throw new NotFoundException("there is no suitable interface");
        }

        ArrayList<ResolvePackich> resolvePackiches = resolveMap.get(argumentType);

        boolean isOnlyOne = false;
        for (ResolvePackich resolvePackich : resolvePackiches) {
            if (isOnlyOne && resolvePackich.getInstanceConfig() == InstanceConfig.SINGLETON) {
                throw new ClassRegistrationException("there are more than one registrated singleton class for this interface");
            }
            if (resolvePackich.getInstanceConfig() == InstanceConfig.SINGLETON) {
                isOnlyOne = true;
            }
        }

        for (ResolvePackich resolvePackich : resolvePackiches) {

            System.out.println("config: " + resolvePackich.getImplementationConfig() + " " + resolvePackich.getRegistratedClass());

            if (implementationConfig == ImplementationConfig.ANY) {
                if (resolvePackich.getImplementationConfig() == ImplementationConfig.FIRST || resolvePackich.getImplementationConfig() == ImplementationConfig.SECOND || resolvePackich.getImplementationConfig() == ImplementationConfig.ANY) {
                    if (resolvePackich.getInstanceConfig() == instanceConfig) {
                        return resolvePackich;
                    }
                }
            } else if (implementationConfig == ImplementationConfig.FIRST) {
                if (resolvePackich.getImplementationConfig() == implementationConfig) {
                    if (resolvePackich.getInstanceConfig() == instanceConfig) {
                        return resolvePackich;
                    }
                }
            } else if (implementationConfig == ImplementationConfig.SECOND) {
                if (resolvePackich.getImplementationConfig() == implementationConfig) {
                    if (resolvePackich.getInstanceConfig() == instanceConfig) {
                        return resolvePackich;
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<ResolvePackich> findAllSuitableResolvePackiches(Class<?> argumentType) throws NotFoundException {

        if (!resolveMap.containsKey(argumentType)) {
            throw new NotFoundException("there is no suitable interface");
        }

        return resolveMap.get(argumentType);
    }

    @Override
    public void print() {
        resolveMap.forEach(this::print2);
    }

    private void print2(Class<?> interfaceType, ArrayList<ResolvePackich> interfaceResolveContents) {
        System.out.print("key: " + interfaceType + " | ");
        interfaceResolveContents.forEach((v) -> System.out.printf("value: {%s, %s, %s, %s, %s} \n", v.getInterfaceType(), v.getRegistratedClass(), v.getImplementationConfig(), v.getInstanceConfig(), v.getDepthConfig()));
    }
}
