package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import models.Confirmation;
import models.Flight;
import models.Offer;
import models.OfferHelper;
import models.User;
import play.mvc.Controller;
import siena.Query;

import com.google.gson.Gson;

public class Offers extends Controller {
	
	/**
	 * Returns all offers in the system
	 * @return
	 */
	public static String view(){
		List<Offer> offers = Offer.all(Offer.class).fetch();
		String x = "";
		for(int i = 0 ; i < offers.size() ; i++)
		{
			x+= offers.get(i).toString()+"\n";
		}
		return x;
	}
	
	/**
	 * @Author mohsen
	 * Gets as an input the offer and it's flight data and edits them in the database.
	 */
	public static String editOffer(){
		String input = "";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(request.body));
			input = br.readLine();
			
			
			//	String jsonFlight = input.split("<>")[0];
			//	String jsonOffer = input.split("<>")[1];
				 
				 
				Gson gson = new Gson();
				//Flight flightIn = gson.fromJson(jsonFlight, Flight.class);
				Offer offerIn = gson.fromJson(input, Offer.class);
			///	Flight flightDb = Flight.getByKey(Flight.class, flightIn.id);
				Offer offerDb = Offer.getByKey(Offer.class,offerIn.id);
				try{
				offerDb.setOfferStatus(offerIn.getOfferStatus());
				offerDb.setNoOfKilograms(offerIn.getNoOfKilograms());
				offerDb.setPricePerKilogram(offerIn.getPricePerKilogram());
				offerDb.setUserStatus(offerIn.getUserStatus());
				offerDb.save();
				
		//		flightDb.setDepartureDate(flightIn.getDepartureDate());
		//		flightDb.setDestination(flightIn.getDestination());
		//		flightDb.setFlightNumber(flightIn.getFlightNumber());
		//		flightDb.setSource(flightIn.getSource());
		//		flightDb.save();
				
				
				return "OK";
				}
				catch(Exception e){
					return "Exception here y " +e.toString()+input+offerDb;
				}
			
		}
		catch(Exception e){
			return "Exception here z "+ e.toString()+input;
		}
		
		 
		
		
		
	}
	
	
	/**
	 * @author mohsen
	 * @changes offer status to 'deactivated' if applicable
	 */
	public static String deactivateOffer(String x){
		 
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(request.body));
			String input = br.readLine();
			long offerId = Integer.parseInt(input);
			Offer offer = Offer.getByKey(Offer.class, offerId);
			offer.setOfferStatus("deactivated");
			offer.save();
			return "OK";
			
		}catch(Exception e){
			return e.toString();
		}
	}
	
	/**
	 * @Author Mohsen
	 * @param uid
	 * @return OK for success, and an exception for failure
	 * Gets its offer and flight information (JSON) over HTTP-POST for security.
	 */
	public static String insertNewOffer(String uid){
		
		BufferedReader br = new BufferedReader(new InputStreamReader(request.body));
		
		try{
			String input = br.readLine();
			String jsonFlight = input.split("<>")[0];
			String jsonOffer = input.split("<>")[1];
			 
			Gson gson = new Gson();
			Flight f = gson.fromJson(jsonFlight, Flight.class);
		
			
			List<Flight>flights = Flight.all(Flight.class).filter("source",f.source).filter("destination", f.destination)
			.filter("departureDate",f.departureDate).filter("flightNumber", f.flightNumber).fetch();
			if(flights.size()==0){
				 f.insert();
			}
			else{
				f = flights.get(0);
			}
			
			Offer offer = gson.fromJson(jsonOffer, Offer.class);
			offer.setFlight(f);
			
			///////////////////////////////////////// SHOULD GET USER INFO FROM SERVER AFTER LOGIN ////////////////////////////////
			
			try{
				if(!uid.equals("0")){
				offer.setUser(User.all(User.class).filter("facebookAccount", uid).fetch().get(0));
				}
				else{
					offer.setUser(User.all(User.class).get());
				}
			}catch(Exception e){
			
				offer.setUser(User.all(User.class).get());	//Assign offer to first user in the database
				//This will be used in case of failure of registration on the client side
				
			}
			
			
			
			///////////////////////////////////////// SHOULD GET USER INFO FROM SERVER AFTER LOGIN ////////////////////////////////
			offer.insert();
			return "OK";
		}
		catch(Exception e){
			return e.toString();
		}
		
		
	}

	public static String insertOffer(long userId, long flightId, int kgs, 
			int price, int userStatus){
		
		try{
		User user = User.getByKey(User.class, userId);
		Flight flight = Flight.getByKey(Flight.class, flightId);

		Offer offer = new Offer(user, flight, kgs, price, userStatus, "new");
		offer.insert();
		return "Success";
		}catch(Exception e){
			return e.toString();
		}
	}


	/**
	 * Search offers with a given flight number, date
	 * @author Magued
	 */
	public static void filterFlightNumberOffers(String flightNumber, String date, int userStatus, int kgs, int price, 
									String gender, String nationality){
		
		try{

			List<Flight>flights = Flight.all(Flight.class)
										.filter("flightNumber", flightNumber).filter("departureDate", date).fetch();

			List<Offer>offers = offerFilterPreferences(flights, userStatus, kgs, price, gender, nationality);

			Offer offer;
				
			for(int i = 0 ; i < offers.size() ; i++){
				
				offer = offers.get(i);
				
				offer.flight.get();
				offer.user.get();
			}
			
			renderJSON(offers);
		
		}catch(Exception e){
		}
		
	}
	
	/**
	 * Search offers with a given source, destination, date
	 * @author Magued
	 */
	public static void filterAirportsOffers(String source, String destination, String date, int userStatus, int kgs, 
										int price, String gender, String nationality){
		
		try{
		
			List<Flight>flights = Flight.all(Flight.class).filter("source", source).
								filter("destination", destination).filter("departureDate", date).fetch();
			
			List<Offer>offers = offerFilterPreferences(flights, userStatus, kgs, price, gender, nationality);
			
			Offer offer;
				
			for(int i = 0 ; i < offers.size() ; i++){
				
				offer = offers.get(i);
				
				offer.flight.get();
				offer.user.get();
			}
			
			renderJSON(offers);
			
			
			}catch(Exception e){
				
			}
	}
	
	/**
	 * Filters offers with a given userStatus, kgs, price
	 * @author Magued
	 */
	private static List<Offer> offerFilterPreferences(List<Flight> flights, int userStatus, int kgs, int price, 
											String gender, String nationality){
		try{
		
			List<Offer> offersList = new ArrayList<Offer>();
			
			Query<Offer> offers;
		
			for(int i = 0; i<flights.size();i++)
			{
				offers = flights.get(i).offers.filter("userStatus", userStatus).filter("offerStatus", "new"); 
		
				offersList.addAll(offers.fetch());
			}
			
			if(kgs != 0)
				offersList = filterByKgs(offersList, userStatus, kgs);	
			
			if(price != 0)
				offersList = filterByPrice(offersList, userStatus, price);
			
			offersList = sortOffersByKgs(offersList, userStatus);
			
			if(gender.equals("both") && nationality.equals("none"))
				return offersList;
				
			return userFilterPreferences(offersList, gender, nationality);
			
		}catch(Exception e){
				return null;
		}
	}
	
	/**
	 * Filters offers with a given kgs
	 * @author Magued
	 */
	private static List<Offer> filterByKgs(List<Offer> offersList, int userStatus , int kgs){
		
		try{
			
			List<Offer> filteredOffers = new ArrayList<Offer>();
			
			for(int i = 0 ; i < offersList.size() ; i++){
				
				Offer offer = offersList.get(i);
				
				if(userStatus == 0 && offer.noOfKilograms >= kgs)
					filteredOffers.add(offer);
				
				else if(userStatus == 1 && offer.noOfKilograms <= kgs)
					filteredOffers.add(offer);
			}
			
			return filteredOffers;
			
		}catch(Exception e){
			return null;}
	}
	
	/**
	 * Filters offers with a given price
	 * @author Magued
	 */
	private static List<Offer> filterByPrice(List<Offer> offersList, int userStatus , int price){
		
		try{
			
			List<Offer> filteredOffers = new ArrayList<Offer>();
			
			for(int i = 0 ; i < offersList.size() ; i++){
				
				Offer offer = offersList.get(i);
				
				if(userStatus == 0 && offer.pricePerKilogram <= price)
					filteredOffers.add(offer);
				
				else if(userStatus == 1 && offer.pricePerKilogram >= price)
					filteredOffers.add(offer);
			}
			
			return filteredOffers;
			
		}catch(Exception e){
			return null;}
	}
	
	/**
	 * Filters offers with a given gender, nationality
	 * @author Magued
	 */
	private static List<Offer> userFilterPreferences(List<Offer> offers, String gender, String nationality){
		try{
		
			User user;
			List<Offer> userFilteredOffers = new ArrayList<Offer>();
		
			for(int i = 0 ; i < offers.size() ; i++){
		
				user = offers.get(i).getUser();
				
				user.get();
		
				if(!gender.equals("both") && !nationality.equals("none")){
					if(gender.equals(user.gender) && nationality.equals(user.nationality))
						userFilteredOffers.add(offers.get(i));
				}
		
				else if(!gender.equals("both") && nationality.equals("none")){
					if(gender.equals(user.gender))
						userFilteredOffers.add(offers.get(i));

				}
		
				else if(gender.equals("both") && !nationality.equals("none")){
					if(nationality.equals(user.nationality))
						userFilteredOffers.add(offers.get(i));
				}
			}
		
			return userFilteredOffers;
		
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Sorts offers according to kgs
	 * @author Magued
	 */
	private static List<Offer> sortOffersByKgs(List<Offer> offers, int userStatus){
		
		try{
			if(userStatus == 0){
				Collections.sort(offers, new Comparator(){ 
					public int compare(Object o1, Object o2) {
						Offer offer1 = (Offer) o1;
						Offer offer2 = (Offer) o2;
	               
						if(offer1.noOfKilograms > offer2.noOfKilograms)
							return 1;
						else if(offer1.noOfKilograms < offer2.noOfKilograms)
							return -1;
	               
						return 0; } });
			}
			
			else{
				Collections.sort(offers, new Comparator(){ 
					public int compare(Object o1, Object o2) {
						Offer offer1 = (Offer) o1;
						Offer offer2 = (Offer) o2;
               
						if(offer1.noOfKilograms > offer2.noOfKilograms)
							return -1;
						else if(offer1.noOfKilograms < offer2.noOfKilograms)
							return 1;
               
						return 0;} }); 
				}
			
			return offers;
			
		}catch(Exception e){
			return null;}
	}
	
	/**
	 * Gets the offers declared by the user.
	 * 
	 * Offers of type:
	 * - New offers I declared
	 * - Half confirmed offers I declared (I am Offer owner)
	 * - Half confirmed offers I confirmed but not declared by me
	 * 	(I am not offer owner)
	 * 
	 * @param facebookID The Facebook ID of the user in the database.
	 * @author Hossam_Amer
	 */
public static void getMyOffers(String facebookID)
{
		
		User user = User.all(User.class).filter("facebookAccount", facebookID).fetch().get(0);
		List <Offer> offers = (List<Offer>) Offer.all(Offer.class).filter("user", user).fetch();
		List<OfferHelper> offersHelper = new ArrayList<OfferHelper>();
		List<Confirmation> confirmations1 = new ArrayList<Confirmation>();
		List<Confirmation> confirmations2 = new ArrayList<Confirmation>();
		
		List<Confirmation> confirmationsOfferID = new ArrayList<Confirmation>();
		
			// Try to get his confirmations as offer owner. (Half Confirmed-Me-Declared)
			// This contains all offers the user participated as an offer owner in. (but probably that user did not confirm yet)
			// Another case is that it might have another empty side
			confirmations1 = (List<Confirmation>) Confirmation.all(Confirmation.class).filter("user1", user).fetch();
		
			boolean found = false;
			// First we fetch the offers that the user declared 
			// and try to get its right side.
			for (Offer offer: offers)
			{
				// Every time the offer I iterate on is not intially found in the confirmation table
				found = false;
				
				for (Confirmation conf: confirmations1)
				{
					conf.get(); conf.offer.get();
					
					// If the offer I currently searching for a confirmation is found
					if(conf.offer.id == offer.id)
					{
						// I set the flag to true, that means that I found the record in the confirmation
						found = true;
						
						//	I will try to get the right side.
						// The case now is I am on the left side, and gamby fady or gamby full
						try
						{
							// If I succeded to get my right side, then I can fetch the other user
							conf.user2.get();
							
							// Then, I will get the offer with its user + flight
							conf.offer.get(); conf.offer.flight.get(); conf.offer.user.get();
							
							OfferHelper offerHelper = new OfferHelper(conf.offer, conf.user2);
							offersHelper.add(offerHelper);
						}// end try
						catch(Exception e)
						{
							// I failed to get my right side, then it is either I half-confirmed the offer, or it is new
							
							// Then, I will get the offer with its user + flight
							conf.offer.get(); conf.offer.flight.get(); conf.offer.user.get();	
							
							OfferHelper offerHelper = new OfferHelper(conf.offer, null);
							offersHelper.add(offerHelper);
							
						}// end catch
							
							// I found the record, no need to continue searching.
							break;
						
					}//end if
					
				}// end inner loop
				
				// if i did not find the offer I have in the confirmation table, that means the offer is still new
				if(!found)
				{
					// I will add it with a no right side.
					// I will first fetch the offer I am looping on
					offer.get(); offer.flight.get(); offer.user.get();
					OfferHelper offerHelper = new OfferHelper(offer, null);
					offersHelper.add(offerHelper);
				}
				
			}// end outer loop
			
		//========================
		
		// Will add the previously got offers, that offers I participated as other
		confirmations2 = (List<Confirmation>) Confirmation.all(Confirmation.class).filter("user2", user).fetch();
			if(!confirmations2.isEmpty())
			{
				for(Confirmation conf: confirmations2)
				{
					
					// Will fetch the confirmation + its offer + flight's offer + offer owner
					conf.get(); conf.offer.get(); conf.offer.flight.get(); conf.offer.user.get();

						// I will add it with a no right side.
					OfferHelper offerHelper = new OfferHelper(conf.offer, conf.offer.user);
					offersHelper.add(offerHelper);
					
				}// end loop
			}// end	if(!confirmations2.isEmpty) 
					
			renderJSON(offersHelper);
}//end method


//	public static void getMyOffers(String facebookID){
//		
//		User user = User.all(User.class).filter("facebookAccount", facebookID).fetch().get(0);
//		List <Offer> offers = (List<Offer>) Offer.all(Offer.class).filter("user", user).fetch();
//		List<OfferHelper> offersHelper = new ArrayList<OfferHelper>();
//		
//		//==================Method One========================================
//		 // Create the list of confirmations
//			List <Confirmation> confirmations = new ArrayList<Confirmation>();
//			try
//			{
//				confirmations =  (List<Confirmation>) 
//					Confirmation.all(Confirmation.class).fetch();
//				
//				for (Confirmation confirmation: confirmations)
//				{
//					try
//					{
//						confirmation.user2.get();
//					
//						// If I am not offer owner + I confirmed this offer	
//						if(confirmation.user2.id.
//								equals(user.id))
//						{
//							confirmation.offer.get();
//							confirmation.offer.flight.get();
//
//							// Try to get the the offer owner of the offer
//							confirmation.offer.user.get();
//							
//							OfferHelper offerHelper = new OfferHelper(confirmation.offer, confirmation.offer.user);
//							offersHelper.add(offerHelper);
//						}//end if
//					}//end try
//					catch (Exception e) {
//						// TODO: handle exception
//						continue;
//					}// end catch	
//				}// end for (Confirmation confirmation: confirmations)
//			
//			}
//			catch (Exception e) {
//				;
//			}
//			
//			
//			//==================Method Two====================================
//			// Create the list of confirmations
//			confirmations = new ArrayList<Confirmation>();
// 
//			try
//			{
//				// Try to get any confirmation
//				confirmations =  (List<Confirmation>) 
//					Confirmation.all(Confirmation.class).fetch();
//			}
//			catch (Exception e) {
//				
//				// If no confirmation > Offer is new > Not confirmed
//				// Set the offerHelper with the offer and a null on the side
//				for(Offer offer: offers)
//				{
//					offer.get();
//					offer.user.get();
//					offer.flight.get();
//					offersHelper.add(new OfferHelper(offer, null));
//					
//					renderJSON(offersHelper);
//					return;
//				}
//				
//			}
//			
//			// Create the new list of the offersHelper
//			
//			for(Offer offer: offers)
//			{
//				for(Confirmation confirmation: confirmations)
//				{
//					OfferHelper offerHelper = null;
//					// Try to find the other user in the confirmation
//					// If he participates he should appear in the confirmation table
//					try
//					{
//						// Try to get the offer id to prove participation
//						confirmation.offer.get();
//						
//						// If the confirmation is related to this offer > add the other user
//						if(offer.id == confirmation.offer.id)
//						{
//							// Get the other user to put it in the array list
//							confirmation.user2.get();
//							offer.get();
//							offer.user.get();
//							offer.flight.get();
//							offerHelper = new OfferHelper(offer, confirmation.user2);
//
//							// add the new object
//							offersHelper.add(offerHelper);
//
//							// Save the offerHelper to be easily retrieved later on
////							offerHelper.save();
//						}
//					}
//					catch (Exception e) {
//						
//						// if there is no confirmation > then no other user yet > add the offer with the default add
//						offer.get();
//						offer.user.get();
//						offer.flight.get();
//						offerHelper = new OfferHelper(offer, null);
//
//						// add the new object
//						offersHelper.add(offerHelper);
//						
//						// Save the offerHelper to be easily retrieved later on
////						offerHelper.save();
//					}
//					
//					
//				}//end inner loop
//			}//end outer loop
//			
//			renderJSON(offersHelper);
//	}
	
	/**
	 * 
	 * @param user User logged in on Sheel Ma3aya
	 * @return Offers half-confirmed by me and not declared by me and their corresponding offer owner.
	 * @author Hossam_Amer
	 */

	private static List<OfferHelper> 
	getAllOffersHalfConfirmedByMeNotDeclaredByMe (User user) 
	{
	
		// Create the list of confirmations
		List <Confirmation> confirmations = new ArrayList<Confirmation>();
		try
		{
			// Try to get the list of confirmations 
			confirmations =  (List<Confirmation>) 
				Confirmation.all(Confirmation.class).fetch();
		}
		catch (Exception e) {
			// If there are no confirmations, that means I did not participate yet.
			return new ArrayList<OfferHelper>();
		}
		
		// Create the new list of the offersHelper
		List<OfferHelper> offersHelper = new ArrayList<OfferHelper>(); 
		
			for (Confirmation confirmation: confirmations)
			{
				try
				{
					// Since I confirmed, I should appear as a part of the confirmation
					confirmation.user2.get();
				
					// If I am not offer owner + I confirmed this offer	
					if(confirmation.user2.id.
							equals(user.id))
					{
						confirmation.offer.get();
						confirmation.offer.flight.get();

						// Try to get the the offer owner of the offer
						confirmation.offer.user.get();
						
						OfferHelper offerHelper = new OfferHelper(confirmation.offer, confirmation.offer.user);
						
						// Save the offerHelper to be easily retrieved later on
//						offerHelper.save();
						
						
						offersHelper.add(offerHelper);
					}//end if
				}//end try
				catch (Exception e) {
					// TODO: handle exception
					continue;
				}// end catch	
			}// end for (Confirmation confirmation: confirmations)
		
		return offersHelper;
	}// end getAllOffersHalfConfirmedByMeNotDeclaredByMe
	
	
	/**
	 * 
	 * @param user User logged in on Sheel Ma3aya
	 * @param List<Offer> offersIDeclared All offers that user declared
	 * @return Offers I am the offer owner in
	 * @author Hossam_Amer
	 */

	private static List<OfferHelper> 
	getAllOffersIAmOfferOwner (User user, List<Offer> offersIDeclared) 
	{
	
		// Create the list of confirmations
		List <Confirmation> confirmations = new ArrayList<Confirmation>();
		List<OfferHelper> offersHelper = new ArrayList<OfferHelper>(); 
		try
		{
			// Try to get any confirmation
			confirmations =  (List<Confirmation>) 
				Confirmation.all(Confirmation.class).fetch();
		}
		catch (Exception e) {
			
			// If no confirmation > Offer is new > Not confirmed
			// Set the offerHelper with the offer and a null on the side
			return getAllOffersNew(user, offersIDeclared);
			
		}
		
		// Create the new list of the offersHelper
		
		for(Offer offer: offersIDeclared)
		{
			for(Confirmation confirmation: confirmations)
			{
				OfferHelper offerHelper = null;
				// Try to find the other user in the confirmation
				// If he participates he should appear in the confirmation table
				try
				{
					// Try to get the offer id to prove participation
					confirmation.offer.get();
					
					// If the confirmation is related to this offer > add the other user
					if(offer.id == confirmation.offer.id)
					{
						// Get the other user to put it in the array list
						confirmation.user2.get();
						offer.get();
						offer.user.get();
						offer.flight.get();
						offerHelper = new OfferHelper(offer, confirmation.user2);

						// Save the offerHelper to be easily retrieved later on
//						offerHelper.save();
					}
				}
				catch (Exception e) {
					
					// if there is no confirmation > then no other user yet > add the offer with the default add
					offer.get();
					offer.user.get();
					offer.flight.get();
					offerHelper = new OfferHelper(offer, null);
					
					// Save the offerHelper to be easily retrieved later on
//					offerHelper.save();
				}
				
				// add the new object
				offersHelper.add(offerHelper);
				
			}//end inner loop
		}//end outer loop
		
		
		return offersHelper;
	}// end getAllOffersIAmOfferOwner
	
//	public static void 
//	test123 (String fb) 
//	{
//	
//		User user = User.all(User.class).filter("facebookAccount", fb).fetch().get(0);
//		List <Confirmation> confirmations = new ArrayList<Confirmation>();
//		try
//		{
//			confirmations =  (List<Confirmation>) 
//				Confirmation.all(Confirmation.class).fetch();
//		}
//		catch (Exception e) {
//			renderJSON(new ArrayList<Offer>());
//		}
//		
//		List<Offer> offers = new ArrayList<Offer>(); 
//		
//			for (Confirmation confirmation: confirmations)
//			{
//				try
//				{
//					confirmation.user2.get();
//				
//						// If I am not offer owner + I confirmed this offer	
//					if(confirmation.user2.id.
//							equals(user.id))
//					{
//						confirmation.offer.get();
//						offers.add(confirmation.offer);
//					}//end if
//				}//end try
//				catch (Exception e) {
//					// TODO: handle exception
//					continue;
//				}// end catch	
//			}// end for (Confirmation confirmation: confirmations)
//		
//		renderJSON(offers);
//	}// end getAllOffersHalfConfirmedByMeNotDeclaredByMe
	
	
	private static List<OfferHelper> getAllOffersNew(User user,
			List<Offer> offersIDeclared) {
		
		List<OfferHelper> offersHelper = new ArrayList<OfferHelper>();
		for(Offer offer: offersIDeclared)
		{
			offer.get();
			offer.user.get();
			offer.flight.get();
			offersHelper.add(new OfferHelper(offer, null));
		}
		
		return offersHelper;
	}

	public static void 
	test123 (String fb) 
	{
		// Find the user
		User user = User.all(User.class).filter("facebookAccount", fb).fetch().get(0);
		List <Offer> offers = (List<Offer>) Offer.all(Offer.class).filter("user", user).fetch();
		
		renderJSON(new OfferHelper(offers.get(0), user));
//		 Create the list of confirmations
//		List <Confirmation> confirmations = new ArrayList<Confirmation>();
//		try
//		{
//			confirmations =  (List<Confirmation>) 
//				Confirmation.all(Confirmation.class).fetch();
//		}
//		catch (Exception e) {
//			renderJSON(new ArrayList<Offer>());
//		}
//		
//		// Create the new list of the offersHelper
//		List<OfferHelper> offersHelper = new ArrayList<OfferHelper>(); 
//		
//			for (Confirmation confirmation: confirmations)
//			{
//				try
//				{
//					confirmation.user2.get();
//				
//					// If I am not offer owner + I confirmed this offer	
//					if(confirmation.user2.id.
//							equals(user.id))
//					{
//						confirmation.offer.get();
//						confirmation.offer.flight.get();
//
//						// Try to get the the offer owner of the offer
//						confirmation.offer.user.get();
//						
//						OfferHelper offerHelper = new OfferHelper(confirmation.offer, confirmation.offer.user);
//						offersHelper.add(offerHelper);
//					}//end if
//				}//end try
//				catch (Exception e) {
//					// TODO: handle exception
//					continue;
//				}// end catch	
//			}// end for (Confirmation confirmation: confirmations)
//		
//		renderJSON(offersHelper);
	}// end getAllOffersHalfConfirmedByMeNotDeclaredByMe
	
	
	
}//end class Offers
