package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Users extends Controller {


	
	
	/* public static String insertUser(String firstName, String middleName, String lastName, String gender, String email, String mobileNumber, String passportNumber, String nationality, String passportPhoto){
		try{
			
		User user = new User("", firstName, middleName, lastName, passportPhoto, passportNumber, email, mobileNumber, "", gender, nationality);
		user.insert();
		return "Success";
		}catch(Exception e){
		return e.toString();
		}
	}
	*/
	 public static String insertUser(String facebookID, String firstName, String middleName, String lastName, String gender, String email, String mobileNumber, String passportNumber, String nationality, String passportPhoto){
		try{
			
		User user = new User("", firstName, middleName, lastName, passportPhoto, passportNumber, email, mobileNumber, facebookID, gender, nationality);
		user.insert();
		return "Success";
		}catch(Exception e){
		return e.toString();
		}
	}
	

}

