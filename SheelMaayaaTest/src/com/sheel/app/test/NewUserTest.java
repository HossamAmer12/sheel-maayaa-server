package com.sheel.app.test;

import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sheel.app.NewUserActivity;
import com.sheel.app.R;



public class NewUserTest extends
ActivityInstrumentationTestCase2<NewUserActivity> {

	NewUserActivity myActivity;
	
	TextView required;
	TextView countryCode;
	TextView mobileNumber;
	TextView nationality;
	TextView passportNumber;
	TextView passportPhoto;
	Button sendValidation;
	ImageButton takePhoto;
	Button register;
	
	/** AutoComplete field for mobile country codes. Required to register */
	AutoCompleteTextView countryCodes;
	/** Mobile number text field. Required to register */
	EditText mobileNumberField;
	EditText validationCodeField;
	/** AutoComplete field for nationality. Not required to register */
	AutoCompleteTextView nationalityField;
	/** Passport number field. Required to register */
	EditText passportNumberField;
	ImageView i;
	Drawable takePhotoIcon;
	
	String requiredString;
	String countryCodeString;
	String mobileNumberString;
	String sendValidateString;
	String nationalityString;
	String passportNumberString;
	String passportPhotoString;
	String registerString;

public NewUserTest(){
		
		super("com.sheel.app", NewUserActivity.class);
	}

@Override
protected void setUp() throws Exception
{
	super.setUp();
    
	myActivity = this.getActivity();
	
	required = (TextView) myActivity.findViewById(com.sheel.app.R.id.textView0);
	countryCode = (TextView) myActivity.findViewById(com.sheel.app.R.id.textView1);
	mobileNumber = (TextView) myActivity.findViewById(com.sheel.app.R.id.TextView01);
	nationality = (TextView) myActivity.findViewById(com.sheel.app.R.id.textView5);
	passportNumber = (TextView) myActivity.findViewById(com.sheel.app.R.id.textView2);
	passportPhoto = (TextView) myActivity.findViewById(com.sheel.app.R.id.textView7);
	
	sendValidation = (Button) myActivity.findViewById(com.sheel.app.R.id.sendValidation);
	takePhoto = (ImageButton) myActivity.findViewById(com.sheel.app.R.id.takePhoto);
	register = (Button) myActivity.findViewById(com.sheel.app.R.id.register);
    
	mobileNumberField = (EditText) myActivity.findViewById(com.sheel.app.R.id.mobileNumber);
	nationalityField = (AutoCompleteTextView) myActivity.findViewById(com.sheel.app.R.id.autoNationality);
	countryCodes = (AutoCompleteTextView) myActivity.findViewById(com.sheel.app.R.id.auto);
	passportNumberField = (EditText) myActivity.findViewById(com.sheel.app.R.id.passNum);
	validationCodeField = (EditText) myActivity.findViewById(com.sheel.app.R.id.validationCode);
	i = (ImageView) myActivity.findViewById(com.sheel.app.R.id.pictureView);
	//takePhotoIcon = (Drawable) myActivity.findViewById(com.sheel.app.R.drawable.camera_icon);
	
	
    requiredString = myActivity.getString(com.sheel.app.R.string.required);
    countryCodeString = myActivity.getString(com.sheel.app.R.string.mobile_code);
    mobileNumberString = myActivity.getString(com.sheel.app.R.string.mobile_number);
    sendValidateString = myActivity.getString(com.sheel.app.R.string.send_validation);
    nationalityString = myActivity.getString(com.sheel.app.R.string.nationality_nada);
    passportNumberString = myActivity.getString(com.sheel.app.R.string.passport_number);
    passportPhotoString = myActivity.getString(com.sheel.app.R.string.passport_photo);
    registerString = myActivity.getString(com.sheel.app.R.string.register);
}

public void testPreconditions ()
{
	assertNotNull(required);
	assertNotNull(countryCode);
	assertNotNull(mobileNumber);
	assertNotNull(nationality);
	assertNotNull(passportNumber);
	assertNotNull(passportPhoto);
	assertNotNull(sendValidation);
	assertNotNull(takePhoto);
	assertNotNull(register);
	assertNotNull(mobileNumberField);
	assertNotNull(nationalityField);
	assertNotNull(countryCodes);
	assertNotNull(passportNumberField);
	assertNotNull(validationCodeField);
	assertNotNull(i);
	
}

public void testText() {
      assertEquals(requiredString, (String)required.getText());
      assertEquals(countryCodeString, (String)countryCode.getText());
      assertEquals(mobileNumberString, (String)mobileNumber.getText());
      assertEquals(sendValidateString, (String)sendValidation.getText());
      assertEquals(nationalityString, (String)nationality.getText());
      assertEquals(passportNumberString, (String)passportNumber.getText());
      assertEquals(passportPhotoString, (String)passportPhoto.getText());
      assertEquals(registerString, (String)register.getText());
      
}

	
}
