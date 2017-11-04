package walksvr;

import java.util.Timer;


public class REST {

	public static void main(String[] args) {
		try {		  
				StartMeeting Task = new StartMeeting() ;
				Timer t = new Timer();
				t.scheduleAtFixedRate(Task, 0, 20000); 
				// run this call every 30 seconds until the start of the meeting
				//every 20 secs because you never know when the pc is started up
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
