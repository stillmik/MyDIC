package com.demo.ioc.config;

import com.demo.ioc.config.configurations.CustomAnnotationsConfig;

public abstract class BaseSubConfig {

    private final MagicConfig parentConfig;

    protected BaseSubConfig(MagicConfig parentConfig){
        this.parentConfig = parentConfig;
    }

    public MagicConfig and(){
        return this.parentConfig;
    }


}
