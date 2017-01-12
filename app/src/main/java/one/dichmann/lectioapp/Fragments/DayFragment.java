package one.dichmann.lectioapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import one.dichmann.lectioapp.R;

public class DayFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.day_frag, container, false);

        TextView day = (TextView) v.findViewById(R.id.schedule_DayAndDate_Day);
        day.setText(getArguments().getString("Day"));

        TextView date = (TextView) v.findViewById(R.id.schedule_DayAndDate_Date);
        date.setText(getArguments().getString("Date"));
        return v;
    }

    public static DayFragment newInstance(Bundle b) {

        DayFragment f = new DayFragment();

        f.setArguments(b);

        return f;
    }
}
