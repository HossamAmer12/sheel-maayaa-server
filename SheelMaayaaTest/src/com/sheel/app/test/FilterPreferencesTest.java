package com.sheel.app.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sheel.app.FilterPreferencesActivity;

public class FilterPreferencesTest extends
		ActivityInstrumentationTestCase2<FilterPreferencesActivity> {


	private FilterPreferencesActivity mActivity;
	
	private TextView weightView;
	private TextView priceView;
	private EditText weightText;
	private EditText priceText;
	private TextView weight_price_view;
	private Button facebookButton;
	private TextView genderView;
	private ToggleButton male;
	private ToggleButton female;
	private TextView nationalityView;
	private AutoCompleteTextView nationalityText;
	private Button searchButton;

	private String weight;
	private String price;
	private String weight_price;
	private String facebook;
	private String gender;
	private String nationality;
	private String search;
	
	public FilterPreferencesTest(){
		
		super("com.sheel.app", FilterPreferencesActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
	    
		mActivity = this.getActivity();
	    
		weightView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView12_maged);
		priceView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView13_maged);
		weightText = (EditText) mActivity.findViewById(com.sheel.app.R.id.kgs);
		priceText = (EditText) mActivity.findViewById(com.sheel.app.R.id.price);
		weight_price_view = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView14_maged);
		facebookButton = (Button) mActivity.findViewById(com.sheel.app.R.id.facebook);
		genderView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView15_maged);
		male = (ToggleButton) mActivity.findViewById(com.sheel.app.R.id.male);
	    female = (ToggleButton) mActivity.findViewById(com.sheel.app.R.id.female);
		nationalityView = (TextView) mActivity.findViewById(com.sheel.app.R.id.textView16_maged);
		nationalityText = (AutoCompleteTextView) mActivity.findViewById(com.sheel.app.R.id.nationality);
        searchButton = (Button) mActivity.findViewById(com.sheel.app.R.id.search);
       
        weight = mActivity.getString(com.sheel.app.R.string.weight_in_kgs);
        price = mActivity.getString(com.sheel.app.R.string.price_per_kg);
        weight_price = mActivity.getString(com.sheel.app.R.string.weight_and_price);
        facebook = mActivity.getString(com.sheel.app.R.string.facebook_search);
        gender = mActivity.getString(com.sheel.app.R.string.gender);
        nationality = mActivity.getString(com.sheel.app.R.string.nationality);
        search = mActivity.getString(com.sheel.app.R.string.search_offers);
	}
	
	public void testPreconditions ()
	{
		assertNotNull(weightView);
		assertNotNull(priceView);
		assertNotNull(weightText);
		assertNotNull(priceText);
		assertNotNull(weight_price_view);
		assertNotNull(facebookButton);
		assertNotNull(genderView);
		assertNotNull(male);
		assertNotNull(female);
		assertNotNull(nationalityView);
		assertNotNull(nationalityText);
		assertNotNull(searchButton);
		
	}
	
	public void testText() {
	      assertEquals(weight, (String)weightView.getText());
	      assertEquals(price, (String)priceView.getText());
	      assertEquals(weight_price, (String)weight_price_view.getText());
	      assertEquals(facebook, (String)facebookButton.getText());
	      assertEquals(gender, (String)genderView.getText());
	      assertEquals(nationality, (String)nationalityView.getText());
	      assertEquals(search, (String)searchButton.getText());
	}
}
