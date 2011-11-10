package models;


import siena.*;

@Table("Confirmation")
public class Confirmation extends Model
{
//	  	@OneToOne
//	    public Offer offer;
//	    @OneToOne
//	    public User user1;
//	    @OneToOne
//	    public User user2;
// 
		
		
		@Column ("offer")
    	public Offer offer;

		@Column ("user1")
    	public User user1;
    	
    	@Column ("user2")
    	public User user2;
		
	
	    public String statusTransactionUser1; //indicates the user confirmed the transaction
	    public String statusTransactionUser2;

	    public String statusDeliveryUser1;
	    public String statusDeliveryUser2;
	    
	    
	    public Confirmation(
	    		Offer offer,
	            User user1,
	            User user2,
	            String statusTransactionUser1,
	            String statusTransactionUser2,
	            String statusDeliveryUser1,
	            String statusDeliveryUser2) {
	        
	        this.offer = offer;
	        this.user1 = user1;
	        this.user2 = user2;
	        this.statusTransactionUser1 = statusTransactionUser1;
	        this.statusTransactionUser2 = statusTransactionUser2;
	        this.statusDeliveryUser1 = statusDeliveryUser1;
	        this.statusDeliveryUser2 = statusDeliveryUser2;
	    }

	    public Offer getOffer() {
	        return offer;
	    }

	    public void setOffer(Offer offer) {
	        this.offer = offer;
	    }

	    public String getStatusDeliveryUser1() {
	        return statusDeliveryUser1;
	    }

	    public void setStatusDeliveryUser1(String statusDeliveryUser1) {
	        this.statusDeliveryUser1 = statusDeliveryUser1;
	    }

	    public String getStatusDeliveryUser2() {
	        return statusDeliveryUser2;
	    }

	    public void setStatusDeliveryUser2(String statusDeliveryUser2) {
	        this.statusDeliveryUser2 = statusDeliveryUser2;
	    }

	    public String getStatusTransactionUser1() {
	        return statusTransactionUser1;
	    }

	    public void setStatusTransactionUser1(String statusTransactionUser1) {
	        this.statusTransactionUser1 = statusTransactionUser1;
	    }

	    public String getStatusTransactionUser2() {
	        return statusTransactionUser2;
	    }

	    public void setStatusTransactionUser2(String statusTransactionUser2) {
	        this.statusTransactionUser2 = statusTransactionUser2;
	    }

	    public User getUser1() {
	        return user1;
	    }

	    public void setUser1(User user1) {
	        this.user1 = user1;
	    }

	    public User getUser2() {
	        return user2;
	    }

	    public void setUser2(User user2) {
	        this.user2 = user2;
	    }
	    

}