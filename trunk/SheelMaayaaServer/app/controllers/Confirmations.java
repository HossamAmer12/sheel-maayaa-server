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
    
    public static String insertConfirmation(long userId, long offerId, int user_status)
    {
		
    	
    		User user = User.getByKey(User.class, userId);
    		Offer offer = Offer.getByKey(Offer.class, offerId);
    		
    		String tmp = "";
    		
    		if (user_status == 1) // More weight
    		{
    			tmp = "I was here";
    			//there is a confirmation 
    			try {
    				
    				Confirmation confirmation = 
    					Confirmation.all(Confirmation.class).
    					filter("offer", offer).fetch().get(0);
    				
    				// 11
    				if (confirmation.getStatusTransactionUser1() 
    						&& confirmation.getStatusTransactionUser2())
    				{
    					return "Failure: This offer has been already confirmed by two users";
    				} //01
    				else if(!confirmation.getStatusTransactionUser1() 
    						&& confirmation.getStatusTransactionUser2())
    					{
//    						
    					confirmation.user1 = user;
    					confirmation.statusTransactionUser1 = true;
    					confirmation.save();
    					

    					user.update();
    					user.save();
    					
    					renderJSON(confirmation);
    						return "Success: User1 confirms an already confirmed offer by User2";
    					}
    				//10
    				else if (confirmation.getStatusTransactionUser1() 
    						&& !confirmation.getStatusTransactionUser2())
    					{
    						return "Failure: User1 is confirming an already confirmed offer by " +
    								"another User1";
    					}
					
				} catch (Exception e) {
					// TODO: handle exception
					//00
					new Confirmation(offer, user, null, true, false, false, false).insert();
					return "Success: This confirmation is new!";
				}
    		}// end if (user_status == 1)
    		else
    		{
    				new Confirmation(offer, null, user, false, true, false, false).insert();
    				return "Success: User2 cofirms a new offer";
    		}
    		
    //		renderJSON(Confirmation.all(Confirmation.class).
	//		filter("offer", offer).fetch().get(0));
    		
			return tmp + " Different Satatus than 1 and 2!!";
    		
    }
}