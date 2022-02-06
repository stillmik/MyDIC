package com.demo.ioc.models;

public class EnqueuedServiceInf {
    private final ServiceInf<?> serviceInf;
    private final Class<?>[] dependencies;
    private final Object[] dependencyInstances;

    public EnqueuedServiceInf(ServiceInf<?> serviceInf) {
        this.serviceInf = serviceInf;
        this.dependencies = serviceInf.getTargetConstructor().getParameterTypes();
        this.dependencyInstances = new Object[this.dependencies.length];
    }

    public boolean isResolved() {
        for (Object dependencyInstance : dependencyInstances) {
            if (dependencyInstance == null)
                return false;
        }
        return true;
    }

    public boolean isDependencyRequired(Class<?> dependencyType) {

        for (Class<?> dependency : dependencies) {
            if (dependency.isAssignableFrom(dependencyType)) {//same types
                return true;
            }
        }
        return false;
    }

    public void addDependencyInstance(Object instance) {

        for (int i = 0; i < dependencies.length; i++) {
            if (dependencies[i].isAssignableFrom(instance.getClass())) {
                dependencyInstances[i] = instance;
                return;
            }
        }
    }

    public ServiceInf<?> getServiceInf() {
        return serviceInf;
    }

    public Class<?>[] getDependencies() {
        return dependencies;
    }

    public Object[] getDependencyInstances() {
        return dependencyInstances;
    }
}
