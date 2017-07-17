package walksvr;

import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


class GetMeetings extends TimerTask{

	   private BufferedWriter bw;

	public GetMeetings(){
	     //this tasks reads the GET call from walks VR
		//will run every 5min
		// also fills up the next.meeting.txt with the upcoming meeting details
		   // need to loop through the loaded up meetings and get the relevant (walker.1)
		   //
	   }

	   public void run() {
		   try {
			   System.out.println("getmeetings");
				URL url = new URL("http://walksvr.com/test/test.php?filename=test.txt");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
				
				//BufferedWriter bw = null;
				FileWriter fw = new FileWriter("meeting.txt");
				bw = new BufferedWriter(fw);
				
				//read the lines in and make them meetings
				//List<meeting> meetings = new ArrayList<>();
				 for(String line; (line = br.readLine()) != null; ) {
					 //parse the line to items
					 bw.write(line);
					 bw.newLine();
					 //System.out.println(line);
					 //meetings.add(new meeting(line));
				  }
				 if (bw != null)
						bw.close();

				if (fw != null)
						fw.close();
				 conn.disconnect();
				 
				
				 
				 /* System.out.println("Before ordering");
				for (meeting start : meetings) {
					System.out.println(start.start);
				}*/
				
				//Collections.sort(meetings, new CustomComparator()); //order the meetings
				
			/*	System.out.println("After ordering");
				for (meeting start : meetings) {
					System.out.println("meeting Id:"+start.meeting_id+ " start: "+start.start);
				}*/
				
				//write the upcoming meeting to the file
				
				// get meeting closest to now
				

			/*	Calendar now = Calendar.getInstance();
				now.setTime(new Date());   
				Calendar start = Calendar.getInstance();
				start.setTime(meetings.get(0).start); */
			   
			 /*  if(now.after(next.start) && now.before(start.stop))
			   {
				   
				System.out.println("meeting start: "+now.getTime());
				System.out.println("meeting should be running now");
			   }*/
				    
				//System.out.println("upcoming meeting:"+meetings.get(0).meeting_id+ " start: "+meetings.get(0).start);
				//meetings.get(0).Writetofile();

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  } 
		   
	   }

	}