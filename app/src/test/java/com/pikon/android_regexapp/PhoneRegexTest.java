package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PhoneRegexTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== Phone Tests ====" );
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
        System.out.println( "==== End Phone Code Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validatePhone( "123546789" ) );
        Assert.assertTrue( Validators.validatePhone( "+48123546789" ) );
        Assert.assertTrue( Validators.validatePhone( "+456123546789" ) );
        Assert.assertTrue( Validators.validatePhone( "3546789" ) );
        System.out.println( "\t[PASSED]> Correct phone" );
    }

    @Test
    public void testLetters(){
        Assert.assertFalse( Validators.validatePhone( "123a46789" ) );
        System.out.println( "\t[PASSED]> Letters" );
    }

    @Test
    public void testLength(){
        Assert.assertFalse( Validators.validatePhone( "123546789333" ) );
        Assert.assertFalse( Validators.validatePhone( "89333" ) );
        System.out.println( "\t[PASSED]> Length" );
    }

}
