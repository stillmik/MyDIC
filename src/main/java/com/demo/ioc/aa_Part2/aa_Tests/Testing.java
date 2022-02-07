package com.demo.ioc.aa_Part2.aa_Tests;

import com.demo.ioc.aa_Part2.*;
import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;
import com.demo.ioc.aa_Part2.testClasses.*;
import com.demo.ioc.tests.OtherService;
import com.demo.ioc.tests.OtherServiceContract;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Testing {

    DependencyRegistrator registrator;

    @BeforeTest
    private void beforeAll() {
        registrator = new DependencyRegistratorImpl();
    }

    @Test()
    private void sucksesfullRegistration() {

        try {
            registrator.register(DepthConfig.T2, ImplementationConfig.SECOND, OtherServiceContract.class, OtherService.class);
            registrator.register(LetterInterface.class, A.class);
            registrator.register(LetterInterface.class, B.class);
            registrator.register(LetterInterface.class, C.class);

            registrator.register(BBinterface.class, BB.class);
            registrator.register(AAinterface.class, AA.class);

            //registrator.register(AAinterface.class, B.class);
            //registrator.register(BB.class, C.class);

            registrator.register(DepthConfig.T3,ImplementationConfig.SECOND, InstanceConfig.SINGLETON,ClassInterface.class, FirstClass.class, SecondClass.class, ThirdClass.class);
        } catch (ClassRegistrationException e) {
            e.printStackTrace();
        }

        Map<Class<?>, ArrayList<InterfaceResolvePackich>> interfaceResolveMap = registrator.getInterfaceResolvePackich();

        ArrayList<InterfaceResolvePackich> interfaceResolvePackichesOSC = interfaceResolveMap.get(OtherServiceContract.class);
        ArrayList<InterfaceResolvePackich> interfaceResolvePackichesLI = interfaceResolveMap.get(LetterInterface.class);
        ArrayList<InterfaceResolvePackich> interfaceResolvePackichesBBI = interfaceResolveMap.get(BBinterface.class);
        ArrayList<InterfaceResolvePackich> interfaceResolvePackichesAAI = interfaceResolveMap.get(AAinterface.class);

        for(int i=0;i<interfaceResolvePackichesOSC.size();i++){
            InterfaceResolvePackich interfaceResolvePackich = interfaceResolvePackichesOSC.get(i);
            List<Class<?>> registratedClasses = interfaceResolvePackich.getRegistratedClasses();
        }


    }

}
