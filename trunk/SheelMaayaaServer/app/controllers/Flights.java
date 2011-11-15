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
	

}
