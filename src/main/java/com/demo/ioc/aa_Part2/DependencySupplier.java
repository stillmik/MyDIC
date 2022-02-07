package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.*;
import com.demo.ioc.aa_Part2.Exceptions.InstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class DependencySupplier implements Supplier {

    private final Map<Class<?>, ArrayList<InterfaceResolvePackich>> interfaceResolveMap;

    public DependencySupplier(Map<Class<?>, ArrayList<InterfaceResolvePackich>> interfaceResolveMap) {
        this.interfaceResolveMap = interfaceResolveMap;
    }

    @Override
    public Object supply(Class<?> interfaceType) throws InterfaceNotFoundException, InstantiationException {
        Object supplyingObject;

        ArrayList<InterfaceResolvePackich> suitableInterfaceResolvePackich;
        suitableInterfaceResolvePackich = checkForSuitableInterface(interfaceType);

        supplyingObject = createInstance(suitableInterfaceResolvePackich);

        return supplyingObject;
    }

    @Override
    public void print() {
        interfaceResolveMap.forEach(this::print2);
    }

    private void print2(Class<?> interfaceType, ArrayList<InterfaceResolvePackich> interfaceResolveContents) {
        System.out.print("key: " + interfaceType + " | ");
        interfaceResolveContents.forEach((v) -> System.out.printf("value: {%s, %s, %s, %s, %s} \n", v.getInterfaceType(), v.getRegistratedClasses(), v.getImplementationConfig(), v.getInstanceConfig(), v.getDepthConfig()));
    }

    private Object createInstance(ArrayList<InterfaceResolvePackich> suitableInterfaceResolvePackich, Object... constructorParams) throws InstantiationException {
        Object supplyingObject;

        InterfaceResolvePackich interfaceResolvePackich = suitableInterfaceResolvePackich.get(0);

        Class<?> aClass = interfaceResolvePackich.getRegistratedClasses().get(0);

        Constructor<?> targetConstructor = findSuitableConstructor(aClass);

        try {
            supplyingObject = targetConstructor.newInstance(constructorParams);
        } catch (IllegalAccessException | InvocationTargetException | java.lang.InstantiationException e) {
            throw new InstantiationException(e.getMessage(),e);
        }


        return supplyingObject;
    }

    /**
     * ERROR in "if" statement
     **/
    private Constructor<?> findSuitableConstructor(Class<?> classType, Object... constructorParams) throws InstantiationException {

        Constructor<?> targetConstructor = classType.getDeclaredConstructors()[0];
        for (Constructor<?> declaredConstructor : classType.getDeclaredConstructors()) {
            declaredConstructor.setAccessible(true);
            targetConstructor = declaredConstructor;
            break;
        }

        if (constructorParams.length != targetConstructor.getParameterCount())
            System.out.print("");
            //throw new InstantiationException("Invalid constructor parameters count: " + classType.getName());

        return targetConstructor;
    }

    private ArrayList<InterfaceResolvePackich> checkForSuitableInterface(Class<?> interfaceOrClassType) throws InterfaceNotFoundException {

        if (interfaceResolveMap.containsKey(interfaceOrClassType)) {
            return interfaceResolveMap.get(interfaceOrClassType);
        } else {
            Collection<ArrayList<InterfaceResolvePackich>> values = interfaceResolveMap.values();

            for (ArrayList<InterfaceResolvePackich> value : values) {
                for (InterfaceResolvePackich interfaceResolvePackich : value) {
                    for (Class<?> registratedClass : interfaceResolvePackich.getRegistratedClasses()) {
                        if (registratedClass.isAssignableFrom(interfaceOrClassType))
                            return registratedClass
                    }
                }
            }
            throw new InterfaceNotFoundException("interface or class " + interfaceOrClassType.getName() + " not found");
        }
    }
}
