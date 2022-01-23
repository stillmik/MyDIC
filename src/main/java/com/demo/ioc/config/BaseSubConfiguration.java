package com.demo.ioc.config;

public abstract class BaseSubConfiguration {

    private final MagicConfig parentConfig;

    protected BaseSubConfiguration(MagicConfig parentConfig){
        this.parentConfig = parentConfig;
    }

    public MagicConfig and(){
        return this.parentConfig;
    }
}
