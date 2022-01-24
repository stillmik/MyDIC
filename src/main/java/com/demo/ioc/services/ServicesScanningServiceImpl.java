package com.demo.ioc.services;

import com.demo.ioc.annotations.*;
import com.demo.ioc.config.configurations.CustomAnnotationsConfig;
import com.demo.ioc.models.ServiceDetails;
import com.demo.ioc.utils.ServiceDetailsConstructComparator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicesScanningServiceImpl implements ServicesScanningService {

    private final CustomAnnotationsConfig config;

    public ServicesScanningServiceImpl(CustomAnnotationsConfig config) {
        this.config = config;
        init();
    }

    @Override
    public Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses) {

        final Set<ServiceDetails<?>> serviceDetailsStorage = new HashSet<>();
        final Set<Class<? extends Annotation>> customServiceAnnotations = config.getCustomServiceAnnotations();

        for (Class<?> cls : locatedClasses) {
            if (cls.isInterface()) {
                continue;
            }

            for (Annotation annotation : cls.getAnnotations()) {
                if (customServiceAnnotations.contains(annotation)) {
                    ServiceDetails serviceDetails = new ServiceDetails(
                            cls,
                            annotation,
                            findSuitableConstructor(cls),
                            findVoidMethodWithZeroParamsAndAnnotations(PostConstruct.class, cls),
                            findVoidMethodWithZeroParamsAndAnnotations(PreDestroy.class, cls),
                            findBeans(cls)
                    );
                }
            }
        }
        return serviceDetailsStorage.stream().sorted(new ServiceDetailsConstructComparator()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Constructor<?> findSuitableConstructor(Class<?> cls) {

        for (Constructor<?> declaredConstructor : cls.getDeclaredConstructors()) {
            //Returns true if an annotation for the specified type is present on this element, else false.
            if (declaredConstructor.isAnnotationPresent(Autowired.class)) {
                declaredConstructor.setAccessible(true);
                return declaredConstructor;
            }
        }

        return cls.getDeclaredConstructors()[0];
    }

    private Method findVoidMethodWithZeroParamsAndAnnotations(Class<? extends Annotation> annotation, Class<?> cls) {
        for (Method declaredMethod : cls.getDeclaredMethods()) {
            if (declaredMethod.getParameterCount() != 0 || (declaredMethod.getReturnType() != void.class && declaredMethod.getReturnType() != Void.class) || !declaredMethod.isAnnotationPresent(annotation)) {
                continue;
            }

            declaredMethod.setAccessible(true);
            return declaredMethod;
        }
        return null;
    }

    private Method[] findBeans(Class<?> cls) {
        Set<Class<? extends Annotation>> customBeanAnnotations = config.getCustomBeanAnnotations();
        Set<Method> beanMethods = new HashSet<>();

        for (Method method : cls.getDeclaredMethods()) {
            if(method.getParameterCount()!=0 || method.getReturnType() ==void.class || method.getReturnType()==Void.class){
                continue;
            }

            for(Class<? extends Annotation> beanAnnotation: customBeanAnnotations){
                if(method.isAnnotationPresent(beanAnnotation)){
                    beanMethods.add(method);
                }
            }
        }

        return beanMethods.toArray(Method[]::new); //An array constructor reference (TypeName[]::new)
    }

    private void init() {
        config.getCustomBeanAnnotations().add(Bean.class);
        config.getCustomServiceAnnotations().add(Service.class);
    }
}
