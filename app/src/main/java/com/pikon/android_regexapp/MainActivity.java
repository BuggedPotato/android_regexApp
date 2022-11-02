package com.pikon.android_regexapp;

import static com.pikon.android_regexapp.ValidationMode.CENSORSHIP;
import static com.pikon.android_regexapp.ValidationMode.ZIPCODE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
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

	private final ArrayList<String> censor = new ArrayList<String>() {
		{
			add( "wtf" );
			add( "one" );
			add( "two" );
		}
	};

	private final HashMap<String, Integer> types = new HashMap<String, Integer>(){{
		put( "zip-code", InputType.TYPE_CLASS_PHONE );
		put( "address", InputType.TYPE_CLASS_TEXT );
		put( "phone", InputType.TYPE_CLASS_PHONE );
		put( "email", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS );
		put( "pesel", InputType.TYPE_CLASS_NUMBER );
		put( "idcard", InputType.TYPE_CLASS_TEXT );
		put( "name&surname", InputType.TYPE_CLASS_TEXT );
		put( "password", InputType. TYPE_TEXT_VARIATION_PASSWORD );
		put( "censorship", InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE );
	}};

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		getSupportActionBar().setTitle( "Validation" );

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
					etInput.setFilters(new InputFilter[]{
							new InputFilter.LengthFilter(5)
					});
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

	public static void hideKeyboard( Activity activity ) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService( Activity.INPUT_METHOD_SERVICE );
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
				result = Validators.validateZipCode( input, tvInfo );
				break;
			case ADDRESS:
				result = Validators.validateAddress( input );
				break;
			case PHONE:
				result = Validators.validatePhone( input );
				break;
			case EMAIL:
				result = Validators.validateEmail( input );
				break;
			case PESEL:
				result = Validators.validatePesel( input, tvInfo );
				break;
			case IDCARD:
				result = Validators.validateIdCard( input );
				break;
			case FULLNAME:
				result = Validators.validateFullname( input );
				break;
			case PASSWORD:
				result = Validators.validatePassword( input );
				break;
			case CENSORSHIP:
                result = Validators.validateCensorship( input, censor );
				break;
		}
		return result;
	}
}