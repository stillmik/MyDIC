package com.demo.ioc;

import com.demo.ioc.config.MagicConfig;

public class MagicInjector {
    public static void main(String[] args) {

    }

    public static void run(Class<?> startupClass){
        run(startupClass,new MagicConfig());
    }

    public static void run(Class<?> startupClass, MagicConfig magicConfig){

    }
}
