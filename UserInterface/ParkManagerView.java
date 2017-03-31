package UserInterface;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Job;
import model.JobController;
import model.ParkManager;
import model.ParkManagerController;



public class ParkManagerView extends HomeView {
	
	private static final int MAX_USER_INPUT_TRIAL = 3;
	private static final int OFFSET = 1;
	private static final int MIN_JOB_POST_DAY_LENGTH = 3; 
	private static final int MAX_NUM_VOLUNTEERS_PER_JOB = 10;
	private static final String V_FOR_VIEW_COMMAND = "V";
	private static final String S_FOR_SUBMIT_OR_SINGUP_COMMAND = "S";
	private static final String L_FOR_LOGOUT_USER_VIEW_COMMAND = "L";
	private static final String R_FOR_RETURN_PREV_VIEW_FOR_USERS = "R";
	private static final int MAX_ALLOWED_DATE_INTO_FUTURE = 75;
	private  ParkManager myManager;
	private  JobController jobValidator;
	private  ParkManagerController myController;
	private Scanner myReader;
	
	
	public ParkManagerView(ParkManagerController theController, JobController theJobValidator, ParkManager theUser){
		super();
		this.myManager = theUser;
		this.jobValidator = theJobValidator;
		this.myController = theController;
	
		HomeHeaderTitle();
	}
	
