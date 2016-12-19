package downloadLectio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;

import static android.R.attr.data;

public class GetGyms extends AsyncTask<String, Void, SortedMap<String, String>> {
    @Override
    public SortedMap<String, String> doInBackground(String... Strings) {
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

    @Override
    public void onPostExecute(SortedMap<String, String> result) {
        try{
            File file = new File("temp");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(result);
            s.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
