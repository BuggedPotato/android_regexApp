package com.pikon.android_regexapp;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Validators {

    public static boolean validateZipCode( String input, TextView tvInfo ) {
        boolean res = Pattern.compile( "\\d{5}" ).matcher( input ).matches();
        if ( res ) {
            String zipCode = input.substring( 0, 2 ) + '-' + input.substring( 2 );
            Call<JsonArray> call = RetrofitZipCode.getClient().create( ZipCodeAPI.class ).getPlaceFromZipCode( zipCode );
            call.enqueue( new Callback<JsonArray>() {
                @Override
                public void onResponse( Call<JsonArray> call, Response<JsonArray> response ) {
                    if ( response.body() == null || response.body().get( 0 ).getAsJsonObject().get( "gmina" ) == null ) {
                        if( tvInfo != null )
                            tvInfo.setText( "No location for given zip code" );
                        return;
                    }
                    ZipCodePlace zipCodePlace = new Gson().fromJson( response.body().get( 0 ), ZipCodePlace.class );
                    tvInfo.setText( zipCodePlace.toString() );
                }

                @Override
                public void onFailure( Call<JsonArray> call, Throwable t ) {
                    if( tvInfo != null )
                        tvInfo.setText( "Unable to find location for given zip code" );
                    t.printStackTrace();
                }
            } );
        }
        return res;
    }

    public static boolean validatePesel( String input, TextView tvInfo ) {
        if ( input.length() != 11 )
            return false;

        final int[] weight = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1 };
        int[] pesel = Stream.of( input.split( "" ) ).mapToInt(x -> Integer.parseInt( x ) ).toArray();
        int[] dateArr = { Integer.parseInt( input.substring( 0, 2 ) ), Integer.parseInt( input.substring( 2, 4 ) ), Integer.parseInt( input.substring( 4, 6 ) ) };
        dateArr[1] = dateArr[1] % 20;
        try {
            LocalDate.of( dateArr[0], dateArr[1] - 1, dateArr[2] );
        } catch ( DateTimeException ex ) {
            if( tvInfo != null )
                Log.e( "CATCH", "Invalid date in PESEL" );
            return false;
        }

        int sum = 0;
        for ( int i = 0; i < pesel.length; i++ )
            sum += pesel[i] * weight[i];
        if ( sum % 10 == 0 ) {
            if( tvInfo != null )
                tvInfo.setText( "GENDER: " + ( pesel[9] % 2 == 0 ? "K" : "M" ) );
            return true;
        } else
            return false;
    }

    public static boolean validatePhone( String input ) {
        String regex = "((\\+\\d{2,3})?\\d{9})|\\d{7}";
        return Pattern.compile( regex, Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    public static boolean validateAddress( String input ) {
        return Pattern.compile( "\\b[[^\\d\\W]\\s]+\\s\\d+[a-z]?", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    public static boolean validateFullname( String input ) {
        return Pattern.compile( "[^\\d\\W]+\\s[^\\d\\W]+(\\-[^\\d\\W]+)?", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    public static boolean validateEmail( String input ) {
        return Pattern.compile( "^[^\\d\\s]\\w{2,}@((\\w{1,}.\\w{3,})|(\\w{2,}.\\w{2,})|(\\w{3,}.\\w{1,}))", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    public static boolean validateIdCard( String input ) {
        return Pattern.compile( "^[a-z][a-z0-9]{0,}\\d+[a-z0-9]*$", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    public static boolean validatePassword( String input ) {
        return Pattern.compile( "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&_])[A-Za-z\\d@$!%*#?&_]{7,}$" ).matcher( input ).matches();
    }

    public static boolean validateCensorship( String input, ArrayList<String> forbidden ) {
        String regex = "\\b(?:" + String.join( "|", forbidden ) + ")\\b";
        Matcher matcher = Pattern.compile( regex, Pattern.CASE_INSENSITIVE|Pattern.MULTILINE ).matcher( input );
        return !matcher.find();
    }
}
