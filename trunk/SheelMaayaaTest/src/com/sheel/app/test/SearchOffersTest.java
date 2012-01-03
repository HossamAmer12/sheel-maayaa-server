package com.sheel.app.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.test.ActivityInstrumentationTestCase2;

import com.sheel.app.FilterPreferencesActivity; 

public class SearchOffersTest extends
	ActivityInstrumentationTestCase2<FilterPreferencesActivity>{
	
	private FilterPreferencesActivity mActivity;
	
	private String server = "sheelmaaayaa.appspot.com";

	public SearchOffersTest(){
		
		super("com.sheel.app", FilterPreferencesActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = this.getActivity();
		
	}

	public void testSearchByFlightNumber() {
		
		try {
			
			///////////////////////// searching extra weight offers ////////////////////////////
			String request =  "/filterflightnumberoffers/LH123/1-31-2011/1/0/0/both/none";
			DefaultHttpClient client = new DefaultHttpClient();
			
			HttpResponse resp = client.execute(new HttpGet(server + request));
			String response = EntityUtils.toString(resp.getEntity());
			JSONArray jsonArray = new JSONArray(response);
			assertEquals(6, jsonArray.length());
			
			///////////////////////// searching less weight offers ////////////////////////////
			request =  "/filterflightnumberoffers/LH123/1-31-2011/0/0/0/both/none";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(1, jsonArray.length());
			
			///////////////////////// filtering offers with weight ////////////////////////////
			request =  "/filterflightnumberoffers/LH123/1-31-2011/1/8/0/both/none";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(3, jsonArray.length());
			
			///////////////////////// filtering offers with weight and price////////////////////////////
			request =  "/filterflightnumberoffers/LH123/1-31-2011/1/8/5/both/none";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(2, jsonArray.length());
			
			///////////////////////// filtering offers with weigh, price and gender ////////////////////////////
			request =  "/filterflightnumberoffers/LH123/1-31-2011/1/8/5/male/none";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(1, jsonArray.length());
			
			///////////////////////// filtering offers with weight, price, gender and nationality ///////////////////////////
			request =  "/filterflightnumberoffers/LH123/1-31-2011/1/8/5/male/78";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(1, jsonArray.length());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSearchByAirports() {
		
		try {
			
			///////////////////////// searching extra weight offers ////////////////////////////
			String request =  "/filterairportsoffers/78/287/1-31-2011/1/0/0/both/none";
			DefaultHttpClient client = new DefaultHttpClient();
			
			HttpResponse resp = client.execute(new HttpGet(server + request));
			String response = EntityUtils.toString(resp.getEntity());
			JSONArray jsonArray = new JSONArray(response);
			assertEquals(6, jsonArray.length());
			
			///////////////////////// searching less weight offers ////////////////////////////
			request =  "/filterairportsoffers/78/287/1-31-2011/0/0/0/both/none";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(1, jsonArray.length());
			
			///////////////////////// filtering offers with weight ///////////////////////////
			request =  "/filterairportsoffers/78/287/1-31-2011/1/8/0/both/none";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(3, jsonArray.length());
			
			///////////////////////// filtering offers with weight and price ///////////////////////////
			request =  "/filterairportsoffers/78/287/1-31-2011/1/8/5/both/none";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(2, jsonArray.length());
			
			///////////////////////// filtering offers with weight, price and gender///////////////////////////
			request =  "/filterairportsoffers/78/287/1-31-2011/1/8/5/male/none";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(1, jsonArray.length());
			
			///////////////////////// filtering offers with weight, price, gender and nationality ///////////////////////////
			request =  "/filterairportsoffers/78/287/1-31-2011/1/8/5/male/78";
			resp = client.execute(new HttpGet(server + request));
			response = EntityUtils.toString(resp.getEntity());
			jsonArray = new JSONArray(response);
			assertEquals(1, jsonArray.length());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
