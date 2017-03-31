package UserInterface;


import java.util.Scanner;

import model.ParksSystem;

public class HomeView {
	
	private static final String Q_FOR_QUIT_SYSTEM_COMMAND = "Q";
	private static final int OFFSET = 1;
	private static final int MAX_USER_INPUT_TRIAL = 3;
	private static Scanner myReader;
	private static ParksSystem mySystem;

	//private boolean pageRedirected = false;
	
	public HomeView() {
		
		HomeHeaderTitle();
	}
	private void HomeHeaderTitle() {
		System.out.println("Welcome to UParks");
	}
	
	public void initHome(Scanner theScanner) {
		myReader = theScanner;

		System.out.println("Enter Email or press " + 
		  Q_FOR_QUIT_SYSTEM_COMMAND + " to quit system!");
	}


	public boolean loginTrialView(ParksSystem theSystem) {
		mySystem = theSystem;
		boolean pageRedirected = false;
		for (int retries = 0; retries < MAX_USER_INPUT_TRIAL; retries++) {
			String userCredential = myReader.nextLine();
			
			if(userCredential.equalsIgnoreCase(Q_FOR_QUIT_SYSTEM_COMMAND)){
				mySystem.logout();
				break;
			}
			if(mySystem.loginSuccessful(userCredential)) {
				
				pageRedirected = true;
				System.out.println("Login Successful");
				break;
			} else {
			
				if ( retries == MAX_USER_INPUT_TRIAL - OFFSET) {
					System.out.println("Failed max trial system exiting....");
					mySystem.logout();
				} else {

					System.out.println("Login failed: try again! or ");
					System.out.println("press "+ Q_FOR_QUIT_SYSTEM_COMMAND +" to quit!");
				}
			}
		}
		//return pageRedirected;
		pageRedirected = mySystem.isPageRedirected();
	
		return pageRedirected;
	}

	public void exitSystem(){
		mySystem.logout();
	}
}
