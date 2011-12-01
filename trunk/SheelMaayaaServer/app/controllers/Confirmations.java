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
    
    public static synchronized String insertConfirmation(long userId, long offerId, int user_status)
    {
    		
    		if (user_status == 1) // More weight
    		{
    			return insertConfirmationUser1(userId, offerId);
    			
    		}// end if (user_status == 1)
    		else
    		{
    			return insertConfirmationUser2(userId, offerId);
    		}
    		    		
    }
        
    /**
     * @author Hossam_Amer
     * @param sender The sender e-mail address
     * @param recepient The recipient e-mail address
     * @param user_type The type of user, 0 > Less w8t, 1 > More w8t
     * Sends mail thru the server
     */
    
    private static void sendMail(String recepient, int user_type, User user1, User user2, Offer offer)
    {
    	String sender = "sheelmaaayaa@sheelmaaayaa.appspotmail.com";
    	Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        String msgBody  = ""; 
        double price = offer.noOfKilograms*offer.pricePerKilogram; 
       if(user_type == 1)
       {  		 
    	   msgBody = "Hello " + user1.username + ", \n\n"
    	   			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
    	   			+ "You have requested " +  offer.noOfKilograms + " kilograms from "  
    	   			+ user2.username +  " with " +  price +  " euros.\n\n" 
    	   			+ "Have a nice flight,\n Sheel M3aya team";
    	   
       }
       else
       {
    	   msgBody = "Hello " + user2.username + ", \n\n"
  			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
  			+ "You have offered " +  offer.noOfKilograms + " kilograms to " 
  			+ user1.username +  " with " +  price +  " euros.\n\n"  
  			+ "Have a nice flight,\n Sheel M3aya team";
    	   
       }
       
    	try {
    		 Message msg = new MimeMessage(session);
             msg.setFrom(new InternetAddress(sender, "Sheel M3aya"));
             msg.addRecipient(Message.RecipientType.TO,
                              new InternetAddress(recepient, "Mr. User"));
             msg.setSubject("[Sheel M3aya] Transaction has been confirmed");
             msg.setText(msgBody);
             Transport.send(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			renderJSON(e);
		}
    }
    
    private static String insertConfirmationUser1(long userId, long offerId)
    {
    
		User user = User.getByKey(User.class, userId);
		Offer offer = Offer.getByKey(Offer.class, offerId);
	
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
			else if(!confirmation.getStatusTransactionUser1())
				{

				// Make the user2 in the cache
				confirmation.user2.get();
				
			if(confirmation.user2.id != user.id)
			{		
				user.get();
				sendMail(confirmation.user2.email, 0, user, confirmation.user2, offer);
				sendMail(user.email, 1, user, confirmation.user2, offer);
				
				confirmation.user1 = user;
				confirmation.statusTransactionUser1 = true;
				
				user.confirmations1.fetch().add(confirmation);
				
				confirmation.save();
				user.save();
						
				return "Success: 12";
//					return "Empty: " + user.confirmations1.fetch().isEmpty() + 
//					") Success: User1 confirms an already confirmed offer by User2";
			}// end if(confirmation.user2.id != user.id)
			else
				return "Failure: The same user1 confirms the same offer!";
			}
			//10
			else if (confirmation.getStatusTransactionUser1())
				{
					return "Failure: User1 is confirming an already confirmed offer by " +
							"another User1";
				}
			
			return "Weired from user1";
			
		} catch (Exception e) {
			// TODO: handle exception
			//00
			new Confirmation(offer, user, null, true, false, false, false).insert();
			return "Success: This confirmation is new!";
//			return  e.getStackTrace().toString() + " " + e.toString() + "\n\nSuccess: This confirmation is new!";
		}
		
    }// end insertConfirmationUser1(long userId, long offerId)

    private static String insertConfirmationUser2(long userId, long offerId)
    {
		User user = User.getByKey(User.class, userId);
		Offer offer = Offer.getByKey(Offer.class, offerId);
	
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
			else if(confirmation.getStatusTransactionUser2())
				{
					return "Failure: User2 is confirming an already confirmed offer by " +
					"another User2";
				
				}
			//10
			else if (!confirmation.getStatusTransactionUser2())
				{
				// Make the user1 in the cache
				confirmation.user1.get();
				
				if(confirmation.user1.id != user.id)
				{
					
				user.get();
				sendMail(confirmation.user1.email, 0, confirmation.user1, user, offer);
				sendMail(user.email, 1, confirmation.user1, user, offer);
				
				confirmation.user2 = user;
				confirmation.statusTransactionUser2 = true;
				
				user.confirmations2.fetch().add(confirmation);
				
				confirmation.save();
				user.save();
				
				return "Success: 13";
//					return "Empty: " + user.confirmations2.fetch().isEmpty() + 
//					") Success: User2 confirms an already confirmed offer by User1";
				}// end if(confirmation.user2.id != user.id)
				else
					return "Failure: The same user2 confirms the same offer!";
				}
			
			return "Weired from user2";
			
		} catch (Exception e) {
			// TODO: handle exception
			//00
			new Confirmation(offer, null, user, false, true, false, false).insert();
			return "Success2: This confirmation is new!";
		}
    }// end insertConfirmationUser2(long userId, long offerId)
    
}// end class Confirmations


/**
 * http://groups.google.com/group/play-framework/browse_thread/thread/32c0c387c68ee30
 * http://groups.google.com/group/play-framework/browse_thread/thread/35053d43ff2fc510?pli=1
 * http://groups.google.com/group/siena-discuss/browse_thread/thread/d9484404594329f1
 * 
 * http://www.ehow.com/how_6011212_send-sms-using-java-applications.html
*/