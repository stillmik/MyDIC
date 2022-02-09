
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

    @Test()
    private void sucksesfullRegistration() {

        System.out.println("ass");
    }

}

