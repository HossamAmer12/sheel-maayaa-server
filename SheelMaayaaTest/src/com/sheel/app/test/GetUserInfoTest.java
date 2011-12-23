package com.sheel.app.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sheel.app.GetUserInfoActivity;

public class GetUserInfoTest extends
		ActivityInstrumentationTestCase2<GetUserInfoActivity> {

	private GetUserInfoActivity mActivity;
	
	private TextView headerView;
	private TextView onView;
	private TextView usingView;
	private RadioButton lessWeightButton;
	private RadioButton extraWeightButton;
	private ToggleButton byFlight;
	private ToggleButton byAirports;
	private TextView dateView;
	private Button changeDateButton;
	private TextView flightView;
	private TextView srcDesView;
	
	private String header;
	private String on;
	private String using;
	private String lessWeight;
	private String extraWeight;
	private String changeDate;
	private String flight;
	private String srcDes;

	
	public GetUserInfoTest() {
		
		super("com.sheel.app", GetUserInfoActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
	    
		mActivity = this.getActivity();
	    
		headerView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView1_maged);
		onView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView2_maged);
		usingView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView3_maged);
		lessWeightButton = (RadioButton) mActivity.findViewById(com.sheel.app.R.id.lessWeight);
	    extraWeightButton = (RadioButton) mActivity.findViewById(com.sheel.app.R.id.extraWeight);
	    byFlight = (ToggleButton) mActivity.findViewById(com.sheel.app.R.id.flightNo);
	    byAirports = (ToggleButton) mActivity.findViewById(com.sheel.app.R.id.srcDes);
        dateView = (TextView) mActivity.findViewById(com.sheel.app.R.id.dateDisplay);
        changeDateButton = (Button) mActivity.findViewById(com.sheel.app.R.id.changeDate);
        flightView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView4_maged);
        srcDesView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView5_maged);
        
        header = mActivity.getString(com.sheel.app.R.string.searching_for_users);
        on = mActivity.getString(com.sheel.app.R.string.on);
        using = mActivity.getString(com.sheel.app.R.string.using);
        lessWeight = mActivity.getString(com.sheel.app.R.string.less_weight);
        extraWeight = mActivity.getString(com.sheel.app.R.string.extra_weight);
        changeDate = mActivity.getString(com.sheel.app.R.string.change_date);
        flight = mActivity.getString(com.sheel.app.R.string.flight_number);
        srcDes = mActivity.getString(com.sheel.app.R.string.src_and_des);
   
	}
	
	public void testPreconditions ()
	{
		assertNotNull(headerView);
		assertNotNull(onView);
		assertNotNull(usingView);
		assertNotNull(lessWeightButton);
		assertNotNull(extraWeightButton);
		assertNotNull(byAirports);
		assertNotNull(byFlight);
		assertNotNull(dateView);
		assertNotNull(changeDateButton);
		assertNotNull(flightView);
		assertNotNull(srcDesView);
		
	}
	
	public void testText() {
	      assertEquals(header, (String)headerView.getText());
	      assertEquals(on, (String)onView.getText());
	      assertEquals(using, (String)usingView.getText());
	      assertEquals(lessWeight, (String)lessWeightButton.getText());
	      assertEquals(extraWeight, (String)extraWeightButton.getText());
	      assertEquals(changeDate, (String)changeDateButton.getText());
	      assertEquals(flight, (String)flightView.getText());
	      assertEquals(srcDes, (String)srcDesView.getText());
	}
}
