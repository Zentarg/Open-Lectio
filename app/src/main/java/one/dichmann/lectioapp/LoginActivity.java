package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LoginActivity extends AppCompatActivity {
    public final static String SKOLE_ID = "one.dichmann.LectioApp.SKOLEID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }


    public void loginOneNext(View view) {
        Intent intent = new Intent(this, LoginTwoActivity.class);
        SearchView login_Search = (SearchView) findViewById(R.id.login_Search);
        String id = login_Search.getQuery().toString();
        intent.putExtra(SKOLE_ID, id);
        startActivity(intent);
    }



 }



