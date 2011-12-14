package models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import siena.Column;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;

@Table("Offer")
public class Offer extends Model
{
	
	@Id(Generator.AUTO_INCREMENT)
	public Long id;

	
	@Column("price_per_kilogram")
	public int pricePerKilogram;
	@Column("no_of_kilograms")
    public int noOfKilograms;

	@Column("user_status")
    public int userStatus;  //1 for Extra weight and 0 for Less weight
	
	/**
	 * OfferStatus is either "half-confirmed" or "confirmed"
	 * 
	 * "half-confirmed" designates for a half-confirmed offer by either 
	 * less or more w8t users
	 * 
	 * "confirmed" designates for a confirmed offer by less and more w8t users
	 */
	
	@Column("offer_status")
    public String offerStatus; //Open or taken or expired . . .
     //I suggest we make integer codes for these status -> Ahmed Mohsen
    
    @Column("flight")
    @Cascade({CascadeType.SAVE_UPDATE})
	public Flight flight;
	
	@Column("user")
	@Cascade({CascadeType.SAVE_UPDATE})
    public User user; //offer Creator
  
    
    //public String currency; //Will be done next sprint
    
	public Offer(){
		
	}
	
	
    public Offer(User user, Flight flight,
    		int noOfKilograms,
            int pricePerKilogram,
            int userStatus,
            String offerStatus) 
    {
    	this.noOfKilograms = noOfKilograms;
        this.pricePerKilogram = pricePerKilogram;
        this.userStatus = userStatus;
        this.offerStatus = offerStatus;
        this.user = user;
        this.flight = flight;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    

    public int getNoOfKilograms() {
        return noOfKilograms;
    }

    public void setNoOfKilograms(int noOfKilograms) {
        this.noOfKilograms = noOfKilograms;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public int getPricePerKilogram() {
        return pricePerKilogram;
    }

    public void setPricePerKilogram(int pricePerKilogram) {
        this.pricePerKilogram = pricePerKilogram;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
    
    public Query <Offer> all()
    {
    	return Model.all(Offer.class);
    }


//XXX problem not solved
//     public Confirmation getConfirmation() {
//        return confirmation;
//    }
//
//    public void setConfirmation(Confirmation confirmation) {
//        this.confirmation = confirmation;
//    }
//
    public Flight getFlight() {
        return flight;
    }
//
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

	@Override
	public String toString() {
		
		User usr = getUser();
		usr.get();
		flight.get();
		return "[id=" + id + ", noOfKilograms=" + noOfKilograms +
				", pricePerKilogram=" + pricePerKilogram +
				", userStatus=" + userStatus + ", offerStatus=" + offerStatus + ", userId=" + usr.id + 
				", flightId= "+flight.id+"]";
	}
	
}// end offer class