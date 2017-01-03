package one.dichmann.lectioapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import permissions.checkStoragePermission;


public class LoginActivity extends Activity implements AsyncResponse {

    //Define Privates of the ID's before onCreate.
    private TextView gymText, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
    private EditText editTextGyms, editTextNames;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;
    private int[] textValueGym = new int[4];
    private int[] textValueNames = new int[4];
    private String valueGyms, valueNames, gymID, nameID, list;
    private String[] gymIDs, NameIDs;
    // Storage Permissions variables
    private int PERMISSION_STORAGE = 0;
    private int gym, name;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    GetGyms asyncTaskGyms = new GetGyms();
    GetNames asyncTaskNames = new GetNames();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //retrieves Gym list and assigns it to a private variable.
    @Override
    public void processFinish(String output) {
        list = output;
        System.out.println(list);
    }

    //persmission method.
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        System.out.println("writePerm : " + writePermission + "   readPerm : " + readPermission);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            System.out.println("Asked user for permission");
        }

        int writePermission2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        System.out.println("writePerm2 : " + writePermission2 + "   readPerm2 : " + readPermission2);

        //Check if we have permission now. If so, then execute GetGyms.
        if (writePermission2 != PackageManager.PERMISSION_DENIED && readPermission2 != PackageManager.PERMISSION_DENIED) {
            //new GetGyms(this).execute();
            System.out.println("GetGyms executing & permission granted.");
        } else {
            System.out.println("We couldn't execute GetGyms");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //this if statement will be used to check if the student is already logged in.
        if (gym==1 && name==1){
            LoginWithout();
        } else {
            //Calling the permission method.
            //verifyStoragePermissions(this);


            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    System.out.println("Have requested permission once or more times before");

                } else {

                    // No explanation needed, we can request the permission.

                    System.out.println("Requesting Permission");

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_STORAGE);


                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.

                    int writePermission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int readPermission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    System.out.println("writePerm2 : " + writePermission2 + "   readPerm2 : " + readPermission2);
                    int[] permissions = {writePermission2, readPermission2};
                    new checkStoragePermission().onRequestPermissionsResult(0, permissions);
                }
            } else {
                    System.out.println("Permission has been granted before we try to get it");
                    //new GetGyms(this).execute();
                }


                //due to bugfixing the below code tries to execute the getgyms method no matter what the permissions are.
                //assigns Getgyms delegate and launches it
                asyncTaskGyms.delegate = this;
                asyncTaskGyms.execute();

                // Define Content View before any other variables of the content.
                setContentView(R.layout.activity_login);

                editTextGyms = (EditText) findViewById(R.id.loginOne_Search_Search);
                textView1 = (TextView) findViewById(R.id.loginOne_Search_Result_One);
                textView2 = (TextView) findViewById(R.id.loginOne_Search_Result_Two);
                textView3 = (TextView) findViewById(R.id.loginOne_Search_Result_Three);
                textView4 = (TextView) findViewById(R.id.loginOne_Search_Result_Four);
                imageView1 = (ImageView) findViewById(R.id.login_UnderscoreImage1);
                imageView2 = (ImageView) findViewById(R.id.login_UnderscoreImage2);
                imageView3 = (ImageView) findViewById(R.id.login_UnderscoreImage3);
                imageView4 = (ImageView) findViewById(R.id.login_UnderscoreImage4);

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

                // Define the contents by using findViewById

        /*
            protected void insertToDatabase(String name) {
                class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

                    @Override
                    protected String doInBackground(String... params) {
                        String paramSchoolName = params[0];

                        String name = Value;

                        list<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("name", Value));

                        try {
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost("http://www.enelleranden.dk/lectio/openLectio.php");
                            httpPost.setEntity(new URLEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpClient.execute(httpPost);
                            HttpEntity entity = response.getEntity;
                        } catch (ClientProtocolException e) {

                        } catch (IOException e) {

                        }
                        return "Success";
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                    }
                }
                SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
                sendPostReqAsyncTask.execute(name);
            }
        */
                // onClick function of the TextView that goes to the next activity with the GymName/ID

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
        for (int i = 0; i < 4; i++) {
            //Sets the Text and ImageViews Visibility to GONE
            textViewsGym[i].setVisibility(View.GONE);
            imageViewsNames[i].setVisibility(View.GONE);
            //Sets the Text and ImageViews Visibility to VISIBLE
            textViewsNames[i].setVisibility(View.VISIBLE);
            imageViewsNames[i].setVisibility(View.VISIBLE);
        }
        //Set the EditText of the first part of the login to GONE
        editTextGyms.setVisibility(View.GONE);
        //Set the EditText for the second part of the login aswell as the TextView that displays the Gym you chose to VISIBLE
        editTextNames.setVisibility(View.VISIBLE);
        gymText.setVisibility(View.VISIBLE);
    }

    //TODO: Check if logged in.
    //TODO: If actually logged in, check who is logged in. Thereafter make sure to go directly to the logged in activity as user.
    //TODO: If not logged in, display as normally.

    public void LoginWithout() {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("gymID", gymID);
        intent.putExtra("nameID", nameID);
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


