package controllers;

import play.*;
import play.mvc.*;
import siena.DateTime;
import siena.Query;

import java.util.*;


import models.*;

public class Offers extends Controller {
	
	
	public static String insertNewOffer(int kgs,
										int price,
										int userStatus,
										String flightNumber,
										String source,
										String destination,
										String date){
		
		try{
			Query<Flight> flights = Flight.all(Flight.class).filter("flightNumber", flightNumber).
					filter("source",source).filter("destination", destination).filter("date", date);
			Flight flight;
			if(flights.count()==0){
				flight = new Flight(flightNumber, source, destination, date);
				flight.save();
			}
			else{
				flight = flights.fetch().get(0);
			}
			User user = User.getByKey(User.class, 13);
			
			
			
			Offer offer = new Offer(user,flight,kgs,price,userStatus,"new");
			offer.save();
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


	public static void getOffersByFlightNumber(String flightNumber, String date, int userStatus){
		
		try{
			
			List<Flight>flights = Flights.getFlightsByFlightNumber(flightNumber, date);

			List<Offer> offers = new ArrayList<Offer>();
			
			for(int i = 0; i<flights.size();i++)
			{ 
				offers.addAll(flights.get(i).offers.filter("userStatus", userStatus).fetch());	
			}
			
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
	
	public static void getOffersByAirports(String source, String destination, String date, int userStatus){
		
		try{
			
			List<Flight>flights = Flights.getFlightsByAirports(source, destination, date);
	
			List<Offer> offers = new ArrayList<Offer>();
			
			for(int i = 0; i<flights.size();i++)
			{
				offers.addAll(flights.get(i).offers.filter("userStatus", userStatus).fetch());	
			}
			
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
	
	public static List<Offer> offerFilterPreferences(List<Flight> flights, int userStatus, int kgs, int price, 
											String gender, String nationality){
		try{
		
			List<Offer> offersList = new ArrayList<Offer>();
			
			Query<Offer> offers;
		
			for(int i = 0; i<flights.size();i++)
			{
				offers = flights.get(i).offers.filter("userStatus", userStatus); 
				
			/*	if(userStatus == 0){
					if(kgs != 0)
						offers = offers.order("noOfKilograms");
					
					else if(kgs == 0 && price != 0)
						offers = offers.order("-pricePerKilogram");
				}
				
				else if(userStatus == 1){
					if(kgs != 0)
						offers = offers.order("-noOfKilograms");
					
					else if(kgs == 0 && price != 0)
						offers = offers.order("pricePerKilogram");
				}*/
				
				offersList.addAll(offers.fetch());
			}
			
			if(kgs != 0)
				offersList = filterByKgs(offersList, userStatus, kgs);	
			
			if(price != 0)
				offersList = filterByPrice(offersList, userStatus, price);
			
			if(gender.equals("both") && nationality.equals("none"))
				return offersList;
				
			return userFilterPreferences(offersList, gender, nationality);
			
		}catch(Exception e){
				return null;
		}
	}
	
	public static List<Offer> filterByKgs(List<Offer> offersList, int userStatus , int kgs){
		
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
	
	public static List<Offer> filterByPrice(List<Offer> offersList, int userStatus , int price){
		
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
	
	public static List<Offer> userFilterPreferences(List<Offer> offers, String gender, String nationality){
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
	
	

	
	
	
	
	
	
	
	
	
	
/*	if(kgs != 0){ 
			if(userStatus == 0)
				offers = offers.filter("noOfKilograms", kgs);

			else 
				offers = offers.filter("noOfKilograms", kgs);
	}
	
	if(price != 0){
			if(userStatus == 0)
				offers = offers.filter("pricePerKilogram", price);
	
			else
				offers = offers.filter("pricePerKilogram", price);		
	}	*/	
	
}
