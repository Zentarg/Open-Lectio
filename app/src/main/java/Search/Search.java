package Search;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import downloadLectio.GetGyms;
import one.dichmann.lectioapp.R;

public class Search extends AsyncTask<String, Void, String[]> {

	private final TextView textView;

	public Search(TextView textView) {
		this.textView = textView;
	}

	@Override
	public String[] doInBackground(String... args) {
		try {
			String input = args[0];
			File file = new File("gymList.txt");
			Scanner input2 = null;
			try {
				input2 = new Scanner(file);
				System.out.println(input2);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			SortedMap<String, String> gyms = (SortedMap<String, String>) file;
			if (gyms == null)
				return null;
			String[] length = input.split("");
			length[0] = length[0].toUpperCase();

			Iterator<Entry<String, String>> keys = gyms.entrySet().iterator();
			int q = 0;
			int res = 4;
			String[] result = new String[res];
			loop:
			while (keys.hasNext()) {
				Entry<String, String> entry = keys.next();
				String[] compare = entry.getKey().split("");
				int k = 0;
				if (length.length <= compare.length) {
					for (int i = 0; i < length.length; i++) {
						if (compare[i].equals(length[i])) {
							k++;
							if (k == length.length) {
								result[q] = entry.getKey();
								q++;
								if (q == res) {
									break loop;
								}
							}
						}
					}
				}
			}
			return result;
		} catch (Exception e) {
			System.out.println("Error while loading gym " + args);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void onPostExecute(String[] result) {
		if (result != null) {
			textView.setText(result[0]);
		}
	}

	@Override
	public void onPreExecute() {
	}

	@Override
	public void onProgressUpdate(Void... values) {
	}

	public static void saveGymList(String filename, Context ctx) {
		FileOutputStream file;
		try {
			String url = "http://www.enelleranden.dk/lectio/logingym.html";
			Document doc = null;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			SortedMap<String, String> values = new TreeMap<String, String>();

			Elements links = doc.select("a");
			for (Element link : links) {
				values.put(link.text(), link.attr("href").replace("/lectio/", "").replace("/default.aspx", ""));
			}

			file = ctx.openFileOutput(filename, Context.MODE_PRIVATE);

			ObjectOutputStream oos = new ObjectOutputStream(file);
			oos.writeObject(values);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}