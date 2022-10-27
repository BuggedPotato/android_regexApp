package com.pikon.android_regexapp;

import static com.pikon.android_regexapp.ValidationMode.ZIPCODE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Spinner spVerificationMode;
    private ValidationMode validationMode;
    private EditText etInput;
    private TextView tvResult;
    private TextView tvInfo;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        etInput = (EditText) findViewById( R.id.etInput );
        tvResult = (TextView) findViewById( R.id.tvResult );
        tvInfo = (TextView) findViewById( R.id.tvInfo );

        spVerificationMode = findViewById( R.id.spVerificationMode );
//        ArrayAdapter<String> adapter = new ArrayAdapter<>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ValidationMode.getAllLabels() );
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.spinner_values, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item );
        adapter.setDropDownViewResource( androidx.appcompat.R.layout.support_simple_spinner_dropdown_item );
        spVerificationMode.setAdapter( adapter );
        spVerificationMode.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
                etInput.setText( "" );
                // TODO
                validationMode = ValidationMode.values()[i];
                etInput.setInputType( ValidationMode.getAllInputTypes()[i] );
                if ( validationMode == ZIPCODE ) {
                    etInput.setFilters( new InputFilter[]{
                            new InputFilter.LengthFilter( 5 )
                    } );
                } else
                    etInput.setFilters( new InputFilter[]{} );
            }

            @Override
            public void onNothingSelected( AdapterView<?> adapterView ) {
            }
        } );

        ( (Button) findViewById( R.id.btnValidate ) ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                hideKeyboard( MainActivity.this );
                updateResultText( validate( validationMode, etInput.getText().toString().trim() ) );
            }
        } );
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if ( view == null )
            view = new View( activity );
        imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
    }

    private void updateResultText( boolean result ) {
        if ( result ) {
            tvResult.setText( "CORRECT" );
            tvResult.setTextColor( Color.GREEN );
        } else {
            tvResult.setText( "WRONG" );
            tvResult.setTextColor( Color.RED );
        }
    }

    private boolean validate( ValidationMode mode, String input ) {
        tvInfo.setText( "" );
        boolean result = false;
        switch ( mode ) {
            case ZIPCODE:
                result = validateZipCode( input );
                break;
            case ADDRESS:
                result = validateAddress( input );
                break;
            case PHONE:
                result = validatePhone( input );
                break;
            case EMAIL:
                result = validateEmail( input );
                break;
            case PESEL:
                result = validatePesel( input );
                break;
            case IDCARD:
                result = validateIdCard( input );
                break;
            case FULLNAME:
                result = validateFullname( input );
                break;
            case PASSWORD:
                break;
            case CENSORSHIP:
                break;
        }
        return result;
    }

    private boolean validateZipCode( String input ) {
        boolean res = Pattern.compile( "\\d{5}" ).matcher( input ).matches();
        if ( res ) {
            String zipCode = input.substring(0,2) + '-' + input.substring(2);
            Call<JsonArray> call = RetrofitZipCode.getClient().create(ZipCodeAPI.class).getPlaceFromZipCode( zipCode );
            call.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    Log.d( "DEBUG", response.body().toString() );
                    if( response.body() == null || response.body().get(0).getAsJsonObject().get( "gmina" ) == null ) {
                        tvInfo.setText("No location for given zip code");
                        return;
                    }
                    ZipCodePlace zipCodePlace = new Gson().fromJson( response.body().get(0), ZipCodePlace.class );
                    tvInfo.setText( zipCodePlace.toString() );
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    tvInfo.setText( "Unable to find location for given zip code" );
                    t.printStackTrace();
                }
            });
        }
        return res;
    }

    private boolean validatePesel( String input ) {
        if ( input.length() != 11 )
            return false;

        final int[] weight = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1 };
        int[] pesel = Stream.of( input.split( "" ) ).mapToInt( x -> Integer.parseInt( x ) ).toArray();
        int[] dateArr = { Integer.parseInt( input.substring( 0, 2 ) ), Integer.parseInt( input.substring( 2, 4 ) ), Integer.parseInt( input.substring( 4, 6 ) ) };
        dateArr[1] = dateArr[1] % 20;
        try {
            LocalDate.of( dateArr[0], dateArr[1] - 1, dateArr[2] );
        } catch ( DateTimeException ex ) {
            Log.e( "CATCH", "Invalid date in PESEL" );
            return false;
        }

        int sum = 0;
        for ( int i = 0; i < pesel.length; i++ )
            sum += pesel[i] * weight[i];
        if ( sum % 10 == 0 ) {
            tvInfo.setText( "GENDER: " + ( pesel[9] % 2 == 0 ? "K" : "M" ) );
            return true;
        } else
            return false;
    }

    private boolean validatePhone( String input ) {
        String regex = "((\\+\\d{2,3})?\\d{9})|\\d{7}";
        return Pattern.compile( regex, Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    private boolean validateAddress( String input ) {
        return Pattern.compile( "\\b[[^\\d\\W]\\s]+\\s\\d+[a-z]?", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    private boolean validateFullname( String input ) {
        return Pattern.compile( "[^\\d\\W]+\\s[^\\d\\W]+(\\-[^\\d\\W]+)?", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    private boolean validateEmail( String input ){
        return Pattern.compile( "^[^\\d\\s]\\w{2,}@((\\w{1,}.\\w{3,})|(\\w{2,}.\\w{2,})|(\\w{3,}.\\w{1,}))", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    private boolean validateIdCard( String input ){
        return Pattern.compile( "^[a-z][a-z0-9]{0,}\\d+[a-z0-9]*$", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    private boolean validatePassword( String input ){
        return Pattern.compile( "[\\da-zA-Z]+[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+[\\da-zA-Z]+" ).matcher( input ).matches();
    }
}