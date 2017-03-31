package model;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dereje Bireda
 *
 */
public class ParkManager extends AbstractUser {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**list of parks managed by the manager**/
	private List<Park> myParks = new ArrayList<Park>();

	/**
	 * setter for parks
	 * 
	 * @param theParks the list of parks be assigned for park manager
	 */
	public void setMyParks(List<Park> theParks) {
		this.myParks = theParks;
	}

	/**
	 * getter for parks
	 * 
	 * @return list of parks managed by the manager
	 */
	public List<Park> getMyParks() {
		return myParks;
	}
    
}
