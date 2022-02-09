package com.demo.ioc.aa_Part2.testClasses;

public class Jackson implements JacksonInterface {

    private ChildInterface child;
    private MichaelInerface michael;

    Jackson(ChildInterface child, MichaelInerface michael) {
        this.child = child;
        this.michael = michael;
    }
}
