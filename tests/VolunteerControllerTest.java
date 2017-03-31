package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Job;
import model.Park;
import model.Volunteer;
import model.VolunteerController;
/**
 * Test VolunteerController for functions that checks volunteer
 * sign up for a job
 * @author Dereje
 *
 */
public class VolunteerControllerTest { 
	private static final int JOB_ID_WITH_NO_CONFLICTING_DATE = 0;
	private static final  int JOB_ID_WITH_CONFLICTING_DATE_AS_JOB_ID_ONE = 1;
	
	private static final int VOLUNTEER_ID = 0;
	private static final int INVALID_JOB_ID_NEGATIVE_VALUE = -1;
	private static final int NOT_USED_JOB_ID = 2;
	private static final int MIN_NUM_DAYS_FOR_JOB_SIGNUP = 2;
	private static final int ONE_DAY_OFFSET = 1;
	
	private Job nonConflictingJob;
	private Job conflictingJob;
	private Volunteer volunteer;
	private VolunteerController controller;
	private List<Job>emptyJobList;
	private List<Job>JobWithTwoPendingJobs;
	private LocalDate myCurrentDate;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	
	public void setUp() {
		volunteer = new Volunteer();
		JobWithTwoPendingJobs = new ArrayList<>();
		emptyJobList = new ArrayList<>();
		
		//makes date testing future 
		myCurrentDate = LocalDate.now();
		
		/*set up non conflicting job that could be added with no issue*/
		nonConflictingJob = new Job(new Park());
		nonConflictingJob.setMyJobId(JOB_ID_WITH_NO_CONFLICTING_DATE);
		LocalDate firstJobStartDate = myCurrentDate.plusDays(3);
		LocalDate firstJobEndDate = myCurrentDate.plusDays(5);
	
		nonConflictingJob.setMyStartDate(firstJobStartDate);
		nonConflictingJob.setMyEndDate(firstJobEndDate);
		//number of volunteers needed
		nonConflictingJob.setMyLightVolunteerNumber(1);
		
		volunteer = new Volunteer();
		volunteer.setMyUserId(VOLUNTEER_ID);
		/*set up non conflicting job that could be added with no issue*/
		conflictingJob = new Job(new Park());
		conflictingJob.setMyJobId(JOB_ID_WITH_CONFLICTING_DATE_AS_JOB_ID_ONE);
		LocalDate secondJobStartDate = myCurrentDate.plusDays(3);
		LocalDate secondJobEndDate =  myCurrentDate.plusDays(5);
	
		conflictingJob.setMyStartDate(secondJobStartDate);
		conflictingJob.setMyEndDate(secondJobEndDate);
		//number of volunteers needed
		conflictingJob.setMyLightVolunteerNumber(1);
		JobWithTwoPendingJobs.add(nonConflictingJob);
		JobWithTwoPendingJobs.add(conflictingJob);
		
	
		/*set up controller*/
		 controller = new VolunteerController(volunteer, JobWithTwoPendingJobs);
	}



	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasJobViolateMaxJobPerDayPerVolunteer(int)}
	 */
	@Test
	public void hasJobViolatedMaxJobPerDayPerVolunteer_AddingJobWithNoPreviousJobs_ReturnedFalse() {
		
		assertFalse("Fail! with no jobs adding a job", 
				controller.hasJobViolateMaxJobPerDayPerVolunteer(nonConflictingJob.getMyJobId()));
	}
	
	
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasJobViolateMaxJobPerDayPerVolunteer(int)}
	 *
	 */
	@Test
	public void hasJobViolatedMaxJobPerDayPerVolunteer_WithAddingAboveLimitPerDay_ReturnedTrue(){
		//add first job
		volunteer.getMyVolunteerJobs().add(JOB_ID_WITH_NO_CONFLICTING_DATE);
		
		//try to add another one which fall same date or days as the one in volunteer bucket
		assertTrue("Fail! new Job added schedule violate max Job limit per day", 
				controller.hasJobViolateMaxJobPerDayPerVolunteer(conflictingJob.getMyJobId()));
		
	}
	
	


	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasMinSignupDaysBeforeJobStartPassed(Job)}
	 */
	
	@Test
	public void hasMinSignupDaysBeforeJobStartPassed_SignupWhenJobIsPast_ReturnedTrue() {
		Job pastJob = new Job(new Park());
		
		pastJob.setMyStartDate(myCurrentDate.minusDays(ONE_DAY_OFFSET));
		pastJob.setMyEndDate(myCurrentDate.plusDays(ONE_DAY_OFFSET));
		assertTrue("Fail! signed up for a past job", controller.hasMinSignupDaysBeforeJobStartPassed(pastJob));
		
		
	}
	
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasMinSignupDaysBeforeJobStartPassed(Job)}
	 */
	
	@Test
	public void hasMinSignupDaysBeforeJobStartPassed_JobHasAtLeastMinDaysForSignup_ReturnedFalse() {
		Job futureJobStillOpen = new Job(new Park());
	
		futureJobStillOpen.setMyStartDate(myCurrentDate.plusDays(MIN_NUM_DAYS_FOR_JOB_SIGNUP));
		futureJobStillOpen.setMyEndDate(myCurrentDate.plusDays(ONE_DAY_OFFSET));
		
		
		assertFalse("Fail! sign up for still open job", controller.hasMinSignupDaysBeforeJobStartPassed(futureJobStillOpen));
		
		
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#hasMinSignupDaysBeforeJobStartPassed(Job)}
	 */
	
	@Test
	public void hasMinSignupDaysBeforeJobStartPassed_JobStartDateBelowMinDaysFoSignup_ReturnedTrue() {
		Job futureJobClosed = new Job(new Park());

	
		futureJobClosed.setMyStartDate(myCurrentDate.plusDays(MIN_NUM_DAYS_FOR_JOB_SIGNUP - ONE_DAY_OFFSET ));
		futureJobClosed.setMyEndDate(myCurrentDate);
		
		
		assertTrue("Fail! sign up for future job, but sign up date passed",
				controller.hasMinSignupDaysBeforeJobStartPassed(futureJobClosed));
		
		
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobFullForSignUp(Job)}
	 */
	@Test
	public void isJobFullForSignUp_JobAlreadyHasMaxVolunteerAllowed_ReturnedTrue() {
		Job fullJob = new Job(new Park());
		int numberOfVolunteersRequested = 2;
		int numberOfCurrentVolunteers = 2;
		fullJob.setMyLightVolunteerNumber(numberOfVolunteersRequested);
		fullJob.setMyMediumVolunteerNumber(0);
		fullJob.setMyHeavyVolunteerNumber(0);
		fullJob.setMyCurrentTotalVolunteers(numberOfCurrentVolunteers);

		assertTrue("Fail!Job already has max number of volunteer requested",
					controller.isJobFullForSignUp(fullJob));
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobFullForSignUp(Job)}
	 */
	@Test
	public void isJobFullForSignUp_JobHasOneSpotTofill_ReturnedFalse() {
		Job fullJob = new Job(new Park());
		//volunteers number with two cases
		int numberOfVolunteersRequested = 2;
		int numberOfCurrentVolunteers = 1;
		
		fullJob.setMyLightVolunteerNumber(numberOfVolunteersRequested);
		fullJob.setMyMediumVolunteerNumber(0);
		fullJob.setMyHeavyVolunteerNumber(0);
		fullJob.setMyCurrentTotalVolunteers(numberOfCurrentVolunteers);

		assertFalse("Fail!Job has one spot left",
					controller.isJobFullForSignUp(fullJob));
	}
	
	


	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#volunteerHasTheJob(Job)}
	 */
	@Test
	public void volunteerHasTheJob_JobIsPreviouslySignedup_ReturnedTrue() {
		
		int jobId = 1;
		Job volunteerJob = new Job(new Park());
		volunteerJob.setMyJobId(jobId);
		//add volunteer to job object
		volunteer.setMyUserId(VOLUNTEER_ID);
		volunteerJob.getMyVolunteerList().add(VOLUNTEER_ID);
		//add job to volunteer
		volunteer.getMyVolunteerJobs().add(jobId);
		assertTrue("Fail!volunteer already signed up for the job",
					controller.volunteerHasTheJob(jobId));
	}
	
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#volunteerHasTheJob(Job)}
	 */
	@Test
	public void volunteerHasTheJob_VolunteerFirstTimeSignupForTheJob_ReturnedFalse() {
		
		int jobId = 1;
		Job newJob = new Job(new Park());
		newJob.setMyJobId(jobId);
		//add volunteer to job object
		volunteer.setMyUserId(VOLUNTEER_ID);
	
		assertFalse("Fail!Volunteer does not have the job",controller.volunteerHasTheJob(jobId));
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobAvailableToSignUp()}
	 */
	@Test
	public void isJobAvailableToSignUp_WhenJobListIsEmpty_ReturnedFalse() {
		//reset controller with empty job list 
		controller = new VolunteerController(volunteer,emptyJobList);
		assertFalse("Fail! no jobs available",controller.isJobAvailableToSignUp());
	}
	
	/**
	 * @author Dereje Bireda
	 * 
	 *{@link model.VolunteerController#isJobAvailableToSignUp()}
	 */
	@Test
	public void isJobAvailableToSignUp_WhenThereIsAJobForSignUp_ReturnedTrue() {
		//reset controller with empty job list 
		emptyJobList.add(nonConflictingJob);
		controller = new VolunteerController(volunteer,emptyJobList);
		assertTrue("Fail! There is one Job available",controller.isJobAvailableToSignUp());
	}
	

}
