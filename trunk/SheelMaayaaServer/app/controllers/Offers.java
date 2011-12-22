package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Confirmation;
import models.Flight;
import models.Offer;
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
		}
		catch(Exception e){
			return e.toString();
		}
		
		try{
		String jsonFlight = input.split("<>")[0];
		String jsonOffer = input.split("<>")[1];
		 
		Gson gson = new Gson();
		Flight flightIn = gson.fromJson(jsonFlight, Flight.class);
		Offer offerIn = gson.fromJson(jsonOffer, Offer.class);
		Flight flightDb = Flight.getByKey(Flight.class, flightIn.id);
		Offer offerDb = Offer.getByKey(Offer.class,offerIn.id);
		
		offerDb.setOfferStatus(offerIn.getOfferStatus());
		offerDb.setNoOfKilograms(offerIn.getNoOfKilograms());
		offerDb.setPricePerKilogram(offerIn.getPricePerKilogram());
		offerDb.setUserStatus(offerIn.getUserStatus());
		offerDb.save();
		
		flightDb.setDepartureDate(flightIn.getDepartureDate());
		flightDb.setDestination(flightIn.getDestination());
		flightDb.setFlightNumber(flightIn.getFlightNumber());
		flightDb.setSource(flightIn.getSource());
		flightDb.save();
		
		return "OK";
		}
		catch(Exception e){
			return e.toString();
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
			if(offer.offerStatus.equals("new")){
				offer.setOfferStatus("deactivated");
				offer.save();
				return "OK";
			}
			else{
				return "Cannot deactivate offer";
			}
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
	public static String insertNewOffer(long uid){
		
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
				if(uid!=0){
				offer.setUser(User.getByKey(User.class, uid));
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
	
	public static void getMyOffers(String facebookID){
		
		User user = User.all(User.class).filter("facebookAccount", facebookID).fetch().get(0);
		List <Offer> offersTemp = getAllOffersHalfConfirmedByMeNotDeclaredByMe(user);
		List <Offer> offers = (List<Offer>) Offer.all(Offer.class).filter("user", user).fetch();
		
		offers.addAll(offersTemp);
		
		for (Offer offer: offers)
		{
			offer.user.get();
			offer.flight.get();
		}
		
		renderJSON(offers);
	}
	
	/**
	 * 
	 * @param user User logged in on Sheel Ma3aya
	 * @return Offers confirmed by me and declared by another user.
	 * @author Hossam_Amer
	 */

	private static List<Offer> 
	getAllOffersHalfConfirmedByMeNotDeclaredByMe (User user) 
	{
	
		List <Confirmation> confirmations = new ArrayList<Confirmation>();
		try
		{
			confirmations =  (List<Confirmation>) 
				Confirmation.all(Confirmation.class).fetch();
		}
		catch (Exception e) {
			return new ArrayList<Offer>();
		}
		
		List<Offer> offers = new ArrayList<Offer>(); 
		
			for (Confirmation confirmation: confirmations)
			{
				try
				{
					confirmation.user2.get();
				
						// If I am not offer owner + I confirmed this offer	
					if(confirmation.user2.id.
							equals(user.id))
					{
						confirmation.offer.get();
						offers.add(confirmation.offer);
					}//end if
				}//end try
				catch (Exception e) {
					// TODO: handle exception
					continue;
				}// end catch	
			}// end for (Confirmation confirmation: confirmations)
		
		return offers;
	}// end getAllOffersHalfConfirmedByMeNotDeclaredByMe
	
	
	public static void 
	test123 (String fb) 
	{
	
		User user = User.all(User.class).filter("facebookAccount", fb).fetch().get(0);
		List <Confirmation> confirmations = new ArrayList<Confirmation>();
		try
		{
			confirmations =  (List<Confirmation>) 
				Confirmation.all(Confirmation.class).fetch();
		}
		catch (Exception e) {
			renderJSON(new ArrayList<Offer>());
		}
		
		List<Offer> offers = new ArrayList<Offer>(); 
		
			for (Confirmation confirmation: confirmations)
			{
				try
				{
					confirmation.user2.get();
				
						// If I am not offer owner + I confirmed this offer	
					if(confirmation.user2.id.
							equals(user.id))
					{
						confirmation.offer.get();
						offers.add(confirmation.offer);
					}//end if
				}//end try
				catch (Exception e) {
					// TODO: handle exception
					continue;
				}// end catch	
			}// end for (Confirmation confirmation: confirmations)
		
		renderJSON(offers);
	}// end getAllOffersHalfConfirmedByMeNotDeclaredByMe
	
}//end class Offers
