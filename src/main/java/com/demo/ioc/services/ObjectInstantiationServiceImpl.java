package com.demo.ioc.services;

import com.demo.ioc.exceptions.BeanInstantiationException;
import com.demo.ioc.exceptions.PostConstructException;
import com.demo.ioc.exceptions.PreDestroyExecutionException;
import com.demo.ioc.exceptions.ServiceInstantiationException;
import com.demo.ioc.models.ServiceBeanDetails;
import com.demo.ioc.models.ServiceDetails;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectInstantiationServiceImpl implements ObjectInstantiationService {
    private static final String INVALID_PARAMETERS_COUNT = "invalid parameters count";

    @Override
    public void createInstance(ServiceDetails<?> serviceDetails, Object... constructorParams) throws ServiceInstantiationException {
        Constructor<?> targetConstructor = serviceDetails.getTargetConstructor();

        if(constructorParams.length!= targetConstructor.getParameterCount()){
            throw new ServiceInstantiationException(INVALID_PARAMETERS_COUNT);
        }

        try {
            Object instance = targetConstructor.newInstance(constructorParams);
            serviceDetails.setInstance(instance);
            invokePostConstruct(serviceDetails);

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ServiceInstantiationException(e.getMessage(),e);
        }
    }

    private void invokePostConstruct(ServiceDetails<?> serviceDetails) throws PostConstructException{
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
    public void createBeanInstance(ServiceBeanDetails<?> serviceBeanDetails) throws BeanInstantiationException {
        Method originMethod = serviceBeanDetails.getOriginMethod();
        Object rootInstance = serviceBeanDetails.getRootService().getInstance();

        try {
            Object instance= originMethod.invoke(rootInstance);
            serviceBeanDetails.setInstance(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanInstantiationException(e.getMessage(),e);
        }
    }

    @Override
    public void destroyInstance(ServiceDetails<?> serviceDetails) throws PreDestroyExecutionException {
        if(serviceDetails.getPreDestroyMethod()!=null){
            try {
                serviceDetails.getPreDestroyMethod().invoke(serviceDetails.getInstance());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new PreDestroyExecutionException(e.getMessage(),e);
            }
        }
        serviceDetails.setInstance(null);
    }
}
