package com.sheel.app.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sheel.app.PhoneCommunication;

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
//		Activity x = this.getActivity();
//		TextView res = (TextView) x.findViewById(com.sheel.app.R.id.)
		
//		assertEquals();
		
	}
	
//	public void testCommunicationUI()
//	{
//		// Run the UI thread
//		// Nested Classes :D
//		
//		mActivity.runOnUiThread
//		(
//				new Runnable() {
//					
//					public void run() {
//						btn_call.requestFocus();
//					}//end of run
//				}// end of Runnable instantiation
//		);//end runUI
		
//		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
//	    for (int i = 1; i <= TEST_POSITION; i++) {
//	      this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
//	    } // end of for loop
//	    
//	    //Pad down key
//	    this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
//	    
//	    
//	    //Check the current status of the spinner
//	    mPos = mSpinner.getSelectedItemPosition();
//	    mSelection = (String)mSpinner.getItemAtPosition(mPos);
//	    TextView resultView =
//	      (TextView) mActivity.findViewById(
//	        com.android.example.spinner.R.id.SpinnerResult
//	      );
//
//	    String resultText = (String) resultView.getText();
//
//	    assertEquals(resultText, mSelection);

	//  } // end of testSpinnerUI() method definition)

}
