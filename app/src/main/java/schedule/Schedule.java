package schedule;

import android.os.AsyncTask; //limits the consumption on the UI thread

import downloadLectio.AsyncResponse; //used to get the product of the Asynctask
import downloadLectio.parseLesson;   //used to parse the data from the schedule to qualify what kind of data it is

public class Schedule extends AsyncTask<String, Void, String> { //works as a parsing terminal
    public AsyncResponse delegate = null; //initialises Asyncresponse delegate which should be set to context before doInBackground executes
    public String[] modules; //a String array of all the modules
    String lessons; //

    @Override
    public String doInBackground(String... Strings) { //gets all the strings send to it from getSchedule
        modules = Strings[0].split("£"); //creates an array of all the modules
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
                lessons = lessons+"££"+time+"---"+team+"---"+room+"---"+teacher+"---"+note+"---"+additionalContent+"---"+homework+"---"+title;
            }
        }
    return lessons; //returns the complete and parsed schedule
    }

    @Override
    protected void onPostExecute(String result) {//recieves the result of the AsyncTask
        //the result is passed on to the processFinish in the desired activity through the delegates context
        delegate.processFinish(result);
    }
}