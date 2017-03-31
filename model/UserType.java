package model;

import java.io.Serializable;

/**
 * Enum to hold the user types
 * 
 * @author Dereje Bireda
 *
 */
public enum UserType implements Serializable {
	
	Volunteer("vol")
	,Manager("mgr") 
	
	,Staff("stf");
	
	private String myType;
	
	/**
	 * parameterized constructor 
	 * 
	 * @param theType the string value of the enum
	 */
	UserType(final String theType) {
        myType = theType;
    }
	
	/**
	 * checks if user with a given string value
	 * 
	 * @param theString the string value of the enum
	 * 
	 * @return true if the user exist from list of enum
	 */
	public static boolean userExist(final String theString) {
        boolean userTypeExist = false ;

        for(final UserType type : UserType.values()) {
        	if(type.myType.equals(theString)) {
        		userTypeExist = true;
        		break;
        	}
        }
        return userTypeExist;
    }
	

	public String getMyType() {
		return myType;
	}
}