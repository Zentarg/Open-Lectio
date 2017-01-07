package schedule;

import android.content.Context;
import android.os.AsyncTask; //limits the consumption on the UI thread
import android.view.View;
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import downloadLectio.AsyncResponse; //used to get the product of the Asynctask
import downloadLectio.GetSchedule;
import downloadLectio.parseLesson;   //used to parse the data from the schedule to qualify what kind of data it is

public class Schedule extends AsyncTask<Object, Object, Object[]> { //works as a parsing terminal
    public AsyncResponse delegate = null; //initialises Asyncresponse delegate which should be set to context before doInBackground executes
    public String[] modules, date; //a String array of all the modules
    public int display, v, h, q, todayYear;
    public Context context;
    public String gymID, nameID, todayDate, todayDay, file, timeStamp, parse, todayWeek;
    public LinearLayout mainLinearLayout;
    public View dayAndDate;

    private Object[] views;
    private boolean downloaded, replace = false;
    private String lessons; //the lessons module

    @Override
    public Object[] doInBackground(Object... Strings) { //gets all the strings send to it from getSchedule
        todayYear = 2017;
        todayDay = Weekday.Weekday();
        if (todayDay=="Lørdag") {
            date = Weekday.Weekend(2).split("");
            todayDay = "Mandag";
            todayWeek = Weekday.todayWeek(1,0);
        } else if (todayDay=="Søndag") {
            date = Weekday.Weekend(1).split("");
            todayDay = "Mandag";
            todayWeek = Weekday.todayWeek(1,0);
        } else {
            date = Weekday.Today().split("");
        }

        if (date[6].equals("0")) {
            date[6] = "";
        }
        if (date[9].equals("0")) {
            date[9] = "";
        }

        todayDate = date[6] + date[7] + "/" + date[9] + date[10] + "-" + date[1] + date[2] + date[3] + date[4];

        if (new permissions.fileManagement().fileExists(context, gymID+nameID)){
            timeStamp = new schedule.Weekday().Today();
            file = new permissions.fileManagement().getFile(context, gymID+nameID);
            parse = ("(.*?)(\\d\\d)(:)(\\d\\d)(:)(\\d\\d)");
            Pattern p = Pattern.compile(parse);
            Matcher m = p.matcher(timeStamp);
            Matcher m2 = p.matcher(file);
            if (m.find() && m2.find()){
                int hour = Integer.parseInt(m.group(2));
                int hour2 = Integer.parseInt(m2.group(2));

                if (hour2+1<hour) {
                    replace = true;
                } else {
                    lessons = file.replace(String.valueOf(m2.group(0)), "");
                    System.out.println("Found file");
                    downloaded=true;
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
                permissions.fileManagement.createFile(context, gymID + nameID, lessons);
            } else {
                permissions.fileManagement.createFile(context, gymID + nameID, new schedule.Weekday().Today()+lessons);
            }
        }

        modules = lessons.split("£"); //creates an array of all the modules
        System.out.println(modules[6]);
        h = 1;
        v = 1;

        for (int i=0;i<modules.length;i++) { //loops through all the modules downloaded
            String team = parseLesson.getTeam(modules[i]); //parses for teams
            String time = parseLesson.getTime(modules[i]); //parses for time
            if (time != null && team != null) {
                h++;
                Pattern noteRegex = Pattern.compile(".*?" + todayDate + ".*?");
                Matcher noteMatcher = noteRegex.matcher(time);
                boolean found = noteMatcher.find();
                Pattern noteRegex2 = Pattern.compile(".*?Alle.*?");
                Matcher noteMatcher2 = noteRegex2.matcher(team);
                boolean found2 = noteMatcher2.find();
                if (found && !found2) {
                    v++;
                }
            }
        }



        Object[] viewsV = new Object[v];
        Object[] viewsH = new Object[h];

        viewsV[0] = new schedule.Display().DayAndDate(todayDate, todayDay, context);

        q=1;
        for (int i=0;i<modules.length;i++) { //loops through all the modules downloaded
            String additionalContent = parseLesson.getAdditionalContent(modules[i]); //parses for additionalContent
            String room = parseLesson.getRoom(modules[i]); //parses for rooms
            String teacher = parseLesson.getTeacher(modules[i]); //parses for teachers
            String time = parseLesson.getTime(modules[i]); //parses for time
            String team = parseLesson.getTeam(modules[i]); //parses for teams
            String note = parseLesson.getNote(modules[i]); //parses for notes
            String homework = parseLesson.getHomework(modules[i]); //parses for homework
            String title = parseLesson.getTitle(modules[i]); //parses for title
            if (time != null) { //we can´t display the module on the schedule if we don´t know when it is
                //creates an array of the lessons and sepperates them with "££".
                lessons = time + "---" + team + "---" + teacher + "---" + room + "---" + note + "---" + additionalContent + "---" + homework + "---" + title;
                if (display == 0 && team != null) {
                    Pattern noteRegex = Pattern.compile(".*?" + todayDate + ".*?");
                    Matcher noteMatcher = noteRegex.matcher(time);
                    boolean found = noteMatcher.find();
                    Pattern noteRegex2 = Pattern.compile(".*?Alle.*?");
                    Matcher noteMatcher2 = noteRegex2.matcher(team);
                    boolean found2 = noteMatcher2.find();
                    if (found && !found2) {
                        viewsV[q] = new schedule.Display().vertical(lessons, context, mainLinearLayout);
                        q++;
                    }
                } else if (display == 1 && team != null) {
                    viewsH[q] = new schedule.Display().horizontal(lessons, context);
                    q++;
                }
            }
        }
        if (display == 0) {
            return viewsV;
        }else return viewsH;
    }
    protected void onPostExecute(Object[] result) {//recieves the result of the AsyncTask
        delegate.processViews(result);
    }
}