package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;


/**
 * JobController handles job validation when job is created
 * 
 * 
 * @author Dereje Bireda
 * 
 *
 */
public class JobController implements Serializable{

	

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_MAX_NUM_PENDING_JOBS = 30;
	private static final int MAX_NUM_VOLUNTEERS_PER_JOB = 10;
	private static final int MAX_ALLOWED_DATE_INTO_FUTURE = 75;
	private static final int MAX_JOB_LENGTH_IN_DAYS = 3;
	private static final int SYSTEM_MAX_JOBS_IN_ANY_GIVEN_DAY = 4;
	private static final int MIN_JOB_POST_DAY_LENGTH = 3; 
	private static final int ONE_DAY_OFFSET = 1;
	private int myMaxNumberOfPendingJobs ;

	/* new added fields*/
	private Job myJob;
	private List<Job>myJobs;
	private ParkManager myJobCreator;
	
	

	/**
	 * 
	 * @param theJob the new job to be created
	 * @param theJobs a list of existing jobs
	 * @param theCreator the user who is creating the job
	 */
	public JobController(Job theJob, List<Job>theJobs, ParkManager theCreator) {
		this.myJob = theJob;
		this.myJobs = theJobs;
		this.myJobCreator = theCreator;
		
		//from old constructor 
		this.myMaxNumberOfPendingJobs = DEFAULT_MAX_NUM_PENDING_JOBS;
	}
	


