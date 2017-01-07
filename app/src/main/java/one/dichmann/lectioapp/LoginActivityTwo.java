package one.dichmann.lectioapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.jar.Attributes;

import Search.Search;
import downloadLectio.AsyncResponse;
import downloadLectio.GetNames;

public class LoginActivityTwo extends AppCompatActivity implements AsyncResponse {

    public static String finalNameID = "one.dichmann.LectioApp.nameID";
    public static String finalGymID = "one.dichmann.LectioApp.gymID";
    public static String finalGymName= "one.dichmann.LectioApp.gymName";

    private TextView gymText, textView1, textView2, textView3, textView4;
    private EditText editTextNames;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    private String valueNames, gymID, nameID, list, gymName;
    private String[] nameIDs;
    private TextView[] textViewsName;
    private ImageView[] imageViewsName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        gymID = intent.getStringExtra(LoginActivity.finalGymID);
        gymName = intent.getStringExtra(LoginActivity.finalGymName);

        GetNames asyncTaskNames = new GetNames();
        asyncTaskNames.GymID = gymID;
        asyncTaskNames.delegate = this;
        asyncTaskNames.execute();

        setContentView(R.layout.activity_login_two);

        //Second part of the login defined.
        gymText = (TextView) findViewById(R.id.loginTwo_gymText);
        editTextNames = (EditText) findViewById(R.id.loginTwo_Search_Search);
        textView1 = (TextView) findViewById(R.id.loginTwo_Search_Result_One);
        textView2 = (TextView) findViewById(R.id.loginTwo_Search_Result_Two);
        textView3 = (TextView) findViewById(R.id.loginTwo_Search_Result_Three);
        textView4 = (TextView) findViewById(R.id.loginTwo_Search_Result_Four);
        imageView1 = (ImageView) findViewById(R.id.loginTwo_UnderscoreImage1);
        imageView2 = (ImageView) findViewById(R.id.loginTwo_UnderscoreImage2);
        imageView3 = (ImageView) findViewById(R.id.loginTwo_UnderscoreImage3);
        imageView4 = (ImageView) findViewById(R.id.loginTwo_UnderscoreImage4);
        //Sets the text of the gymText TextView to the selectedGym string.
        gymText.setText(gymName);
        list = "wait";

        //Defines arrays of the TextViews and ImageViews we need for the first part of the login (1-4)
        textViewsName = new TextView[]{textView1, textView2, textView3, textView4};
        imageViewsName = new ImageView[]{imageView1, imageView2, imageView3, imageView4};

        editTextNames.addTextChangedListener(new TextWatcher() {
            // We don't use this, however it is required to have it for the TextWatcher to work.
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // Here we do stuff the moment you change text in the EditText.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Make whatever is in the EditText a string of lower case words every time you change the EditText.
                valueNames = editTextNames.getText().toString().toLowerCase();

                if (!list.equals("wait")){
                    Search search = new Search();
                    search.delegate = LoginActivityTwo.this;
                    String[] result = search.Search(imageViewsName, textViewsName, list, valueNames);

                    if (result != null) {
                      nameIDs = result;
                    } else {
                        Toast.makeText(LoginActivityTwo.this, "No internet Connection", Toast.LENGTH_LONG).show();
                        GetNames asyncTaskNames = new GetNames();
                        asyncTaskNames.GymID = gymID;
                        asyncTaskNames.delegate = LoginActivityTwo.this;
                        asyncTaskNames.execute();
                    }
                }
            }

            // We don't use this either. Still required for the TextWatcher to work.
            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    public void newTextView(View view) {
        //Defines the TextView that was clicked.
        TextView selectedGym = (TextView) view;
        int id = selectedGym.getId();
        for (int i = 0; i < 4; i++) {
            if (id == textViewsName[i].getId()) {
                nameID = nameIDs[i];
            }
        }


    }


    @Override
    public void processFinish(String output) {
        list = output;
        if (list.equals("non")) {
            Toast.makeText(LoginActivityTwo.this, "Dette gymnasie er ikke i brug", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(LoginActivityTwo.this, LoginActivity.class);
            startActivity(intent2);
        }
    }

    @Override
    public void processViews(Object[] objects) {

    }

    public void LoginWithout(View view) {
        TextView selectedGym = (TextView) view;
        int id = selectedGym.getId();
        for (int i = 0; i < 4; i++) {
            if (id == textViewsName[i].getId()) {
                nameID = nameIDs[i];
                permissions.fileManagement.createFile(this, "login", gymID+"-"+nameID);
            }
        }
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(finalGymID, gymID);
        intent.putExtra(finalNameID, nameID);
        startActivity(intent);
    }
}

