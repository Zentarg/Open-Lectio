package downloadLectio;

//Jsoup is imported and the entire libary coexists in the project file
import org.jsoup.Jsoup; //used to connect to Lectio
import org.jsoup.nodes.Document; //used to specify the download
import org.jsoup.nodes.Element; //used to specify the text and attr for our callback
import org.jsoup.select.Elements; //used to select the elements we needed. (parse)

import java.io.IOException;//used to catch errors like (connection refused by server)

public class GetSchedule {
	private String compact; //compact is a compact list of all the modules the student has on the gym
	public String gymID; //the GymID specifies which gym the schedule should be created from
	public String nameID; //the nameID specifies which student the schudele should be created from

	public String getSchedule() { //runs in an asynctask to minimize cpu usage on the UI Thread (main)
		String url = "https://www.lectio.dk/lectio/"+gymID+"/SkemaNy.aspx?type=elev&elevid="+nameID; //creates the URL we need to connect to in order to download the schedule.
		Document doc = null;
		try { //initiates a download of the Webpage
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get(); //connects in whichever useragent is preferred by the device
		} catch (IOException e) { //cathes a denial exception from lectio
			e.printStackTrace(); //prints error code
			return null; //returns null to be caught by the search fundtion and get relaunced.
		}
		//never reaches here if the connection to lectio failed
		Elements links = doc.select("tr").select("div").select("a"); //selects all a tags under the div tags under the tr tags
		for (Element link : links) { //loops through all the results and writes them onto a String.
			//since all information is in the title we only need to read the title
			//link.attr(tirle) is the link component of the a tag <a title="this"></a>
			//this returns a long list where all the infomations for each module are seperated by a "£"
			compact = compact+"£"+link.attr("title");
		}
		return compact.replace("\n", "§-§").replace("null£","");//replaces all the newlines in the document with blankspaces so the parser parses it faster.
	}
}