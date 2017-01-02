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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import one.dichmann.lectioapp.R;

import static android.R.attr.data;

public class Search extends AsyncTask<String, Void, String[]> {
	private String mResult;

	private TextView textView = null;

	public Search(TextView textView, String result) {
		this.textView = textView; mResult = result;
	}

	@Override
	public String[] doInBackground(String... args) {
		String[] result = new String[0];
		String read;
		String input = args[0];


		String[] length = input.split("");
		length[0] = length[0].toUpperCase();
		String[] key = mResult.split("\n");

		int q = 0;
		int entry = 0;
		int res = 4;
		result = new String[res];
		loop:
        while (entry<key.length) {
            String[] getgym = key[q].split("==");
            String[] compare = getgym[0].split("");
            int k = 0;
            if (length.length <= compare.length) {
                for (int i = 0; i < length.length; i++) {
                    if (compare[i].equals(length[i])) {
                        k++;
                        if (k == length.length) {
                            result[q] = length[entry];
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