	/**
	 * adds a park to a job directly, if ParkManager has only one park
	 * 
	 * @return true if park added to a job for single park case
	 */
	public boolean isParkAdded() {
		
		if(myJobCreator.getMyParks().size() == 1) {
			Park thePark = myJobCreator.getMyParks().get(0);
			myJob =  new Job(thePark);
			myJob.setMyJobManagerId(myJobCreator.getMyUserId());
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * depending on the ParkManager choice, assign the park to a new job
	 * 
	 * @param theManagerChoice one of two parks ParkManager manages
	 */
	public void pickAPark(int theManagerChoice) {
		Park thePark;
		if (theManagerChoice == 1) {
			thePark = myJobCreator.getMyParks().get(0);
		} else  {
			thePark = myJobCreator.getMyParks().get(1);
		} 
		
		
		myJob =  new Job(thePark);
		myJob.setMyJobManagerId(myJobCreator.getMyUserId());
	}
	
    /**
     * Our assumption - job needs to be several days in the future for volunteers to sign up.
     * Checks against MIN_JOB_POST_DAY_LENGTH
     * 
     * @param theCurrentDate
     * @param theJobStartDate
     * @return true if signing up date for the job is not over, false otherwise
     */
	public boolean hasJobStartDateAllowVolunteerSignUp(LocalDate theCurrentDate, LocalDate theJobStartDate) {
		return numOfDaysBetweenTwoDays(theCurrentDate,theJobStartDate)>= MIN_JOB_POST_DAY_LENGTH;
	}
	
	
	  /**
     *  Checks business rule: A job cannot be scheduled more than the maximum allowed date into the future
     *  checks if a job is scheduled more than the MAX_ALLOWED_DATE_INTO_FUTURE
     *  
     * @param currentDate
     * @param jobStartDate
     * @return true if the dates meet the business rule
     * @author Chris
     */
    public boolean hasStartDateTooFar(LocalDate currentDate, LocalDate jobStartDate) {
        return numOfDaysBetweenTwoDays(currentDate,jobStartDate)<= MAX_ALLOWED_DATE_INTO_FUTURE;
    }
    
    
	/**
	 * Checks if starting date input for a job acceptable against the business
	 * rules to be added to a job.
	 * 
	 * @param theDate the start date to be added
	 * @return false if the start date for the job is not set, true otherwise
	 * @precondition expects valid date object 
	 */
	public boolean isStartDateAdded(LocalDate theDate) {
		boolean dateAdded = false;
	
		if(theDate != null) {
			LocalDate currentDate = LocalDate.now();
			 //MIN_JOB_POST_DAY_LENGTH at least more than MIN_DIFFERENCE_BETWEEN_JOB_SIGNUP_JOB_START_DATE
		
			if (hasJobStartDateAllowVolunteerSignUp(currentDate, theDate) && 
					   hasStartDateTooFar(currentDate,theDate)){
				//dont have max jobs on start date
				if(hasLessThanMaxJobsOnJobDate(theDate)) {
					myJob.setMyStartDate(theDate);
					dateAdded = true;
				} 
				
			}
		} 
		
		return dateAdded;
		
	}
	

    /**
     *
     * Checks business rule: A job cannot be longer than the maximum number of days
     * Checks a if a job is not longer than the MAX_JOB_LENGTH_IN_DAYS
     * 
     * 
     * @param theDuration of the new job being submitted
     * @precondition duration should be within the range and a number, otherwise return false
     * @return true if the job has a valid duration
     * 
     */
	public boolean isJobDurationNumWithinAllowedRange(int theDuration) {
		
		
		return (theDuration <= MAX_JOB_LENGTH_IN_DAYS && theDuration >= 1);
		
	}
	
	/**
	 * calculate the end date based on the duration, and checks if 
	 * each day during the duration 
	 * @param theDuration the duration of the job
	 * @precondition the duration should be valid positive number
	 * 
	 * @return true if the job duration pass business rules, false
	 * otherwise  
	 */
	public boolean isEndDateAdded(int theDuration) {
		
		boolean dateAdded = false;
		if(!isJobDurationNumWithinAllowedRange(theDuration)) {
			
			dateAdded = false;
			return dateAdded;
		} 
		
		//add duration to startDate
		LocalDate endDate = myJob.getMyStartDate().plusDays(theDuration);
		//check for duplicates
		if(hasDurationDayshasNoMaxJobs(myJob.getMyStartDate(),theDuration) && hasStartDateTooFar(LocalDate.now(), endDate.minusDays(1))) {
			myJob.setMyEndDate(endDate);
			dateAdded = true;
		} else {
			//cant be added
			dateAdded = false;
		}
		
		return dateAdded;

	}
	
	/**
	 * assign starting time for the job
	 * 
	 * @param theTime the starting time of the job
	 */
	public void addTime(String theTime) {
		LocalTime time = convertStringToTime(theTime);
		if(time!=null){
			myJob.setMyTime(time);
		}
	}
	
	/**
	 * assign string description of the job task
	 * 
	 * @param theDescription the description of the job
	 * @precondition expects at least one character input
	 * @return true if job description added, false otherwise
	 */
	public boolean isJobDescriptionAdded(String theDescription) {
		if (theDescription.length() > 5) {
			myJob.setMyDescription(theDescription);
			return true;
			} else {
				return false;
			}
			
		
	}
	/**
	 * Checks business rule: Not more than the maximum number of volunteers for any job
	 * assign required number of volunteers for light work
	 * 
	 * @param theNum the number of light work volunteers needed
	 * @precondition theNum should be >= 0 and <=MAX_NUM_VOLUNTEERS_PER_JOB
	 * @return true if light volunteers number is accepted, false otherwise
	 * @author Dereje Bireda
	 */
	public boolean isMaxLightVolNumberValid(int theNum) {
		boolean numAccepted = false;
		if(theNum <=MAX_NUM_VOLUNTEERS_PER_JOB && theNum >=0) {
			myJob.setMyLightVolunteerNumber(theNum);
			numAccepted = true;
		} else {
		  numAccepted = false;
		}
		
		return numAccepted;
	}
	
	/**
	 * Checks business rule: Not more than the maximum number of volunteers for any job
	 * assign required number of volunteers for medium work
	 * 
	 * @param theNum the number of light work volunteers needed
	 * @precondition theNum should be >= 0 and (light Volunteer + theNum) <= MAX_NUM_VOLUNTEERS_PER_JOB
	 * @return true if medium volunteers number is accepted, false otherwise
	 * @author Dereje Bireda
	 */
	public boolean isMaxMediumVolNumValid( int theNum) {
		boolean numAccepted = false;
		int currentTotal = myJob.getMyLightVolunteerNumber() + theNum;
		
		if(currentTotal >=0 && currentTotal <= MAX_NUM_VOLUNTEERS_PER_JOB ) {
			myJob.setMyMediumVolunteerNumber(theNum);

			numAccepted = true;
		} else {
		  numAccepted = false;
		}
		
		return numAccepted;
	}
	
	/**
	 * Checks business rule: Not more than the maximum number of volunteers for any job
	 * assign required number of volunteers for heavy work
	 * 
	 * @param theNum the number of light work volunteers needed
	 * @precondition theNum should be > 0 and 
	 * (light Volunteer number + medium volunteer number + theNum) <= MAX_NUM_VOLUNTEERS_PER_JOB
	 * 
	 * @return true if heavy volunteers number is accepted, false otherwise
	 * @author Dereje Bireda
	 */
	public boolean isMaxHeavyVolNumValid(int theNum) {
		boolean numAccepted = false;
		int currentTotal = myJob.getMyLightVolunteerNumber() + myJob.getMyMediumVolunteerNumber() + theNum;
		if(currentTotal > 0 && currentTotal <= MAX_NUM_VOLUNTEERS_PER_JOB ) {
			myJob.setMyHeavyVolunteerNumber(theNum);
			numAccepted = true;
		} else {
		  numAccepted = false;
		}
		
		return numAccepted;

	}
	

	/**
	 * assign a job an id, and add the job
	 * to system job list
	 * 
	 */
	//set Job ID/
	public void addJob() {
		//the size of the existing job becomes the id of the new job
		myJob.setMyJobId(myJobs.size());
		//add Job
		myJobs.add(myJob);
	}
	
	 /**
     * Checks business rule: There can be no more than the maximum number of jobs scheduled on any given day
     * 
     * Checks against the system's total job limit for input day (SYSTEM_MAX_JOBS_IN_ANY_GIVEN_DAY).
     * Called by hasDurationDayshasNoMaxJobs() once for each day of the new job
     * @precondition theDate should be valid LocalDate object
     * 
     * @param theDate passed from hasDurationDayshasNoMaxJobs()
     * @return true if there's room for a new job in the system
     *         false if a job cannot be added because the system is at limit
     */
		 public boolean hasLessThanMaxJobsOnJobDate(LocalDate theDate) {
			
			 
			  	boolean dateHasPassed = true;
				int countSameStartDayJobs = 0;
				for (int i = 0; i < myJobs.size(); i++) {
					if(myJobs.get(i).getMyStartDate().equals(theDate)){
						
							countSameStartDayJobs += ONE_DAY_OFFSET;
							if(countSameStartDayJobs == SYSTEM_MAX_JOBS_IN_ANY_GIVEN_DAY) {
								
								dateHasPassed = false;
								break;
							}
						
					}
					
				}
				
				return dateHasPassed;
		 }

    /**
     * Checks business rule:There can be no more than the maximum number of jobs scheduled on any given day 
     * 
     * calls hasLessThanMaxJobsOnJobDate() for each day during the duration of the job, that function enforces the rule
     * 
     * @param theStartDate of the new submitted job
     * @param theDuration of the submitted job
     * @precondition valid LocalDate as startDate expected
     * 
     * @return true if there's room for a new job in the system
     *         false if a job cannot be added because the system is at limit
     */
	 public boolean hasDurationDayshasNoMaxJobs(LocalDate theStartDate,int theDuration) {
		 
		 boolean dateHasPassed = true;
		 if(theDuration == 1) {
			 //no need to check already passed in checking start date
			 return dateHasPassed;
		 }
			
		 for(int i = 0; i < theDuration; i++) {
			LocalDate checkedDate = theStartDate.plusDays(i);
	
			//if it doesnt pass ,exit, the end date not accepted
			 if(!hasLessThanMaxJobsOnJobDate(checkedDate)){
				 dateHasPassed = false;
				 break;
			 }
		 }
		 	
		
				
				return dateHasPassed;
		 }	
	 

	 
	/********************
	 *  Helper methods
	 *******************/
	 //format time string to Time
	 
	 /**
	  * convert the string time to LocalTime object
	  * 
	  * @param timeString the time given as string
	  * @return the formatted string date as LocalTime object
	  */
	 public static LocalTime convertStringToTime(String timeString)
	 {
		 //possible user time input formats
		String[] formats = {"Hmm", "HH:mm","HHmm","H:mm"};
		
	   LocalTime time = null;
	   for (int i = 0; i < formats.length; i++)
	   {
	     String format = formats[i];
	     Locale locale = Locale.US;
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( format ).withLocale (locale);

	     try
	     {
	       time = LocalTime.parse(timeString, formatter);
	   
	       break;
	     }
	     catch(DateTimeParseException e)
	     {
	       
	     }
	   }

	   return time;
	 }
	 
	 /**
	  * convert string date to LocalDate object 
	  * 
	  * @param theDate the date given as string
	  * @return the formated string date as LocalDate object
	  */
	//format string date to LocalDate
	public static LocalDate convertStringToDate(String theDate) {
	

		try {
			Locale locale = Locale.US;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "MM/dd/yy" ).withLocale (locale);
			LocalDate localDate = LocalDate.parse ( theDate , formatter );

			return localDate;
			} catch(Exception e) {
			
				
				return null;
			}

		
	}
	
	
	/**
	 * calculate the difference between two days 
	 * Example,given 02/06/2017(first date) and 02/08/2017
	 *  will return 2(exclude end date), but not 3
	 * 
	 * @param firstDate the first date
	 * @param secondDate the second date, latest
	 * 
	 * @return the difference between the two days, exclusive of the second date
	 */
	//calculate difference between dates
	 public static long numOfDaysBetweenTwoDays(LocalDate firstDate, LocalDate secondDate) {
		
	    return  ChronoUnit.DAYS.between(firstDate,secondDate);
	 }
	 
	//this simplify testing but our application users--volunteer and manager cant use it
	 public void setMyMaxNumberOfPendingJobs(int theMaxPendingJobsAllowed) {
			this.myMaxNumberOfPendingJobs = theMaxPendingJobsAllowed;
	 }
	
	 
}