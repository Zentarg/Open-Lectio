package Search;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import junit.framework.Assert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import downloadLectio.GetGyms;
import one.dichmann.lectioapp.R;

import static android.R.attr.data;

public class Search extends AsyncTask<String, Void, String[]> {

	private final TextView textView;

	public Search(TextView textView) {
		this.textView = textView;
	}

	@Override
	public String[] doInBackground(String... args) {
		String[] result = new String[0];
		try {
			String input = args[0];
			File root = new File(Environment.getExternalStorageDirectory(), "temp.txt");
			if (!root.exists()) {
				root.mkdirs();
			}
			System.out.println(Environment.getExternalStorageState());
			System.out.println("trying to get file!");
			FileInputStream f = new FileInputStream(root);
			ObjectInputStream s = new ObjectInputStream(f);
			SortedMap<String, String> fileObj2 = (SortedMap<String, String>) s.readObject();
			s.close();

			System.out.println("file loaded");
			Assert.assertEquals(fileObj2.hashCode(), fileObj2.hashCode());
			Assert.assertEquals(fileObj2.toString(), fileObj2.toString());
			Assert.assertTrue(fileObj2.equals(fileObj2));

			System.out.println("Map constructed");

			SortedMap<String, String> gyms = fileObj2;
			if (gyms == null)
				return null;

			String[] length = input.split("");
			length[0] = length[0].toUpperCase();

			Iterator<Entry<String, String>> keys = gyms.entrySet().iterator();
			int q = 0;
			int res = 4;
			result = new String[res];
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

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error while loading gym " + args);
			e.printStackTrace();
		}
		return result;
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
}