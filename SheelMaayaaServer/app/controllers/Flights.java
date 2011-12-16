package controllers;

import play.*;
import play.mvc.*;
import java.util.*;

import models.*;

public class Flights extends Controller {

	public static String view(){
		List<Flight> flights = Flight.all(Flight.class).fetch();
		String x = "";
		for(int i = 0 ; i < flights.size() ; i++)
		{
			x+= flights.get(i).toString()+"\n";
		}
		return x;
	}
	
	
	public static String insertFlight(String flightNumber, String source, String destination, String departureDate){
		try{
			
		Flight flight = new Flight(flightNumber, source, destination, departureDate);
		flight.insert();
		return "Success";
		}catch(Exception e){
		return e.toString();
		}
	}
	
	public static String getAllFlightNumbers(){
		
		try{
		String flightString = "";
		
		List<Flight> flights = Flight.all(Flight.class).fetch();
		
		List<String> numbers = new ArrayList<String>();
		
		for(int i = 0; i<flights.size();i++)
		{
			numbers.add(flights.get(i).flightNumber);
			
			flightString += numbers.get(i) + " , ";
			
		}
		return flightString;
		
		}catch(Exception e){
			return e.toString();
		}
	}
}
