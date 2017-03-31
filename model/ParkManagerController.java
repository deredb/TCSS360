package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ParkManagerController does queries on behalf of the ParkManager
 * holds the ParkManager who control the Controller, 
 * list of system jobs, and volunteers
 * 
 * @author Dereje Bireda
 *
 */

public class ParkManagerController extends AbstractController implements Serializable{
    
	
	private static final long serialVersionUID = 1L;
	private List<Job>myJobs;
	private List<Volunteer>myVolunteers;
	private ParkManager myUser;
	
	
    public ParkManagerController(List<Job>theJobs, ParkManager theUser, List<Volunteer>theVolunteers) { 
        super(theJobs, theUser);
        myJobs = theJobs;
        myVolunteers = theVolunteers;
        myUser = theUser;
        
    }
    
    /***********************
	 * Searches for Park Managers
	 *******************/
	
	/**
	 * search for list of Volunteers for a job (past or present)
	 * with specific manager id
	 * 
	 * @param theVolunteers the volunteers to be searched
	 * @param theManagerId the id used to filter the volunteers
	 * @return list of volunteers signed up for specific manager
	 * @precondition must be called in properly initialized controller,
	 * i.e ParkManagerController, which holds the volunteers list, job list
	 * for the system
	 * 
	 * @author Dereje Bireda
	 */
	public List<Volunteer> getVolunteersByManagerId() {
		List<Job> managerJobs = getJobsByManagerId();
		List<Volunteer> managerVolunteers = new ArrayList<>();
		
		for(int i = 0; i < managerJobs.size(); i++) {
			
			int currentJobId = managerJobs.get(i).getMyJobId();
			
			for(int j = 0; j < myVolunteers.size(); j++) {
				//list of jobs vol volunteered
				List<Integer> currentVolJobIds = myVolunteers.get(j).getMyVolunteerJobs();
				//check if the job id belong to current manager id
				if(currentVolJobIds.contains(currentJobId)) {
					//add to volunteers under manager list
					managerVolunteers.add(myVolunteers.get(j));
					
				}
			}
		}
		
		return managerVolunteers;
	}
	
	/**
	 * Search for jobs by a manager id
	 * 
	 * @return a list of jobs by specified manager id
	 * @precondition must be called in properly initialized controller,
	 * i.e ParkManagerController, which holds current ParkManager with its id
	 *  
	 * @author Dereje Bireda
	 */
	 //get jobs by manager Id
	 public List<Job> getJobsByManagerId() {
		 List<Job> managerJobs = new ArrayList<Job>(); 
		 
		 for (int i = 0; i < myJobs.size(); i++) {
			 	Job jobChecked = myJobs.get(i);
				if(jobChecked.getMyJobManagerId()== myUser.getMyUserId()) {
						//add it to manager Jobs
						managerJobs.add(jobChecked);
					
				}
				
			}
		 
		 return managerJobs;
	 }
	
}
