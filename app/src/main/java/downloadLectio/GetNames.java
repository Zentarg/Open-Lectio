package downloadLectio;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetNames extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;
    private String compact;
    public String GymID;

    @Override
    public String doInBackground(String... Strings) {
        for(char alphabet = 'A'; alphabet <= 'Z';alphabet++) {
            String url = "http://www.lectio.dk/lectio/" + GymID + "/FindSkema.aspx?type=elev&forbogstav=" + alphabet;
            Document doc = null;
            try {
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            Elements links = doc.select("li").select("a");
            for (Element link : links) {
                compact = compact + link.text() + "==" + link.attr("href").replace("/lectio/" + GymID + "/SkemaNy.aspx?type=elev&elevid=", "") + "£";
            }
        }
        System.out.println(compact);
        return compact;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}