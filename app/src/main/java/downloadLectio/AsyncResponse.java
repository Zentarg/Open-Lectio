package downloadLectio;

import android.view.View;
import android.widget.TextView;

public interface AsyncResponse {
    public void processFinish(String output);

    public void processViews(TextView[] textView);
}

