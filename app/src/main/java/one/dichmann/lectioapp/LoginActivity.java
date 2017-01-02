package one.dichmann.lectioapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Search.Search;
import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import permissions.checkStoragePermission;


public class LoginActivity extends Activity implements AsyncResponse {
    public final static String SKOLE_ID = "one.dichmann.LectioApp.SKOLEID";

    //Define Privates of the ID's before onCreate.
    private EditText editText;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private String Value;
    private int PERMISSION_STORAGE = 0;
    private String gymList;
    private TextView[] Textviews;
    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    GetGyms asynctask = new GetGyms();

    //retrieves Gym list and assigns it to a private variable.
    @Override
    public void processFinish(String output) {
        gymList = output;
        System.out.println(gymList);
    }
    //persmission method.
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        System.out.println("writePerm : "+writePermission + "   readPerm : "+readPermission);

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

        System.out.println("writePerm2 : "+writePermission2 + "   readPerm2 : "+readPermission2);

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
        asynctask.delegate = this;
        asynctask.execute();

        // Define Content View before any other variables of the content.
        setContentView(R.layout.activity_login);


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


        editText = (EditText) findViewById(R.id.loginOne_Search_Search);
        textView1 = (TextView) findViewById(R.id.logineOne_Search_Result_One);
        textView2 = (TextView) findViewById(R.id.logineOne_Search_Result_Two);
        textView3 = (TextView) findViewById(R.id.logineOne_Search_Result_Three);
        textView4 = (TextView) findViewById(R.id.logineOne_Search_Result_Four);

        // A function that does something whenever you change the text in the specific EditText
        editText.addTextChangedListener(new TextWatcher() {

            // We don't use this, however it is required to have it for the TextWatcher to work.
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // Here we do stuff the moment you change text in the EditText.
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Make whatever is in the EditText a string of lower case words every time you change the EditText.
                System.out.println("i do");
                Value = editText.getText().toString().toLowerCase();
                TextView[] textviews = new TextView[]{textView1, textView2, textView3, textView4};
                new Search().Search(textviews , gymList , Value);

                if (Value.length() >= 1) {
                    textView1.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.VISIBLE);
                } else {
                    textView1.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    textView3.setVisibility(View.INVISIBLE);
                    textView4.setVisibility(View.INVISIBLE);
                }

            }
            // We don't use this either. Still required for the TextWatcher to work.
            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


    }

    public void newTextView(View view) {


        TextView selectedGym = (TextView) view;
        String selectedGymString = selectedGym.getText().toString();

        Toast.makeText(this, selectedGymString, Toast.LENGTH_SHORT).show();

        TextView newTextView = new TextView(this);
        newTextView.setText(selectedGym.getText().toString());

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_login);
        layout.addView(newTextView);
    }
    //TODO: Check if logged in.
        //TODO: If actually logged in, check who is logged in. Thereafter make sure to go directly to the logged in activity as user.
        //TODO: If not logged in, display as normally.
/*
    public void loginOneNext(View view) {
        Intent intent = new Intent(this, LoginTwoActivity.class);
        SearchView login_Search = (SearchView) findViewById(R.id.login_Search);
        String id = login_Search.getQuery().toString();
        intent.putExtra(SKOLE_ID, id);
        startActivity(intent);
    }
  */
};


