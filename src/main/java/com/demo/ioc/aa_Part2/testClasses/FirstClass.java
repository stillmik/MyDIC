package com.demo.ioc.aa_Part2.testClasses;

public class FirstClass implements ClassInterface {

    //private AAinterface aAinterface;
    //private BBinterface bBinterface;
    private LetterInterface letterInterface;


    public FirstClass(LetterInterface letterInterface) {
        this.letterInterface = letterInterface;
    }

    public LetterInterface getLetterInterface() {
        return letterInterface;
    }
}
