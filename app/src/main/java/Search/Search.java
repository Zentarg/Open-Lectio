package Search;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Search {
	private String mResult;
	private int q = 0;
	private TextView textView = null;
	private ImageView imageView = null;

	public void Search(ImageView[] imageView, TextView[] textView, String result, String args) {
		mResult = result;

		String[] key = mResult.split("£");

		loop:
		for (int i = 0; i < key.length; i++) {
			String[] list = key[i].split("==");
			Pattern noteRegex = Pattern.compile("^" + args + ".*?");
			Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase());
			if(noteMatcher.find()){
				textView[q].setText(list[0]);
				textView[q].setVisibility(View.VISIBLE);
				imageView[q].setVisibility(View.VISIBLE);
				q++;
				if (q==4) {
					break loop;
				}
			}
		}
		if (q<4) {
			loop:
			for (int i = 0; i < key.length; i++) {
				String[] list = key[i].split("==");
				Pattern noteRegex = Pattern.compile(".*?" + args + ".*?");
				Matcher noteMatcher = noteRegex.matcher(list[0].toLowerCase());
				if(noteMatcher.find()){
					textView[q].setText(list[0]);
					textView[q].setVisibility(View.VISIBLE);
					imageView[q].setVisibility(View.VISIBLE);
					q++;
					if (q==4) {
						break loop;
					}
				}
			}
		}
		if (q<4) {
			while (q<4){
				textView[q].setVisibility(View.GONE);
				imageView[q].setVisibility(View.GONE);
				q++;
			}
		}
	}
}
