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
    		
    		if (user_status == 1) // More weight
    		{
    			//there is a confirmation 
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
    
    private static void sendMail(String sender, String recepient, int user_type)
    {
    	Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        String msgBody  = ""; 
        
       if(user_type == 1)
    	  msgBody = "More w8t";
       else
    	   msgBody = "Less w8t";
       
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
			else if(!confirmation.getStatusTransactionUser1() 
					&& confirmation.getStatusTransactionUser2())
				{

				sendMail("hossam.amer12@gmail.com", "hossam.amer12@gmail.com", 0);
				sendMail("hossam.amer12@gmail.com", "hossam.amer12@gmail.com", 1);
				
				confirmation.user1 = user;
				confirmation.statusTransactionUser1 = true;
				
				user.confirmations1.fetch().add(confirmation);
				
				confirmation.save();
				user.save();
						
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
			
			return "Weired from user1";
			
		} catch (Exception e) {
			// TODO: handle exception
			//00
			new Confirmation(offer, user, null, true, false, false, false).insert();
			return "Success: This confirmation is new!";
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
				
				confirmation.user2 = user;
				confirmation.statusTransactionUser2 = true;
				
				user.confirmations2.fetch().add(confirmation);
				
				confirmation.save();
				user.save();
				
				sendMail("hossam.amer12@gmail.com", "hossam.amer12@gmail.com", 0);
				sendMail("hossam.amer12@gmail.com", "hossam.amer12@gmail.com", 1);
					
					return "Empty: " + user.confirmations2.fetch().isEmpty() + 
					") Success: User2 confirms an already confirmed offer by User1";
					
				}
			
			return "Weired from user2";
			
		} catch (Exception e) {
			// TODO: handle exception
			//00
			new Confirmation(offer, null, user, false, true, false, false).insert();
			return "Success2: This confirmation is new!";
		}
    }// end insertConfirmationUser2(long userId, long offerId)

    
}