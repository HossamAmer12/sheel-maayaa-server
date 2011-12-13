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
	
		
	public static void index() {		
		render();
    }
    
}