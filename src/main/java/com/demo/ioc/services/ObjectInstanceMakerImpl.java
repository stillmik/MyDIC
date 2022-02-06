package com.demo.ioc.services;

import com.demo.ioc.exceptions.BeanInstantiationException;
import com.demo.ioc.exceptions.PostConstructException;
import com.demo.ioc.exceptions.PreDestroyExecutionException;
import com.demo.ioc.exceptions.ServiceInstantiationException;
import com.demo.ioc.models.ServiceBeanInf;
import com.demo.ioc.models.ServiceInf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectInstanceMakerImpl implements ObjectInstanceMaker {
    private static final String INVALID_PARAMETERS_COUNT = "invalid parameters count";

    @Override
    public void createInstance(ServiceInf<?> serviceInf, Object... constructorParams) throws ServiceInstantiationException {
        Constructor<?> targetConstructor = serviceInf.getTargetConstructor();

        if(constructorParams.length!= targetConstructor.getParameterCount()){
            throw new ServiceInstantiationException(INVALID_PARAMETERS_COUNT);
        }

        try {
            Object instance = targetConstructor.newInstance(constructorParams);
            serviceInf.setInstance(instance);
            invokePostConstruct(serviceInf);

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ServiceInstantiationException(e.getMessage(),e);
        }
    }

    private void invokePostConstruct(ServiceInf<?> serviceDetails) throws PostConstructException{
        if(serviceDetails.getPostConstructorMethod()==null){
            return;
        }

        try {
            serviceDetails.getPostConstructorMethod().invoke(serviceDetails.getInstance());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PostConstructException(e.getMessage(),e);
        }
    }

    @Override
    public void createBeanInstance(ServiceBeanInf<?> serviceBeanInf) throws BeanInstantiationException {
        Method originMethod = serviceBeanInf.getOriginMethod();
        Object rootInstance = serviceBeanInf.getRootService().getInstance();

        try {
            Object instance= originMethod.invoke(rootInstance);
            serviceBeanInf.setInstance(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanInstantiationException(e.getMessage(),e);
        }
    }

    @Override
    public void destroyInstance(ServiceInf<?> serviceInf) throws PreDestroyExecutionException {
        if(serviceInf.getPreDestroyMethod()!=null){
            try {
                serviceInf.getPreDestroyMethod().invoke(serviceInf.getInstance());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new PreDestroyExecutionException(e.getMessage(),e);
            }
        }
        serviceInf.setInstance(null);
    }
}
