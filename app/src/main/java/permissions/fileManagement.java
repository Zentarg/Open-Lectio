package permissions;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
        } catch(Exception e) {
        }
    }


    public static String getFile(Context context, String filename) {

        String sbString = null;
        try {

            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            sbString = sb.toString();

        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (Exception e) {
            return "";
        }

        return sbString;
    }

}
