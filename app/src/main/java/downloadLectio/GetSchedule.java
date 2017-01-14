package downloadLectio;

//Jsoup is imported and the entire libary coexists in the project file
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup; //used to connect to Lectio
import org.jsoup.nodes.Document; //used to specify the download
import org.jsoup.nodes.Element; //used to specify the text and attr for our callback
import org.jsoup.select.Elements; //used to select the elements we needed. (parse)

import java.io.IOException;//used to catch errors like (connection refused by server)

import permissions.fileManagement;

public class GetSchedule extends AsyncTask<String, Void, Void> {
	private String compact = null; //compact is a compact list of all the modules the student has on the gym

	public Context context;
	public String gymID; //the GymID specifies which gym the schedule should be created from
	public String nameID; //the nameID specifies which student the schudele should be created from
	public String week; // sets the week of the year for the schedule we want
	public String year; // sets the year of the schedule

	@Override
	protected Void doInBackground(String... params) {
		String url = "https://www.lectio.dk/lectio/"+gymID+"/SkemaNy.aspx?type=elev&elevid="+nameID+"&=week&week="+week+year; //creates the URL we need to connect to in order to download the schedule.
		System.out.println(url);
		Document doc = null;
		try { //initiates a download of the Webpage
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").timeout(0).get(); //connects in whichever useragent is preferred by the device
		} catch (IOException e) { //cathes a denial exception from lectio
			e.printStackTrace(); //prints error code
			return null;
		}
		//never reaches here if the connection to lectio failed
		Elements links = doc.select("tr").select("div").select("a"); //selects all a tags under the div tags under the tr tags
		for (Element link : links) { //loops through all the results and writes them onto a String.
			//since all information is in the title we only need to read the title
			//link.attr(tirle) is the link component of the a tag <a title="this"></a>
			//this returns a long list where all the infomations for each module are seperated by a "£"
			compact = compact+"£"+link.attr("title");
		}
		String save = compact.replace("\n", "§-§").replace("null£","");//replaces all the newlines in the document with blankspaces so the parser parses it faster.
		fileManagement.createFile(context, gymID+nameID+week, save);
		//note that the "§-§" also gets used as a stop method for the regex in the parser
		return null;
	}
}