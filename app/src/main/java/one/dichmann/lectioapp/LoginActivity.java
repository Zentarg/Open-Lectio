package one.dichmann.lectioapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Search.Search;
import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import downloadLectio.GetNames;
import schedule.Schedule;


public class LoginActivity extends Activity implements AsyncResponse {

    public static String finalNameID = "one.dichmann.LectioApp.nameID";
    public static String finalGymID = "one.dichmann.LectioApp.gymID";
    public static String finalGymName= "one.dichmann.LectioApp.gymName";

    //Define Privates of the ID's before onCreate.
    private TextView textView1, textView2, textView3, textView4;
    private EditText editTextGyms;
    private ImageView imageView1, imageView2, imageView3, imageView4;
    private String valueGyms, nameID, gymID, list, file, parse;
    private String[] gymIDs;
    private TextView[] textViewsGym;
    private ImageView[] imageViewsGym;
    private boolean loggedIn = false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //Retrieves Gym list and assigns it to a private variable.
    @Override
    public void processFinish(String output) {
        list = output;
    }

    @Override
    public void processViews(Object[] objects) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new permissions.fileManagement().fileExists(this, "login")){
            file = new permissions.fileManagement().getFile(this, "login");
            if (file!=null){
                parse = ("(.*?)(-)(.*)");
                Pattern p = Pattern.compile(parse);
                Matcher m = p.matcher(file);
                if (m.find()) {
                    //loggedIn = true;
                    //gymID = m.group(1);
                    //nameID = m.group(3);
                    //LoginWithout(findViewById(R.id.loginOne_Search_Result_One));
                }
            }
        }
        setContentView(R.layout.activity_login);
        //this if statement will be used to check if the student is already logged in.
        //assigns Getgyms delegate and launches it
        GetGyms asyncTaskGyms = new GetGyms();
        asyncTaskGyms.delegate = this;
        asyncTaskGyms.execute();

        // Define Content View before any other variables of the content.
        setContentView(R.layout.activity_login);


        //First part of the login defined.
        editTextGyms = (EditText) findViewById(R.id.loginOne_Search_Search);
        textView1 = (TextView) findViewById(R.id.loginOne_Search_Result_One);
        textView2 = (TextView) findViewById(R.id.loginOne_Search_Result_Two);
        textView3 = (TextView) findViewById(R.id.loginOne_Search_Result_Three);
        textView4 = (TextView) findViewById(R.id.loginOne_Search_Result_Four);
        imageView1 = (ImageView) findViewById(R.id.login_UnderscoreImage1);
        imageView2 = (ImageView) findViewById(R.id.login_UnderscoreImage2);
        imageView3 = (ImageView) findViewById(R.id.login_UnderscoreImage3);
        imageView4 = (ImageView) findViewById(R.id.login_UnderscoreImage4);

        //Defines arrays of the TextViews and ImageViews we need for the first part of the login (1-4)
        textViewsGym = new TextView[]{textView1, textView2, textView3, textView4};
        imageViewsGym = new ImageView[]{imageView1, imageView2, imageView3, imageView4};

        // A function that does something whenever you change the text in the specific EditText
        editTextGyms.addTextChangedListener(new TextWatcher() {
            // We don't use this, however it is required to have it for the TextWatcher to work.
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // Here we do stuff the moment you change text in the EditText.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Make whatever is in the EditText a string of lower case words every time you change the EditText.
                valueGyms = editTextGyms.getText().toString().toLowerCase();
                Search search = new Search();
                search.delegate = LoginActivity.this;
                String[] result = search.Search(imageViewsGym, textViewsGym, list, valueGyms);
                if (result!=null){
                    gymIDs=result;
                } else {
                    Toast.makeText(LoginActivity.this, "No internet Connection", Toast.LENGTH_LONG).show();
                    GetGyms asyncTaskGyms = new GetGyms();
                    asyncTaskGyms.delegate = LoginActivity.this;
                    asyncTaskGyms.execute();

                }
            }

            // We don't use this either. Still required for the TextWatcher to work.
            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void LoginWithout(View view) {
        if (!loggedIn) {
            TextView selectedGym = (TextView) view;
            String gymName = selectedGym.getText().toString();
            int id = selectedGym.getId();
            for (int i = 0; i < 4; i++) {
                if (id == textViewsGym[i].getId()) {
                    gymID = gymIDs[i];
                    Intent intent = new Intent(this, LoginChooseActivity.class);
                    intent.putExtra(finalGymID, gymID);
                    intent.putExtra(finalGymName, gymName);
                    startActivity(intent);
                }
            }
        } else {
            Intent intent = new Intent(this, ScheduleActivity.class);
            intent.putExtra(finalGymID, gymID);
            intent.putExtra(finalNameID, nameID);
            startActivity(intent);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
};


