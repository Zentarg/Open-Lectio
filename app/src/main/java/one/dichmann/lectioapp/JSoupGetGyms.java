package one.dichmann.lectioapp;

import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class JSoupGetGyms {
    public static Map<String, String> Map(String[] args) throws MalformedURLException, IOException {
        String url = "http://www.lectio.dk/lectio/login_list.aspx?showall=1";
        Document doc = Jsoup.connect(url).get();
        Map<String, String> values = new HashMap<String, String>();
        String text = doc.select("div").first().text();
        System.out.println(text);

        Elements links = doc.select("a");
        for (Element link : links) {
            values.put(link.attr("href").replace("/lectio/", "").replace("/default.aspx", ""), link.text());
        }

        return values;
    }
}
