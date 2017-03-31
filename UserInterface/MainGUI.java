package UserInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import model.ParkManager;
import model.ParkManagerController;
import model.ParksSystem;
import model.Volunteer;
import model.VolunteerController;

public class MainGUI {

	private static Scanner myReader;
	private static ParksSystem mySystem;
	private static HomeView home;
	private static ObjectInputStream myInputStream;
	private static ObjectOutputStream myOutPutStream;
	
	public static void main(String[] args) {
		myReader = new Scanner(System.in);
		
		try {
			readFile();
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		//initially launched redirected to Home screen so true
	 while(mySystem.isPageRedirected()) {	
		home = new HomeView();
		home.initHome(myReader);
		
		//mySystem.isPageRedirected();true as long as we are not logged out
		if(home.loginTrialView(mySystem)) {
		
			
			if (mySystem.getMyCurrentUser() instanceof ParkManager) {
				
				displayManagerView();
			} else if (mySystem.getMyCurrentUser() instanceof Volunteer){
				displayVolunteerView();
				
			} else {
				System.out.println("Unknown");
			}
		
			//after user view return back to home screen
		} 
	 }
	 
	try {
		myInputStream.close();
		
	} catch (IOException e) {
	
		e.printStackTrace();
	}
	 
	}

	public static void displayManagerView() {
		
		home = new ParkManagerView((ParkManagerController) mySystem.getMyUserController(), 
				  mySystem.getMyJobController(), (ParkManager)mySystem.getMyCurrentUser());
		
		//while we are not logged out
	
		while(!mySystem.getMyUserController().getIsPageRedicrected()) {
			home.initHome(myReader);
			
		}
		
		mySystem.getMyUserController().writeToFile(mySystem, myOutPutStream);
	}
	
	public static void displayVolunteerView() {
		
		home = new VolunteerView((VolunteerController)mySystem.getMyUserController(), 
				(Volunteer)mySystem.getMyCurrentUser());
		//while we are not logged out
		while(!mySystem.getMyUserController().getIsPageRedicrected()) {
			home.initHome(myReader);
			
		}
		
		mySystem.getMyUserController().writeToFile(mySystem, myOutPutStream);
		
	}
	public static void readFile() throws FileNotFoundException, IOException{
		myInputStream = new ObjectInputStream(new FileInputStream("uparksdata.bin"));
	
		try {
			mySystem = (ParksSystem)myInputStream.readObject();
		} catch (ClassNotFoundException e) {
			myInputStream.close();
			e.printStackTrace();
		}
	}
	
}
