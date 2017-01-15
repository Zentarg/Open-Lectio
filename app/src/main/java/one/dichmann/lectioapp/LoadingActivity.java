package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    public static String finalIntent = "one.dichmann.LectioApp.Intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Intent intent = getIntent();
        String intention = intent.getStringExtra(finalIntent);

        switch (intention) {
            case "Schedule": {

            }
        }
    }
}
