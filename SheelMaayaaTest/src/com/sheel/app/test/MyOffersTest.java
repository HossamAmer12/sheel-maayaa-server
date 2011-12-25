package com.sheel.app.test;

import static com.sheel.utils.SheelMaayaaConstants.HTTP_CHECK_REGISTERED;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.test.ActivityInstrumentationTestCase2;

import com.sheel.app.MyOffersActivity;
import com.sheel.utils.HTTPManager;

/**
 * Junit test case for my offers activity
 * It fetches the offers of Hossam Amer in the data base
 * @author Hossam_Amer
 *
 */

public class MyOffersTest extends
	ActivityInstrumentationTestCase2<MyOffersActivity>{
	
	/**
	 * Reference to my acivity
	 */
	private MyOffersActivity mActivity;
	
	/**
	 * Server path 
	 */
	private String server = "sheelmaaayaa.appspot.com";
	
	
	String path;
	String response;
	
	DefaultHttpClient client = new DefaultHttpClient();

	public MyOffersTest(){
		
		super("com.sheel.app", MyOffersActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = this.getActivity();
	}	
	

	public void getMyoffersTest(){
		try {
			
				
			path =  "/getmyoffers/673780564";
			HTTPManager.startHttpService(path, HTTP_CHECK_REGISTERED, mActivity.getApplicationContext());
			HttpResponse resp = client.execute(new HttpGet(server + path));
			response = EntityUtils.toString(resp.getEntity());
			
			// Make sure that the size of the sting is more than 20
			assert(response.length()>20);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}	
}
