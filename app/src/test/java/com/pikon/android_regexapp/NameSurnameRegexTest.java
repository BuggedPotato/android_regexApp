package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NameSurnameRegexTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== Name & Surname Tests ====" );
    }

    @Before
    public void before(){
        System.out.println( "> Begin test" );
    }

    @After
    public void after(){
        System.out.println( "< End test" );
    }

    @AfterClass
    public static void afterClass(){
        System.out.println( "==== End Name & Surname Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validateFullname( "Anna Kowalska" ) );
        System.out.println( "\t[PASSED]> Correct" );
    }

    @Test
    public void testOnlyName(){
        Assert.assertFalse( Validators.validateFullname( "Anna" ) );
        System.out.println( "\t[PASSED]> Only name" );
    }

    @Test
    public void testMultipleSpaces(){
        Assert.assertFalse( Validators.validateFullname( "Anna Kowalska Kwiatkowska" ) );
        System.out.println( "\t[PASSED]> Multiple spaces" );
    }

    @Test
    public void testMultiPartSurname(){
        Assert.assertTrue( Validators.validateFullname( "Anna Kowalska-Kwiatkowska" ) );
        Assert.assertFalse( Validators.validateFullname( "Anna Kowalska-Kwiatkowska-Szatan" ) );
        System.out.println( "\t[PASSED]> Multi part surname" );
    }
}
