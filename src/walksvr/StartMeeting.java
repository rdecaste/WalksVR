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
			   Date now = new Date();
			   Date start = new Date(Long.MAX_VALUE);
				String meeting_id=null;
				meeting next_meeting=new meeting("johnnie walker;Bruno;20000713T110000;20170713T112000;0");
			   System.out.println(now+ " start cycle task2");
			   
			   GetMeetings getmeeting = new GetMeetings();
			  // getmeeting.run();
			   
			   
			   List<meeting> meetings = new ArrayList<>();
			   meetings=getmeeting.get();
			   
			 /*  br = new BufferedReader(new FileReader(FILENAME));
			   if(br.ready()) {
				   for(String line; (line = br.readLine()) != null; ) {
						 //parse the line to items

						 meetings.add(new meeting(line));
					  }
			   }	*/		
				
			   //loop through meetings to get the one that needs to be started next
			   
			 //check if a meeting needs to run now!
				//(if the prog just starts up and the task is not sheduled yet)	
			   for (meeting next : meetings) {
				  
					   if(now.after(next.start) && now.before(next.stop) || (next.start.equals(now)))
					   {
						this.cancel();
						System.out.println("meeting starting: "+now + " meeting_id: " + next.meeting_id);
						System.out.println("meeting will be done at: "+next.stop);
						//Runtime.getRuntime().exec("cmd /c start chrome http://google.com");
						TimeUnit.MILLISECONDS.sleep((next.stop.getTime()- now.getTime()));
						//Runtime.getRuntime().exec("cmd /c TASKKILL /IM chrome.exe");
						now = new Date();
						System.out.println("meeting done: "+now);
						
						meeting_done=true;

					   }
			   }

				
				//System.out.println(now+ " start looping through meetings");
				for (meeting next : meetings) { //loop through meetings to get the one that needs to be started next
					//System.out.println("looping through meetings");
					if( start.getTime()>next.start.getTime() && (next.start.getTime()- now.getTime()>0)){
						if( (next.start.getTime()- now.getTime() <(11*60*60*1000))){ // the time for the next meeting needs to be smaller than 11hours
						start=next.start;
						meeting_id=next.meeting_id;	
						next_meeting=next;
						//System.out.println("meeting "+ next.meeting_id+" start time: "+ next.start);
						}
					}
					//System.out.println(" looping through meetings");
					//System.out.println("meeting "+ next.meeting_id+" start time: "+ next.start);
				}
				//System.out.println(now+ " done looping through meetings");
				
			   //if not: cancel the thread and schedule the next meeting.
				if(meeting_id != null){
					System.out.println(now+ " cancel thread and shedule for when next meeting starts");
					 this.cancel();
					 this.shedule(start, meeting_id);
					 meeting_id=null;
					
				}
				//
				else if (meeting_done) {
					System.out.println(now+ "re-schedule thread");
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
		    try {
				this.timer= new Timer();
				StartMeeting Task2 = new StartMeeting();
				this.timer.scheduleAtFixedRate( Task2, 5000, 20000 );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	   public void shedule(Date start, String meeting_id){
		   try {
			this.timer= new Timer();
			    StartMeeting Task2 = new StartMeeting();
			    this.timer.schedule( Task2, start );
			    //this just shedules the same process, not with any specfic meeting_id, because a meeting can always be cancelled.
			    System.out.println("meeting sheduled: "+meeting_id+" time: "+start);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	}

