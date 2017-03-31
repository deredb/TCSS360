package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.ParkManager;
import model.ParksSystem;
import model.Volunteer;

/**
 * 
 * @author Dereje Bireda
 *
 */
public class ParksSystemTest {
	List<Volunteer>validVolunteerList;
	List<ParkManager>validParkManagerList;
	String validVolunteerEmail;
	String validParkManagerEmail;
	String invalidEmail;
	String anotherInvalidEmail;
	ParksSystem pSystem;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		validVolunteerEmail = "ebanks7@angelfire.com";
		validParkManagerEmail = "jerry0@alki.com";
		
		validVolunteerList = new ArrayList<>();
		Volunteer volunteer = new Volunteer();
		volunteer.setMyEmail(validVolunteerEmail);
		//add to list
		validVolunteerList.add(volunteer);
		
		validParkManagerList = new ArrayList<>();
		ParkManager parkManager = new ParkManager();
		parkManager.setMyEmail(validParkManagerEmail);
		//add to list
		validParkManagerList.add(parkManager);

		invalidEmail = "yahoo.com";
		anotherInvalidEmail = "star@gmail.com";
		
		pSystem = new ParksSystem();
		pSystem.setMyParkManagers(validParkManagerList);
		pSystem.setMyVolunteers(validVolunteerList);
		
		
	}

	/**
	 * 
	 *{@link model.ParksSystem#loginSuccessful(String)}
	 *@author Dereje Bireda
	 */
	@Test
	public void loginSuccessful_WhenInvalidEmailGiven_ReturnedFalse() {
		
		assertFalse("Fail invalid email given",pSystem.loginSuccessful(invalidEmail));
		assertFalse("Fail another invalid email given",pSystem.loginSuccessful(anotherInvalidEmail));
	} 
	
	/**
	 * 
	 *{@link model.ParksSystem#loginSuccessful(String)}
	 *@author Dereje Bireda
	 */
	@Test
	public void loginSuccessful_WhenValidEmailGiven_ReturnedTrue() {
		
		assertTrue("Fail valid email given",pSystem.loginSuccessful(validVolunteerEmail));
		assertTrue("Fail another valid email given",pSystem.loginSuccessful(validParkManagerEmail));
	}
	
	/**
	 * 
	 *{@link model.ParksSystem#loginSuccessful(String)}
	 *@author Dereje Bireda
	 */
	@Test
	public void loginSuccessful_WhenLoggedInAsVolunteerCurrentUserIsVolunteer_ReturnedTrue() {
		
		assertTrue("Fail valid email given",pSystem.loginSuccessful(validVolunteerEmail));
	
		
		assertTrue(pSystem.getMyCurrentUser() instanceof Volunteer);
	}

	/**
	 * 
	 *{@link model.ParksSystem#loginSuccessful(String)}
	 *@author Dereje Bireda
	 */
	@Test
	public void loginSuccessful_WhenLoggedInAsVolunteerCurrentUserIsParkManager_ReturnedFalse() {
		//logged in
		assertTrue("Fail valid email given",pSystem.loginSuccessful(validVolunteerEmail));
		
		assertFalse(pSystem.getMyCurrentUser() instanceof ParkManager);
	}
	
	/**
	 * 
	 *{@link model.ParksSystem#loginSuccessful(String)}
	 *@author Dereje Bireda
	 */
	@Test
	public void loginSuccessful_WhenLoggedInAsParkManagerCurrentUserIsParkManager_ReturnedTrue() {
		
		assertTrue("Fail valid email given",pSystem.loginSuccessful(validParkManagerEmail));
	
		
		assertTrue(pSystem.getMyCurrentUser() instanceof ParkManager);
	}

	/**
	 * 
	 *{@link model.ParksSystem#loginSuccessful(String)}
	 *@author Dereje Bireda
	 */
	@Test
	public void loginSuccessful_WhenLoggedInAsParkManagerCurrentUserIsVolunteer_ReturnedFalse() {
		//logged in
		assertTrue("Fail valid email given",pSystem.loginSuccessful(validParkManagerEmail));
		
		assertFalse(pSystem.getMyCurrentUser() instanceof Volunteer);
	}
}
