package com.demo.ioc.config.configurations;

import com.demo.ioc.config.BaseSubConfig;
import com.demo.ioc.config.MagicConfig;
import com.demo.ioc.constants.Constants;

public class InstantiationConfiguration extends BaseSubConfig {

    private int MAX_ALLOWED_ITERATIONS;

    protected InstantiationConfiguration(MagicConfig parentConfig) {
        super(parentConfig);
        this.MAX_ALLOWED_ITERATIONS = Constants.MAX_NUMBER_OF_INSTANTIATION_CONFIGURATIONS;
    }

    public InstantiationConfiguration setMaximumNumberOfAllowedIterations(int num){
        this.MAX_ALLOWED_ITERATIONS = num;
        return this;
    }

    public int getMaxAllowedIterations(){
        return this.MAX_ALLOWED_ITERATIONS;
    }
}
