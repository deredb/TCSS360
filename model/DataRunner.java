package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * test runner
 * @author Dereje Bireda
 */
public class DataRunner {

	
	private static List<Job> myJobs;
	private static List<Volunteer> myVolunteers;
	private static List<ParkManager> myParkManagers;
	private static LocalDate myCurrentDate;

	public static void main(String[] args) {
		 myJobs = new ArrayList<Job>();
		 myVolunteers = new ArrayList<Volunteer>();
		    myParkManagers = new ArrayList<ParkManager>();
		    myCurrentDate = LocalDate.now();
		  
		
		ParksSystem uParks = new ParksSystem();
	
		

		//parks total 4
		Park p0 = new Park();p0.setMyCity("Seattle");p0.setMyManagerId(0);p0.setMyName("Alki Beach and Park");p0.setMyParkId(0);
		Park p1 = new Park();p1.setMyCity("Renton");p1.setMyManagerId(2);p1.setMyName("Gene Coulon memorial Beach Park");p1.setMyParkId(1);
		Park p2 = new Park();p2.setMyCity("Seatac");p2.setMyManagerId(1);p2.setMyName("Angel lake Park");p2.setMyParkId(2);
		Park p3 = new Park();p3.setMyCity("Federal Way");p3.setMyManagerId(3);p3.setMyName("DashPoint");p3.setMyParkId(3);
		
		
		//park managers--total 3
		ParkManager pm0 = new ParkManager();
		List<Park> pm0Parks = new ArrayList<>();
		pm0Parks.add(p0);
		pm0.setMyAddress("1702 Alki Ave");pm0.setMyEmail("jerry0@alki.com");pm0.setMyName("Jerry D. Dumas");pm0.setMyPhone("111-222-3333");pm0.setMyType(UserType.Manager);pm0.setMyUserId(0);pm0.setMyUserName("mgr0");pm0.setMyParks(pm0Parks);
		
		ParkManager pm1 = new ParkManager();
		List<Park> pm1Parks = new ArrayList<>();
		pm1Parks.add(p2);
        pm1.setMyAddress("63321 S West James St.");pm1.setMyEmail("charles1@angel.com");pm1.setMyName("Charles J. Riley");pm1.setMyPhone("111-222-4444");pm1.setMyType(UserType.Manager);pm1.setMyUserId(1);pm1.setMyUserName("mgr1");pm1.setMyParks(pm1Parks);
        
		ParkManager pm2 = new ParkManager();
		List<Park> pm2Parks = new ArrayList<>();
		pm2Parks.add(p1);pm2Parks.add(p3);
		pm2.setMyAddress("19408 Int Blvd");pm2.setMyEmail("jessica2@coulon.com");pm2.setMyName("Jessica S. Hill");pm2.setMyPhone("111-222-5555");pm2.setMyType(UserType.Manager);pm2.setMyUserId(2);pm2.setMyUserName("mgr2");pm2.setMyParks(pm2Parks);
		
		ParkManager pm3 = new ParkManager();
		List<Park> pm3Parks = new ArrayList<>();
		pm3Parks.add(p3);
		pm3.setMyAddress("320th St. Pacific Hwy");pm3.setMyEmail("mark@dash.com");pm3.setMyName("Mark L. Thom");pm3.setMyPhone("425-345-0045");pm3.setMyType(UserType.Manager);pm3.setMyUserId(3);pm3.setMyUserName("mgr3");pm3.setMyParks(pm3Parks);
		/*
		 * add managers to the arraylist
		 */
		myParkManagers.add(pm0);myParkManagers.add(pm1);myParkManagers.add(pm2);myParkManagers.add(pm3);
		
		uParks.setMyParkManagers(myParkManagers);
		
		//Jobs
		//job with 9 vol, still available for sign up 
		Job j0 = new Job(p0);
		List<Integer> j0Vol = new ArrayList<>();
		j0Vol.add(1);j0Vol.add(2);j0Vol.add(3);j0Vol.add(4);j0Vol.add(5);j0Vol.add(6);j0Vol.add(7);j0Vol.add(8);j0Vol.add(9);
		j0.setMyCreationDate(myCurrentDate.minusDays(10));
		j0.setMyCurrentTotalVolunteers(9);
		j0.setMyDescription("Free Lunch Program for kids-need guides and energetic volunteers who plays with the kids");
		j0.setMyStartDate(myCurrentDate.plusDays(3));
		j0.setMyEndDate(myCurrentDate.plusDays(5));
		j0.setMyLightVolunteerNumber(4);
		j0.setMyMediumVolunteerNumber(5);
		j0.setMyHeavyVolunteerNumber(1);
		j0.setMyJobId(0);
		j0.setMyJobIsPast(false);
		j0.setMyJobIsPending(true);
		j0.setMyJobManagerId(0);
		j0.setMyPark(p0);
		j0.setMyVolunteerList(j0Vol);
		j0.setMyTime(JobController.convertStringToTime("09:35"));
		
		//1 5 volunteers job
		Job j1 = new Job(p0);
		List<Integer> j1Vol = new ArrayList<>();
		j1Vol.add(4);j1Vol.add(5);
		j1.setMyCreationDate(myCurrentDate.minusDays(10));
		j1.setMyCurrentTotalVolunteers(2);
		j1.setMyDescription("volunteer reception and registration");
		j1.setMyStartDate(myCurrentDate.plusDays(3));
		j1.setMyEndDate(myCurrentDate.plusDays(5));
		j1.setMyLightVolunteerNumber(3);
		j1.setMyMediumVolunteerNumber(0);
		j1.setMyHeavyVolunteerNumber(0);
		j1.setMyJobId(1);
		j1.setMyJobIsPast(false);
		j1.setMyJobIsPending(true);
		j1.setMyJobManagerId(0);
		j1.setMyPark(p0);
		j1.setMyVolunteerList(j1Vol);
		j1.setMyTime(JobController.convertStringToTime("08:00"));
		
		//job with 10 volunteers, past
		Job j2 = new Job(p0);
		List<Integer> j2Vo1 = new ArrayList<>();
	
		for (int i = 1 ; i <= 10; i++) {
			j2Vo1.add(i);
		}
		j2.setMyCreationDate(myCurrentDate.minusDays(3));
		j2.setMyCurrentTotalVolunteers(10);
		j2.setMyDescription("Coastal cleanup Day-beaches, lakes and rivers");
		j2.setMyStartDate(myCurrentDate.minusDays(3));
		j2.setMyEndDate(myCurrentDate.minusDays(5));
		j2.setMyLightVolunteerNumber(0);
		j2.setMyMediumVolunteerNumber(10);
		j2.setMyHeavyVolunteerNumber(0);
		j2.setMyJobId(2);
		j2.setMyJobIsPast(true);
		j2.setMyJobIsPending(false);
		j2.setMyJobManagerId(0);
		j2.setMyPark(p0);
		j2.setMyVolunteerList(j2Vo1);
		j2.setMyTime(JobController.convertStringToTime("09:35"));
		
		//job 3 
		Job j3 = new Job(p3);
		List<Integer> j3Vo2 = new ArrayList<>();
		j3Vo2.add(1);j3Vo2.add(2);j3Vo2.add(3);j3Vo2.add(4);
		
		j3.setMyCreationDate(myCurrentDate.minusDays(3));
		j3.setMyCurrentTotalVolunteers(4);
		j3.setMyDescription("recycling, tree trimming, lawn mowing, trail maintenance");
		j3.setMyStartDate(myCurrentDate.plusDays(3));
		j3.setMyEndDate(myCurrentDate.plusDays(5));
		j3.setMyLightVolunteerNumber(0);
		j3.setMyMediumVolunteerNumber(10);
		j3.setMyHeavyVolunteerNumber(0);
		j3.setMyJobId(3);
		j3.setMyJobIsPast(false);
		j3.setMyJobIsPending(true);
		j3.setMyJobManagerId(2);
		j3.setMyPark(p1);
		j3.setMyVolunteerList(j3Vo2);
		j3.setMyTime(JobController.convertStringToTime("09:00"));
		
		
		/*
		 * add jobs to main arraylist total 4
		 */
		myJobs.add(j0);myJobs.add(j1); myJobs.add(j2);myJobs.add(j3);
		
		//volunteer list with their jobs
		uParks.setMyJobs(myJobs);
		//loop and add jobs
		for (int i =0; i < 4; i++) {
			int id = myJobs.size()-1;
			id+=1;
			
			Job j = new Job(p3);
			List<Integer> jVo2 = new ArrayList<>();
			
			j.setMyCreationDate(myCurrentDate.minusDays(3));
			j.setMyCurrentTotalVolunteers(0);
			j.setMyDescription("tree trimming, and mulching of flowerbeds");
			j.setMyStartDate(myCurrentDate.plusDays(10+i));
			j.setMyEndDate(myCurrentDate.plusDays(11+i));
			j.setMyLightVolunteerNumber(0);
			j.setMyMediumVolunteerNumber(10);
			j.setMyHeavyVolunteerNumber(0);
			j.setMyJobId(id);
			j.setMyJobIsPast(false);
			j.setMyJobIsPending(true);
			j.setMyJobManagerId(i);
			Park park;
			if(i == 0) {
				park = p0;
				j.setMyDescription("Litter removal");
			} else if (i == 1) {
				park = p1;
				j.setMyDescription("wear animal costume and play with kids age 3-8");
			} else if(i == 2){
				park = p2;
				j.setMyDescription("watering plants");
			} else {//(i == 3)
				
				park = p3;
				j.setMyDescription("green waste removal and planting");
			}
				
			j.setMyPark(park);
			j.setMyVolunteerList(jVo2);
			j.setMyTime(JobController.convertStringToTime("10:00"));
			myJobs.add(j);
		}
		for (int i =0; i < 4; i++) {
			int id = myJobs.size()-1;
			id+=1;
		 
			Job j = new Job(p3);
			List<Integer> jVo2 = new ArrayList<>();
			
			j.setMyCreationDate(myCurrentDate.minusDays(3));
			j.setMyCurrentTotalVolunteers(0);
		
			j.setMyStartDate(myCurrentDate.plusDays(10+i));
			j.setMyEndDate(myCurrentDate.plusDays(11+i));
			j.setMyLightVolunteerNumber(0);
			j.setMyMediumVolunteerNumber(10);
			j.setMyHeavyVolunteerNumber(0);
			j.setMyJobId(id);
			j.setMyJobIsPast(false);
			j.setMyJobIsPending(true);
			j.setMyJobManagerId(i);
			Park park;
			if(i == 0) {
				park = p0;
				j.setMyDescription("Greeting park visitors");
			} else if (i == 1) {
				park = p1;
				j.setMyDescription("General cleanup of park grounds");
			} else if(i == 2){
				park = p2;
				j.setMyDescription("weed management");
			} else {//(i == 3)
				park = p3;
				j.setMyDescription("painting trails,picnic tables");
			}
				
			j.setMyPark(park);
			j.setMyVolunteerList(jVo2);
			j.setMyTime(JobController.convertStringToTime("09:00"));
			myJobs.add(j);
		}
		
		for (int i =0; i < 4; i++) {
			int id = myJobs.size()-1;
			id+=1;
		
			Job j = new Job(p3);
			List<Integer> jVo2 = new ArrayList<>();
			
			j.setMyCreationDate(myCurrentDate.minusDays(3));
			j.setMyCurrentTotalVolunteers(0);
			j.setMyDescription("tree trimming, and mulching of flowerbeds");
			j.setMyStartDate(myCurrentDate.plusDays(10+i));
			j.setMyEndDate(myCurrentDate.plusDays(11+i));
			j.setMyLightVolunteerNumber(0);
			j.setMyMediumVolunteerNumber(10);
			j.setMyHeavyVolunteerNumber(0);
			j.setMyJobId(id);
			j.setMyJobIsPast(false);
			j.setMyJobIsPending(true);
			j.setMyJobManagerId(i);
			Park park;
			if(i == 0) {
				park = p0;
				j.setMyDescription("Litter removal");
			} else if (i == 1) {
				park = p1;
				j.setMyDescription("wear animal costume and play with kids age 3-8");
			} else if(i == 2){
				park = p2;
				j.setMyDescription("watering plants");
			} else {//(i == 3)
				
				park = p3;
				j.setMyDescription("green waste removal and planting");
			}
				
			j.setMyPark(park);
			j.setMyVolunteerList(jVo2);
			j.setMyTime(JobController.convertStringToTime("10:00"));
			myJobs.add(j);
		}
		
		for (int i =0; i < 2; i++) {
			int id = myJobs.size()-1;
			id+=1;
		
			Job j = new Job(p3);//park changes below
			List<Integer> jVo2 = new ArrayList<>();
			
			j.setMyCreationDate(myCurrentDate.minusDays(3));
			j.setMyCurrentTotalVolunteers(0);
			j.setMyDescription("tree trimming, and mulching of flowerbeds");
			j.setMyStartDate(myCurrentDate.plusDays(15+i));
			j.setMyEndDate(myCurrentDate.plusDays(16+i));
			j.setMyLightVolunteerNumber(5);
			j.setMyMediumVolunteerNumber(0);
			j.setMyHeavyVolunteerNumber(0);
			j.setMyJobId(id);
			j.setMyJobIsPast(false);
			j.setMyJobIsPending(true);
			j.setMyJobManagerId(i);
			Park park;
			if(i == 0) {
				park = p0;
				j.setMyDescription("soil replenishment, ");
			} else if (i == 1) {
				park = p1;
				j.setMyDescription("Greeting park visitors");
			} else if(i == 2){
				park = p2;
				j.setMyDescription("watering plants, and pruning");
			} else {//(i == 3)
				
				park = p3;
				j.setMyDescription("line Control");
			}
				
			j.setMyPark(park);
			j.setMyVolunteerList(jVo2);
			j.setMyTime(JobController.convertStringToTime("10:00"));
			myJobs.add(j);
		}
		
		
		
		//job too late for sign up
		Job j = new Job(p0);
		List<Integer> jVo2 = new ArrayList<>();
	
		j.setMyCreationDate(myCurrentDate.minusDays(3));
		j.setMyCurrentTotalVolunteers(0);
		j.setMyDescription("weed management,soil replenishment");
		j.setMyStartDate(myCurrentDate.plusDays(1));
		j.setMyEndDate(myCurrentDate.plusDays(2));
		j.setMyLightVolunteerNumber(0);
		j.setMyMediumVolunteerNumber(10);
		j.setMyHeavyVolunteerNumber(0);
	
		j.setMyJobId(myJobs.size());
		j.setMyJobIsPast(false);
		j.setMyJobIsPending(true);
		j.setMyJobManagerId(0);
		j.setMyPark(p0);
		j.setMyVolunteerList(jVo2);
		j.setMyTime(JobController.convertStringToTime("09:00"));
		myJobs.add(j);
		
		
		String[] names = { "Carolyn Griffin","Elizabeth Banks","Helen Montgomery","Sarah Richards","Nancy Simmons","Jeremy Kennedy","Robin Howard"
		,"Lisa Cunningham","Harold Ruiz","Carl Thompson","Terry Rose","Kathryn Carter","Judith Jacobs","Willie Arnold","Henry Fuller","Antonio Ramirez"
		,"Craig Berry","Paul Matthews","Craig Carter","Bobby Baker","Antonio Watkins","Kevin Diaz","Linda Coleman","Ryan Taylor","Howard Freeman"
		,"Anne Ryan","Denise Rice","Jessica Matthews","Kathryn Palmer","Eric Fuller","Brenda Powell","Rebecca Green" ,"Aaron Jones","Eugene Smith"
		,"Roger Moreno","Jeremy Lewis","Daniel Mitchell","Nancy Thomas","Marilyn Kelly","Eugene Barnes","Harry Hughes","Julia Stanley","Betty Knight"};
		String[] phones = { "(170)190-5987","(695)598-1884","(131)176-9262","(419)343-9256","(405)553-6796","(514)195-7857","(910)994-2090"
				,"(848)872-8315","(698)670-2755","(669)280-3583","(601)499-1095","(484)344-3022","(813)348-8938","(332)664-1805","(402)816-9670","(217)715-2262"
				,"(744)706-3920","(576)546-4327","(460)307-9565","(861)130-1716r","(494)151-5053","(477)802-1911","(300)699-7929","(850)302-0062","(217)807-6351"
				,"(753)491-1423","(976)184-5748","(937)208-9623","(445)742-5684","(599)314-5679","(349)464-4234","(247)232-6911" ,"(456)983-7452","(416)172-8223"
				,"(843)200-0656","(487)536-1730","(102)995-5358","(860)664-6593","(853)255-2632","(893)749-1911","(561)631-8682","(155)414-7867","(670)814-2789"};
		
		String[] address = { "1251 Melby Avenue","37 Sauthoff Alley","39175 Fremont Circle","40 Fairfield Crossing","772 Golf View Park","590 Knutson Place","947 West Crossing"
				,"2946 Clarendon Hill","35 Superior Junction","70 Garrison Crossing","035 Northland Place","73315 Emmet Center","4921 Stone Corner Avenue","6 Declaration Road","43033 Mcguire Plaza","272 Hauk Place"
				,"87 Anderson Place","964 Bultman Street","3 Burrows Lane","6 Huxley Street","1648 Stephen Place","62 Anderson Parkway","6270 Menomonie Park","2719 Onsgard Circle","8 Springview Hill"
				,"2 Prairie Rose Terrace","63 Almo Parkway","37054 Sherman Hill","68 Northfield Road","4619 Declaration Center","846 Artisan Road","5 Lien Park" ,"09927 Corry Pass","0708 Grover Terrace"
				,"37520 Mcbride Point","52246 Elka Circle","86595 Ruskin Junction","39016 Reindahl Plaza","679 Hauk Way","47 Pankratz Lane","876 Prairieview Court","16592 Mariners Cove Trail","55 Lyons Alley"};
		String[] email = { "cgriffin6@marketwatch.com","ebanks7@angelfire.com","hmontgomery8@t.co","srichards9@ow.ly","nsimmonsa@narod.ru","jkennedyb@psu.edu","rhowardc@msn.com"
				,"lcunninghamd@independent.co.uk","hruize@prweb.com","cthompsonf@cbc.ca","troseg@alibaba.com","kcarterh@scribd.com","jjacobsi@bbb.org","warnoldj@lycos.com","hfullerk@hao123.com","aramirezl@usgs.gov"
				,"cberrym@vistaprint.com","pmatthewsn@mit.edu","ccartero@google.com.br","bbakerp@ucoz.com","awatkinsq@yellowpages.com","kdiazr@tripod.com","lcolemans@bbc.co.uk","rtaylort@goo.ne.jp","hfreemanu@reverbnation.com1"
				,"aryanv@discovery.com","dricew@mozilla.com","jmatthewsx@creativecommons.org","kpalmery@oracle.com","efullerz@behance.net","bpowell10@reference.com","rgreen11@answers.com" ,"ajones12@princeton.edu","esmith13@shinystat.com"
				,"rmoreno14@nbcnews.com","jlewis15@skype.com","dmitchell16@deliciousdays.com","nthomas17@cdbaby.com","mkelly18@canalblog.com","ebarnes19@netlog.com","hhughes1a@bandcamp.com","jstanley1b@printfriendly.com","bknight1c@google.de"};
		
		//blackballed Volunteer with no job
		Volunteer vol0 = new Volunteer();
		List<Integer>vol0Jobs = new ArrayList<>();
		vol0.setMyAddress("1251 Melby Avenue");
		vol0.setMyBlackballStatus(true);
		vol0.setMyEmail("cgriffin6@marketwatch.com");
		vol0.setMyName("Carolyn Griffin");
		vol0.setMyPhone("(170)190-5987");
		vol0.setMyType(UserType.Volunteer);
		vol0.setMyUserId(0);
		vol0.setMyUserName("vol0");
		vol0.setMyVolunteerJobs(vol0Jobs);//no job for blackballed volunteer
		myVolunteers.add(vol0);
		
		
		//creating volunteers with different attributes
		//11-29 has no job 
		for(int i = 1; i <= 29;i++) {
			Volunteer vol = new Volunteer();
			List<Integer>volJobs = new ArrayList<>();
	
		
			if(i >=1 && i <=9) {
				volJobs.add(0);//1 to 9 has job 0
				volJobs.add(2);// 1- 9 has job 2 too
			}
			if(i == 4 || i == 5) {
				volJobs.add(1);//4 and 5 has job 1
			}
			if(i == 10) {
				volJobs.add(2);//10 has job 2
			}
			
			if(i >=1 && i <=4) {
				volJobs.add(3);//1 to 4 has job 3
			}
			
			vol.setMyAddress(address[i]);
			vol.setMyBlackballStatus(false);
			vol.setMyEmail(email[i]);
			vol.setMyName(names[i]);
			vol.setMyPhone(phones[i]);
			vol.setMyType(UserType.Volunteer);
			vol.setMyUserId(i);//vol id 0 is added above who is blackballed
			String username = "vol"+i;
			vol.setMyUserName(username);
			vol.setMyVolunteerJobs(volJobs);
			
			/*
			 * add it to vol arraylist****/
			myVolunteers.add(vol);//volunteers added here,
		}
		
	
		
		uParks.setMyVolunteers(myVolunteers);
		/**************************
		 * 
		 * 
		 *************************/
		try {
		
			 writeToFile(uParks);
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
//	
	}
	
	public static void writeToFile(ParksSystem theSystem) throws FileNotFoundException, IOException {
		ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream("uparksdata.bin"));
		outs.writeObject(theSystem);
	}
	
	public static void readFile() throws FileNotFoundException, IOException{
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("uparksdata.bin"));
		try {
			ParksSystem parksSystem = (ParksSystem)input.readObject();
		} catch (ClassNotFoundException e) {
			input.close();
			e.printStackTrace();
		}
	}
	
	
}
