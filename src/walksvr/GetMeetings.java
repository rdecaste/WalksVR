package walksvr;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


class GetMeetings { // extends TimerTask{

	private BufferedWriter bw;
	public void MeetingsFile() {
		try {
			  // System.out.println(now +" getmeetings");
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
				
				 for(String line; (line = br.readLine()) != null; ) {
					 bw.write(line);
					 bw.newLine();
				  }
				 if (bw != null)
						bw.close();//test comment

				if (fw != null)
						fw.close();
				 conn.disconnect();

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  } 
		   
	   }
	public List<meeting> get() throws IOException {
		// TODO Auto-generated method stub
		//this just gets the file from the server and reads it in (does not send to another file
		this.MeetingsFile();
		List<meeting> meetings = new ArrayList<>();
		 BufferedReader br;
		 URL url = new URL("http://walksvr.com/test/test.php?filename=test.txt");
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			 br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
		try {
				   for(String line; (line = br.readLine()) != null; ) {
						 //parse the line to items

						 meetings.add(new meeting(line));
					  }
			   
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		return meetings;
	}

	}