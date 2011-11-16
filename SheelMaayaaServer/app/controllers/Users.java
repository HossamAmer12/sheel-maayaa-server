package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Users extends Controller {

	/*public static String insertUser(String username, String gender, String nationality){
		try{
			
		User user = new User(username, "ff", "mm", "ll", "", "A12345", "ff.ll@gmail.com", "123", "mm", gender, nationality);
		user.insert();
		return "Success";
		}catch(Exception e){
		return e.toString();
		}
	}
	

	*/
	
	
	public static String insertUser(String firstName, String middleName, String lastName, String gender, String email, String mobileNumber, String passportNumber, String nationality){
		try{
			
		User user = new User("", firstName, middleName, lastName, "", passportNumber, email, mobileNumber, "", gender, nationality);
		user.insert();
		return "Success";
		}catch(Exception e){
		return e.toString();
		}
	}
	

}
