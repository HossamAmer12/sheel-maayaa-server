package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

	
	public static String getFlightOffers(long id){
		try{
			Flight flight = Flight.getByKey(Flight.class, id);
			
			String offerString = "";
			List<Offer>offers =  flight.offers.fetch();
			for(int i = 0; i<flight.offers.count();i++)
			{
				offerString +=  offers.get(i).toString();
			}
			return offerString;
		}catch(Exception e){
			return e.toString();
		}
	}

	
	public static void filterFlightNumberOffers(String flightNumber, String date, int userStatus, int kgs, int price, 
									String gender, String nationality){
		
		try{

			List<Flight>flights = Flights.getFlightsByFlightNumber(flightNumber, date);

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
	
	public static void filterAirportsOffers(String source, String destination, String date, int userStatus, int kgs, 
										int price, String gender, String nationality){
		
		try{
		
			List<Flight>flights = Flights.getFlightsByAirports(source, destination, date);
			
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
	
	private static List<Offer> offerFilterPreferences(List<Flight> flights, int userStatus, int kgs, int price, 
											String gender, String nationality){
		try{
		
			List<Offer> offersList = new ArrayList<Offer>();
			
			Query<Offer> offers;
		
			for(int i = 0; i<flights.size();i++)
			{
				offers = flights.get(i).offers.filter("userStatus", userStatus); 
		
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
	 * Gets the offers declared by the user
	 * @param facebookID The Facebook ID of the user in the database.
	 * @author Hossam_Amer
	 */
	
	public static void getMyOffers(String facebookID){
		
//		List<Flight>flights = Flight.all(Flight.class).filter("source",f.source).filter("destination", f.destination)
//		.filter("departureDate",f.departureDate).filter("flightNumber", f.flightNumber).fetch();
		
		User user = User.all(User.class).filter("facebookAccount", facebookID).fetch().get(0);
		
//		User user = (User) User.all(User.class).filter("facebookAccount", facebookID).fetch();
		
		List <Offer> offers = (List<Offer>) Offer.all(Offer.class).filter("user", user);
		
		renderJSON(offers);
		renderJSON(user);
		
	}
	
}
