package controllers;

import play.*;
import play.mvc.*;
import siena.DateTime;

import java.util.*;

import ClientModels.CFlight;

import models.*;

public class Application extends Controller {

	
	public static String test(){
		
		return "Test successful";
		
	}
	/*
	public static String insertFlight(CFlight flight){
		
		try{
			Flight f = new Flight(flight);
	 	    f.insert();
	 	    return "Success";
		}catch(Exception e){
			return e.toString();
		}
		
	}*/
	
	public static String getFlightByFlightNumber(String flightNumber){
		
		try{
			Flight flight = Flight.all(Flight.class).search(flightNumber, "flightNumber").fetch().get(0);
	 		return flight.toString();
		}catch(Exception e){
			return e.toString();
		}
		
	}
	

	public static String insertFlight(){
		try{
		Date dt = new Date();
		Flight f = new Flight("LH123", "Frankfurt", "Cairo", dt);
		f.insert();
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
	
	
	public static String insertOffer(){
		try{
			User hashas = User.all(User.class).get();
			Flight flight = Flight.all(Flight.class).get();
			
			Offer offer = new Offer(hashas,flight,(float)1.2,(float)5.0,"Excess weight","NEW");
			offer.insert();
			return "Success";
			}catch(Exception e){
			return e.toString();
			}
	}
	
    public static void index() {
        
    	User bob = new User((long)(Math.random()*10000),"balalaika", 
    			"aa", "", "", 
    			"", "", "", "", "");
    	
    	bob.insert();
    	
    	User hashas = User.all(User.class).get();
    	
    	renderJSON (hashas);
    }

}