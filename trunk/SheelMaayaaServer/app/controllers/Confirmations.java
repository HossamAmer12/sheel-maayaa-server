package controllers;

import models.Confirmation;
import models.Offer;
import models.User;
import play.mvc.Controller;

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
//    						confirmation.setUser1(user);
//    						confirmation.setStatusTransactionUser1(true);
    					confirmation.user1 = user;
//    					user.update();
    					confirmation.statusTransactionUser1 = true;
    					
    					user.confirmations1.fetch().add(confirmation);
    					
    					
//    					confirmation.update();
//   					confirmation.save();
//    					user.update();
    					confirmation.save();
    					user.save();
    					
    					render(confirmation);

    						return "Empty: " + user.confirmations1.fetch().isEmpty() + 
    						") Success: User1 confirms an already confirmed offer by User2";
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