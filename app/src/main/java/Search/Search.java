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
		String[] key = mResult.split("£");

		if (mResult != null) {
            loop:
			for (int i = 0; i < key.length; i++) {
				String[] list = key[i].split("==");
				Pattern noteRegex = Pattern.compile("^" + args + ".*?");
				Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase());
				if(noteMatcher.find()){
					IDs[q] = list[1];
					textView[q].setText(list[0]);
					textView[q].setVisibility(View.VISIBLE);
					imageView[q].setVisibility(View.VISIBLE);
					q++;
					if (q==textView.length) {
						break loop;
					}
				}
			}
			if (q<textView.length) {
				loop:
				for (int i = 0; i < key.length; i++) {
					String[] list = key[i].split("==");
					Pattern noteRegex = Pattern.compile(".*?" + args + ".*?");
					Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase());
					for (int k=0; k<textView.length; k++){if (list[1]==IDs[k]){dupe=false;}}
					if(noteMatcher.find() && dupe==false){
						IDs[q] = list[1];
						textView[q].setText(list[0]);
						textView[q].setVisibility(View.VISIBLE);
						imageView[q].setVisibility(View.VISIBLE);
						q++;
						if (q==textView.length) {
							break loop;
						}
					}
				}
			}
			if (q<textView.length) {
				while (q<textView.length){
					IDs[q] = "null";
					textView[q].setVisibility(View.GONE);
					imageView[q].setVisibility(View.GONE);
					q++;
				}
			}
			if (args.isEmpty()){
				for (int i=0; i<textView.length; i++){
					textView[i].setVisibility(View.GONE);
					imageView[i].setVisibility(View.GONE);
				}
			}
		} else {
			Toast.makeText(delegate, "No internet connection", Toast.LENGTH_SHORT).show();
            return null;
		}
		return IDs;
	}
}