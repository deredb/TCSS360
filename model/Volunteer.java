package model;



import java.util.ArrayList;
import java.util.List;

public class Volunteer extends AbstractUser {
	
	
	private static final long serialVersionUID = 1L;
	private boolean isUserBlackBalled = false;
	 private List<Integer> myVolunteerJobs = new ArrayList<>();
	
	 public boolean getMyBlackballStatus() {
		return isUserBlackBalled;
	}
	public void setMyBlackballStatus(boolean theBlackballStatus) {
		this.isUserBlackBalled = theBlackballStatus;
	}
	public List<Integer> getMyVolunteerJobs() {
		return myVolunteerJobs;
	}
	public void setMyVolunteerJobs(List<Integer> theVolunteerJobs) {
		this.myVolunteerJobs = theVolunteerJobs;
	}


}
