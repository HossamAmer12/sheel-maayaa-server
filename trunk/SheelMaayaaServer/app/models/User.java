package models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import siena.Column;
import siena.Filter;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.Query;
import siena.Table;

/**
 * 
 * @author Hossam_Amer
 * 	Ahmed Mohsen
 * 
 * XXXMissing things:
 *1- validations
 *2- controllers
 */

@Table("User")
public class User extends Model
{
		@Id(Generator.AUTO_INCREMENT)
		//@Id
		public Long id;

		
		@Column("username")  
	 	public String username;
		//@Column("password")  //remove password
	    //public String password;

		@Column("first_name")
	    public String firstName;
		@Column("middle_name")
	    public String middleName;
		@Column("last_name")
	    public String lastName;

		@Column("passport_photo")
	    public String passportPhoto; //url for the passport photo
		@Column("passport_number")
	    public String passportNumber;
		
		//XXX	@Unique
		@Column("email")
	    public String email;
		@Column("mobile_number")
	    public String mobileNumber;
		@Column("facebook_account")
	    public String facebookAccount;
		@Column("gender")
		public String gender;
		@Column("nationality")
		public String nationality;
	    
	    @Filter("user")
	    public Query <Offer> offers;
	    
	    @Filter("user1")
	    @Cascade({CascadeType.SAVE_UPDATE})
	    public Query <Confirmation> confirmations1;
	    
	    @Filter("user2")
	    @Cascade({CascadeType.SAVE_UPDATE})
	    public Query <Confirmation> confirmations2;
	    
	    public User(
	    		String username, 
	            String firstName,
	            String middleName,
	            String lastName,
	            String passportPhoto,
	            String passportNumber,
	            String email,
	            String mobileNumber,
	            String facebookAccount,
	            String gender,
	            String nationality) {
	        this.username = username;
	        this.firstName = firstName;
	        this.middleName = middleName;
	        this.lastName = lastName;
	        this.passportPhoto = passportPhoto;
	        this.passportNumber = passportNumber;
	        this.email = email;
	        this.mobileNumber = mobileNumber;
	        this.facebookAccount = facebookAccount;
	        this.gender = gender;
	        this.nationality = nationality;

	    }
	    
	    public Query <User> all()
	    {
	    	return Model.all(User.class);
	    }
	    
	    public String toString()
	    {
	    	return
	    	"Username: " + username + 
	    	", First Name: " + firstName +
	    	", Middle Name: " + middleName +
	    	", Last Name: " + lastName +
	    	", Passport photo: " + passportPhoto +
	    	", Passport number: " + passportNumber +
	    	", Email: " + email +
	    	", Mobile number: " + mobileNumber +
	    	", Facebook account: " + facebookAccount +
	    	", Gender: " + gender +
	    	", Nationality: " + nationality;
	    }
}