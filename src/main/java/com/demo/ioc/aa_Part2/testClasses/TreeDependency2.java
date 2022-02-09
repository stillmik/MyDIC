package com.demo.ioc.aa_Part2.testClasses;

public class TreeDependency2 implements TreeDependencyInterface {

    NoDependencyInterface noDependencyInterface;
    SimpleDependencyInterface simpleDependencyInterface;

    public TreeDependency2(SimpleDependencyInterface simpleDependencyInterface, NoDependencyInterface noDependencyInterface) {
        this.simpleDependencyInterface = simpleDependencyInterface;
        this.noDependencyInterface = noDependencyInterface;
    }
}