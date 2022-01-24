package com.demo.ioc.tests;

import com.demo.ioc.annotations.*;

@Service
public class TestServiceTwo {

    private final TestServiceOne testServiceOne;

    @Autowired
    public TestServiceTwo(TestServiceOne testServiceOne){
        this.testServiceOne = testServiceOne;
    }

    @PostConstruct
    private void onInit(){

    }

    @PreDestroy
    public void onDestroy(){

    }

    @Bean
    public OtherService otherService(){
        return new OtherService();
    }
}
