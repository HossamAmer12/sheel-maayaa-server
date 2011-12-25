package com.sheel.app.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.test.ActivityInstrumentationTestCase2;

import com.sheel.app.MyOffersActivity;
import com.sheel.utils.HTTPManager;


import static com.sheel.utils.SheelMaayaaConstants.*;

/**
 * Junit test case for my offers activity
 * It fetches the offers of Hossam Amer in the data base
 * @author Hossam_Amer
 *
 */

/**
 * The compiler asking to load a file in my apk that does not exist
 * 
 * It is in the package but we do not use.
 * 
 */

public class MyOffersNewTest extends
		ActivityInstrumentationTestCase2<MyOffersActivity> {

	
	/**
	 * Reference to my acivity
	 */
	private MyOffersActivity mActivity;
	
	/**
	 * Server path 
	 */
	private String server = "http://sheelmaaayaa.appspot.com/";
	
	
	String path;
	String response;
	
	DefaultHttpClient client;
	
	
	public MyOffersNewTest(String pkg, Class<MyOffersActivity> activityClass) {
		super("com.sheel.app", MyOffersActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	    protected void setUp() throws Exception {
	        super.setUp();
	        mActivity = this.getActivity();
	        
	    }
	
	/**
	 * Fetches my offers 
	 * @author Hossam_Amer
	 */
	
	public void getMyoffersTest(){
		try {
			
			client = new DefaultHttpClient();	
			path =  "/getmyoffers/673780564";
			HTTPManager.startHttpService(path, HTTP_GET_MY_OFFERS_FILTER, mActivity.getApplicationContext());
			HttpResponse resp = client.execute(new HttpGet(server + path));
			
			String response = EntityUtils.toString(resp.getEntity());
			JSONArray jsonArray = new JSONArray(response);
			
			assert(jsonArray.length()>3);
			
			// Make sure that the size of the sting is more than 20
			assert(response.length()>20);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}	

	
	
}
