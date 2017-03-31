package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




/*
 * ParksSystem to control the system data,
 * and login 
 * 
 * @author Dereje Bireda
 *
 */
public class ParksSystem implements Serializable{


	
	private static final long serialVersionUID = 1L;

	private  List<Job> myJobs;
	private  List<Volunteer> myVolunteers;
	private  List<ParkManager> myParkManagers;
	
	private boolean pageRedirected;;
	private AbstractUser myCurrentUser;
	private JobController myJobController;
	private AbstractController myUserController;
	
	public ParksSystem(){
	    
	    myJobs = new ArrayList<Job>();
	    myVolunteers = new ArrayList<Volunteer>();
	    myParkManagers = new ArrayList<ParkManager>();
	    
	    //default system redirect to home
	    pageRedirected = true;
	}
	
	
     /********************************
      * Handle user login and logouts
      ********************************/
	 
	
	/**
	 * loads the current user to the system, if it it exist
	 * using a email as an input
	 * 
	 * @param theEMailCredential the email credential
	 * @return true if current user object created
	 * @author Dereje Bireda
	 */
	
	public boolean loginSuccessful(String theEMailCredential) {
		boolean userLoggedIn = false;
		for (int i = 0; i < myParkManagers.size(); i++) {
			
			if(myParkManagers.get(i).getMyEmail().equals(theEMailCredential)) {
				
				this.myCurrentUser = (ParkManager)myParkManagers.get(i); 
				this.myUserController = new ParkManagerController(this.myJobs, (ParkManager)myCurrentUser, this.myVolunteers);
				myJobController = new JobController(new Job(null), myJobs, (ParkManager)myCurrentUser);
				userLoggedIn = true;
				
			}
		}
		
		//not parkmanager check volunteers
		if(!userLoggedIn) {
			for (int i = 0; i < myVolunteers.size(); i++) {
				
				if(myVolunteers.get(i).getMyEmail().equals(theEMailCredential)) {
					
					this.myCurrentUser = (Volunteer)myVolunteers.get(i); 
					this.myUserController = new VolunteerController((Volunteer)myCurrentUser, this.myJobs);
					userLoggedIn = true;
				}
			}
		
		}
		
		return userLoggedIn;
	}
	
	
	public  AbstractController getMyUserController() {
		return this.myUserController;
	}

	public void setMyUserController(AbstractController theUserController) {
		this.myUserController = theUserController;
	}

	/**
	 * checks a user id exist for a list of users
	 * 
	 * @param users the list of users
	 * @param theId the id to be checked
	 * @return true if that id exist for a user
	 * @author Dereje Bireda
	 */
	public <E> boolean isIdExist(List<E> users, int theId) {

		//the user id follows the index
		//user id greater than or equal the size, it doesnt exist
		return (theId>=0 && theId < users.size());
			
	}
	
	


	public List<ParkManager> getMyParkManagers() {
		return myParkManagers;
		
	}

	public void setMyParkManagers(List<ParkManager> theParkManagers) {
		this.myParkManagers = theParkManagers;
		
	}
	

	public  List<Volunteer> getMyVolunteers() {
		return myVolunteers;
		
	}

	public  void setMyVolunteers(List<Volunteer> theVolunteers) {
		this.myVolunteers = theVolunteers;
		
	}

	public JobController getMyJobController() {
		return myJobController;
	}

	public List<Job> getMyJobs() {
		return myJobs;
	}

	public void setMyJobs(List<Job> theJobs) {
		this.myJobs = theJobs;
	}



	public void logout() {
		this.pageRedirected = false;
	    
	}
	

	public boolean isPageRedirected() {
		
		return this.pageRedirected;
	}
	
	public AbstractUser getMyCurrentUser() {
		return myCurrentUser;
	}
}
