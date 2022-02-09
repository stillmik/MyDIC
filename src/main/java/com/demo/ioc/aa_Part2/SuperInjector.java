package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;
import com.demo.ioc.aa_Part2.Exceptions.InstantiationException;
import com.demo.ioc.aa_Part2.Exceptions.NotFoundException;
import com.demo.ioc.aa_Part2.testClasses.*;

public class SuperInjector {

    public static void main(String[] args) throws NotFoundException, InstantiationException {

        DependencyRegistrator registrator = new DependencyRegistratorImpl();

        register(registrator);

        Supplier supplier = new DependencySupplier(registrator.getResolveMap());
        supplier.print();
        System.out.println("\n\n\n");

        FirstClass supplyByInterface1 = (FirstClass) supplier.supply(ClassInterface.class,InstanceConfig.SINGLETON);
        System.out.println(supplyByInterface1);
        System.out.println(supplyByInterface1.getLetterInterface());

        FirstClass supplyByInterface2 = (FirstClass) supplier.supply(ClassInterface.class,InstanceConfig.SINGLETON);
        System.out.println(supplyByInterface2);
        System.out.println(supplyByInterface2.getLetterInterface());

        FirstClass supplyByInterface3 = (FirstClass) supplier.supply(ClassInterface.class,InstanceConfig.SINGLETON);
        System.out.println(supplyByInterface3);
        System.out.println(supplyByInterface3.getLetterInterface());

    }

    private static void register(DependencyRegistrator registrator) {
        try {
            registrator.register(InstanceConfig.SINGLETON,ClassInterface.class, FirstClass.class);
            /*registrator.register(AAinterface.class,AA.class);
            registrator.register(BBinterface.class,BB.class);
            registrator.register(SuperInterface.class,SuperClass.class);*/
            registrator.register(LetterInterface.class, C.class);
            registrator.register(InstanceConfig.SINGLETON, LetterInterface.class, B.class);
            /*registrator.register(LetterInterface.class,A.class);
            registrator.register(LetterInterface.class,C.class);*/
            //registrator.register(DepthConfig.T2, ImplementationConfig.SECOND, OtherServiceContract.class, OtherService.class);
            //registrator.register(LetterInterface.class, A.class);
            //registrator.register(LetterInterface.class, B.class);
            //registrator.register(LetterInterface.class, C.class);
            //registrator.register(DepthConfig.T2, LetterInterface.class, A.class);
            //registrator.register(DepthConfig.T3, ImplementationConfig.SECOND, LetterInterface.class, B.class);
            //registrator.register(LetterInterface.class, C.class);

            //registrator.register(BBinterface.class, BB.class);
            //registrator.register(AAinterface.class, AA.class);

            //registrator.register(A.class, A.class);

            //registrator.register(AAinterface.class, B.class);
            //registrator.register(BB.class, C.class);

            //registrator.register(DepthConfig.T3, ImplementationConfig.SECOND, InstanceConfig.SINGLETON, ClassInterface.class, FirstClass.class, SecondClass.class, ThirdClass.class);

        } catch (ClassRegistrationException e) {
            e.printStackTrace();
        }


    }
}
