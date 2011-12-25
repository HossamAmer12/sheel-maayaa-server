/*
package models;

public class OfferHelper
{

	*
	 * Table for helping to display offers in Sheel Ma3aya
	 * @author Hossam_Amer
	 *
	
    public User userOther; //offer other side
    public Offer offer; // Offer displayed
  
    
    public OfferHelper(Offer offer, User userOther) 
    {
    	this.userOther = userOther;
    	this.offer = offer;
    }
 
}// end offer class
*/

 
 package models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import siena.Column;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Table;

public class OfferHelper
{
	
    public User userOther; //offer other side
    
    public Offer offer; // Offer displayed
  
    
    public OfferHelper(Offer offer, User userOther) 
    {
    	this.userOther = userOther;
    	this.offer = offer;
    }
 
}// end offer class
