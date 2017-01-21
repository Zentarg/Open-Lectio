package one.dichmann.lectioapp;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Toast;

public class LoginChooseActivity extends AppCompatActivity {

    public static String finalGymID = "one.dichmann.LectioApp.gymID";
    public static String finalGymName = "one.dichmann.LectioApp.gymName";
    public static String type = "one.dichmann.LectioApp.type";

    private String GymID;
    private String GymName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choose);

        Intent intent = getIntent();
        GymID = intent.getStringExtra(LoginActivity.finalGymID);
        GymName = intent.getStringExtra(LoginActivity.finalGymName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void FindStudent(View view) {

        Intent intent = new Intent(this, LoginActivityTwo.class);
        intent.putExtra(finalGymID, GymID);
        intent.putExtra(finalGymName, GymName);
        intent.putExtra(type, "Student");
        startActivity(intent);

    }

    public void FindTeacher(View view) {

        Intent intent = new Intent(this, LoginActivityTwo.class);
        intent.putExtra(finalGymID, GymID);
        intent.putExtra(finalGymName, GymName);
        intent.putExtra(type, "Teacher");
        startActivity(intent);
    }

    public void UseLogin(View view) {

        Toast.makeText(this, "This feature is not yet available.", Toast.LENGTH_SHORT).show();

    }
}
