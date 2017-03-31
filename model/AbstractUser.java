package model;

import java.io.Serializable;

public abstract class AbstractUser implements Serializable{



	
	private static final long serialVersionUID = 1L;
	private int myUserId;
	private String myName;
	private String myPhone;
	private String myAddress;
	private UserType myType;
	private String myUserName;
	private String myEmail;
	
	
	public void setMyUserId(int theUserId) {
		this.myUserId = theUserId;
	}





	public void setMyName(String theName) {
		this.myName = theName;
	}





	public void setMyPhone(String thePhone) {
		this.myPhone = thePhone;
	}





	public void setMyAddress(String theAddress) {
		this.myAddress = theAddress;
	}





	public void setMyType(UserType theType) {
		this.myType = theType;
	}





	public void setMyUserName(String theUserName) {
		this.myUserName = theUserName;
	}





	public void setMyEmail(String theEmail) {
		this.myEmail = theEmail;
	}



			
			
			
			
	public int getMyUserId() {
		return myUserId;
	}





	public String getMyName() {
		return myName;
	}





	public String getMyPhone() {
		return myPhone;
	}





	public String getMyAddress() {
		return myAddress;
	}





	public UserType getMyType() {
		return myType;
	}





	public String getMyUserName() {
		return myUserName;
	}





	public String getMyEmail() {
		return myEmail;
	}



}
