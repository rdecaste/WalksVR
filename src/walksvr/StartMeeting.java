package walksvr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class StartMeeting extends TimerTask{
 // how to know there's a meeting to start?
	private static final String FILENAME = "meeting.txt";
	private BufferedReader br;
	private Timer timer;
	   public void run() {
		   try {
			   boolean testje = false;
			   System.out.println("ping");
			   br = new BufferedReader(new FileReader(FILENAME));
			   
			   List<meeting> meetings = new ArrayList<>();
			   
			   for(String line; (line = br.readLine()) != null; ) {
					 //parse the line to items

					 meetings.add(new meeting(line));
				  }
 
				Date now = new Date();
				
			   //loop through meetings to get the one that needs to be started next

				Date start = new Date(Long.MAX_VALUE);
				String test=null;
				System.out.println("ping5");
				for (meeting next : meetings) { //loop through meetings to get the one that needs to be started next
					if( start.getTime()>next.start.getTime() && (next.start.getTime()- now.getTime()>0)){
						if( (next.start.getTime()- now.getTime() <(11*60*60*1000))){ // the time for the next meeting needs to be smaller than 11hours
						start=next.start;
						test=next.meeting_id;	
						}
					}
					System.out.println("ping9");
				}
				//what needs to run now?	
				System.out.println("ping4");
			   for (meeting next : meetings) {
					   if(now.after(next.start) && now.before(next.stop))
					   {
						this.cancel();
						System.out.println("meeting start: "+now);
						System.out.println("meeting should be running now: " + next.meeting_id);
						//Runtime.getRuntime().exec("cmd /c start chrome http://google.com");
						TimeUnit.MINUTES.sleep(5);
						//Runtime.getRuntime().exec("cmd /c TASKKILL /IM chrome.exe");
						now = new Date();
						System.out.println("meeting stop: "+now);
						TimeUnit.SECONDS.sleep(5); //this more for testing purposes
						
						testje=true;
						System.out.println("ping8");

					   }
					   System.out.println("ping10");
			   }
			   
				if(test != null){
					System.out.println("ping2");
					 this.cancel();
					 this.shedule(start, test);
					 test=null;
					
				}
				else if (testje) {
					System.out.println("ping3");
					this.resume();
					testje=false;
				}
			 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   }
	   public void resume() {
		   System.out.println("ping6");
		    this.timer= new Timer();
		    StartMeeting Task2 = new StartMeeting();
		    this.timer.scheduleAtFixedRate( Task2, 5000, 10000 );
		}
	   public void shedule(Date start, String test){
		   System.out.println("ping7");
		   this.timer= new Timer();
		    StartMeeting Task2 = new StartMeeting();
		    this.timer.schedule( Task2, start );
		    System.out.println("meeting sheduled: "+test+" time: "+start);
	   }
	}

