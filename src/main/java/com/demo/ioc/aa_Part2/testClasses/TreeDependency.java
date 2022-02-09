package com.demo.ioc.aa_Part2.testClasses;

public class TreeDependency implements TreeDependencyInterface {

    NoDependencyInterface noDependencyInterface;
    SimpleDependencyInterface simpleDependencyInterface;

    public TreeDependency(SimpleDependencyInterface simpleDependencyInterface, NoDependencyInterface noDependencyInterface) {
        this.simpleDependencyInterface = simpleDependencyInterface;
        this.noDependencyInterface = noDependencyInterface;
    }
}
