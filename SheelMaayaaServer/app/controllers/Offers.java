package controllers;

import play.*;
import play.mvc.*;
import siena.DateTime;
import siena.Query;

import java.util.*;


import models.*;

public class Offers extends Controller {
	

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


	public static String getOffersByFlightNumber(String flightNumber, String date, int userStatus){
		
		try{
			
			List<Flight>flights = Flights.getFlightsByFlightNumber(flightNumber, date);
				
			String offerString = "";
			
			List<Offer> offers = new ArrayList<Offer>();
			
			for(int i = 0; i<flights.size();i++)
			{
				offers.addAll(flights.get(i).offers.filter("userStatus", userStatus).fetch());	
			}
			
			for(int i = 0; i<offers.size();i++)
			{
				offerString +=  offers.get(i).toString();
				
			}
			return offerString;
			
		}catch(Exception e){
			return e.toString();
		}
	}
	
	public static String getOffersByAirports(String source, String destination, String date, int userStatus){
		
		try{
			
			List<Flight>flights = Flights.getFlightsByAirports(source, destination, date);
				
			String offerString = "";
			
			List<Offer> offers = new ArrayList<Offer>();
			
			for(int i = 0; i<flights.size();i++)
			{
				offers.addAll(flights.get(i).offers.filter("userStatus", userStatus).fetch());	
			}
			
			for(int i = 0; i<offers.size();i++)
			{
				offerString +=  offers.get(i).toString();
			}
			return offerString;
			
		}catch(Exception e){
			return e.toString();
		}
	}
	
	public static String filterFlightNumberOffers(String flightNumber, String date, int userStatus, int kgs, int price, 
									String gender, String nationality){
		
		try{

			List<Flight>flights = Flights.getFlightsByFlightNumber(flightNumber, date);
			
			String offerString = "";
			
			List<Offer>offers = filterPreferences(flights, userStatus, kgs, price, gender, nationality);
			
			for(int i = 0; i<offers.size();i++)
			{
				offerString +=  offers.get(i).toString();
			}
			return offerString;
			
		}catch(Exception e){
			return e.toString();
		}
		
	}
	
	public static String filterAirportsOffers(String source, String destination, String date, int userStatus, int kgs, 
										int price, String gender, String nationality){
		
		try{
		
			List<Flight>flights = Flights.getFlightsByAirports(source, destination, date);
		
			String offerString = "";
			
			List<Offer>offers = filterPreferences(flights, userStatus, kgs, price, gender, nationality);
			
			for(int i = 0; i<offers.size();i++)
			{
				offerString +=  offers.get(i).toString();
			}
			return offerString;
			
			}catch(Exception e){
				return e.toString();
			}
		
		}
	
	public static List<Offer> filterPreferences(List<Flight> flights, int userStatus, int kgs, int price, 
											String gender, String nationality){
		try{
			
			List<Offer> offersList = new ArrayList<Offer>();
			
			Query<Offer> offers;

			
			for(int i = 0; i<flights.size();i++)
			{
				offers = flights.get(i).offers.filter("userStatus", userStatus);
				
				if(kgs != 0){ 
						if(userStatus == 0)
							offers.filter("noOfKilograms!=", kgs);
				
						else 
							offers.filter("noOfKilograms!=", kgs);
				}
				
				if(price != 0){
						if(userStatus == 0)
							offers.filter("pricePerKilogram <= ", price);
				
						else
							offers.filter("pricePerKilogram >= ", price);
				}
				
				//if(kgs != 0 && userStatus == 0)
					//offers.order("-noOfKilograms");
				offersList = offers.fetch();
				
			}
			
			
			
			return offersList;
			
		}catch(Exception e){
				return null;
		}
	}
}
