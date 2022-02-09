import com.demo.ioc.aa_Part2.*;
import com.demo.ioc.aa_Part2.Exceptions.ClassRegistrationException;
import com.demo.ioc.aa_Part2.Exceptions.InstantiationException;
import com.demo.ioc.aa_Part2.Exceptions.NotFoundException;
import com.demo.ioc.aa_Part2.testClasses.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Tests {

    private int MAX_CYCLES_3 = 3;
    private int MAX_CYCLES_10 = 10;

    @BeforeTest
    public void beforeAll() {

    }

    @Test()
    public void zeroDependencies() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply = dependencySupplier.supply(NoDependencyInterface.class);
        Assert.assertTrue(supply instanceof NoDependencyClass);
    }

    @Test()
    public void zeroDependenciesAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply = dependencySupplier.supply(NoDependencyInterface.class);
        Assert.assertTrue(supply instanceof NoDependencyClass);
    }

    @Test()
    public void zeroDependenciesSingleton() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON);
        Assert.assertTrue(supply1 instanceof NoDependencyClass);
        Assert.assertTrue(supply2 instanceof NoDependencyClass);
        Assert.assertEquals(supply1, supply2);
    }

    @Test()
    public void zeroDependenciesSingletonAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, TrashInterface.class, TrashClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON);
        Assert.assertTrue(supply1 instanceof NoDependencyClass);
        Assert.assertTrue(supply2 instanceof NoDependencyClass);
        Assert.assertEquals(supply1, supply2);
    }

    @Test(expectedExceptions = {ClassRegistrationException.class})
    public void zeroDependenciesTwoSingletonesAndTrashException() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON);
        dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON);
        dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
        dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
    }

    @Test()
    public void zeroDependenciesTwoSingletonesAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, TrashInterface.class, TrashClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON);

        Object supply11 = dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply22 = dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
        Assert.assertTrue(supply1 instanceof NoDependencyClass);
        Assert.assertTrue(supply2 instanceof NoDependencyClass);
        Assert.assertEquals(supply1, supply2);

        Assert.assertTrue(supply11 instanceof DependencyA);
        Assert.assertTrue(supply22 instanceof DependencyA);
        Assert.assertEquals(supply11, supply22);
    }

    @Test()
    public void zeroDependenciesAndTrashGetAll() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass2.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);


        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object[] supply = dependencySupplier.supplyAll(NoDependencyInterface.class);

        Assert.assertEquals(supply.length, 2);
        Assert.assertTrue(supply[0] instanceof NoDependencyInterface);
        Assert.assertTrue(supply[1] instanceof NoDependencyInterface);
    }

    @Test()
    public void zeroDependenciesAndTrashImplementationConfig() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(ImplementationConfig.FIRST, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(ImplementationConfig.SECOND, NoDependencyInterface.class, NoDependencyClass2.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);


        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply = dependencySupplier.supply(NoDependencyInterface.class, ImplementationConfig.SECOND);
        Object supply2 = dependencySupplier.supply(NoDependencyInterface.class, ImplementationConfig.FIRST);

        Assert.assertTrue(supply instanceof NoDependencyInterface);
        Assert.assertTrue(supply instanceof NoDependencyClass2);

        Assert.assertTrue(supply2 instanceof NoDependencyInterface);
        Assert.assertTrue(supply2 instanceof NoDependencyClass);
    }

    @Test(expectedExceptions = {NotFoundException.class})
    public void zeroDependenciesAndTrashImplementationConfigAndSingleTone() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(ImplementationConfig.FIRST, InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(ImplementationConfig.SECOND, InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass2.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON, ImplementationConfig.SECOND);
        dependencySupplier.supply(NoDependencyInterface.class, InstanceConfig.SINGLETON, ImplementationConfig.FIRST);
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------------
     */

    @Test()
    public void SimpleDependency() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply = dependencySupplier.supply(SimpleDependencyInterface.class);

        Assert.assertTrue(supply instanceof SimpleDependencyClass);
        for (Field field : supply.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Assert.assertNotNull(field.get(supply));
            Assert.assertTrue(field.get(supply) instanceof DependencyA);
        }
    }

    @Test()
    public void SimpleDependencyAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(DependencyInterface.class, DependencyB.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply = dependencySupplier.supply(SimpleDependencyInterface.class);
        Assert.assertTrue(supply instanceof SimpleDependencyClass);
        for (Field field : supply.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Assert.assertNotNull(field.get(supply));
            Assert.assertTrue(field.get(supply) instanceof DependencyB);
        }
    }

    @Test()
    public void SimpleDependencySingleton() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON);
        Assert.assertTrue(supply1 instanceof SimpleDependencyClass);
        Assert.assertTrue(supply2 instanceof SimpleDependencyClass);
        Assert.assertEquals(supply1, supply2);
        for (Field field : supply1.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Assert.assertNotNull(field.get(supply1));
            Assert.assertTrue(field.get(supply1) instanceof DependencyA);
        }
        for (Field field : supply2.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Assert.assertNotNull(field.get(supply2));
            Assert.assertTrue(field.get(supply2) instanceof DependencyA);
        }
    }

    @Test()
    public void SimpleDependencySingletonAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, TrashInterface.class, TrashClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);
        dependencyRegistrator.register(TrashInterface.class, TrashClass.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON);
        Assert.assertTrue(supply1 instanceof SimpleDependencyClass);
        Assert.assertTrue(supply2 instanceof SimpleDependencyClass);
        Assert.assertEquals(supply1, supply2);
        for (Field field : supply1.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Assert.assertNotNull(field.get(supply1));
            Assert.assertTrue(field.get(supply1) instanceof DependencyA);
        }
        for (Field field : supply2.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Assert.assertNotNull(field.get(supply2));
            Assert.assertTrue(field.get(supply2) instanceof DependencyA);
        }
    }

    @Test(expectedExceptions = {ClassRegistrationException.class})
    public void simpleDependencyTwoSingletonesAndTrashException() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyB.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON);
        dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON);

        dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
        dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
    }

    @Test
    public void simpleDependencyTwoSingletonesAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, TrashInterface.class, TrashClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON);

        Object supply11 = dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply22 = dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
        Assert.assertTrue(supply1 instanceof SimpleDependencyClass);
        Assert.assertTrue(supply2 instanceof SimpleDependencyClass);
        Assert.assertEquals(supply1, supply2);

        Assert.assertTrue(supply11 instanceof DependencyA);
        Assert.assertTrue(supply22 instanceof DependencyA);
        Assert.assertEquals(supply11, supply22);
    }

    @Test()
    public void SimpleDependencyAndTrashGetAll() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass2.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass2.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);


        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object[] supply = dependencySupplier.supplyAll(SimpleDependencyInterface.class);

        Assert.assertEquals(supply.length, 2);
        Assert.assertTrue(supply[0] instanceof SimpleDependencyInterface);
        Assert.assertTrue(supply[1] instanceof SimpleDependencyInterface);
    }

    @Test()
    public void simpleDependencyAndTrashImplementationConfig() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(ImplementationConfig.FIRST, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(ImplementationConfig.SECOND, SimpleDependencyInterface.class, SimpleDependencyClass2.class);
        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);


        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(SimpleDependencyInterface.class, ImplementationConfig.SECOND);
        Object supply2 = dependencySupplier.supply(SimpleDependencyInterface.class, ImplementationConfig.FIRST);

        Assert.assertTrue(supply1 instanceof SimpleDependencyInterface);
        Assert.assertTrue(supply1 instanceof SimpleDependencyClass2);

        Assert.assertTrue(supply2 instanceof SimpleDependencyInterface);
        Assert.assertTrue(supply2 instanceof SimpleDependencyClass);

        for (Field field : supply1.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Assert.assertNotNull(field.get(supply1));
            Assert.assertTrue(field.get(supply1) instanceof DependencyA);
        }
        for (Field field : supply2.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Assert.assertNotNull(field.get(supply2));
            Assert.assertTrue(field.get(supply2) instanceof DependencyA);
        }
    }

    @Test(expectedExceptions = {NotFoundException.class})
    public void simpleDependencyAndTrashImplementationConfigAndSingleTone() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(ImplementationConfig.FIRST, InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(ImplementationConfig.SECOND, InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON, ImplementationConfig.SECOND);
        dependencySupplier.supply(SimpleDependencyInterface.class, InstanceConfig.SINGLETON, ImplementationConfig.FIRST);
    }


    /**
     * ---------------------------------------------------------------------------------------------------------------------
     */

    @Test()
    public void treeDependency() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);


        DependencySupplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply = dependencySupplier.supply(TreeDependencyInterface.class);

        Assert.assertTrue(supply instanceof TreeDependency);
        Field[] fields = supply.getClass().getDeclaredFields();
        fields[0].setAccessible(true);
        fields[1].setAccessible(true);
        Assert.assertNotNull(fields[0].get(supply));
        Assert.assertTrue(fields[0].get(supply) instanceof NoDependencyClass);
        Assert.assertNotNull(fields[1].get(supply));
        Assert.assertTrue(fields[1].get(supply) instanceof SimpleDependencyClass);

        Field[] innerFields = fields[1].get(supply).getClass().getDeclaredFields();

        innerFields[0].setAccessible(true);
        Assert.assertNotNull(innerFields[0].get(fields[1].get(supply)));
        Assert.assertTrue(innerFields[0].get(fields[1].get(supply)) instanceof DependencyA);
    }

    @Test()
    public void treeDependencyAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(TrashInterface.class,TrashClass.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);


        DependencySupplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply = dependencySupplier.supply(TreeDependencyInterface.class);

        Assert.assertTrue(supply instanceof TreeDependency);
        Field[] fields = supply.getClass().getDeclaredFields();
        fields[0].setAccessible(true);
        fields[1].setAccessible(true);
        Assert.assertNotNull(fields[0].get(supply));
        Assert.assertTrue(fields[0].get(supply) instanceof NoDependencyClass);
        Assert.assertNotNull(fields[1].get(supply));
        Assert.assertTrue(fields[1].get(supply) instanceof SimpleDependencyClass);

        Field[] innerFields = fields[1].get(supply).getClass().getDeclaredFields();

        innerFields[0].setAccessible(true);
        Assert.assertNotNull(innerFields[0].get(fields[1].get(supply)));
        Assert.assertTrue(innerFields[0].get(fields[1].get(supply)) instanceof DependencyA);
    }

    @Test()
    public void treeDependencySingleton() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);


        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON);

        Assert.assertTrue(supply1 instanceof TreeDependency);
        Assert.assertTrue(supply2 instanceof TreeDependency);
        Assert.assertSame(supply1, supply2);
        Field[] fields1 = supply1.getClass().getDeclaredFields();
        Field[] fields2 = supply2.getClass().getDeclaredFields();
        fields1[0].setAccessible(true);
        fields1[1].setAccessible(true);
        fields2[0].setAccessible(true);
        fields2[1].setAccessible(true);
        Assert.assertNotNull(fields1[0].get(supply1));
        Assert.assertNotNull(fields2[0].get(supply2));
        Assert.assertTrue(fields1[0].get(supply1) instanceof NoDependencyClass);
        Assert.assertTrue(fields2[0].get(supply2) instanceof NoDependencyClass);
        Assert.assertSame(fields1[0].get(supply2), fields2[0].get(supply2));
        Assert.assertNotNull(fields1[1].get(supply1));
        Assert.assertNotNull(fields2[1].get(supply2));
        Assert.assertTrue(fields1[1].get(supply1) instanceof SimpleDependencyClass);
        Assert.assertTrue(fields2[1].get(supply2) instanceof SimpleDependencyClass);
        Assert.assertSame(fields1[1].get(supply2), fields2[1].get(supply2));

        Field[] innerFields1 = fields1[1].get(supply1).getClass().getDeclaredFields();
        Field[] innerFields2 = fields2[1].get(supply2).getClass().getDeclaredFields();

        innerFields1[0].setAccessible(true);
        innerFields2[0].setAccessible(true);
        Assert.assertNotNull(innerFields2[0].get(fields2[1].get(supply2)));
        Assert.assertNotNull(innerFields1[0].get(fields1[1].get(supply1)));
        Assert.assertTrue(innerFields1[0].get(fields1[1].get(supply1)) instanceof DependencyA);
        Assert.assertTrue(innerFields2[0].get(fields2[1].get(supply2)) instanceof DependencyA);
        Assert.assertSame(innerFields1[0].get(fields1[1].get(supply1)), innerFields2[0].get(fields2[1].get(supply2)));
    }

    @Test()
    public void treeDependencySingletonAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, TrashInterface.class, TrashClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);


        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON);

        Assert.assertTrue(supply1 instanceof TreeDependency);
        Assert.assertTrue(supply2 instanceof TreeDependency);
        Assert.assertSame(supply1, supply2);
        Field[] fields1 = supply1.getClass().getDeclaredFields();
        Field[] fields2 = supply2.getClass().getDeclaredFields();
        fields1[0].setAccessible(true);
        fields1[1].setAccessible(true);
        fields2[0].setAccessible(true);
        fields2[1].setAccessible(true);
        Assert.assertNotNull(fields1[0].get(supply1));
        Assert.assertNotNull(fields2[0].get(supply2));
        Assert.assertTrue(fields1[0].get(supply1) instanceof NoDependencyClass);
        Assert.assertTrue(fields2[0].get(supply2) instanceof NoDependencyClass);
        Assert.assertSame(fields1[0].get(supply2), fields2[0].get(supply2));
        Assert.assertNotNull(fields1[1].get(supply1));
        Assert.assertNotNull(fields2[1].get(supply2));
        Assert.assertTrue(fields1[1].get(supply1) instanceof SimpleDependencyClass);
        Assert.assertTrue(fields2[1].get(supply2) instanceof SimpleDependencyClass);
        Assert.assertSame(fields1[1].get(supply2), fields2[1].get(supply2));

        Field[] innerFields1 = fields1[1].get(supply1).getClass().getDeclaredFields();
        Field[] innerFields2 = fields2[1].get(supply2).getClass().getDeclaredFields();

        innerFields1[0].setAccessible(true);
        innerFields2[0].setAccessible(true);
        Assert.assertNotNull(innerFields2[0].get(fields2[1].get(supply2)));
        Assert.assertNotNull(innerFields1[0].get(fields1[1].get(supply1)));
        Assert.assertTrue(innerFields1[0].get(fields1[1].get(supply1)) instanceof DependencyA);
        Assert.assertTrue(innerFields2[0].get(fields2[1].get(supply2)) instanceof DependencyA);
        Assert.assertSame(innerFields1[0].get(fields1[1].get(supply1)), innerFields2[0].get(fields2[1].get(supply2)));
    }

    @Test(expectedExceptions = {ClassRegistrationException.class})
    public void treeDependencyTwoSingletonesAndTrashException() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, TrashInterface.class, TrashClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyB.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON);
        dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON);
    }

    @Test
    public void treeDependencyTwoSingletonesAndTrash() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(InstanceConfig.SINGLETON, TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, TrashInterface.class, TrashClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(InstanceConfig.SINGLETON, DependencyInterface.class, DependencyA.class);


        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply1 = dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply2 = dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON);

        Object supply11 = dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);
        Object supply22 = dependencySupplier.supply(DependencyInterface.class, InstanceConfig.SINGLETON);

        Assert.assertTrue(supply1 instanceof TreeDependency);
        Assert.assertTrue(supply2 instanceof TreeDependency);
        Assert.assertSame(supply1, supply2);
        Field[] fields1 = supply1.getClass().getDeclaredFields();
        Field[] fields2 = supply2.getClass().getDeclaredFields();
        fields1[0].setAccessible(true);
        fields1[1].setAccessible(true);
        fields2[0].setAccessible(true);
        fields2[1].setAccessible(true);
        Assert.assertNotNull(fields1[0].get(supply1));
        Assert.assertNotNull(fields2[0].get(supply2));
        Assert.assertTrue(fields1[0].get(supply1) instanceof NoDependencyClass);
        Assert.assertTrue(fields2[0].get(supply2) instanceof NoDependencyClass);
        Assert.assertSame(fields1[0].get(supply2), fields2[0].get(supply2));
        Assert.assertNotNull(fields1[1].get(supply1));
        Assert.assertNotNull(fields2[1].get(supply2));
        Assert.assertTrue(fields1[1].get(supply1) instanceof SimpleDependencyClass);
        Assert.assertTrue(fields2[1].get(supply2) instanceof SimpleDependencyClass);
        Assert.assertSame(fields1[1].get(supply2), fields2[1].get(supply2));

        Field[] innerFields1 = fields1[1].get(supply1).getClass().getDeclaredFields();
        Field[] innerFields2 = fields2[1].get(supply2).getClass().getDeclaredFields();

        innerFields1[0].setAccessible(true);
        innerFields2[0].setAccessible(true);
        Assert.assertNotNull(innerFields2[0].get(fields2[1].get(supply2)));
        Assert.assertNotNull(innerFields1[0].get(fields1[1].get(supply1)));
        Assert.assertTrue(innerFields1[0].get(fields1[1].get(supply1)) instanceof DependencyA);
        Assert.assertTrue(innerFields2[0].get(fields2[1].get(supply2)) instanceof DependencyA);
        Assert.assertSame(innerFields1[0].get(fields1[1].get(supply1)), innerFields2[0].get(fields2[1].get(supply2)));


        Assert.assertTrue(supply11 instanceof DependencyA);
        Assert.assertTrue(supply22 instanceof DependencyA);
        Assert.assertSame(supply11, supply22);
    }

    @Test()
    public void treeDependencyAndTrashGetAll() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(TrashInterface.class, TrashClass.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);

        dependencyRegistrator.register(TreeDependencyInterface.class, TreeDependency2.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object[] supply = dependencySupplier.supplyAll(TreeDependencyInterface.class);

        Assert.assertEquals(supply.length, 2);
        Assert.assertTrue(supply[0] instanceof TreeDependency);
        Assert.assertTrue(supply[1] instanceof TreeDependency2);
    }

    @Test()
    public void treeDependencyAndTrashImplementationConfig() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(ImplementationConfig.FIRST, TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(ImplementationConfig.SECOND, TreeDependencyInterface.class, TreeDependency2.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);


        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        Object supply2 = dependencySupplier.supply(TreeDependencyInterface.class, ImplementationConfig.SECOND);
        Object supply1 = dependencySupplier.supply(TreeDependencyInterface.class, ImplementationConfig.FIRST);

        Assert.assertTrue(supply2 instanceof TreeDependency2);
        Assert.assertTrue(supply1 instanceof TreeDependency);
    }

    @Test(expectedExceptions = {NotFoundException.class})
    public void treeDependencyAndTrashImplementationConfigAndSingleTone() throws ClassRegistrationException, NotFoundException, InstantiationException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(ImplementationConfig.FIRST, InstanceConfig.SINGLETON, TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(ImplementationConfig.SECOND, InstanceConfig.SINGLETON, TreeDependencyInterface.class, TreeDependency.class);
        dependencyRegistrator.register(SimpleDependencyInterface.class, SimpleDependencyClass.class);
        dependencyRegistrator.register(DependencyInterface.class, DependencyA.class);
        dependencyRegistrator.register(NoDependencyInterface.class, NoDependencyClass.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());

        dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON, ImplementationConfig.SECOND);
        dependencySupplier.supply(TreeDependencyInterface.class, InstanceConfig.SINGLETON, ImplementationConfig.FIRST);
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------------
     */

    @Test()
    public void CycleDependency() throws ClassRegistrationException, NotFoundException, InstantiationException, IllegalAccessException {

        DependencyRegistrator dependencyRegistrator = new DependencyRegistratorImpl();

        dependencyRegistrator.register(MichaelInerface.class, Michael.class);
        dependencyRegistrator.register(JacksonInterface.class, Jackson.class);
        dependencyRegistrator.register(ChildInterface.class, Child.class);

        Supplier dependencySupplier = new DependencySupplier(dependencyRegistrator.getResolveMap());
        dependencySupplier.registerCycle(MichaelInerface.class,6);
        Object supply = dependencySupplier.supply(MichaelInerface.class);


    }


}