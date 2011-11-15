package controllers;

import play.*;
import play.mvc.*;
import siena.DateTime;

import java.util.*;

//import ClientModels.CFlight;

import models.*;

public class Confirmations extends Controller {
	
	 /**
     * @author Hossam Amer
     * Creates a confirmation inside the database.
     */
    
    public static String insertConfirmation(long userId, long offerId, int user_status){
		
    	try
    	{
    		User user = User.getByKey(User.class, userId);
    		Offer offer = Offer.getByKey(Offer.class, offerId);
    		
    		if (user_status == 1)
    		{
    			try {
    				
//    				Confirmation confirmation = 
    					Confirmation.all(Confirmation.class).filter("user1", user).
    				filter("offer", offer).fetch().get(0);
    				
    				return "There is already a confirmation from this user";
    				
				} catch (NullPointerException e) {
					// TODO: handle exception
					
					new Confirmation(offer, user, null, true, false, false, false).insert();
					return "This confirmation is fresh!";
				}
    				
    		}
    		
    		else
    			new Confirmation(offer, user, null, false, true, false, false).insert();
    	
    		
    		
    		
			return "Success";
		 }
    	
    	catch(Exception e)
    	{
			return e.toString();
		}
	}
	
	
    
}
