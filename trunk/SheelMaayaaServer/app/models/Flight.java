package models;

import java.util.ArrayList;

import siena.Column;
import siena.DateTime;
import siena.Filter;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;
import java.util.Date;

//import ClientModels.CFlight;

@Table("Flight")
public class Flight extends Model
{
	
		@Id(Generator.AUTO_INCREMENT)
		public Long id;
		
		@Column("flight_number")
	  	public String flightNumber;
		@Column("source")
	    public String source;
		
		@Column("destination")
	    public String destination;
		
		@Column("departure_date")
	    public String departureDate;   //Use java.util.Date, it works. Don't use Siena DateTime, its an annotation, not an object.

	 //   @OneToMany(mappedBy = "flight")
	 //   public List<Offer> offers;
	    
	    @Filter("flight")
	    public Query <Offer> offers;
	    
	    /*
	    public Flight(CFlight flight) {
	        this.flightNumber = flight.flightNumber;
	        this.source = flight.source;
	        this.destination = flight.destination;
	        this.departureDateTime = flight.departureDateTime;
	        
	    }*/
	    
	    public Flight(String flightNumber, String source, String destination, String departureDate) {
	        this.flightNumber = flightNumber;
	        this.source = source;
	        this.destination = destination;
	        this.departureDate = departureDate;
	        
	    }
	    
	    
	    public String getDepartureDate() {
	        return departureDate;
	    }

	    public void setDepartureDate(String departureDate) {
	        this.departureDate = departureDate;
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

	        
	    public Query<Offer> getOffers() {
	        return offers;
	    }

	    public void setOffers(Query<Offer> offers) {
	        this.offers = offers;
	    }
	    
	    public Query <Flight> all()
	    {
	    	return Model.all(Flight.class);
	    }


		@Override
		public String toString() {
			return "Flight [id=" + id + ", flightNumber=" + flightNumber
					+ ", source=" + source + ", destination=" + destination
					+ ", departureDate=" + departureDate + "]";
		}

	    //XXX I am not sure yet!!!
	    /*
	    public ArrayList<Offer> getOffers(String flighNumber) {
	    	ArrayList <Offer> x = new ArrayList <Offer> ();
	    	Flight y = Model.getByKey(Flight.class, flighNumber);
	    	
	    	x = (ArrayList<Offer>) y.offers.fetch();
	    	
			return x;
	    }
	    */
	    
}