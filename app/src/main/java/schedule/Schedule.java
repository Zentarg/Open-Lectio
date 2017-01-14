package schedule;

import android.content.Context;
import android.os.AsyncTask; //limits the consumption on the UI thread
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import downloadLectio.AsyncResponse; //used to get the product of the Asynctask
import downloadLectio.GetSchedule;
import downloadLectio.parseLesson;   //used to parse the data from the schedule to qualify what kind of data it is


public class Schedule extends AsyncTask<Object, Object, Object> { //works as a parsing terminal
    public AsyncResponse delegate = null; //initialises Asyncresponse delegate which should be set to context before doInBackground executes
    public String[] date; //a String array of all the modules
    public String year, week;
    public Context context;
    public String gymID, nameID, todayDate, file, timeStamp, parse, todayWeek;
    private String lessons; //the lessons module
    public Calendar c = Calendar.getInstance();


    private boolean downloaded, replace = false;


    @Override
    public Object doInBackground(Object... Strings) { //gets all the strings send to it from getSchedule

        year = 2017+""; //hardcoded for now

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
            timeStamp = new schedule.Weekday().Today(c); // creates a new timestamp whcih should be equal to the time of execution
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
            GetSchedule.year = year;
            GetSchedule.context = context;
            if (replace) {
                permissions.fileManagement.createFile(context, gymID + nameID, timeStamp + lessons);
            } else {
                permissions.fileManagement.createFile(context, gymID + nameID, timeStamp + lessons);
            }
        }
        return null;
    }
}

        /*

        modules = lessons.split("£"); //creates an array of all the modules

        ArrayList<Object> week = new ArrayList<Object>();
        ArrayList<Object> day = new ArrayList<Object>();
        timeDay = "first";

        for (int i = 0; i < modules.length; i=i+2) {
            String time = parseLesson.getDate(modules[i]);
            String team = parseLesson.getTeam(modules[i]);
            String teacher = parseLesson.getTeacher(modules[i]);
            String room = parseLesson.getRoom(modules[i]);
            if (timeDay!=null && team!=null) {
                Pattern teamRegex = Pattern.compile("Alle");
                Matcher teamMatcher = teamRegex.matcher(team);
                Pattern roomRegex = Pattern.compile("\\,(.*?)(\\,|$)");
                Matcher roomMatcher = roomRegex.matcher(room);
                if (roomMatcher.find()){
                    room = roomMatcher.group(1);
                }
                if (!teamMatcher.find()) {
                    if (timeDay.equals("first")) {
                        timeDay = time;
                        day.add((Object) new Display().vertical(team + "---" + teacher + "---" + room, context));
                    } else if (timeDay.equals(time)) {
                        day.add((Object) new Display().vertical(team + "---" + teacher + "---" + room, context));
                    } else {
                        week.add((Object) day);
                        timeDay = time;
                        day = new ArrayList<Object>();
                        day.add((Object) new Display().vertical(team + "---" + teacher + "---" + room, context));
                    }
                }
            }
        }
        week.add((Object) day);
        return (Object) week;
    }

    protected void onPostExecute(Object result) {//recieves the result of the AsyncTask
        delegate.processViews(result); //sends the result to the processViews task in the context activity.
    }
}
*/