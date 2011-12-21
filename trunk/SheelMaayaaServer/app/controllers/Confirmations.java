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
     * user_status 0 > Less weight (insertConfirmation2), 1 > More weight (insertConfirmation1)
     */
	
	/**
	 * Confirmation Statuses
	 */
	
	private final static String alreadyConfirmed = "alreadyConfirmed";
	private final static String notFromOfferOwner = "notFromOfferOwner";
	private final static String notSameUser = "notSameUser";
	private final static String confirmedByAnotherPerson = "confirmedByAnotherPerson";
	
    public static synchronized void insertConfirmation(String facebookID, long offerId, int user_status)
    {
    		
    	insertConfirmationUser1(facebookID, offerId);
    	
//    		if (user_status == 1) // More weight
//    		{
//    			insertConfirmationUser1(facebookID, offerId);
//    			
//    		}// end if (user_status == 1)
//    		else
//    		{
//    			insertConfirmationUser2(facebookID, offerId);
//    		}
    		    		
    }
        
    /**
     * @author Hossam_Amer
     * @param sender The sender e-mail address
     * @param recepient The recipient e-mail address
     * @param user_type The type of user, 0 > Less w8t, 1 > More w8t
     * Sends mail thru the server
     */
    
  private static void  sendMail(String offerOwnerEmail, String offerOtherEmail, int offerStatus, User user1, User user2, Offer offer) {
		
        String msgBodyOfferOwner  = "";
        String msgBodyOther  = "";
        double price = offer.noOfKilograms*offer.pricePerKilogram;
        
        // More weight
       if(offerStatus == 1)
       {  		 
    	   msgBodyOfferOwner = "Hello " + user1.username + ", \n\n"
    	   			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
    	   			+ "You have requested " +  offer.noOfKilograms + " kilograms from "  
    	   			+ user2.username +  " with " +  price +  " euros.\n\n" 
    	   			+ "Have a nice flight,\n Sheel M3aya team";
    	   
    	   msgBodyOther = "Hello " + user2.username + ", \n\n"
 			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
 			+ "You have offered " +  offer.noOfKilograms + " kilograms to " 
 			+ user1.username +  " with " +  price +  " euros.\n\n"  
 			+ "Have a nice flight,\n Sheel M3aya team";

    	   
       }
       else
       {
    	   msgBodyOfferOwner = "Hello " + user1.username + ", \n\n"
			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
			+ "You have offered " +  offer.noOfKilograms + " kilograms to " 
			+ user2.username +  " with " +  price +  " euros.\n\n"  
			+ "Have a nice flight,\n Sheel M3aya team";
    		  
			msgBodyOther =  "Hello " + user2.username + ", \n\n"
  			+ "This is an auto confirmation from Sheel M3aya app describing details of your transaction.\n\n"
  			+ "You have requested " +  offer.noOfKilograms + " kilograms from "  
  			+ user1.username +  " with " +  price +  " euros.\n\n" 
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
				
				sendMail(user.email, confirmation.user2.email, offer.userStatus, user, confirmation.user2, offer);
				
				
				confirmation.user1 = user;
				confirmation.statusTransactionUser1 = true;
				
				user.confirmations1.fetch().add(confirmation);
				
				offer.get();
				offer.offerStatus = "confirmed";
				
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
					
					sendMail(confirmation.user1.email, user.email, offer.userStatus, confirmation.user1, user, offer);
					
					
					confirmation.user2 = user;
					confirmation.statusTransactionUser2 = true;
					
					user.confirmations2.fetch().add(confirmation);
					
					offer.get();
					offer.offerStatus = "confirmed";
					
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
			offer.get();
			offer.offerStatus = "half-confirmed";
			offer.save();
		
			Confirmation confirmation;
			
		if(offerOwner.id == user.id)	
			{
				confirmation = new Confirmation(offer, user, null, true, false, false, false);
				confirmation.insert();
				confirmation.user1.get();
			}
		else
		{
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

//    private static void insertConfirmationUser2(String facebookID, long offerId)
//    {
//    	User user = User.all(User.class).filter("facebookAccount", facebookID).fetch().get(0);
//		Offer offer = Offer.getByKey(Offer.class, offerId);
//	
//		try {
//			
//			Confirmation confirmation = 
//				Confirmation.all(Confirmation.class).
//				filter("offer", offer).fetch().get(0);
//			
//			// 11
//			if (confirmation.getStatusTransactionUser1() 
//					&& confirmation.getStatusTransactionUser2())
//			{
//				renderJSON(alreadyConfirmed);
////				return "Failure: This offer has been already confirmed by two users";
//			} //01
//			else if(confirmation.getStatusTransactionUser2())
//				{
//					renderJSON(notSameUser);
////					return "Failure: User2 is confirming an already confirmed offer by " +
////					"another User2";
//				
//				}
//			//10
//			else if (!confirmation.getStatusTransactionUser2())
//				{
//				// Make the user1 in the cache
//				confirmation.user1.get();
//				
//				if(confirmation.user1.id != user.id)
//				{
//					// Make sure that one of the user is an offer owner
//					offer.user.get();
//					confirmation.user1.get();
//					User offerOwner = offer.user;
//										
//					if(offer.user.id.equals(user.id) || offerOwner.id.equals(confirmation.user1.id))
//					{
//					
//						user.get();
//						sendMail(confirmation.user1.email, 0, confirmation.user1, user, offer);
//						sendMail(user.email, 1, confirmation.user1, user, offer);
//						
//						confirmation.user2 = user;
//						confirmation.statusTransactionUser2 = true;
//						
//						user.confirmations2.fetch().add(confirmation);
//						
//						offer.get();
//						offer.offerStatus = "taken";
//						
//						offer.save();
//						confirmation.save();
//						user.save();
//				
//						//Get everything about the confirmation
//						confirmation.user1.get();
//						confirmation.user2.get();
//						confirmation.offer.get();
//								
//						renderJSON(confirmation);
//
////						return "Success: 13";
//						//					return "Empty: " + user.confirmations2.fetch().isEmpty() + 
//						//					") Success: User2 confirms an already confirmed offer by User1";
//					}
//					else
//						renderJSON(notFromOfferOwner);
////						return "Failure: Offer owner2 is not confirming the offer: OfferOwner: " + offerOwner.id   
////						+ ", User.Id: " + user.id + ", Conf.userId: " + confirmation.user1.id
////						+ ", " + (offerOwner.id == user.id) + ", " + (offerOwner.id == confirmation.user1.id)
////						+ ", " + (offerOwner.id == user.id || offerOwner.id == confirmation.user1.id);
//				}// end if(confirmation.user2.id != user.id)
//				else
//					renderJSON(notSameUser);
////					return "Failure: The same user2 confirms the same offer!";
//				}
//			
//			renderJSON("");
////			return "Weired from user2";
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			//00
//			offer.get();
//			offer.offerStatus = "half-confirmed";
//			offer.save();
//			Confirmation confirmation = new Confirmation(offer, null, user, false, true, false, false);
//			
//			confirmation.insert();
//			
//			//Get everything about the confirmation
//			confirmation.user2.get();
//			confirmation.offer.get();
//			confirmation.offer.flight.get();
//			
//					
//			renderJSON(confirmation);
//
////			return "Success2: This confirmation is new!";
//		}
//    }// end insertConfirmationUser2(long userId, long offerId)
//    
}// end class Confirmations


/**
 * http://groups.google.com/group/play-framework/browse_thread/thread/32c0c387c68ee30
 * http://groups.google.com/group/play-framework/browse_thread/thread/35053d43ff2fc510?pli=1
 * http://groups.google.com/group/siena-discuss/browse_thread/thread/d9484404594329f1
 * 
 * http://www.ehow.com/how_6011212_send-sms-using-java-applications.html
*/