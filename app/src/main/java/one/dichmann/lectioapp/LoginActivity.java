package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public final static String SKOLE_ID = "one.dichmann.LectioApp.SKOLEID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //TODO: Check if logged in.
        //TODO: If actually logged in, check who is logged in. Thereafter make sure to go directly to the logged in activity as user.
        //TODO: If not logged in, display as normally.

    }


    public void loginOneNext(View view) {
        Intent intent = new Intent(this, LoginTwoActivity.class);
        SearchView login_Search = (SearchView) findViewById(R.id.login_Search);
        String id = login_Search.getQuery().toString();
        intent.putExtra(SKOLE_ID, id);
        startActivity(intent);
    }



 }



