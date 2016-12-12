package Search;

import android.content.SyncStatusObserver;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
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
			SortedMap<String, String> gyms = GetGyms.Map(args[0]);
			if (gyms == null)
				return null;
			String[] length = input.split("");
			length[0] = length[0].toUpperCase();

			Iterator<Entry<String, String>> keys = gyms.entrySet().iterator();
			int q = 0;
			int res = 4;
			String[] hello = new String[res];
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
								hello[q] = entry.getKey();
								q++;
								if (q == res) {
									break loop;
								}
							}
						}
					}
				}
			}
			/*
			while (q<res) {
				hello[q] = null;
				q++;
				if (q==res) {
					return hello;
				}
			}*/
			return hello;
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
	public void onPreExecute() { }

	@Override
	public void onProgressUpdate(Void... values) { }
}