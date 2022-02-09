package com.demo.ioc.aa_Part2.testClasses;

public class Michael implements MichaelInerface {

    private JacksonInterface jackson;

    Michael(JacksonInterface jackson){
        this.jackson = jackson;
    }


}
