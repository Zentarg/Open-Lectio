package schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Weekday {

    public String Weekday() {
        Date date;

        try {
            date = new SimpleDateFormat("dd/mm/yyyy").parse(Today());
        } catch (ParseException e) {
            e.printStackTrace();
            return "Wrong format";
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return Day(dayOfWeek);
    }

    public static String Today() {
        try{
        SimpleDateFormat s = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String format = s.format(new Date());
        return format;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String Day(int args) {
        final int Mon = 0;
        final int Tue = 1;
        final int Wed = 2;
        final int Thu = 3;
        final int Fri = 4;
        final int Sat = 5;
        final int Sun = 6;

        switch (args) {
            case Mon: { return "Mandag"; }
            case Tue: { return "Tirsdag"; }
            case Wed: { return "Onsdag"; }
            case Thu: { return "Torsdag"; }
            case Fri: { return "Fredag"; }
            case Sat: { return "Lørdag"; }
            case Sun: { return "Søndag"; }
        }

        return null;
    }
}
