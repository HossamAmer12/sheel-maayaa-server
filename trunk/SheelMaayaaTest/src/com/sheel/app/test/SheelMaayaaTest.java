package com.sheel.app.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import android.app.*;
import android.content.*;
import android.test.*;
import android.test.suitebuilder.annotation.*;
import android.util.*;
import android.view.*;
import android.widget.*;


import com.sheel.app.PhoneCommunication;
import com.sheel.app.R;

public class SheelMaayaaTest extends
		ActivityInstrumentationTestCase2<PhoneCommunication> {

	private PhoneCommunication mActivity;
	private TextView username;
	private TextView mobile_number;
	private TextView email;
	private TextView fb_account;
	private ImageButton btn_call;
	private Button btn_send_sms;
	private Button btn_confirm;	
	
	public SheelMaayaaTest() {
		
		super("com.sheel.app", PhoneCommunication.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
	    mActivity = this.getActivity();
        username = (TextView) mActivity.findViewById(com.sheel.app.R.id.username);
        mobile_number = (TextView) mActivity.findViewById(com.sheel.app.R.id.mobile_number);
        email = (TextView) mActivity.findViewById(com.sheel.app.R.id.email);
        fb_account = (TextView) mActivity.findViewById(com.sheel.app.R.id.fb_account);
        btn_call = (ImageButton) mActivity.findViewById(com.sheel.app.R.id.btn_call);
        btn_send_sms = (Button) mActivity.findViewById(com.sheel.app.R.id.btn_send_sms);
        btn_confirm = (Button) mActivity.findViewById(com.sheel.app.R.id.btn_confirm);
        
//        mView = (TextView) mActivity.findViewById(com.example.helloandroid.R.id.textview);
//        resourceString = mActivity.getString(com.example.helloandroid.R.string.hello);

	}
	
	// Testing the preconditions of the app, avoiding a null pointer exception
	public void testPreconditions ()
	{
		assertNotNull(username);
		assertNotNull(mobile_number);
		assertNotNull(email);
		assertNotNull(fb_account);
		assertNotNull(btn_call);
		assertNotNull(btn_send_sms);
		assertNotNull(btn_confirm);
		
		// Testing data from database
		assertNotNull(username.getText());
		assertNotNull(mobile_number.getText());
		assertNotNull(email.getText());
		assertNotNull(fb_account.getText());
	
	}	
	
	
	public void testCommunicationUI()
	{
		mActivity.runOnUiThread(
				new Runnable()
				{

					public void run() {
						// TODO Auto-generated method stub
						btn_call.requestFocus();
//						btn_confirm.requestFocus();
//						btn_send_sms.requestFocus();
						
					}
					
				}
		);
		
//		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
//	    TouchUtils.clickView(btn_call, v)

		
//		Activity x = this.getActivity();
//		TextView res = (TextView) x.findViewById(com.sheel.app.R.id.)
		
//		assertEquals();
		
	}
	

	   public void test2() {

	      Instrumentation instrumentation = getInstrumentation();

	      // Register we are interested in the authentication activiry...
	      Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(PhoneCommunication.class.getName(), null, false);

	      // Start the PhoneCommunication activity as the first activity...
	      Intent intent = new Intent(Intent.ACTION_MAIN);
	      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      intent.setClassName(instrumentation.getTargetContext(), PhoneCommunication.class.getName());
	      intent.putExtra("fb_account", "hossam.amer@facebook.com");
	      intent.putExtra("fbId", "a999asas");
	    	
	    	intent.putExtra("mobile", "0101577990");
	    	intent.putExtra("kgs", 2);
	    	intent.putExtra("offerId", 13);
	    	intent.putExtra("email", "hossam.amer12@gmail.com");
	    	intent.putExtra("fullName", "Hossam Amer");
	    	intent.putExtra("userId", "8");
	    	intent.putExtra("user_status", 1);
	      instrumentation.startActivitySync(intent);

	      // Wait for it to start...
	      Activity currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
//	      assertThat(currentActivity, is(notNullValue()));
	      assertNotNull(currentActivity);

	      // Type into the username field...
	      
	      Button currentButton = (Button) currentActivity.findViewById(R.id.btn_call);
	      assertNotNull(currentButton);
	      TouchUtils.clickView(this, currentButton);
	      instrumentation.sendStringSync("0101577990");
	      
//	      Intent intent2 = new Intent()
	      
//	      Activity currentActivity2 = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
	      instrumentation.startActivitySync(intent);
//	      assertThat(currentActivity, is(notNullValue()));
//	      assertNotNull(currentActivity);
	      


	      // Type into the password field...
//	      currentView = currentActivity.findViewById(password_field);
//	      assertThat(currentView, is(notNullValue()));
//	      assertThat(currentView, instanceOf(EditText.class));
//	      TouchUtils.clickView(this, currentView);
//	      instrumentation.sendStringSync("MyPassword");
//
//	      // Register we are interested in the welcome activity...
//	      // this has to be done before we do something that will send us to that
//	      // activity...
//	      instrumentation.removeMonitor(monitor);
//	      monitor = instrumentation.addMonitor(WelcomeActivity.class.getName(), null, false);
//
//	      // Click the login button...
//	      currentView = currentActivity.findViewById(login_button;
//	      assertThat(currentView, is(notNullValue()));
//	      assertThat(currentView, instanceOf(Button.class));
//	      TouchUtils.clickView(this, currentView);
//
//	      // Wait for the welcome page to start...
//	      currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
//	      assertThat(currentActivity, is(notNullValue()));
//
//	      // Make sure we are logged in...
//	      currentView = currentActivity.findViewById(welcome_message);
//	      assertThat(currentView, is(notNullValue()));
//	      assertThat(currentView, instanceOf(TextView.class));
//	      assertThat(((TextView)currentView).getText().toString(), is("Welcome, MyUsername!"));
	   }
}
