package controllers;

import play.*;
import play.mvc.*;
import java.util.*;

import models.*;

public class Flights extends Controller {

	public static String insertFlight(String flightNumber, String source, String destination, String departureDate){
		try{
			
		Flight flight = new Flight(flightNumber, source, destination, departureDate);
		flight.insert();
		return "Success";
		}catch(Exception e){
		return e.toString();
		}
	}
	
	public static List<Flight> getFlightsByFlightNumber(String flightNumber, String date){
		
		try{
			
			List<Flight>flights = Flight.all(Flight.class).filter("flightNumber", flightNumber)
						.filter("departureDate", date).fetch();
				
			return flights;
			
		}catch(Exception e){
			return null;
		}
	}
	
	public static List<Flight> getFlightsByAirports(String source, String destination, String date){
		
		try{
			
			List<Flight>flights = Flight.all(Flight.class).filter("source", source).
					filter("destination", destination).filter("departureDate", date).fetch();
				
			return flights;
			
		}catch(Exception e){
			return null;
		}
	}
}
