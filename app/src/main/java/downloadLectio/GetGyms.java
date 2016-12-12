package downloadLectio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class GetGyms {
    @SuppressWarnings("unchecked")
    public static SortedMap<String, String> Map(String args) throws MalformedURLException, IOException {
        String url = "http://www.enelleranden.dk/lectio/logingym.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        SortedMap<String, String> values = new TreeMap<String, String>();

        Elements links = doc.select("a");
        for (Element link : links) {
            values.put(link.text(), link.attr("href").replace("/lectio/", "").replace("/default.aspx", ""));
        }
        System.out.println(values);
        return values;
    }


    public static String GymID(){
    	return "523";
	}
}