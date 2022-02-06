package com.demo.ioc.tests;

import com.demo.ioc.annotations.*;
import org.w3c.dom.ls.LSOutput;

@Service
public class TestServiceTwo {

    private final TestServiceOne testServiceOne;

    //private final OtherService otherService;

    @Autowired
    public TestServiceTwo(TestServiceOne testServiceOne){
        this.testServiceOne = testServiceOne;
        //this.otherService=otherService;
    }

    @PostConstruct
    private void onInit(){
        System.out.println("creating service two");
    }

    @PreDestroy
    public void onDestroy(){

    }

    @Bean
    public OtherService otherService(){
        return new OtherService();
    }
}
