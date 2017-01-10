package schedule;

import android.content.Context;
import android.os.AsyncTask; //limits the consumption on the UI thread
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import downloadLectio.AsyncResponse; //used to get the product of the Asynctask
import downloadLectio.GetSchedule;
import downloadLectio.parseLesson;   //used to parse the data from the schedule to qualify what kind of data it is

public class Schedule extends AsyncTask<Object, Object, Object> { //works as a parsing terminal
    public AsyncResponse delegate = null; //initialises Asyncresponse delegate which should be set to context before doInBackground executes
    public String[] modules, date; //a String array of all the modules
    public int display, v, h, q, todayYear;
    public Context context;
    public String gymID, nameID, todayDate, todayDay, file, timeStamp, parse, todayWeek;
    public LinearLayout mainLinearLayout;
    public View dayAndDate;

    private Object[] views;
    private boolean downloaded, replace = false;
    private String lessons, timeDay; //the lessons module

    @Override
    public Object doInBackground(Object... Strings) { //gets all the strings send to it from getSchedule

        todayYear = 2017; //hardcoded for now

        todayDay = Weekday.Weekday(); //gets a string with todays physical name
        if (todayDay == "Lørdag") { //checks if it´s saturday
            date = Weekday.Weekend(2).split(""); //asks a function to add 2 days to the date
            todayDay = "Mandag"; //sets the day to Monday
            todayWeek = Weekday.todayWeek(1, 0); //adds one week to the week of the year
        } else if (todayDay == "Søndag") { //checks if it´s sunday
            date = Weekday.Weekend(1).split("");//asks a function to add 1 days to the date
            todayDay = "Mandag"; //sets the day to Monday
            todayWeek = Weekday.todayWeek(1, 0);//adds one week to the week of the year
        } else { //if it´s a weekday it just passes on.
            date = Weekday.Today().split("");
            todayWeek = Weekday.todayWeek(0, 0);//adds one week to the week of the year
        }

        //date were split due to us formatting it from a american standard to a more common danish way (not the Dansih standard)

        if (date[6].equals("0")) { //zero´s look bad in the monthday and months number
            date[6] = "";
        }
        if (date[9].equals("0")) { //zero´s look bad in the monthday and months number
            date[9] = "";
        }

        //parses the date into a more viable string.
        todayDate = date[6] + date[7] + "/" + date[9] + date[10] + "-" + date[1] + date[2] + date[3] + date[4];

        if (new permissions.fileManagement().fileExists(context, gymID + nameID)) { //checks if a file with the schedule already exists
            timeStamp = new schedule.Weekday().Today(); // creates a new timestamp whcih should be equal to the time of execution
            file = new permissions.fileManagement().getFile(context, gymID + nameID); //loads the file to a string from Storage with the GetFile method from fileManagement
            parse = ("(.*?)(\\d\\d)(:)(\\d\\d)(:)(\\d\\d)"); // creates a pattern for the date method
            Pattern p = Pattern.compile(parse); //compiles the pattern
            Matcher m = p.matcher(timeStamp); //matches the pattern against the entire file
            Matcher m2 = p.matcher(file); //matches the pattern against the entire file
            if (m.find() && m2.find()) { //if both of them are equal they both contain valid date information and therefore we can see how old the file is
                int hour = Integer.parseInt(m.group(2)); //sets the hour of the timestamp
                int hour2 = Integer.parseInt(m2.group(2)); //sets the hour of the file´s timestamp

                if (hour2 + 1 < hour) {//compares the 2 hour numbers. the schedule can at max be 2 hours old.
                    replace = true; //if it is older than 2 hours a replace order is issued
                } else {
                    lessons = file.replace(String.valueOf(m2.group(0)), ""); //removes the date tag from the file before the content of the file is placed as our schedule
                    System.out.println("Found file"); //for debugging purposes it prints that the file was loaded from storage
                    downloaded = true; //declares that there is no need for downloading a new schedule.
                }
            }
        }
        if (!downloaded || replace) {
            GetSchedule GetSchedule = new downloadLectio.GetSchedule();
            System.out.println("Downloaded new schedule");
            GetSchedule.gymID = gymID;
            GetSchedule.nameID = nameID;
            GetSchedule.week = todayWeek;
            GetSchedule.year = todayYear;
            lessons = GetSchedule.getSchedule();
            if (replace) {
                permissions.fileManagement.createFile(context, gymID + nameID, timeStamp + lessons);
            } else {
                permissions.fileManagement.createFile(context, gymID + nameID, timeStamp + lessons);
            }
        }

        modules = lessons.split("£"); //creates an array of all the modules

        ArrayList<ArrayList<Object>> week = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> day = new ArrayList<Object>();
        timeDay = "first";

        for (int i = 0; i < modules.length; i=i+2) {
            String time = parseLesson.getDate(modules[i]);
            String team = parseLesson.getTeam(modules[i]);
            String teacher = parseLesson.getTeacher(modules[i]);
            String room = parseLesson.getRoom(modules[i]);
            if (timeDay!=null) {
                if (timeDay.equals("first")) {
                    timeDay = time;
                    day.add((Object) new Display().vertical(team + "---" + teacher + "---" + room, context));
                } else if (timeDay.equals(time)) {
                    day.add((Object) new Display().vertical(team + "---" + teacher + "---" + room, context));
                } else {
                    week.add(day);
                    timeDay = time;
                    day.clear();
                    day.add((Object) new Display().vertical(team + "---" + teacher + "---" + room, context));
                }
            }
        }
        return (Object) week;
    }

    protected void onPostExecute(Object result) {//recieves the result of the AsyncTask
        delegate.processViews(result); //sends the result to the processViews task in the context activity.
    }
}