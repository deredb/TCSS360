package model;
/*
 * Stub for AbstractController
 * everyone feel free to use, fix, enhance, whatever 
 * 
 */ 
 

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;

/* 
 * @authors Christopher Hall, Dereje Bireda, Tony Richardson, Brian Hess
 * @Winter Quarter 2017
 * 
 */


public abstract class AbstractController implements Serializable {
  
	private static final long serialVersionUID = 1L;
	private static final int MIN_DIFFERENCE_BETWEEN_JOB_SIGNUP_JOB_START_DATE = 2; 
	private static final int DEFAULT_MAX_NUM_PENDING_JOBS = 30;
	private static final int MIN_STRING_LENGTH_DISPLAYED = 20;

	
    private  List<Job> myJobs;
    private  AbstractUser myUser;
    
    private boolean pageRedirected;
    
    public AbstractController(List<Job>theJobs, AbstractUser theUser) {
    	myJobs = theJobs;
    	myUser = theUser;
    	
    	//
    	pageRedirected = false;
    	runUpdate(myJobs);
    }
    

    /**
 	 * updates job status for past and pending conditions
 	 * 
 	 * @param theJobs list of jobs to be updated,
 	 * @precondition must be called properly initialized
 	 * user controller-Eg. VolunteerController, 
 	 * 
 	 * @author Dereje Bireda
 	 */
    public void runUpdate(List<Job>theJobs) {
    	for(int i = 0 ; i < theJobs.size(); i++) {
    		Job job = theJobs.get(i);
    		 if(isMyJobPast(job)) {
    			 job.setMyJobIsPast(true);
    			 //past no longer pending too
    			 job.setMyJobIsPending(false);
    		 }
    		 
    		 if(hasMinSignupDaysBeforeJobStartPassed(job)){
    			 job.setMyJobIsPending(false);
    		 }
    		 
    		 if(!isMyJobPast(job) && isJobFullForSignUp(job)){
    			 job.setMyJobIsPending(false);
    		 }
    		 
    	}
    }
    
    /**
	 * Checks business rule: Not more than the maximum number of pending jobs at a time
	 * Checks against DEFAULT_MAX_NUM_PENDING_JOBS
	 * 
	 * @precondition must be called properly initialized
	 * user controller-Eg. VolunteerController, which holds
	 * the system jobs.
	 * 
	 * @return true if new job can be submitted, false otherwise
	 * @author Dereje Bireda
	 */

	 public boolean isNewJobAccepted() {
		 List<Job> pendingJobs = getSystemPendingJobs();
		 
		 return pendingJobs.size() < DEFAULT_MAX_NUM_PENDING_JOBS;
	 }
	 
	 /**
	  * generates a list of pending jobs for the System in 
	  * general
	  * 
	  * @return list of jobs not completed or closed(present jobs)
	  * @precondition must be called properly initialized
	  * user controller-Eg. VolunteerController, with a list
	  * of jobs
	  * @author Dereje Bireda
	  */
	 //Revised: get pending jobs for system is job that is not past
	 public List<Job> getSystemPendingJobs() {
		 List<Job> pendingJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < myJobs.size(); i++) {
			 	Job jobChecked = myJobs.get(i);
				if(!jobChecked.getMyJobIsPast()) {
					//job is not full and signing up day not passed
					//if(!hasMinSignupDaysBeforeJobStartPassed(jobChecked)) //&&
							//!isJobFullForSignUp(jobChecked)) {
						//add it to pending
						pendingJobs.add(jobChecked);
					//}
				}
				
			}
		 
