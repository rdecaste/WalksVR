package walksvr;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class meeting {
	
	public String walker;
	public String viewer;
    public Date  start;
    public Date  stop ;
    public String line1;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public String data;
	public String meeting_id;
	
	public meeting(String line) { //this is the setter
		// TODO Auto-generated constructor stub
		line1=line;
		 List<String> items = Arrays.asList(line.split(";"));
		  //read the items
		 for(int i = 0; i < items.size(); i++) {
			 switch (i){
			 case 0: 
				 walker=items.get(i).toString();
	           // System.out.println("walker: "+walker);
	            break;
			 case 1: 
				 viewer=items.get(i).toString();
		            //System.out.println("viewer: "+viewer);
		            break;
			 case 2: 
				data = items.get(i).toString();
				data=data.replace("T", "");
				data= data.substring(0, 4)+"/"+data.substring(4, 6)+"/"+data.substring(6, 8)+" "+data.substring(8, 10)+":"+data.substring(10, 12)+":"+data.substring(12, 14);
				try {
					start = dateFormat.parse(data);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 //start=items.get(i).toString();*/
		          //  System.out.println("start: "+start.toString());
		            break;
			 case 3: 
					data = items.get(i).toString();
					data=data.replace("T", "");
					data= data.substring(0, 4)+"/"+data.substring(4, 6)+"/"+data.substring(6, 8)+" "+data.substring(8, 10)+":"+data.substring(10, 12)+":"+data.substring(12, 14);
					try {
						stop = dateFormat.parse(data);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 //start=items.get(i).toString();*/
			           // System.out.println("stop: "+stop.toString());
			            break;
			 case 4: 
				// meeting_id ="https://www.telepport.com/admin/player/"+items.get(i).toString();
				 meeting_id= items.get(i).toString();
		            //System.out.println("meeting_id: "+meeting_id);
		            break;
            default: break;
			 }
		 }
	}

}
