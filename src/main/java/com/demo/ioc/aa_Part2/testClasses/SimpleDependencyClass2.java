package com.demo.ioc.aa_Part2.testClasses;

public class SimpleDependencyClass2 implements SimpleDependencyInterface {
    private DependencyInterface letterInterface;


    public SimpleDependencyClass2(DependencyInterface letterInterface) {
        this.letterInterface = letterInterface;
    }

    public DependencyInterface getLetterInterface() {
        return letterInterface;
    }
}
