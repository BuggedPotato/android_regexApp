package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddressRegexTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== Address Tests ====" );
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
        System.out.println( "==== End Address Code Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validateAddress( "Adama Mickiewicza 32" ) );
        System.out.println( "\t[PASSED]> Correct address" );
    }

    @Test
    public void testSpecialChar(){
        Assert.assertFalse( Validators.validateAddress( "wkjdhakjwd &djhag 2" ) );
        Assert.assertFalse( Validators.validateAddress( "wkjdhakjwd djhag 2%" ) );
        System.out.println( "\t[PASSED]> Special characters" );
    }

    @Test
    public void testNoNumber(){
        Assert.assertFalse( Validators.validateAddress( "dadad okdwpao" ) );
        System.out.println( "\t[PASSED]> No number" );
    }

    @Test
    public void testEndLetter(){
        Assert.assertTrue( Validators.validateAddress( "dadad 32a" ) );
        Assert.assertTrue( Validators.validateAddress( "daoitujro 52" ) );
        System.out.println( "\t[PASSED]> End letter" );
    }
}
