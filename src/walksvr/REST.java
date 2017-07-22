package walksvr;

import java.util.Timer;

public class REST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {

				/*GetMeetings Task1 = new GetMeetings();
				Timer t1 = new Timer();
				t1.scheduleAtFixedRate(Task1, 0, 10000);*/
		
				StartMeeting Task2 = new StartMeeting() ;
				//Task2.run();
				Timer t2 = new Timer();
				//t2.cancel();
				t2.scheduleAtFixedRate(Task2, 0, 20000); 
				// run this call every 30 seconds until the start of the meeting
				//every 30 secs because you never know when the pc is started up
				
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
