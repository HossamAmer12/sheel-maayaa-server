package controllers;

import models.Confirmation;
import models.Offer;
import models.User;



import play.libs.Mail;
import play.mvc.Controller;

// Mail import
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
    
//    public static void sendMail()
//    {
//    	SimpleEmail email = new SimpleEmail();
//    	try {
//			email.setFrom("hossam.amer12@gmail.com");
//			email.addTo("hossam.amer12@gmail.com");
//	    	email.setSubject("subject");
//	    	email.setMsg("Hello from mail");
//	    	Mail.send(email);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//			renderJSON(e);
//		}
//    	 
//    }
    
    public static void sendMail()
    {
    	Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "...";
    	try {
    		 Message msg = new MimeMessage(session);
             msg.setFrom(new InternetAddress("hossam.amer12@gmail.com", "Example.com Admin"));
             msg.addRecipient(Message.RecipientType.TO,
                              new InternetAddress("hossam.amer12@gmail.com", "Mr. User"));
             msg.setSubject("Your SheelExample.com account has been activated");
             msg.setText(msgBody);
             Transport.send(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			renderJSON(e);
		}
    	 
    }

    
    
}