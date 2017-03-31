package model;

import java.io.Serializable;

public class Park implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private int myParkId;
	private String myName;
	private String myCity;
	private int myManagerId;
	
	public Park() {
		
	}

	
	public int getMyParkId() {
		return myParkId;
	}


	public String getMyName() {
		return myName;
	}


	public String getMyCity() {
		return myCity;
	}


	public int getMyManagerId() {
		return myManagerId;
	}


	public void setMyParkId(int theParkId) {
		this.myParkId = theParkId;
	}

	public void setMyName(String theName) {
		this.myName = theName;
	}

	public void setMyCity(String theCity) {
		this.myCity = theCity;
	}
	
	public void setMyManagerId(int theManagerId) {
		this.myManagerId = theManagerId;
	}
}
