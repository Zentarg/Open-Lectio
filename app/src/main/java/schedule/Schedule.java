package schedule;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import downloadLectio.AsyncResponse;
import downloadLectio.GetSchedule;
import downloadLectio.parseLesson;

public class Schedule extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;
    public String[] modules;
    public String schedule;
    String[] lesson;
    String lessons;

    @Override
    public String doInBackground(String... Strings) {
        modules = Strings[0].split("£");
        lesson = new String[modules.length];
        for (int i=0;i<modules.length;i++) {
            String lessonData = modules[i];
            String additionalContent = parseLesson.getAdditionalContent(lessonData);
            String room = parseLesson.getRoom(lessonData);
            String teacher = parseLesson.getTeacher(lessonData);
            String time = parseLesson.getTime(lessonData);
            String team = parseLesson.getTeam(lessonData);
            String note = parseLesson.getNote(lessonData);
            String homework = parseLesson.getHomework(lessonData);
            String title = parseLesson.getTitle(lessonData);
            if (time != null) {
                lesson[i] = "££"+time+"---"+team+"---"+room+"---"+teacher+"---"+note+"---"+additionalContent+"---"+homework+"---"+title;
                lessons = lessons+lesson[i];
            }
        }
    return lessons;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}