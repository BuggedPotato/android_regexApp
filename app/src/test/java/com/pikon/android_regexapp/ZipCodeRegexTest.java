package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

public class ZipCodeRegexTest {

    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== Zip Code Tests ====" );
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
        System.out.println( "==== End Zip Code Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validateZipCode( "32540", null ) );
        System.out.println( "\t[PASSED]> Correct zip code" );
    }

    @Test
    public void testSpecialChar(){
        Assert.assertFalse( Validators.validateZipCode( "32-40", null ) );
        Assert.assertFalse( Validators.validateZipCode( "3+240", null ) );
        System.out.println( "\t[PASSED]> Special characters" );
    }

    @Test
    public void testLength(){
        Assert.assertFalse( Validators.validateZipCode( "322540", null ) );
        Assert.assertFalse( Validators.validateZipCode( "655", null ) );
        System.out.println( "\t[PASSED]> Length" );
    }

    @Test
    public void testLetters(){
        Assert.assertFalse( Validators.validateZipCode( "a4211", null ) );
        Assert.assertFalse( Validators.validateZipCode( "32a22", null ) );
        Assert.assertFalse( Validators.validateZipCode( "7324f", null ) );
        System.out.println( "\t[PASSED]> Letters" );
    }
}
