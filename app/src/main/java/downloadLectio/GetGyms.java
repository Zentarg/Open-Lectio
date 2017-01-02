package downloadLectio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

import android.Manifest;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Toast;

import one.dichmann.lectioapp.LoginActivity;
import one.dichmann.lectioapp.R;

import static android.R.attr.data;
import static android.content.Context.MODE_PRIVATE;
import static one.dichmann.lectioapp.R.layout.activity_login;

public class GetGyms extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;
    private String compact;

    @Override
    public String doInBackground(String... Strings) {
        String url = "http://www.enelleranden.dk/lectio/logingym.html";
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