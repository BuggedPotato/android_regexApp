package com.pikon.android_regexapp;

import android.text.InputType;

import java.util.Arrays;

public enum ValidationMode {
    ZIPCODE( "Zip-code", InputType.TYPE_CLASS_PHONE ),
    ADDRESS( "Address", InputType.TYPE_CLASS_TEXT ),
    PHONE( "Phone", InputType.TYPE_CLASS_PHONE ),
    EMAIL( "Email", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS ),
    PESEL( "PESEL", InputType.TYPE_CLASS_NUMBER ),
    IDCARD( "Idcard", InputType.TYPE_CLASS_TEXT ),
    FULLNAME( "Name&surname", InputType.TYPE_CLASS_TEXT ),
    PASSWORD( "Password", InputType.TYPE_TEXT_VARIATION_PASSWORD ),
    CENSORSHIP( "Censorship", InputType.TYPE_CLASS_TEXT );

    public final String label;
    public final int inputType;

    private ValidationMode( String label, int inputType ) {
        this.label = label;
        this.inputType = inputType;
    }

    public static String[] getAllLabels() {
        return Arrays.stream( ValidationMode.values() ).map( v -> v.label ).toArray( String[]::new );
    }

    public static int[] getAllInputTypes() {
        return Arrays.stream( ValidationMode.values() ).mapToInt( x -> x.inputType ).toArray();
    }
}
