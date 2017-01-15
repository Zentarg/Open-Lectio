package permissions;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
            FileOutputStream fOut = context.openFileOutput(filename, context.MODE_WORLD_READABLE);
            fOut.write(data.getBytes());
            fOut.close();
        } catch(Exception e) {
            e.printStackTrace();
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
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        return sbString;
    }

    public static void writeObject(Context context, String filename, Object object) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object readObject(Context context, String filename) {

        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Object> list = (ArrayList<Object>) ois.readObject();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    return null;
    }


}
