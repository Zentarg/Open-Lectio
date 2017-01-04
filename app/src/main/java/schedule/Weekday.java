package schedule;

import java.text.ParseException; //catches a exception if todays date can´t fit into the wanted format
import java.text.SimpleDateFormat; //extracts the milliseconds into a more civilian day date format
import java.util.Calendar; //needed to calculate amount of milliseconds since newyear 1970
import java.util.Date; //a format dates are stored in

public class Weekday { //handles day arrangement of modules on the schedule

    public String Weekday() {
        Date date; //initialices date
        String weekDay = null;
        date = new Date();//gets todays date and parses it to a more simpified one
        Calendar c = Calendar.getInstance();//calender is created and called upon
        c.setTime(date);                    //the calender is set with today date
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.MONDAY == dayOfWeek) weekDay = "Mandag";
        else if (Calendar.TUESDAY == dayOfWeek) weekDay = "Tirsdag";
        else if (Calendar.WEDNESDAY == dayOfWeek) weekDay = "Onsdag";
        else if (Calendar.THURSDAY == dayOfWeek) weekDay = "Torsdag";
        else if (Calendar.FRIDAY == dayOfWeek) weekDay = "Fredag";
        else if (Calendar.SATURDAY == dayOfWeek) weekDay = "Lørdag";
        else if (Calendar.SUNDAY == dayOfWeek) weekDay = "Søndag";

        return weekDay;//returns day after it is convertet to a string
        }

    public static String Today() { //returns today´s date
        try{
        SimpleDateFormat s = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");//today down to every detail
        String format = s.format(new Date()); //new Date should have todays date after parsed since Date is generated by currentmillis
        return format; //returns the Date, can be parsed upon recieve
        } catch (Exception e) { //catches all exceptions
            e.printStackTrace(); //prints any errors that might occur
            return null; //should never return null and might cause a crash if i does
        }
    }
}
