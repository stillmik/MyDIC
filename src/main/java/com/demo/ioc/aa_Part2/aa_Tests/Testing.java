
package com.demo.ioc.aa_Part2.aa_Tests;

import com.demo.ioc.aa_Part2.*;
import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;
import com.demo.ioc.aa_Part2.testClasses.*;
import com.demo.ioc.tests.OtherService;
import com.demo.ioc.tests.OtherServiceContract;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Testing {

    private DependencyRegistrator registrator;

    @BeforeTest
    private void beforeAll() {
        registrator = new DependencyRegistratorImpl();
    }

    /*@Test()
    private void sucksesfullRegistration() {

        DependencyRegistrator registrator = new DependencyRegistratorImpl();

        try {
            registrator.register(ClassInterface.class,FirstClass.class);
            registrator.register(AAinterface.class,AA.class);
            registrator.register(BBinterface.class,BB.class);
            registrator.register(SuperInterface.class,SuperClass.class);
            registrator.register(LetterInterface.class,A.class);
        } catch (ClassRegistrationException e) {
            e.printStackTrace();
        }
        Supplier supplier = new DependencySupplier(registrator.getResolveMap());
        supplier.print();
        System.out.println("\n\n\n");

        FirstClass supplyByInterface = (FirstClass)supplier.supply(ClassInterface.class);

        System.out.println(supplyByInterface.getaAinterface());
        System.out.println(supplyByInterface.getbBinterface());
        System.out.println();
    }*/

}

