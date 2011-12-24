package controllers;

import models.Confirmation;
import models.Flight;
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
     * user_status 0 > Less weight (insertConfirmation2), 1 > More weight (insertConfirmation1)
     */
	
	/**
	 * Confirmation Statuses
	 */
	
	private final static String alreadyConfirmed = "alreadyConfirmed";
	private final static String confirmedByAnotherPerson = "confirmedByAnotherPerson";
	public final static String half_confirmed_offerOwner = "half_confirmed_1";
	public final static String half_confirmed_offerOther = "half_confirmed_2";
	
    public static synchronized void insertConfirmation(String facebookID, long offerId)
    {
    		
    	insertConfirmationUser1(facebookID, offerId);
    	    		    		
    }
        
    /**
     * @author Hossam_Amer
     * @param sender The sender e-mail address
     * @param recepient The recipient e-mail address
     * @param user_type The type of user, 0 > Less w8t, 1 > More w8t
     * Sends mail thru the server
     */
    
  private static void  sendMail(String offerOwnerEmail, String offerOtherEmail, int offerStatus, User user1, 
		  User user2, Offer offer, Flight flight) {
		
        String msgBodyOfferOwner  = "";
        String msgBodyOther  = "";
        double price = offer.noOfKilograms*offer.pricePerKilogram;
        
        // More weight
       if(offerStatus == 1)
       {  		 
    	   msgBodyOfferOwner = "Hello " + user1.username + ", \n\n"
    	   			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
    	   			+ "You have requested " +  offer.noOfKilograms + " kilograms from "  
    	   			+ user2.username +  " with " +  price +  " euros on flight " + flight.getFlightNumber() + " and date: " 
    	   			+ flight.getDepartureDate() + ".\n\n" 
    	   			+ "Have a nice flight,\n Sheel M3aya team";
    	   
    	   msgBodyOther = "Hello " + user2.username + ", \n\n"
 			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
 			+ "You have offered " +  offer.noOfKilograms + " kilograms to " 
 			+ user1.username +  " with " +  price +  " euros on flight " + flight.getFlightNumber() + " and date: " 
   			+ flight.getDepartureDate() + ".\n\n"  
 			+ "Have a nice flight,\n Sheel M3aya team";

    	   
       }
       else
       {
    	   msgBodyOfferOwner = "Hello " + user1.username + ", \n\n"
			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
			+ "You have offered " +  offer.noOfKilograms + " kilograms to " 
			+ user2.username +  " with " +  price +  " euros on flight " + flight.getFlightNumber() + " and date: " 
   			+ flight.getDepartureDate() + ".\n\n"  
			+ "Have a nice flight,\n Sheel M3aya team";
    		  
			msgBodyOther =  "Hello " + user2.username + ", \n\n"
  			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
  			+ "You have requested " +  offer.noOfKilograms + " kilograms from "  
  			+ user1.username +  " with " +  price +  " euros on flight " + flight.getFlightNumber() + " and date: " 
   			+ flight.getDepartureDate() + ".\n\n" 
  			+ "Have a nice flight,\n Sheel M3aya team"; 
    	   
       }
       
       sendMail(msgBodyOfferOwner, offerOwnerEmail);
       sendMail(msgBodyOther, offerOtherEmail);
       

	}
    
    private static void sendMail(String msgBody, String recepient) {
		// TODO Auto-generated method stub
    	try {
        	
    		String sender = "sheelmaaayaa@sheelmaaayaa.appspotmail.com"; 
    		Properties props = new Properties();
    		Session session = Session.getDefaultInstance(props, null);
   		 	Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sender, "Sheel M3aya"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(recepient, "Mr. User"));
            msg.setSubject("[Sheel M3aya] Transaction has been confirmed");
            msg.setText(msgBody);
            Transport.send(msg);
	
    	} catch (Exception e) {

			e.printStackTrace();
			renderJSON(e);
		}
		
	}
    
    private static void insertConfirmationUser1(String facebookID, long offerId)
    {
    
    	User user = User.all(User.class).filter("facebookAccount", facebookID).fetch().get(0);
		Offer offer = Offer.getByKey(Offer.class, offerId);
		offer.get();
		User offerOwner = offer.user;
		
		try {
			
			Confirmation confirmation = 
				Confirmation.all(Confirmation.class).
				filter("offer", offer).fetch().get(0);
			
			// 11
			if (confirmation.getStatusTransactionUser1() 
					&& confirmation.getStatusTransactionUser2())
			{
				renderJSON(alreadyConfirmed);
//				return "Failure: This offer has been already confirmed by two users";
			} //01

			// If offer owner did not confirm the offer yet > Make him confirm it
			else if(!confirmation.getStatusTransactionUser1() && offerOwner.id == user.id)
				{
				
				// Make the user2 in the cache
				confirmation.user2.get();
				
				user.get();
				offer.flight.get();
				
				sendMail(user.email, confirmation.user2.email, offer.userStatus, user, confirmation.user2, offer, offer.flight);
				
				
				confirmation.user1 = user;
				confirmation.statusTransactionUser1 = true;
				
				user.confirmations1.fetch().add(confirmation);
				
				offer.get();
				offer.offerStatus = "confirmed_1";
				
				offer.save();
				confirmation.save();
				user.save();
				
				//Get everything about the confirmation
				confirmation.get();
				confirmation.user1.get();
				confirmation.user2.get();
				confirmation.offer.get();
				confirmation.offer.flight.get();		
				
				renderJSON(confirmation);
				
//				return "Success: 12";
				//	return "Empty: " + user.confirmations1.fetch().isEmpty() + 
				//") Success: User1 confirms an already confirmed offer by User2";
			}
			// If someone else wants to confirm an offer declared > make him confirm it
			else if(!confirmation.getStatusTransactionUser2() && offerOwner.id != user.id)
			{
				// Make sure that one of the user is an offer owner
				offer.user.get();
				confirmation.user1.get();
									
					user.get();
					offer.flight.get();
					
					sendMail(confirmation.user1.email, user.email, offer.userStatus, confirmation.user1, user, offer, offer.flight);
					
					
					confirmation.user2 = user;
					confirmation.statusTransactionUser2 = true;
					
					user.confirmations2.fetch().add(confirmation);
					
					offer.get();
					offer.offerStatus = "confirmed_2";
					
					offer.save();
					confirmation.save();
					user.save();
			
					//Get everything about the confirmation
					confirmation.user1.get();
					confirmation.user2.get();
					confirmation.offer.get();
							
					renderJSON(confirmation);

//					return "Success: 13";
					//					return "Empty: " + user.confirmations2.fetch().isEmpty() + 
					//					") Success: User2 confirms an already confirmed offer by User1";

			}//end Success States		
			// Not From Owner Status
			else 
				renderJSON(confirmedByAnotherPerson);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			//00
						
			Confirmation confirmation;
			
		if(offerOwner.id == user.id)	
			{
				offer.get();
				offer.offerStatus = half_confirmed_offerOwner;
				offer.save();
				
				confirmation = new Confirmation(offer, user, null, true, false, false, false);
				confirmation.insert();
				confirmation.user1.get();
			}
		else
		{
			offer.get();
			offer.offerStatus = half_confirmed_offerOther;
			offer.save();
			
			confirmation = new Confirmation(offer, null, user, false, true, false, false);
			confirmation.insert();
			confirmation.user2.get();
		}
			//Get everything about the confirmation
			confirmation.offer.get();
			confirmation.offer.flight.get();
			
			renderJSON(confirmation);
			
//			return "Success: This confirmation is new!";
//			return  e.getStackTrace().toString() + " " + e.toString() + "\n\nSuccess: This confirmation is new!";
		}
		
    }// end insertConfirmationUser1(long userId, long offerId)
    
}// end class Confirmations


/**
 * http://groups.google.com/group/play-framework/browse_thread/thread/32c0c387c68ee30
 * http://groups.google.com/group/play-framework/browse_thread/thread/35053d43ff2fc510?pli=1
 * http://groups.google.com/group/siena-discuss/browse_thread/thread/d9484404594329f1
 * 
 * http://www.ehow.com/how_6011212_send-sms-using-java-applications.html
*/