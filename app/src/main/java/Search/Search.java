package Search;
import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import downloadLectio.AsyncResponse;
import downloadLectio.GetGyms;
import one.dichmann.lectioapp.LoginActivity;


public class Search {
    public Context delegate = null;
    private String mResult;
    private String[] IDs = new String[4];
    private int q = 0;
    private TextView textView = null;
    private ImageView imageView = null;
    private boolean dupe = true;

    public String[] Search(ImageView[] imageView, TextView[] textView, String result, String args) {
        mResult = result;
        String pattern = null;
        if (result != null) {

            String[] name = result.split("£");

            if (!args.contains(" ")) {
                loop:
                for (int i = 0; i < name.length; i++) {
                    String[] list = name[i].split("==");
                    Pattern noteRegex = Pattern.compile("^" + args.toLowerCase() + ".*?");
                    Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase());
                    if (noteMatcher.find()) {
                        IDs[q] = list[1];
                        textView[q].setText(list[0]);
                        textView[q].setVisibility(View.VISIBLE);
                        imageView[q].setVisibility(View.VISIBLE);
                        q++;
                        if (q == textView.length) {
                            break loop;
                        }
                    }
                }
                loop:
                if (q<4){
                    for (int i = 0; i < name.length; i++) {
                        String[] list = name[i].split("==");
                        Pattern noteRegex = Pattern.compile(".*?" + args.toLowerCase() + ".*?");
                        Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase());
                        for (int k = 0; k < q; k++) {
                            if (list[1].equals(IDs[k])) {
                                dupe = false;
                            }
                        }
                        if (noteMatcher.find() && dupe) {
                            IDs[q] = list[1];
                            textView[q].setText(list[0]);
                            textView[q].setVisibility(View.VISIBLE);
                            imageView[q].setVisibility(View.VISIBLE);
                            q++;
                            if (q == textView.length) {
                                break loop;
                            }
                        }
                    }
                }
            } else {
                String[] myLast = args.toLowerCase().split(" ");
                pattern = "^";
                for (int i = 0; i < myLast.length; i++) {
                    pattern = pattern + myLast[i]+ ".*?" ;
                }

                loop:
                for (int i = 0; i < name.length; i++) {
                    String[] list = name[i].split("==");
                    Pattern noteRegex = Pattern.compile(pattern);
                    Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase());
                    if (noteMatcher.find()) {
                        IDs[q] = list[1];
                        textView[q].setText(list[0]);
                        textView[q].setVisibility(View.VISIBLE);
                        imageView[q].setVisibility(View.VISIBLE);
                        q++;
                        if (q == textView.length) {
                            break loop;
                        }
                    }
                }
            }
            if (q < textView.length) {
                while (q < textView.length) {
                    IDs[q] = "null";
                    textView[q].setVisibility(View.GONE);
                    imageView[q].setVisibility(View.GONE);
                    q++;
                }
            }
            if (args.isEmpty()) {
                for (int i = 0; i < textView.length; i++) {
                    textView[i].setVisibility(View.GONE);
                    imageView[i].setVisibility(View.GONE);
                }
            }
        } else {
            Toast.makeText( delegate , "No internet connection", Toast.LENGTH_SHORT).show();
            GetGyms getGyms = new GetGyms();
            getGyms.delegate = (AsyncResponse) delegate;
            getGyms.execute();
            return null;
        }
        return IDs;
    }
}