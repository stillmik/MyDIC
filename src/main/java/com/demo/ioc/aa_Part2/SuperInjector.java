package com.demo.ioc.aa_Part2;

import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;
import com.demo.ioc.aa_Part2.testClasses.*;
import com.demo.ioc.tests.OtherService;
import com.demo.ioc.tests.OtherServiceContract;

public class SuperInjector {

    public static void main(String[] args) {
        DependencyRegistrator registrator = new DependencyRegistratorImpl();

        try {
            registrator.register(OtherServiceContract.class, OtherService.class);
            registrator.register(LetterInterface.class, A.class);
            registrator.register(LetterInterface.class, B.class);
            registrator.register(LetterInterface.class, C.class);

            registrator.register(BBinterface.class, BB.class);
            registrator.register(AAinterface.class, AA.class);

            registrator.register(AAinterface.class, B.class);
            registrator.register(BB.class, C.class);

            registrator.register(ClassInterface.class, FirstClass.class, SecondClass.class, ThirdClass.class);

        } catch (ClassRegistrationException e) {
            e.printStackTrace();
        }

        registrator.print();
    }
}
