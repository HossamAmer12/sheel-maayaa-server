package models;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import siena.*;

@Table("Confirmation")
public class Confirmation extends Model
{
		@Id
		public Long Id;
	
		@Column ("offer")
    	public Offer offer;

		@Column ("user1")
//		@Cascade({CascadeType.SAVE_UPDATE})
    	public User user1;
    	
    	@Column ("user2")
//    	@Cascade({CascadeType.SAVE_UPDATE})
    	public User user2;
		
		/**
		 * Indicates User confirmed first exchange of luggange
		 */
	    public boolean statusTransactionUser1; 
	    /**
		 * Indicates User confirmed first exchange of luggange
		 */
	    public boolean statusTransactionUser2;
	    /**
  		 * Indicates User confirmed second exchange of luggange
  		 */
	    public boolean statusDeliveryUser1;   
	    /**
  		 * Indicates User confirmed second exchange of luggange
  		 */
	    public boolean statusDeliveryUser2;
	    
	    
	    public Confirmation(
	    		Offer offer,
	            User user1,
	            User user2,
	            boolean statusTransactionUser1,
	            boolean statusTransactionUser2,
	            boolean statusDeliveryUser1,
	            boolean statusDeliveryUser2) {
	        
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

	    public boolean getStatusDeliveryUser1() {
	        return statusDeliveryUser1;
	    }

	    public void setStatusDeliveryUser1(boolean statusDeliveryUser1) {
	        this.statusDeliveryUser1 = statusDeliveryUser1;
	    }

	    public boolean getStatusDeliveryUser2() {
	        return statusDeliveryUser2;
	    }

	    public void setStatusDeliveryUser2(boolean statusDeliveryUser2) {
	        this.statusDeliveryUser2 = statusDeliveryUser2;
	    }

	    public boolean getStatusTransactionUser1() {
	        return statusTransactionUser1;
	    }

	    public void setStatusTransactionUser1(boolean statusTransactionUser1) {
	        this.statusTransactionUser1 = statusTransactionUser1;
	    }

	    public boolean getStatusTransactionUser2() {
	        return statusTransactionUser2;
	    }

	    public void setStatusTransactionUser2(boolean statusTransactionUser2) {
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
	    
	    public Query <Confirmation> all()
	    {
	    	return Model.all(Confirmation.class);
	    }
	    

}