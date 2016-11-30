package one.dichmann.lectioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    public void login_Type_Lectiologin(View view) {
        /*
        EditText login_lectio_Brugernavn = (EditText) findViewById(R.id.login_Brugernavn);
        EditText login_lectio_Password = (EditText) findViewById(R.id.login_Password);
        EditText login_elev_ID = (EditText) findViewById(R.id.login_ElevID);

        login_lectio_Brugernavn.setVisibility(View.VISIBLE);
        login_lectio_Password.setVisibility(View.VISIBLE);

        login_elev_ID.setVisibility(View.INVISIBLE);*/

        Toast.makeText(getApplicationContext(), "This Feature Is Currently Not Available.", Toast.LENGTH_LONG).show();

    }

    public void login_type_ElevID(View view) {
        EditText login_lectio_Brugernavn = (EditText) findViewById(R.id.login_Brugernavn);
        EditText login_lectio_Password = (EditText) findViewById(R.id.login_Password);
        EditText login_elev_ID = (EditText) findViewById(R.id.login_ElevID);

        login_elev_ID.setVisibility(View.VISIBLE);

        login_lectio_Brugernavn.setVisibility(View.INVISIBLE);
        login_lectio_Password.setVisibility(View.INVISIBLE);
    }
}
