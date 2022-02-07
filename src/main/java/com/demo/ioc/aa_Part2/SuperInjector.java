package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;
import com.demo.ioc.aa_Part2.Exceptions.InstantiationException;
import com.demo.ioc.aa_Part2.Exceptions.InterfaceNotFoundException;
import com.demo.ioc.aa_Part2.testClasses.*;
import com.demo.ioc.tests.OtherService;
import com.demo.ioc.tests.OtherServiceContract;

public class SuperInjector {

    public static void main(String[] args) throws InterfaceNotFoundException, InstantiationException {
        DependencyRegistrator registrator = new DependencyRegistratorImpl();

        register(registrator);

        Supplier supplier = new DependencySupplier(registrator.getInterfaceResolvePackich());

        Object supply = supplier.supply(FirstClass.class);

        supplier.print();
    }

    private static void register(DependencyRegistrator registrator) {
        try {
            registrator.register(DepthConfig.T2, ImplementationConfig.SECOND, OtherServiceContract.class, OtherService.class);
            registrator.register(LetterInterface.class, A.class);
            registrator.register(LetterInterface.class, B.class);
            registrator.register(LetterInterface.class, C.class);

            registrator.register(BBinterface.class, BB.class);
            registrator.register(AAinterface.class, AA.class);

            //registrator.register(AAinterface.class, B.class);
            //registrator.register(BB.class, C.class);

            registrator.register(DepthConfig.T3,ImplementationConfig.SECOND,InstanceConfig.SINGLETON,ClassInterface.class, FirstClass.class, SecondClass.class, ThirdClass.class);

        } catch (ClassRegistrationException e) {
            e.printStackTrace();
        }


    }
}
