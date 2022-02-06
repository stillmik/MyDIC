package com.demo.ioc.config;

import com.demo.ioc.config.configurations.CustomAnnotationsConfig;
import com.demo.ioc.config.configurations.InstantiationConfiguration;

public class MagicConfig {

    private final CustomAnnotationsConfig annotations;
    private final InstantiationConfiguration instantiationConfiguration;//instantiation - создание экземпляра

    public MagicConfig() {
        annotations = new CustomAnnotationsConfig(this);
        instantiationConfiguration = new InstantiationConfiguration(this);
    }

    public CustomAnnotationsConfig annotations(){
        return this.annotations;
    }

    public InstantiationConfiguration getInstantiations(){
        return instantiationConfiguration;
    }

    public MagicConfig build(){
        return this;
    }
}
