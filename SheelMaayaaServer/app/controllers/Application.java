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
		
	}
	
	public static String getFlightByFlightNumber(String flightNumber){
		
		try{
			Flight flight = Flight.all(Flight.class).search(flightNumber, "flightNumber").fetch().get(0);
			
	 		return flight.toString();
		}catch(Exception e){
			return e.toString();
		}
		
	}
	
	public static String getFlightByAirports(String source, String destination){
		
		try{
			Flight flight = Flight.all(Flight.class).search(source, "source").search(destination, "destination").get();
						
	 		return flight.toString();
		}catch(Exception e){
			return e.toString();
		}
		
	}

	public static String insertFlight(){
		try{
			
		Flight f = new Flight("LH123", "Frankfurt", "Cairo", "11-15-2011");
		f.insert();
		return "Success";
		}catch(Exception e){
		return e.toString();
		}
	}*/
		
	public static void index() {
        
//    	User bob = new User("balalaika", 
//    			"aa", "", "", 
//    			"", "", "", "", "","","");
//    	
//    	bob.insert();
//    	
//    	User hashas = User.all(User.class).get();
//    	
//    	renderJSON (hashas);

		//For testing the bootstrap
//		User x = User.all(User.class).get();
//		renderJSON(x);
		
		render();
    }
    
}