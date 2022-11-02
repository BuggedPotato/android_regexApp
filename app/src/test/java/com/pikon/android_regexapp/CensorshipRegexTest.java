package com.pikon.android_regexapp;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CensorshipRegexTest {
    @BeforeClass
    public static void beforeClass(){
        System.out.println( "==== Censorship Tests ====" );
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
        System.out.println( "==== End Censorship Tests ====" );
    }

    @Test
    public void testCorrect(){
        Assert.assertTrue( Validators.validateCensorship( "jawhdkjah wiudhawiuh iauhwidhi auh", new ArrayList<>(Arrays.asList("one", "two") )) );
        System.out.println( "\t[PASSED]> Correct" );
    }

    @Test
    public void testCensor(){
        Assert.assertFalse( Validators.validateCensorship( "one wiudhawiuh iauhwidhi auh", new ArrayList<>(Arrays.asList("one", "two") )) );
        Assert.assertFalse( Validators.validateCensorship( "wiudhawiuh tWo iauhwidhi One auh", new ArrayList<>(Arrays.asList("one", "two") )) );
        System.out.println( "\t[PASSED]> Censored" );
    }
}
