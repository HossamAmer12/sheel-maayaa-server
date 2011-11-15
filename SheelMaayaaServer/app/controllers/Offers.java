package controllers;

import play.*;
import play.mvc.*;
import siena.DateTime;

import java.util.*;


import models.*;

public class Offers extends Controller {

	
	public static String insertOffer(long userId, long flightId, String kgs, 
									String price, String userStatus){
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


	public static String getOffersByFlightNumber(String flightNumber, String userStatus, String date){
		
		try{
			
			List<Flight>flights = Flight.all(Flight.class).filter("flightNumber", flightNumber)
						.filter("departureDate", date).fetch();
				
			String offerString = "";
			
			List<Offer>allOffers = new ArrayList<Offer>();
			
			for(int i = 0; i<flights.size();i++)
			{
				allOffers.addAll(flights.get(i).offers.filter("userStatus", userStatus).fetch());	
			}
			
			for(int i = 0; i<allOffers.size();i++)
			{
				offerString +=  allOffers.get(i).toString();
				
			}
			return offerString;
			
		}catch(Exception e){
			return e.toString();
		}
	}
	
	public static String getOffersByAirports(String source, String destination, String userStatus, String date){
		
		try{
			
			List<Flight>flights = Flight.all(Flight.class).filter("source", source).
						filter("destination", destination).filter("departureDate", date).fetch();
				
			String offerString = "";
			
			List<Offer>allOffers = new ArrayList<Offer>();
			
			for(int i = 0; i<flights.size();i++)
			{
				allOffers.addAll(flights.get(i).offers.filter("userStatus", userStatus).fetch());	
			}
			
			for(int i = 0; i<allOffers.size();i++)
			{
				offerString +=  allOffers.get(i).toString();
			}
			return offerString;
			
		}catch(Exception e){
			return e.toString();
		}
	}
}
