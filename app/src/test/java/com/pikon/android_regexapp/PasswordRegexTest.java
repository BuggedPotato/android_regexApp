package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PasswordRegexTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== Password Tests ====" );
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
        System.out.println( "==== End Password Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validatePassword( "lU765%2nn" ) );
        System.out.println( "\t[PASSED]> Correct" );
    }

    @Test
    public void testLength(){
        Assert.assertFalse( Validators.validatePassword( "lU#6" ) );
        System.out.println( "\t[PASSED]> Length" );
    }

    @Test
    public void testNoNumbers(){
        Assert.assertFalse( Validators.validatePassword( "LhdiauwhU&d" ) );
        System.out.println( "\t[PASSED]> No numbers" );
    }

    @Test
    public void testNoLetters(){
        Assert.assertFalse( Validators.validatePassword( "78127931827@@#" ) );
        System.out.println( "\t[PASSED]> No letters" );
    }
}
