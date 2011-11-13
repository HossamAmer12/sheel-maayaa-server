package controllers;

import play.*;
import play.mvc.*;
import siena.DateTime;

import java.util.*;


import models.*;

public class Offers extends Controller {

	
	public static String insertOffer(){
		try{
			User hashas = User.all(User.class).get();
			Flight flight = Flight.all(Flight.class).get();
			
			Offer offer = new Offer(hashas,flight,(float)10.0,(float)5.0,"Extra weight","NEW");
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


	public static String getOffersByFlightNumber(String flightNumber){
		
		try{
			Flight flight = Flight.all(Flight.class).search(flightNumber, "flightNumber").fetch().get(0);
			
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
}