package controllers;

import play.*;
import play.mvc.*;
import siena.DateTime;

import java.util.*;

//import ClientModels.CFlight;

import models.*;

public class Confirmations extends Controller {
	
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
