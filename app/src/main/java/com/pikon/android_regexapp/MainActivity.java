package com.pikon.android_regexapp;

import static com.pikon.android_regexapp.ValidationMode.ZIPCODE;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ValidationMode.getAllLabels() );
        adapter.setDropDownViewResource( androidx.appcompat.R.layout.support_simple_spinner_dropdown_item );
        spVerificationMode.setAdapter( adapter );
        spVerificationMode.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
                etInput.setText( "" );
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
                updateResultText( validate( validationMode, etInput.getText().toString().trim() ) );
            }
        } );
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
                break;
            case PESEL:
                result = validatePesel( input );
                break;
            case IDCARD:
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
        String regex = "((\\+\\d{2})?\\d{9})|\\d{7}";
        return Pattern.compile( regex, Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    private boolean validateAddress( String input ) {
        return Pattern.compile( "\\b[[^\\d\\W]\\s]+\\s\\d+[a-z]?", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }

    private boolean validateFullname( String input ) {
        return Pattern.compile( "[^\\d\\W]+\\s[^\\d\\W]+(\\-[^\\d\\W]+)?", Pattern.CASE_INSENSITIVE ).matcher( input ).matches();
    }
}