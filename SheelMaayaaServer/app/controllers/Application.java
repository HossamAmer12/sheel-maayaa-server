package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	
	public static String test(){
		
		return "Test successful";
		
	}
	
	
    public static void index() {
        
    	User bob = new User("bob", "as", 
    			"aa", "", "", 
    			"", "", "", "", "");
    	
    	bob.insert();
    	
    	User hashas = User.all(User.class).get();
    	
    	renderJSON (hashas);
    }

}