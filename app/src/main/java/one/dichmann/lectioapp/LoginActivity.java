package one.dichmann.lectioapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import Search.Search;
import downloadLectio.GetGyms;

public class LoginActivity extends Activity {
    public final static String SKOLE_ID = "one.dichmann.LectioApp.SKOLEID";

    //Define Privates of the ID's before onCreate.
    private EditText editText;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private String Value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Search.saveGymList("gymList.txt", this);

        // Define Content View before any other variables of the content.
        setContentView(R.layout.activity_login);

        // Define the contents by using findViewById
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
                Value = editText.getText().toString().toLowerCase();

                new Search(textView1).execute(Value);
                new Search(textView2).execute(Value);


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