		 return pendingJobs;
	 }
    

    
	 /**
	  * find job with a given id
	  * 
	  * @param id index of the job list
	  * @return a job
	  */
    public Job getSingleJobByGivenId(Integer id){
	     return myJobs.get(id);
	     
	 }
    
    
 /**
  * Checks business rule: A volunteer may sign up only if the job is at least a minimum number of calendar days from the current date.
  * Checks against MIN_DIFFERENCE_BETWEEN_JOB_SIGNUP_JOB_START_DATE.
  * 
  * @param theJob the job checked for sign up
  * @precondition theJob should be a proper job object
  * 
  * @return true if the job start date fails to qualify for 
  *        MIN_DIFFERENCE_BETWEEN_JOB_SIGNUP_JOB_START_DATE 
  *  
  */
    public boolean hasMinSignupDaysBeforeJobStartPassed(Job theJob) {
    	 return ChronoUnit.DAYS.between(LocalDate.now(), theJob.getMyStartDate()) < MIN_DIFFERENCE_BETWEEN_JOB_SIGNUP_JOB_START_DATE;
    }
    

	 
    /**
     * Format job description length to MIN_STRING_LENGTH_DISPLAYED length
     * 
     * @param theJob
     * @return formatted string
     * @precondition theJob should be a proper job object
     * @author Dereje Bireda
     */
	 public String truncateJobDescriptionForDisplay(Job theJob) {
		 String shortDescription = "";
		 if(!(theJob.getMyDescription().length() < MIN_STRING_LENGTH_DISPLAYED)){
				shortDescription = theJob.getMyDescription().substring(0, MIN_STRING_LENGTH_DISPLAYED);
			} else {
				 shortDescription = theJob.getMyDescription();
			}
		 
		 return shortDescription;
	 }
	
	 /**
	  * checks if job reached full capacity for volunteering
	  * 
	  * @param theJob the job to be checked 
	  * @return true if job reached maximum volunteer limit, false otherwise
	  * @precondition theJob should be a proper job object
	  * 
	  * @author Dereje Bireda
	  */
	
	 public boolean isJobFullForSignUp(Job theJob) {
		 
		 int totalVolunteersNeeded = totalVolunteersPerJob(theJob);
		 int currentTotal = theJob.getMyCurrentTotalVolunteers();
		
		 return (totalVolunteersNeeded == currentTotal) ;
	 }
	 
	 /**
	  * calculate the total number of volunteers for a job
	  * 
	  * @param theJob the job to be checked
	  * @return the total number of volunteers(light,medium,heavy)
	  * @author Dereje Bireda
	  */
	 //total volunteers per job
	 public static int totalVolunteersPerJob(Job theJob) {
		 int currentTotalVolunteers = 0;
		 
		 currentTotalVolunteers = theJob.getMyLightVolunteerNumber() + 
				                 theJob.getMyMediumVolunteerNumber() + 
				                  theJob.getMyHeavyVolunteerNumber();
		 return currentTotalVolunteers;
		 
	 } 
	 

	 /**
		 * calcualte the difference between two days 
		 * Example,given 02/06/2017(first date) and 02/08/2017
		 *  will return 2(exclude end date), but not 3
		 * 
		 * @param firstDate the first date
		 * @param secondDate the second date, latest
		 * 
		 * @return the difference between the two days, exclusive of the second date
		 * @author Dereje Bireda
		 */
		//calculate difference between dates
		 public  long numOfDaysBetweenTwoDays(LocalDate firstDate, LocalDate secondDate) {
			
		    return  ChronoUnit.DAYS.between(firstDate,secondDate);
		 }
		 
		 
	 /**
	  * checks if the job is older than current date
	  * 
	  * @param theJob the job to be checked
	  * @return true if job is older than the current date
	  * @author Dereje Bireda
	  */
	 //check if job is past
	 public boolean isMyJobPast(Job theJob){
		 LocalDate currentDate = LocalDate.now();
		 //-ve means past,true
		 return this.numOfDaysBetweenTwoDays(currentDate,theJob.getMyEndDate()) < 0;
		
			 
	 }

	 
	public AbstractUser getMyUser() {

		return this.myUser;
	}


	
	public String convertLocalDatetToReadableString(LocalDate theDate) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String formattedString = theDate.format(formatter);
		
		return formattedString;
	}

	public List<Job> getJobs() {
		return myJobs;
	}

	/**
	 * set the page redirected to true
	 * @author Dereje Bireda
	 */
	public void logout() {
		this.pageRedirected = true;
	}
	
	/**
	 * write theSystem object to bin file
	 * 
	 * @param theSystem
	 * @param theOuts
	 * @author Dereje Bireda
	 */
	public void writeToFile(ParksSystem theSystem, ObjectOutputStream theOuts)  {
	
		
		try{
			theOuts = new ObjectOutputStream(new FileOutputStream("uparksdata.bin",false));
			theOuts.writeObject(theSystem);
			
			theOuts.close();
			
		} catch(Exception e) {
			try {
	
				theOuts.close();
			} catch (IOException e1) {
			
				e1.printStackTrace();
			}
		}
	}
	 
	/**
	 * 
	 * @return true if a user is logged out
	 * @author Dereje Bireda
	 */
	 public boolean getIsPageRedicrected() {
		 
		 return this.pageRedirected;
	 }
}