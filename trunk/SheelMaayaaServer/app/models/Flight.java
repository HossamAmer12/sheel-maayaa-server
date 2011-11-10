package models;

import java.util.ArrayList;

import siena.Column;
import siena.DateTime;
import siena.Filter;
import siena.Model;
import siena.Query;
import siena.Table;

@Table("Flight")
public class Flight extends Model
{
		@Column("fligh_number")
	  	public String flightNumber;
		@Column("source")
	    public String source;
		
		@Column("destination")
	    public String destination;
		
		@Column("departure_date_time")
	    public DateTime departureDateTime;  //holds both the date and the time of the departure
	    									//XXXChanged into DateTime!! -> Hossam Amer

	 //   @OneToMany(mappedBy = "flight")
	 //   public List<Offer> offers;
	    
	    @Filter("flight")
	    public Query <Offer> offers;
	    
	    //XXXXTake care the constructor is without offers!!
	    public Flight(String flightNumber, String source, String destination, DateTime departureDateTime) {
	        this.flightNumber = flightNumber;
	        this.source = source;
	        this.destination = destination;
	        this.departureDateTime = departureDateTime;
	        
	    }
	    
	    
	    public DateTime getDepartureDateTime() {
	        return departureDateTime;
	    }

	    public void setDepartureDateTime(DateTime departureDateTime) {
	        this.departureDateTime = departureDateTime;
	    }

	    public String getDestination() {
	        return destination;
	    }

	    public void setDestination(String destination) {
	        this.destination = destination;
	    }

	    public String getFlightNumber() {
	        return flightNumber;
	    }

	    public void setFlightNumber(String flightNumber) {
	        this.flightNumber = flightNumber;
	    }

	    public String getSource() {
	        return source;
	    }

	    public void setSource(String source) {
	        this.source = source;
	    }

	    
//XXX problem not solved!	    
//	    public List<Offer> getOffers() {
//	        return offers;
//	    }
//
//	    public void setOffers(List<Offer> offers) {
//	        this.offers = offers;
//	    }

	    //XXX I am not sure yet!!!
	    public ArrayList<Offer> getOffers(String flighNumber) {
	    	ArrayList <Offer> x = new ArrayList <Offer> ();
	    	Flight y = Model.getByKey(Flight.class, flighNumber);
	    	
	    	x = (ArrayList<Offer>) y.offers.fetch();
	    	
			return x;
	    }
	    
	    
}