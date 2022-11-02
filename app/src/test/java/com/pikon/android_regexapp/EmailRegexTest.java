package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmailRegexTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== Email Tests ====" );
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
        System.out.println( "==== End Email Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validateEmail( "a2dk@djwa2io.pl" ) );
        System.out.println( "\t[PASSED]> Correct email" );
    }

    @Test
    public void testFirstDigit(){
        Assert.assertFalse( Validators.validateEmail( "2afa@djwa2io.pl" ) );
        Assert.assertTrue( Validators.validateEmail( "affa@djwa2io.pl" ) );
        System.out.println( "\t[PASSED]> First digit" );
    }

    @Test
    public void test3BeforeAt(){
        Assert.assertFalse( Validators.validateEmail( "ak@djwa2io.pl" ) );
        Assert.assertTrue( Validators.validateEmail( "ak6@djwa2io.pl" ) );
        System.out.println( "\t[PASSED]> Three before @" );
    }
}
