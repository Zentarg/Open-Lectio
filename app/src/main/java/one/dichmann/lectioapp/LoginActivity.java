package one.dichmann.lectioapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import Search.Search;
import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import downloadLectio.GetNames;


public class LoginActivity extends Activity implements AsyncResponse {

    public static String finalNameID = "one.dichmann.LectioApp.nameID";
    public static String finalGymID = "one.dichmann.LectioApp.gymID";

    //Define Privates of the ID's before onCreate.
    private TextView gymText, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
    private EditText editTextGyms, editTextNames;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;
    private LinearLayout fragment_loginOne, fragment_loginTwo;
    private String valueGyms, valueNames, nameID, gymID, list;
    private String[] gymIDs, NameIDs;

    // Storage Permissions variables
    private int gym, name;

    GetGyms asyncTaskGyms = new GetGyms();
    GetNames asyncTaskNames = new GetNames();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //Retrieves Gym list and assigns it to a private variable.
    @Override
    public void processFinish(String output) {
        list = output;
        System.out.println(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //this if statement will be used to check if the student is already logged in.
        if (gym==1 && name==1){
            //LoginWithout();
            Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show();
        } else {
            //assigns Getgyms delegate and launches it
            asyncTaskGyms.delegate = this;
            asyncTaskGyms.execute();

            // Define Content View before any other variables of the content.
            setContentView(R.layout.activity_login);

            // Define the contents by using findViewById
            fragment_loginOne = (LinearLayout) findViewById(R.id.fragment_LoginOne);
            fragment_loginTwo = (LinearLayout) findViewById(R.id.fragment_LoginTwo);

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

            //Second part of the login defined.
            gymText = (TextView) findViewById(R.id.loginTwo_gymText);
            editTextNames = (EditText) findViewById(R.id.loginTwo_Search_Search);
            textView5 = (TextView) findViewById(R.id.loginTwo_Search_Result_One);
            textView6 = (TextView) findViewById(R.id.loginTwo_Search_Result_Two);
            textView7 = (TextView) findViewById(R.id.loginTwo_Search_Result_Three);
            textView8 = (TextView) findViewById(R.id.loginTwo_Search_Result_Four);
            imageView5 = (ImageView) findViewById(R.id.loginTwo_UnderscoreImage1);
            imageView6 = (ImageView) findViewById(R.id.loginTwo_UnderscoreImage2);
            imageView7 = (ImageView) findViewById(R.id.loginTwo_UnderscoreImage3);
            imageView8 = (ImageView) findViewById(R.id.loginTwo_UnderscoreImage4);

            // A function that does something whenever you change the text in the specific EditText
            editTextGyms.addTextChangedListener(new TextWatcher() {
                //Defines arrays of the TextViews and ImageViews we need for the first part of the login (1-4)
                TextView[] textViewsGym = new TextView[]{textView1, textView2, textView3, textView4};
                ImageView[] imageViewsGym = new ImageView[]{imageView1, imageView2, imageView3, imageView4};

                // We don't use this, however it is required to have it for the TextWatcher to work.
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                // Here we do stuff the moment you change text in the EditText.
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Make whatever is in the EditText a string of lower case words every time you change the EditText.
                    valueGyms = editTextGyms.getText().toString().toLowerCase();
                    gymIDs = new Search().Search(imageViewsGym, textViewsGym, list, valueGyms);
                }

                // We don't use this either. Still required for the TextWatcher to work.
                @Override
                public void afterTextChanged(Editable editable) {

                }

            });

            editTextNames.addTextChangedListener(new TextWatcher() {
                //Defines arrays of the TextViews and ImageViews we need for the first part of the login (1-4)
                TextView[] textViewsName = new TextView[]{textView5, textView6, textView7, textView8};
                ImageView[] imageViewsName = new ImageView[]{imageView5, imageView6, imageView7, imageView8};

                // We don't use this, however it is required to have it for the TextWatcher to work.
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                // Here we do stuff the moment you change text in the EditText.
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Make whatever is in the EditText a string of lower case words every time you change the EditText.
                    valueNames = editTextNames.getText().toString().toLowerCase();
                    NameIDs = new Search().Search(imageViewsName, textViewsName, list, valueNames);
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
    }


    public void newTextView(View view) {
        //textViewGym is defined here for the sake of retrieving the ID for the gym pressed
        TextView[] textViewsGym = new TextView[]{textView1, textView2, textView3, textView4};
        //Defines arrays of the TextViews and ImageViews we need for the second part of the login (5-8)
        TextView[] textViewsNames = new TextView[]{textView5, textView6, textView7, textView8};
        ImageView[] imageViewsNames = new ImageView[]{imageView5, imageView6, imageView7, imageView8};
        //Defines the TextView that was clicked.
        TextView selectedGym = (TextView) view;
        int id = selectedGym.getId();
        if (gym == 0) {
            for (int i = 0; i < 4; i++) {
                if (id == textViewsGym[i].getId()) {
                    gymID = gymIDs[i];
                    //assigns GetNames gymid and delegate and launches it
                    asyncTaskNames.GymID = gymID;
                    asyncTaskNames.delegate = this;
                    asyncTaskNames.execute();
                    gym = 1;
                }
            }
        }else{
            for (int i = 0; i < 4; i++) {
                if (id == textViewsGym[i].getId()) {
                    nameID = NameIDs[i];
                    name = 1;
                }
            }
        }

        //Get the string of the TextView that was clicked.
        String selectedGymString = selectedGym.getText().toString();

        //Makes a toast. This is only for debugging purposes and should be deleted
        Toast.makeText(this, selectedGymString, Toast.LENGTH_SHORT).show();

        //Sets the text of the gymText TextView to the selectedGym string.
        gymText.setText(selectedGym.getText().toString());

        //For too that sets the appropriate TextViews and ImageViews to either GONE or VISIBLE
        fragment_loginOne.setVisibility(View.GONE);
        fragment_loginTwo.setVisibility(View.VISIBLE);

        //Set the EditText of the first part of the login to GONE
        editTextGyms.setVisibility(View.GONE);
        //Set the EditText for the second part of the login aswell as the TextView that displays the Gym you chose to VISIBLE
        editTextNames.setVisibility(View.VISIBLE);
        gymText.setVisibility(View.VISIBLE);

        finalGymID = gymID;

    }

    //TODO: Check if logged in.
    //TODO: If actually logged in, check who is logged in. Thereafter make sure to go directly to the logged in activity as user.
    //TODO: If not logged in, display as normally.

    public void LoginWithout(View view) {
        //Set finalNameID to the chosen nameID before we start the intent.
        finalNameID = nameID;

        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("gymID", finalGymID);
        intent.putExtra("nameID", finalNameID);
        startActivity(intent);
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


