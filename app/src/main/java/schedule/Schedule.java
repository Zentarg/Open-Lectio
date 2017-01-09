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
            todayWeek = Weekday.todayWeek(0,0);
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
            file = new permissions.fileManagement().getFile(context, gymID + nameID); //loads the file to a string from Storage with th GetFile method from fileManagement
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
                permissions.fileManagement.createFile(context, gymID + nameID, lessons);
            } else {
                permissions.fileManagement.createFile(context, gymID + nameID, new schedule.Weekday().Today() + lessons);
            }
        }

        modules = lessons.split("£"); //creates an array of all the modules

        h = 1;
        v = 1;

        for (int i = 0; i < modules.length; i++) { //loops through all the modules downloaded
            String team = parseLesson.getTeam(modules[i]); //parses for teams
            String time = parseLesson.getTime(modules[i]); //parses for time
            if (time != null && team != null) { //checks if the time and team is defined for the mdoule
                //// TODO: 07-01-2017 add support for student modules
                h++; //adds one to the horizontal view
                Pattern noteRegex = Pattern.compile(".*?" + todayDate + ".*?"); //checks if the module is on the day todayDate
                Matcher noteMatcher = noteRegex.matcher(time); //Matches
                boolean found = noteMatcher.find(); //sets if the matcher finds todaysDate on the module
                // TODO: 07-01-2017 add support for people to choose which teams they would like to not have displayed on the schedule
                // TODO: 07-01-2017 add a method to check if the module is obligational
                //for now we assume that if the module contains team "everyone" it is obligational and therefore not shown on schedule
                Pattern noteRegex2 = Pattern.compile(".*?Alle.*?"); //checks if the module if for everyone
                Matcher noteMatcher2 = noteRegex2.matcher(team); //matches
                boolean found2 = noteMatcher2.find(); //setes if the Matcher finds that the module is for everyone
                if (found && !found2) { //if it happens today and is not for everyone
                    v++; //add one module to the vertical view
                }
            }
        }


        Object[] viewsV = new Object[v]; //sets the length of a Object module array for vertical view
        Object[] viewsH = new Object[h]; //sets the length of a Object module array for horizontal view

        viewsV[0] = new schedule.Display().DayAndDate(todayDate, todayDay, context); //gets todays date and weekday and creates these displays to module 0 of the vertical schedule

        q = 1; //since we have information in module 0 we set the array to start at 1
        for (int i = 0; i < modules.length; i++) { //loops through all the modules downloaded
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
                if (display == 0 && team != null) { //checks if the display is vertical and if there is a team assigned to the module
                    //todo support student modules and not just team lessons
                    Pattern noteRegex = Pattern.compile(".*?" + todayDate + ".*?"); //compiles a pattern which searches for the date of the day.
                    Matcher noteMatcher = noteRegex.matcher(time); //matches all the possibilities to see if the module happens on the date
                    boolean found = noteMatcher.find(); //finds all matches
                    Pattern noteRegex2 = Pattern.compile(".*?Alle.*?"); //compiles a pattern which searches for modules involving all students and filter them off.
                    Matcher noteMatcher2 = noteRegex2.matcher(team); //matches all the possibilities to see if the module involves large teams
                    boolean found2 = noteMatcher2.find(); //finds all matches
                    if (found && !found2) { //checks if the module happens today and if it doesn´t involve large teams
                        viewsV[q] = new schedule.Display().vertical(lessons, context, mainLinearLayout); //calls display.vertical method to construct a textview[]
                        q++; //adds one to found modules for today
                    }
                } else if (display == 1 && team != null) { //not implemented yet
                    viewsH[q] = new schedule.Display().horizontal(lessons, context);
                    q++; //adds one to found modules for the week
                }
            }
        }
        if (display == 0) { //checks if the view is horizontal or vertical
            return viewsV; //returns viewsV if the phone is vertical
        } else return viewsH; //else it returns a horizontal view
    }

    protected void onPostExecute(Object[] result) {//recieves the result of the AsyncTask
        delegate.processViews(result); //sends the result to the processViews task in the context activity.
    }
}