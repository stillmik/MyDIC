package com.demo.ioc.models;

public class EnqueuedServiceDetails {
    private final ServiceDetails<?> serviceDetails;
    private final Class<?>[] dependencies;
    private final Object[] dependencyInstances;

    public EnqueuedServiceDetails(ServiceDetails<?> serviceDetails){
        this.serviceDetails = serviceDetails;
        this.dependencies = serviceDetails.getTargetConstructor().getParameterTypes();
        this.dependencyInstances = new Object[this.dependencies.length];
    }

    public boolean isResolved(){
        for(Object dependencyInstance: dependencyInstances){
            if(dependencyInstance==null)
                return false;
        }
        return true;
    }

    public boolean isDependencyRequired(Class<?> dependencyType){

        for(Class<?> dependency :dependencies){
            if(dependency.isAssignableFrom(dependencyType)){//same types
                return true;
            }
        }
        return false;
    }

    public void addDependencyInstance(Object instance){

        for (int i=0;i<dependencies.length;i++){
            if(dependencies[i].isAssignableFrom(instance.getClass())){
                dependencyInstances[i] =instance;
                return;
            }
        }
    }

    public ServiceDetails<?> getServiceDetails() {
        return serviceDetails;
    }

    public Class<?>[] getDependencies() {
        return dependencies;
    }

    public Object[] getDependencyInstances() {
        return dependencyInstances;
    }
}