	private void HomeHeaderTitle() {
		System.out.println("Today, " + myController.convertLocalDatetToReadableString(LocalDate.now())+
				" Welcome to UParks " + myManager.getMyName());
	   
	}
	public void initHome(Scanner theScanner){

		myReader = theScanner;
		StringBuilder managerMenu = new StringBuilder();

		managerMenu.append("Choose the command option, and press enter:\n");
		if(myController.isNewJobAccepted()) {
			managerMenu.append(S_FOR_SUBMIT_OR_SINGUP_COMMAND +" Submit a job\n");
			managerMenu.append(V_FOR_VIEW_COMMAND + " View my Jobs\n");
			managerMenu.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " Logout");
		} else {
			managerMenu.append("We are not accepting new job currently\n");
			managerMenu.append(V_FOR_VIEW_COMMAND+ " View my Jobs\n");
			//show the jobs already created and 1 option
			managerMenu.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " logout");
		}
		System.out.println(managerMenu);
		String userChoice = myReader.nextLine();
		
	
		showChoosenCommand(userChoice);
	}

	public void showChoosenCommand(String userInput) {
		
		if(userInput.equalsIgnoreCase(S_FOR_SUBMIT_OR_SINGUP_COMMAND)) {
			sumbitJobView();
			
			exitOrReturnView();
	
		}  else if(userInput.equalsIgnoreCase(L_FOR_LOGOUT_USER_VIEW_COMMAND)) {
			myController.logout();
		} else if (userInput.equalsIgnoreCase(V_FOR_VIEW_COMMAND)) {
			
			viewManagerJobs();
			exitOrReturnView();
		}
	}
	
	public void exitOrReturnView() {
		
		StringBuilder exitString = new StringBuilder();
		exitString.append("What would you like to do?\n");
		exitString.append(R_FOR_RETURN_PREV_VIEW_FOR_USERS + " Return to prior menu\n");
		exitString.append(L_FOR_LOGOUT_USER_VIEW_COMMAND + " Logout");
		
		System.out.println(exitString);
		String userInput = myReader.nextLine();
		if (userInput.equalsIgnoreCase(L_FOR_LOGOUT_USER_VIEW_COMMAND)) {
			myController.logout();
		} else if(userInput.equalsIgnoreCase(R_FOR_RETURN_PREV_VIEW_FOR_USERS)) {
			initHome(myReader);
		}
		
		
	}
	
	public void sumbitJobView(){
	
		if(acceptParkView() && acceptDateView()  && acceptEndDateView() 
				&& acceptDescriptionView() && acceptNumOfVolunteers() 
				&& confirmSubmitView()) {
			
			System.out.println("Job added successfully");
		}
		
	
	}
	public boolean acceptParkView() {
		boolean parkAdded = false;
		if(!jobValidator.isParkAdded()){
			System.out.println("Enter 1: for " + myManager.getMyParks().get(0).getMyName() + "\n"
					+ " Or enter 2 for " + myManager.getMyParks().get(1).getMyName());
			
			for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
				String parkChoice = myReader.nextLine();
				
				int parkChoiceNum = 0;
				
				try {
				
					parkChoiceNum = Integer.parseInt(parkChoice);
					if(parkChoiceNum <= 0) {
						parkAdded = false;
						System.out.println("Please enter number 1 or 2");
						continue;
					} else {
						jobValidator.pickAPark(parkChoiceNum);
						parkAdded = true;
						break;
					}
				} catch(InputMismatchException | NumberFormatException ex){
					
					if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
		    			System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
		    			parkAdded = false;
		    			break;
		    		} else {
		    			System.out.println("Please enter number 1 or 2");
		    		}
		    		continue;
				}
			
			}
				
		
		} else {
			//park manager has only 1 park
			parkAdded = true;
		}
		
		
		return parkAdded;
		
	}
	public boolean acceptDateView() {

		System.out.println("Enter dates between " + LocalDate.now().plusDays(MIN_JOB_POST_DAY_LENGTH) + " and " +
		LocalDate.now().plusDays(MAX_ALLOWED_DATE_INTO_FUTURE)+" inclusive");
		System.out.println("Enter Start date(format MM/dd/yy)");
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
		
		    	String date = myReader.nextLine();
		    	//initially done inside isStartDdateAdded
		    	LocalDate startDate = JobController.convertStringToDate(date);
		    	
		    	if(!jobValidator.isStartDateAdded(startDate)) {
		    		if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
		    			System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
		    			
		    		} else {
		    		System.out.println("Re-enter correct date: Reasons for failure\n"
		    				+ "Date entered doesn't allow volunteer to sign up\n"
		    				+ "date picked already has max limit of jobs\n"
		    				+ "the date is too far int the future to signup.\n"
		    				+ "Or invalid date input");
		    		}
		    		continue;
		    	} else {
		    		return true;
		    		
		    	}
		    	
		   
		}
		
		
		return false;
	}
	
	public boolean acceptEndDateView() {
		System.out.println("Enter job duration: 1 2 or 3");
		boolean numAccepted = false;
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			String jobDuration = myReader.nextLine();
			
			int jobDurationIntValue = 0;
			
			try {
			
				jobDurationIntValue = Integer.parseInt(jobDuration);
			} catch(InputMismatchException | NumberFormatException ex){
				
				numAccepted = false;
				System.out.println("Unexpected input exiting..");
				break;
			}
		
		
			
			//we have number read this part
	    	if(jobDurationIntValue < 0 || !jobValidator.isEndDateAdded(jobDurationIntValue)) {
	    		if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
	    			System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
	    			numAccepted = false;
	    			break;
	    		} else {
	    			System.out.println("Please enter valid duration");
	    		}
	    		continue;
	    	} else {
	    		numAccepted = true;
	    		break;
	    	}
	    	
	   
		}
	
	
		return numAccepted;
	}

	public boolean acceptDescriptionView() {
		
		System.out.println("Enter job Description:");
		
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			
			String jobDescription = myReader.nextLine();

	    	if(!jobValidator.isJobDescriptionAdded(jobDescription)) {
	    		if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
	    			System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
	    			break;
	    			
	    		} else {
	    			System.out.println("Please enter valid description");
	    		}
	    		continue;
	    	} else {
	    		return true;
	    	}
	    	
	   
		}
	
	
		return false;
	}

	public boolean acceptNumOfVolunteers() {
	
		boolean numAccepted = false;
		System.out.println("Enter Volunteers number required: light + medium + heavy <= " + MAX_NUM_VOLUNTEERS_PER_JOB);
		
		//each question has max 3 trials
		
		
		for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
			System.out.println("Light: ");
			int lightIntValue = 0;
			try {
				String light = myReader.nextLine();
				lightIntValue = Integer.parseInt(light);
			} catch(InputMismatchException | NumberFormatException ex){
				System.out.println("Failed: Unexpected input exiting..");
				numAccepted = false;
				break;
			}
			
			if(!jobValidator.isMaxLightVolNumberValid(lightIntValue)) {
				
				if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
					System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
	    			numAccepted = false;
	    			break;
	    		} else {

					System.out.println("Enter valid max light volunteer number");
	    		}
	    	
	    		continue;
			} else {
				numAccepted = true;
				break;//go to medium
			}
		}	
		
		//start getting medium volunteer number
		if (numAccepted) {//previous category accepted we check this
			for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
				System.out.println("Medium: ");
				int medIntValue =0;
				try {
					String med = myReader.nextLine();
					medIntValue = Integer.parseInt(med);
				} catch(InputMismatchException ex){
					
					numAccepted = false;
					System.out.println("Failed: Unexpected input exiting..");
					break;
				}
				if(!jobValidator.isMaxMediumVolNumValid(medIntValue)) {
					if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
						System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
		    			numAccepted = false;
		    		} else {
		    			System.out.println("Enter valid max medium volunteer number");
		    		}
		    	
				
		    		continue;
				} else {
					numAccepted = true;
					break;//go to heavy
				}
			}	
		}

		//start getting medium volunteer number
		if(numAccepted) {//previous categeory accepted we check this
			for (int retries = 0;retries < MAX_USER_INPUT_TRIAL; retries++) {
				System.out.println("Heavy: ");
				int heavyIntValue = 0;
				
				try {
					String heavy = myReader.nextLine();
					heavyIntValue = Integer.parseInt(heavy);
				} catch(InputMismatchException ex){
					
					numAccepted = false;
					System.out.println("Failed: Unexpected input exiting..");
					break;
				}
				
				
				if(!jobValidator.isMaxHeavyVolNumValid(heavyIntValue)) {
					if(retries == MAX_USER_INPUT_TRIAL-OFFSET) {
						System.out.println("Failed " + MAX_USER_INPUT_TRIAL+ "times exiting..");
		    			numAccepted = false;
		    		
		    		} else {
		    			System.out.println("Enter valid max heavy number of volunteer");
		    		}
		    		continue;
				} else {
					numAccepted = true;
					break;
				}
			}	
				
		
		}
		
		return numAccepted;
	}
	
	public boolean confirmSubmitView() {
		boolean jobSubSuccess = false;
		System.out.println("Enter y/n to accept or reject the job submission");
		
		String userConfirmation = myReader.nextLine();
		if(userConfirmation.equalsIgnoreCase("Y")) {
			jobValidator.addJob();
			
			jobSubSuccess = true;
		} else {
			jobSubSuccess = false;
		}
		//clean it for new job Validation
		jobValidator = new JobController(new Job(null), myController.getJobs(),myManager);
		return jobSubSuccess;
	}
	
    /**
     * 
     * @author Tony Richardson
     * I did not write the whole function. I just reworked the printing format.
     */
	public void viewManagerJobs(){
        int jobIdSection = 11;
        int descriptionSection = 50;
        int startDateSection = 17;
        int endDateSection = 17;
        int currentVolunteersSection = 20;
		List<Job>managerJobs = myController.getJobsByManagerId();
		StringBuilder managerJobsString = new StringBuilder();
        
        
        managerJobsString.append("Job Id");
        managerJobsString.append(nDashes(jobIdSection - "Job Id".length()));
        managerJobsString.append("Job Description(Park Name)");
        managerJobsString.append(nDashes(descriptionSection - "Job Description(Park Name)".length()));
        managerJobsString.append("Start Date");
        managerJobsString.append(nDashes(startDateSection - "Start Date".length()));
        managerJobsString.append("End Date");
        managerJobsString.append(nDashes(endDateSection - "End Date".length()));
        managerJobsString.append("Current Volunteers # \n");
        
        
		//managerJobsString.append("Job Id-----Job Description(Park Name)-------Start Date-------End Date-----Current Volunters # \n");
		
        
		String shortDescription = "";
		for(int i = 0; i < managerJobs.size(); i++) {
			
			shortDescription = myController.truncateJobDescriptionForDisplay(managerJobs.get(i));
			
            managerJobsString.append(managerJobs.get(i).getMyJobId());
            //                                                            gets number of digits in job id
            managerJobsString.append(nDashes(jobIdSection - String.valueOf(managerJobs.get(i).getMyJobId()).length()));
            managerJobsString.append(shortDescription);
            managerJobsString.append('(');
            managerJobsString.append(managerJobs.get(i).getMyPark().getMyName());
            managerJobsString.append(')');
            // the 2 is for the 2 parenthesis added
            managerJobsString.append(nDashes(descriptionSection - (shortDescription.length() + managerJobs.get(i).getMyPark().getMyName().length() + 2)));
            managerJobsString.append(managerJobs.get(i).getMyStartDate());
            managerJobsString.append(nDashes(startDateSection - managerJobs.get(i).getMyStartDate().toString().length()));
            managerJobsString.append(managerJobs.get(i).getMyEndDate());
            managerJobsString.append(nDashes(endDateSection - managerJobs.get(i).getMyEndDate().toString().length()));
            managerJobsString.append(managerJobs.get(i).getMyCurrentTotalVolunteers());
            managerJobsString.append('\n');
            
            
            /*
			managerJobsString.append(managerJobs.get(i).getMyJobId() +" ------------ " + shortDescription + "(" + managerJobs.get(i).getMyPark().getMyName() + ")"+ "------" +
		         managerJobs.get(i).getMyStartDate()+ " ------- "+ managerJobs.get(i).getMyEndDate()+" ----- "
					+ managerJobs.get(i).getMyCurrentTotalVolunteers() + " \n");
			*/
		}
		System.out.println(managerJobsString);
		
	}
    
    private String nDashes(int numDashes) {
        StringBuilder sb = new StringBuilder(numDashes);
        for(int i = 0; i < numDashes; i++) {
            sb.append('-');
        }
        return sb.toString();
    }
}