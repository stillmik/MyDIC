package com.demo.ioc.aa_Part2.testClasses;

public class A implements LetterInterface{
    private SuperInterface superInterface;

    A(SuperInterface superInterface){
        this.superInterface = superInterface;
    }
}
