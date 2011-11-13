package controllers;

import play.*;
import play.mvc.*;
import siena.DateTime;

import java.util.*;

//import ClientModels.CFlight;

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
		
	public static void index() {
        
    	User bob = new User((long)(Math.random()*10000),"balalaika", 
    			"aa", "", "", 
    			"", "", "", "", "");
    	
    	bob.insert();
    	
    	User hashas = User.all(User.class).get();
    	
    	renderJSON (hashas);
    }
    
    /**
     * @author Hossam Amer
     * Creates a confirmation inside the database.
     */
    
    public static String insertConfirmation(){
		
    	try
    	{
			User hashas = User.all(User.class).get();
			User hashas2 = User.all(User.class).get();
			Offer x = Offer.all(Offer.class).get();
			
			Confirmation confirmation = new Confirmation (x,
									hashas,
									hashas2,
									true,
									false,
									true,
									false);
			
	
			confirmation.insert();
			return "Success";
		 }
    	
    	catch(Exception e)
    	{
			return e.toString();
		}
	}
	
	
    

}