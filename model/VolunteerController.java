package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * VolunteerController does queries on behalf of the Volunteer
 * holds the volunteer who control the Controller, 
 * list of system jobs
 * 
 * @author Dereje Bireda
 *
 */

public class VolunteerController extends AbstractController {
	
   
	private static final long serialVersionUID = 1L;
	private static final int MAX_ALLOWED_JOB_PER_VOLUNTEER_PER_DAY = 1;
	private Volunteer myUser;    
    private List<Job>myJobs;    
   
    
    public VolunteerController(Volunteer theUser, 
           List<Job> theJobs) { 
    	
        super(theJobs,theUser);
        
        this.myUser = theUser;
        this.myJobs = theJobs;        
    }

    /**
     * Checks if pending Job for current user of the controller
     * is empty or not.
     * 
     * @precondition must be called on properly initialized controller
     * Eg. VolunteerController which holds list of jobs and 
     * current User(Eg.Volunteer)
     * 
     * @return true if the current user of the VolunteerController 
     * has the job, false otherwise
     *
     * @author Dereje Bireda
     * 
     */
    public boolean isJobAvailableToSignUp() {
    	List<Job> pendingJobs = this.getMyPendingJobsForVolunteer();
    	
    	return !pendingJobs.isEmpty();
    }
    
    /*****************************
	  * Query for volunteers
	  **************************/
	 
	 
	 /**
	  * Search for available jobs for a volunteer to sign up for
	  * 
	  * @param theJobs theJobs the jobs to be searched
	  * @param volunteerId volunteer id the pending jobs searched for
	  * @return a list of jobs that are pending for given volunteer
	  * @precondition must be called on properly initialized controller
	  * which holds the list of jobs
	  * 
	  * @author Dereje Bireda
	  */
	 //get pending jobs(open for sign up + is not full)
	 //volunteer specific job pending query
	 public List<Job> getMyPendingJobsForVolunteer() {
		 List<Job> pendingJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < myJobs.size(); i++) {
			 	Job jobChecked = myJobs.get(i);
				if(!jobChecked.getMyJobIsPast()) {
					//job is not full and signing up day not passed and 
					//the volunteer does not have the job
					if(!hasMinSignupDaysBeforeJobStartPassed(jobChecked) &&
							!isJobFullForSignUp(jobChecked) &&
							//this volunteer specific
							!volunteerHasTheJob( jobChecked.getMyJobId())) {
						//add it to pending
						pendingJobs.add(jobChecked);
					}
				}
				
			}
		 
		 return pendingJobs;
	 }
	 
		/**
		 * gets a list of upcoming jobs for volunteer
		 * 
		 * @return list of jobs not completed by volunteer
		 * @precondition must be called on properly initialized controller
		 * which holds the list of jobs, and current user,i.e the volunteer
		 * @author Dereje Bireda
		 * 
		 */
		//jobs a volunteer currently  signed up 
		public List<Job> getVolunteerUpcomingJobs() {
			
			List<Integer> currentJobsIds = myUser.getMyVolunteerJobs();
			List<Job> signupJobs = new ArrayList<>();
			for(int i = 0; i < currentJobsIds.size(); i++) {
				Job checkedJob = myJobs.get(currentJobsIds.get(i));
				//if not past job add it
				
				if(!checkedJob.getMyJobIsPast()) {
					signupJobs.add(checkedJob);
				}
			}
			
			return signupJobs;
		}
		
		/**
		 * gets booked or occupied calendar days for a volunteer
		 * 
		 * @return a list of calendar days the volunteer signed up for
		 * @precondition must be called on properly initialized controller
	     * which holds the list of jobs, and current user of the controller,
	     *  i.e the volunteer
	     *  
	     * @author Dereje Bireda
		 */
		public List<LocalDate> bookedUpcomingJobsCalendarDaysForVolunteer() {
		
			List<Job> upcomingJobs = this.getVolunteerUpcomingJobs();
			//each specific days volunteer signed up
			List<LocalDate> bookedDays = new ArrayList<>();
			
			//check each upcoming jobs
			for (int i = 0; i < upcomingJobs.size(); i++) {
				Job jobCheckced = upcomingJobs.get(i);
				
				int jobDuration = (int) numOfDaysBetweenTwoDays(jobCheckced.getMyStartDate(),
							jobCheckced.getMyEndDate());
				
				//get each days during the job duration
				for(int j = 0; j < jobDuration; j++) {
					LocalDate dateBooked = jobCheckced.getMyStartDate().plusDays(j);
					
					bookedDays.add(dateBooked);
				}
			}
			
			
			
			return bookedDays;
		}
		
		/**
		 *  Checks business rule: A volunteer cannot sign up for more than one job on any given day.
		 *  Checks against MAX_ALLOWED_JOB_PER_VOLUNTEER_PER_DAY
		 *  
		 *  Checks if the duration of the new job overlap with calendar days
		 *  the volunteer already signed up for 
		 *  
		 * @param theNewJobId to be signed up
		 * @return true if the new job id violates MAX_ALLOWED_JOB_PER_VOLUNTEER_PER_DAY
		 * @precondition theNewJobId should be >=0 and it should be among a list of available 
		 * jobs for sign up
		 * 
		 * @author Dereje Bireda
		 * 
		 */
	 //check MAX_ALLOWED_JOB_PER_VOLUNTEER_PER_DAY, exclude end date in calculation
	 public boolean hasJobViolateMaxJobPerDayPerVolunteer(int theNewJobId) {
		 
		 boolean maxDayViolated = false;
		 
		 //list of calendar days occupied by volunteer
		 List<LocalDate> bookedListOfDays =  bookedUpcomingJobsCalendarDaysForVolunteer();
		 
		 Job jobChecked = this.getSingleJobByGivenId(new Integer(theNewJobId));
		 
		 int jobCheckedDuration = (int) numOfDaysBetweenTwoDays(jobChecked.getMyStartDate(),jobChecked.getMyEndDate());
		 
		 //loop the duration of the new job, and check each days with volunteer calendar days
		 for(int i = 0; i < jobCheckedDuration; i++) {
			 //Bug, I should be adding the day to start date, not end date
			 LocalDate dateChecked = jobChecked.getMyStartDate().plusDays(i);
			 
			 //count occurence of new job date in booked calendar days
			 int countDateCheckedOccurence = 0;
			 //Need another for loop to if MAX_ALLOWED_JOB_PER_VOLUNTEER_PER_DAY becomes >1
			 for(int j = 0; j < bookedListOfDays.size(); j++) {
				 if(bookedListOfDays.get(j).equals(dateChecked)) {
					 
					 countDateCheckedOccurence += 1;
					 
					 if(countDateCheckedOccurence >= MAX_ALLOWED_JOB_PER_VOLUNTEER_PER_DAY) {
						 
						 maxDayViolated = true;
						 
						 break;
					 }
				 }
			 
			 }
		 }
		 
		 
		 
		return maxDayViolated; 
		 
	 }
	 
	
	
	 /**
	  * checks if the volunteer has already signed up for the job
	  * 
	  * @param theJobId the job checked 
	  * @return true if the volunteer has the job id, false otherwise
	  * @precondition the id should be positive number to get expected
	  * result
	  */
	public boolean volunteerHasTheJob( int theJobId) {
		 List<Integer> currentJobsList = myUser.getMyVolunteerJobs();
	
		   return currentJobsList.contains(new Integer(theJobId));
		
	}
	

}