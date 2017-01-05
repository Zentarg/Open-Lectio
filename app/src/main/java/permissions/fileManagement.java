package permissions;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by apgch on 05-01-2017.
 */

public class fileManagement {

    public boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public static void createFile(Context context, String filename, String data) {
        try {
            FileOutputStream fOut = context.openFileOutput(filename,context.MODE_WORLD_READABLE);
            fOut.write(data.getBytes());
            fOut.close();
            Toast.makeText(context, "stuff", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
        }
    }


    public static void doStuff(Context context, String filename) {
        System.out.println("Before try");
        try {
            System.out.println("Trying");
            FileInputStream fin = context.openFileInput(filename);
            int c;
            String temp = "";
            while( (c = fin.read()) != -1) {
                temp = temp + Character.toString((char)c);
            }
            Toast.makeText(context, "This is what file said:"+temp, Toast.LENGTH_SHORT).show();
        } catch(Exception e) {

        }
        System.out.println("After try");
    }

}
