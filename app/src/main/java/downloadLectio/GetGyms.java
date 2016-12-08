package downloadLectio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;


 public abstract class GetGyms extends AsyncTask<URL, Integer, Long> {
	public static <V> SortedMap<String, V> Map (String[]args) throws IOException {
		{
			String url = "http://www.lectio.dk/lectio/login_list.aspx?showall=1";
			Document doc = Jsoup.connect(url).get();
			SortedMap<String, V> values = new TreeMap<String, V>();

			Elements links = doc.select("a");
			for (Element link : links) {
				values.put(link.text(), (V) link.attr("href").replace("/lectio/", "").replace("/default.aspx", ""));
			}
			return values;
		}
	}

	public static String GymID() {

		return "523";
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println(GetGyms.Map(args));
	}
}