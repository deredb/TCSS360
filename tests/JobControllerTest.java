package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.AbstractController;
import model.Job;
import model.JobController;
import model.Park;
import model.VolunteerController;

public class JobControllerTest {
	
	private List<Job> myJobs; 
    private JobController jobController;
    AbstractController theController;
    private Job newJobToBeAdded;
    private static final int MAX_NUM_VOLUNTEERS_PER_JOB = 10;
    private static final int NOT_VALID_VOLUNTEER_NUMBER = 11;
    private static final int ONE_VOLUNTEER_NUMBER = 1;
    private static final int MAX_ALLOWED_DATE_INTO_FUTURE = 75;
	private static final int MIN_JOB_POST_DAY_LENGTH = 3; 
	private static LocalDate dateAtMaxJobLimitPerDay;
    private LocalDate currentDate;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		myJobs = new ArrayList<Job>(); 
		newJobToBeAdded = new Job(new Park());
		jobController = new JobController(newJobToBeAdded, myJobs, null);
		theController = new VolunteerController(null, myJobs);
		currentDate = LocalDate.now();
		dateAtMaxJobLimitPerDay = LocalDate.now().plusDays(3);
	
	}


	
	/**
	 * {@link model.JobController#isMaxLightVolNumberValid(int)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isMaxLightVolNumberValid_AddingOneAboveMaxNumVolunteersPerJob_returnedFalse() {
		
		assertFalse("fail: light volunteer above 10",
				jobController.isMaxLightVolNumberValid(NOT_VALID_VOLUNTEER_NUMBER));
	}
	
	
	/**
	 * {@link model.JobController#isMaxMediumVolNumValid(int)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isMaxMediumVolNumValid_AddingOneAboveMaxNumVolunteersPerJob_returnedFalse() {
		assertFalse("fail: medium volunteer above 10",
				jobController.isMaxMediumVolNumValid(NOT_VALID_VOLUNTEER_NUMBER));
	}
	
	/**
	 * {@link model.JobController#isMaxHeavyVolNumValid(int)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isMaxHeavyVolNumValid_AddingOneAboveMaxNumVolunteersPerJob_returnedFalse() {
		assertFalse("fail: heavy volunteer above 10",
				jobController.isMaxHeavyVolNumValid(NOT_VALID_VOLUNTEER_NUMBER));
	}
	
	/**
	 * {@link model.JobController#isMaxLightVolNumberValid(int)}
	 */
	@Test
	public void isMaxLightVolNumberValid_AddingOneLessMaxNumVolunteersPerJob_returnedTrue() {
		
		assertTrue("fail: light volunteer less than 10, i.e 9",
				jobController.isMaxLightVolNumberValid(NOT_VALID_VOLUNTEER_NUMBER -ONE_VOLUNTEER_NUMBER));
	}
	
	/**
	 * {@link model.JobController#isMaxMediumVolNumValid(int)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isMaxMediumVolNumValid_AddingOneLessMaxNumVolunteersPerJob_returnedTrue() {
		
		assertTrue("fail: medium volunteer less than 10, i.e 9",
				jobController.isMaxMediumVolNumValid(NOT_VALID_VOLUNTEER_NUMBER -ONE_VOLUNTEER_NUMBER));
	}
	
	/**
	 * {@link model.JobController#isMaxHeavyVolNumValid(int)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isMaxHeavyVolNumValid_AddingOneLessMaxNumVolunteersPerJob_returnedTrue() {

		assertTrue("fail: heavy volunteer less than 10, i.e 9",
				jobController.isMaxHeavyVolNumValid(ONE_VOLUNTEER_NUMBER));
	}
	/**
	 * 
	 * {@link model.JobController#isMaxMediumVolNumValid(int)}
	 * @author Dereje Bireda
	 */
	
	@Test
	public void isMaxMediumVolNumValid_LightVolunteerHasMaxVolunteerLimit_returnedFalse() {
		newJobToBeAdded.setMyLightVolunteerNumber(MAX_NUM_VOLUNTEERS_PER_JOB);
		assertFalse("fail: heavy volunteer addition exceed max, i.e 10",
				jobController.isMaxMediumVolNumValid(ONE_VOLUNTEER_NUMBER));
		
	}
	
	/**
	 * 
	 * {@link model.JobController#isMaxMediumVolNumValid(int)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isMaxHeavyVolNumValid_LightPlusMediumVolunteerHasMaxVolunteerLimit_returnedFalse() {
		newJobToBeAdded.setMyLightVolunteerNumber(MAX_NUM_VOLUNTEERS_PER_JOB-1);
		newJobToBeAdded.setMyMediumVolunteerNumber(ONE_VOLUNTEER_NUMBER);
		assertFalse("fail: heavy volunteer additon becomes above 10",
				jobController.isMaxHeavyVolNumValid(ONE_VOLUNTEER_NUMBER));
		
	}

	/**
	 * 
	 * {@link model.JobController#isStartDateAdded(String)}
	 * @author Dereje Bireda
	 */
	
	@Test
	public void isStartDateAdded_WhenDateIsLEQTwoDaysFromCurrent_ReturnedFalse() {
		LocalDate startDate = currentDate.plusDays(1);
		
		assertFalse("Fail: date is less than two days in to future",
				jobController.isStartDateAdded(startDate));
	}
	

	/**
	 * 
	 * {@link model.JobController#isStartDateAdded(LocalDate)}
	 * @author Dereje Bireda
	 */
	
	@Test
	public void isStartDateAdded_WhenDateIsTwoDaysFromCurrent_ReturnedFalse() {
		LocalDate startDate = currentDate.plusDays(2);
		
		assertFalse("Fail: date is less two days in to future",
				jobController.isStartDateAdded(startDate));
	}
	/**
	 * 
	 * {@link model.JobController#isStartDateAdded(LocalDate)}
	 * @author Dereje Bireda
	 */
	
	@Test
	public void isStartDateAdded_WhenDateIsMoreThanTwoDaysFromCurrent_ReturnedTrue() {
		LocalDate startDate = currentDate.plusDays(MIN_JOB_POST_DAY_LENGTH);
		
		assertTrue("Fail: date is more than two days in to future",
				jobController.isStartDateAdded(startDate));
	}
	
	/**
	 * 
	 * {@link model.JobController#isStartDateAdded(LocalDate)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isStartDateAdded_WhenStartDateIsAboveSeventyFiveDays_ReturnedFalse() {
		LocalDate startDate = currentDate.plusDays(MAX_ALLOWED_DATE_INTO_FUTURE + 1);
		
		assertFalse("Fail: date is more than 75 days",
				jobController.isStartDateAdded(startDate));
	}
	
	/**
	 * 
	 * {@link model.JobController#isStartDateAdded(LocalDate)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isStartDateAdded_WhenStartDateIsSeventyFiveDays_ReturnedTrue() {
		LocalDate startDate = currentDate.plusDays(MAX_ALLOWED_DATE_INTO_FUTURE);
		
		assertTrue("Fail: date is 75 days in to the future",
				jobController.isStartDateAdded(startDate));
	}
	
	/**
	 * 
	 * {@link model.JobController#isStartDateAdded(LocalDate)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isStartDateAdded_WhenJobDateHasMaxJobs_ReturnedFalse() {
		LocalDate startDate = currentDate.plusDays(MIN_JOB_POST_DAY_LENGTH);
		//set up previous jobs
		Job openJobOne = new Job(new Park());
		openJobOne.setMyStartDate(startDate);
		openJobOne.setMyEndDate(startDate.plusDays(1));
		Job openJobTwo = new Job(new Park());
		openJobTwo.setMyStartDate(startDate);
		openJobTwo.setMyEndDate(startDate.plusDays(1));
		Job openJobThree = new Job(new Park());
		openJobThree.setMyStartDate(startDate);
		openJobThree.setMyEndDate(startDate.plusDays(1));
		Job openJobFour = new Job(new Park());
		openJobFour.setMyEndDate(startDate.plusDays(1));
		openJobFour.setMyStartDate(startDate);
		myJobs.add(openJobOne); myJobs.add(openJobTwo); myJobs.add(openJobThree); myJobs.add(openJobFour); 
		
		newJobToBeAdded.setMyStartDate(startDate);
		newJobToBeAdded.setMyEndDate(startDate.plusDays(1));
		assertFalse("Fail: Job date has max number of jobs per day",jobController.isStartDateAdded(startDate));
	}
	
	/**
	 * 
	 * {@link model.JobController#isStartDateAdded(LocalDate)}
	 * @author Dereje Bireda
	 */
	@Test
	public void isStartDateAdded_WhenJobDateHasOneLessThanMaxJobs_ReturnedTrue() {
		LocalDate startDate = currentDate.plusDays(MIN_JOB_POST_DAY_LENGTH);
		//set up previous jobs
		Job openJobOne = new Job(new Park());
		openJobOne.setMyStartDate(startDate);
		openJobOne.setMyEndDate(startDate.plusDays(1));
		Job openJobTwo = new Job(new Park());
		openJobTwo.setMyStartDate(startDate);
		openJobTwo.setMyEndDate(startDate.plusDays(1));
		Job openJobThree = new Job(new Park());
		openJobThree.setMyStartDate(startDate);
		openJobThree.setMyEndDate(startDate.plusDays(1));
		myJobs.add(openJobOne); myJobs.add(openJobTwo); myJobs.add(openJobThree); 
		
		newJobToBeAdded.setMyStartDate(startDate);
		newJobToBeAdded.setMyEndDate(startDate.plusDays(1));
		assertTrue("Fail: Job date has one less than max number of jobs per day",
				jobController.isStartDateAdded(startDate));
	}
}
