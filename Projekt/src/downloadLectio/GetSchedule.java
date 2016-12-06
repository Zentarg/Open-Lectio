package downloadLectio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetSchedule {
	public static List<String> schedule(int module) throws MalformedURLException, IOException {
			String[] ID = (GetID.ID()).split("-");
			String url = "http://www.lectio.dk/lectio/"+ID[0]+"/SkemaNy.aspx?type=elev&elevid="+ID[1];
	        Document doc = Jsoup.connect(url).get();	        
	        
	        List<String> values = new ArrayList<String>();
	        Elements links = doc.select("a");
	        for (Element link : links) {
	        	String scheme = link.attr("title");
	        	if (scheme.contains("\n") || scheme.contains("\r")) 
	        		scheme = scheme.replaceAll("\n", " ");
	        		scheme = scheme.replaceAll("\r", "");
	        	if (!scheme.isEmpty() && scheme.contains("Hold:"))
	        		values.add(scheme);
	        }
			return values;
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		List<String> values = schedule(0);
		System.out.println(values);
	}
}