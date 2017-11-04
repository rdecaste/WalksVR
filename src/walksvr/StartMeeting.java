package walksvr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;


class StartMeeting extends TimerTask{
 // how to know there's a meeting to start?

	private Timer timer;
	   public void run() {
		   try {  
			   meeting next_meeting = null;
			   Date now = new Date();
			   System.out.println(now+ " start cycle thread: " + Thread.currentThread().getName() +" threads running: "+ Thread.activeCount());
			   GetMeetings getmeeting = new GetMeetings();	   
			   List<meeting> meetings = new ArrayList<>();
			   meetings=getmeeting.get();
			   next_meeting= now_meeting(meetings);
		
			   if(next_meeting!=null) { //there is a meeting now!
					this.cancel();
					meeting_start(next_meeting);// start the meeting
					this.resume();
			   }
			   else {
				   next_meeting= next_meeting(meetings);//get the meeting thats needs to start next (within next 11 hours)
			   
				   if(next_meeting!=null) {
					   // the meeting needs to be sheduled
					System.out.println(now+ " cancel thread and shedule for when next meeting starts");
					
					this.cancel(); 
					this.shedule(next_meeting.start);							
				   }
			   }	 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
	} 
		   
	private meeting now_meeting(List<meeting> meetings) {
		// TODO Auto-generated method stub
		Date now = new Date();
		for (meeting next : meetings) { //loop through meetings to get the one that needs to be started next  = not now but within the next 11 hours
			if( now.after(next.start) && now.before(next.stop) || (next.start.equals(now))) {//
				return next;
			}
		}
		return null;
	}
	private meeting next_meeting(List<meeting> meetings) {
		// TODO Auto-generated method stub
		 Date start = new Date(Long.MAX_VALUE);
		 Date now = new Date();
		 meeting next_meeting=new meeting("johnnie walker;Bruno;20000713T110000;20170713T112000;-1");
		for (meeting next : meetings) { //loop through meetings to get the one that needs to be started next  = not now but within the next 11 hours
			if( start.getTime()>next.start.getTime() && (next.start.getTime()- now.getTime()>0)) {// if the next start time is greater than the current and in the future
				if( (next_meeting.start.getTime()- now.getTime() <(11*60*60*1000))){// the time for the next meeting needs to be smaller than 11hours
					start=next.start;	
					next_meeting=next;
				}
			}
		}
		if(!next_meeting.meeting_id.equals("-1")){ 
			return next_meeting;
		}
		else {
			return null;
		}
	}
	private void meeting_start(final meeting next) {
		// TODO Auto-generated method stub
		Date now = new Date();
		System.out.println("meeting starting: "+now + " meeting_id: " + next.meeting_id);
		System.out.println("meeting will be done at: "+next.stop);
		try {
			Runtime.getRuntime().exec("cmd /c start chrome https://www.telepport.com/admin/player/"+next.meeting_id);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Mission_start_Anna.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			TimeUnit.MILLISECONDS.sleep(3000); //=pause the current thread
			clip.close();
			TimeUnit.MILLISECONDS.sleep((next.stop.getTime()- now.getTime()-60000));
			audioInputStream = AudioSystem.getAudioInputStream(new File("mission_endsoon_Anna.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			TimeUnit.MILLISECONDS.sleep(4000);
			clip.close();
			now = new Date();
			TimeUnit.MILLISECONDS.sleep((next.stop.getTime()- now.getTime()));
			audioInputStream = AudioSystem.getAudioInputStream(new File("mission_end_Anna.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			TimeUnit.MILLISECONDS.sleep(5000);
			clip.close();
			Runtime.getRuntime().exec("cmd /c TASKKILL /IM chrome.exe");
			now = new Date();
			System.out.println("meeting done: "+now);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void resume() {
		this.timer= new Timer();
		StartMeeting Task = new StartMeeting();
		this.timer.scheduleAtFixedRate( Task, 0, 20000 );
	}
	public void shedule(Date start){
		this.timer= new Timer();
		StartMeeting Task = new StartMeeting();
		this.timer.schedule( Task, start );//this just shedules the same process, not with any specfic meeting_id, because a meeting can always be cancelled.
		System.out.println("meeting sheduled: time: "+start);
	}
}

