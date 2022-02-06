package com.demo.ioc.tests;

import com.demo.ioc.annotations.PostConstruct;
import com.demo.ioc.annotations.Service;

@Service
public class TestServiceOne {

    //private final OtherService otherService;

    public TestServiceOne() {
        //this.otherService = otherService;
    }

    @PostConstruct
    public void onInit(){
        System.out.println("Creating ServiceOne");
    }


}
