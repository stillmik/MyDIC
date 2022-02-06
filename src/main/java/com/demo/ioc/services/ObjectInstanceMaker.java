package com.demo.ioc.services;

import com.demo.ioc.exceptions.BeanInstantiationException;
import com.demo.ioc.exceptions.PreDestroyExecutionException;
import com.demo.ioc.exceptions.ServiceInstantiationException;
import com.demo.ioc.models.ServiceBeanInf;
import com.demo.ioc.models.ServiceInf;

public interface ObjectInstanceMaker {

    void createInstance(ServiceInf<?> serviceDetails, Object... constructorParams) throws ServiceInstantiationException;

    void createBeanInstance(ServiceBeanInf<?> serviceBeanDetails) throws BeanInstantiationException;

    void destroyInstance(ServiceInf<?> serviceDetails) throws PreDestroyExecutionException;
}
