package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PeselRegexTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== PESEL Tests ====" );
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
        System.out.println( "==== End PESEL Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validatePesel( "47092041142", null ) );
        System.out.println( "\t[PASSED]> Correct PESEL" );
    }

    @Test
    public void testBirthDate(){
        Assert.assertFalse( Validators.validatePesel( "47192041142", null ) );
        System.out.println( "\t[PASSED]> Birthdate" );
    }

    @Test
    public void testLength(){
        Assert.assertFalse( Validators.validatePesel( "4709204114222", null ) );
        Assert.assertTrue( Validators.validatePesel( "47092041142", null ) );
        Assert.assertFalse( Validators.validatePesel( "204114222", null ) );
        System.out.println( "\t[PASSED]> Length" );
    }
}
