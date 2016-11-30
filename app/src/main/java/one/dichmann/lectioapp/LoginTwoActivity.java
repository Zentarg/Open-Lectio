package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoginTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_two);

        Intent intent = getIntent();
        String id = intent.getStringExtra(LoginActivity.SKOLE_ID);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(id);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_login_two);
        layout.addView(textView);

    }
}
