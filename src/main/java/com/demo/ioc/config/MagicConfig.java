package com.demo.ioc.config;

import com.demo.ioc.config.configurations.CustomAnnotationsConfig;

public class MagicConfig {

    private final CustomAnnotationsConfig annotations;

    public MagicConfig() {
        annotations = new CustomAnnotationsConfig(this);
    }

    public CustomAnnotationsConfig annotations(){
        return this.annotations;
    }

    public MagicConfig build(){
        return this;
    }
}
