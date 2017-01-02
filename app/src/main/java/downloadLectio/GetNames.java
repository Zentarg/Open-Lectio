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

    @Override
    public String doInBackground(String... Strings) {
        String url = "http://www.enelleranden.dk/lectio/loginnamestest.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Elements links = doc.select("a");
           for (Element link : links) {
               compact = compact+link.text() + "==" + link.attr("href").replace("/lectio/", "").replace("/default.aspx", "") + "£";
           }
        return compact;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}