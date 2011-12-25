package controllers;

import play.*;
import play.mvc.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import com.google.gson.Gson;

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
	
	/**
	 * @Author mohsen
	 * Gets as an input the offer's flight data and  edits them in the database.
	 */
	public static String editFlight(){
		String input = "";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(request.body));
			input = br.readLine();
			
				 
				Gson gson = new Gson();
				Flight flightIn = gson.fromJson(input, Flight.class);
			
			    Flight flightDb = Flight.getByKey(Flight.class, flightIn.id);
				
				try{

				
				flightDb.setDepartureDate(flightIn.getDepartureDate());
				flightDb.setDestination(flightIn.getDestination());
				flightDb.setFlightNumber(flightIn.getFlightNumber());
				flightDb.setSource(flightIn.getSource());
				flightDb.save();
				
				
				return "OK";
				}
				catch(Exception e){
					return "Exception here y " +e.toString()+input+flightDb;
				}
			
		}
		catch(Exception e){
			return "Exception here z "+ e.toString()+input;
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
