package models;

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
	public float pricePerKilogram;
	@Column("no_of_kilograms")
    public float noOfKilograms;

	@Column("user_status")
    public String userStatus;  //Excess weight or Less weight
	@Column("offer_status")
    public String offerStatus; //Open or taken or expired . . .
     //I suggest we make integer codes for these status -> Ahmed Mohsen
    
//    @ManyToOne
//    public Flight flight;
//    @OneToOne
//    public Confirmation confirmation;  
//  @ManyToOne
//  public User user; //offer Creator

    @Column("flight")
	public Flight flight;
	
	@Column("user")
    public User user; //offer Creator
  
//	@Column("confirmation")
//	public Confirmation confirmation;
    
    
    //public String currency; //Will be done next sprint
    
    public Offer(User user, Flight flight,
            float pricePerKilogram,
            float noOfKilograms,
            String userStatus,
            String offerStatus) 
    {
        
        this.pricePerKilogram = pricePerKilogram;
        this.noOfKilograms = noOfKilograms;
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

    

    public float getNoOfKilograms() {
        return noOfKilograms;
    }

    public void setNoOfKilograms(float noOfKilograms) {
        this.noOfKilograms = noOfKilograms;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public float getPricePerKilogram() {
        return pricePerKilogram;
    }

    public void setPricePerKilogram(float pricePerKilogram) {
        this.pricePerKilogram = pricePerKilogram;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
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
		return "Offer [id=" + id + ", pricePerKilogram=" + pricePerKilogram
				+ ", noOfKilograms=" + noOfKilograms + ", userStatus="
				+ userStatus + ", offerStatus=" + offerStatus + "]";
	}
	
}// end offer class