package com.demo.ioc.aa_Part2.testClasses;

public class SimpleDependencyClass implements SimpleDependencyInterface {

    private DependencyInterface dependencyInterface;


    public SimpleDependencyClass(DependencyInterface dependencyInterface) {
        this.dependencyInterface = dependencyInterface;
    }

    public DependencyInterface getDependencyInterface() {
        return dependencyInterface;
    }
}
