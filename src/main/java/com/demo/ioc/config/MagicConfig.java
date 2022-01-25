package com.demo.ioc.config;

import com.demo.ioc.config.configurations.CustomAnnotationsConfig;
import com.demo.ioc.config.configurations.InstantiationConfiguration;

public class MagicConfig {

    private final CustomAnnotationsConfig annotations;

    private final InstantiationConfiguration instantiations;//instantiation - создание экземпляра

    public MagicConfig() {
        annotations = new CustomAnnotationsConfig(this);
        instantiations = new InstantiationConfiguration(this);
    }

    public CustomAnnotationsConfig annotations(){
        return this.annotations;
    }

    public InstantiationConfiguration getInstantiations(){
        return instantiations;
    }

    public MagicConfig build(){
        return this;
    }
}
