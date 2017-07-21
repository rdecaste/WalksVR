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
			   boolean meeting_done = false;
			   System.out.println("start cycle task2");
			   br = new BufferedReader(new FileReader(FILENAME));
			   
			   List<meeting> meetings = new ArrayList<>();
			   
			   for(String line; (line = br.readLine()) != null; ) {
					 //parse the line to items

					 meetings.add(new meeting(line));
				  }
 
				Date now = new Date();
				
			   //loop through meetings to get the one that needs to be started next

				Date start = new Date(Long.MAX_VALUE);
				String meeting_id=null;
				System.out.println("before start looping through meetings");
				for (meeting next : meetings) { //loop through meetings to get the one that needs to be started next
					if( start.getTime()>next.start.getTime() && (next.start.getTime()- now.getTime()>0)){
						if( (next.start.getTime()- now.getTime() <(11*60*60*1000))){ // the time for the next meeting needs to be smaller than 11hours
						start=next.start;
						meeting_id=next.meeting_id;	
						}
					}
					System.out.println("looping throug meetings");
				}
				
				System.out.println("done looping through meetings");
				//check if a meeting needs to run now!
				//(if the prog just starts up and the task is not sheduled yet)	
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
						
						meeting_done=true;
						System.out.println("meeting done");

					   }
					   System.out.println("meeting does not need to start now: " + next.meeting_id);
			   }
			   //if not: cancel the thread and schedule the next meeting.
				if(meeting_id != null){
					System.out.println("cancel thread and shedule run for when next meeting starts");
					 this.cancel();
					 this.shedule(start, meeting_id);
					 meeting_id=null;
					
				}
				//
				else if (meeting_done) {
					System.out.println("meeting done, re-schedule thread");
					this.resume();
					meeting_done=false;
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
		    this.timer= new Timer();
		    StartMeeting Task2 = new StartMeeting();
		    this.timer.scheduleAtFixedRate( Task2, 5000, 10000 );
		}
	   public void shedule(Date start, String meeting_id){
		   this.timer= new Timer();
		    StartMeeting Task2 = new StartMeeting();
		    this.timer.schedule( Task2, start );
		    //this just shedules the same process, not with any specfic meeting_id, because a meeting can always be cancelled.
		    System.out.println("meeting sheduled: "+meeting_id+" time: "+start);
	   }
	}

