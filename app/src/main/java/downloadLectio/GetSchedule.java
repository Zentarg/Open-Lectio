package downloadLectio;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import schedule.Schedule;

public class GetSchedule extends AsyncTask<String, Void, String> {
	public AsyncResponse delegate = null;
	public String gymID;
	public String nameID;
	private String compact;

	@Override
	public String doInBackground(String... Strings) {
		String url = "https://www.lectio.dk/lectio/"+gymID+"/SkemaNy.aspx?type=elev&elevid="+nameID;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Elements links = doc.select("tr").select("div").select("a");
		for (Element link : links) {
			compact = compact+"£"+link.attr("title");
		}
		return compact.replace("\n", "");
	}

	@Override
	protected void onPostExecute(String result) {
		Schedule schedule = new schedule.Schedule();
		schedule.delegate = delegate;
		schedule.execute(result);
	}
}