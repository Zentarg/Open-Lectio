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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import one.dichmann.lectioapp.R;

import static android.R.attr.data;

public class Search {
	private String mResult;
	private int q = 0;
	private TextView textView = null;

	public void Search(TextView[] textView, String result, String args) {
		mResult = result;

		String[] key = mResult.split("£");

		loop:
		for (int i = 0; i < key.length; i++) {
			String[] list = key[i].split("==");
			Pattern noteRegex = Pattern.compile("^.?" + args + ".*?");
			Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase());
			if(noteMatcher.find()){
				textView[q].setText(list[0]);
				q++;
				if (q==4) {
					break loop;
				}
			}
		}
	}
}
