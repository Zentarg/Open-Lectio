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
    public static <V> SortedMap<String, V> Map(String[] args) throws MalformedURLException, IOException {


        class Download extends AsyncTask<Object, Object, SortedMap<String, V>> {
            protected SortedMap<String, V> doInBackground(Object... urls) {
                String url = "http://www.lectio.dk/lectio/login_list.aspx?showall=1";
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SortedMap<String, V> values = new TreeMap<String, V>();

                Elements links = doc.select("a");
                for (Element link : links) {
                    values.put(link.text(), (V) link.attr("href").replace("/lectio/", "").replace("/default.aspx", ""));
                }
                return values;
            }
        }
        return null;
    }


    public static GymID(){
    	return "523"    
	}
}