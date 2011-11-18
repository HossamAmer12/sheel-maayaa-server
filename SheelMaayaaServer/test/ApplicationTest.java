import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLConnection;

import javax.swing.Timer;

import org.junit.Test;

import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {
    
	/**
	 * @author Hossam_Amer
	 * Tests whether the insertConfimation controller working
	 * 
	 */

	static String response;
	
    @Test
    public void testThatInsertConfirmationPageWorks() {
        
    	response = sendRequest2GAE("/insertconfirmation/8/13/0");
    	assertTrue(response.contains("Success"));
    	
    	Response resp = GET("/");
        assertIsOk(resp);
        assertContentType("text/html", resp);
        assertCharset(play.Play.defaultWebEncoding, resp);
    }
    
   
    /**
     * @author Hossam_Amer
     * @param PATH The path you give to the server from the routes
     * @return The response string returned by the server
     * @see http://stackoverflow.com/questions/1359689/how-to-send-http-request-in-java
     * Used for sending request to the GAE server with the date stored there 
     */
    
	private String sendRequest2GAE(String PATH)
    {
    	String SERVER = "http://sheelmaaayaa.appspot.com/";
    	
    	try {
			BufferedReader in;
    		java.net.URL yahoo = new java.net.URL(SERVER + PATH);
			 URLConnection yc = yahoo.openConnection();
			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			// XXXDuring successive calls, it makes it bad for the NULL			
//			assertNotNull(in);
//			assertNotNull(yc);
			String inputLine = "";
			// Take care I am reading just one statement from the server
			// if you want to read many statements you have to loop on
			// in.readLine();
			inputLine += in.readLine();
			in.close();		
			
			System.out.println("Respone String: " +inputLine);
			return inputLine;
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "Failure";
		}
    	
    }
    
}