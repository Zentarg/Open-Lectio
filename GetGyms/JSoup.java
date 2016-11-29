import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class JSoup {
	public static void main(String[] args) throws MalformedURLException, IOException {
	        String url = "http://www.lectio.dk/lectio/login_list.aspx?showall=1";
	        Document doc = Jsoup.connect(url).get();

	        String text = doc.select("div").first().text();
	        System.out.println(text);

	        Elements links = doc.select("a");
	        for (Element link : links) {
	            System.out.println(link.attr("href"));
	        }
	    }
	}
