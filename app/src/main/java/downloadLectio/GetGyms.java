package downloadLectio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class GetGyms {
    @SuppressWarnings("unchecked")
    public static String[] Map(String[] args) throws MalformedURLException, IOException {


        class Download extends AsyncTask<URL, Integer, String[]> {
            protected String[] doInBackground(URL... urls) {
                String url = "http://www.lectio.dk/lectio/login_list.aspx?showall=1";
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] values = new String[2500];
                int i;
                Elements links = doc.select("a");
                for (Element link : links) {
                    i++;
                    values[i] = link.text()+"="+link.attr("href").replace("/lectio/", "").replace("/default.aspx", ""));
                }
                return values;
            }
        }
        return null;
    }


    public static String GymID(){
    	return "523";
	}
}