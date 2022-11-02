package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IdCardRegexTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== Id Card Tests ====" );
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
        System.out.println( "==== End Id Card Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validateIdCard( "a22" ) );
        System.out.println( "\t[PASSED]> Correct Id Card" );
    }

    @Test
    public void testLetterFirst(){
        Assert.assertFalse( Validators.validateIdCard( "8dhAWu" ) );
        Assert.assertTrue( Validators.validateIdCard( "A72daj" ) );
        System.out.println( "\t[PASSED]> Letter first" );
    }

    @Test
    public void testContainsDigit(){
        Assert.assertFalse( Validators.validateIdCard( "aADUWHIuhdajwhdiauH" ) );
        Assert.assertTrue( Validators.validateIdCard( "wdoAWOduhau2Ha19Dadskl" ) );
        System.out.println( "\t[PASSED]> Contains digit" );
    }
}
