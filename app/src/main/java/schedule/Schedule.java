package schedule;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import downloadLectio.AsyncResponse;
import downloadLectio.GetSchedule;
import downloadLectio.parseLesson;

public class Schedule extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate;
    public String[] modules;
    public String schedule;
    String lesson;

    @Override
    public String doInBackground(String... Strings) {
        System.out.println(schedule);
        modules = Strings[0].split("£");
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
            lesson = lesson+"££"+time+"---"+team+"---"+room+"---"+teacher+"---"+note+"---"+additionalContent+"---"+homework+"---"+title;
        }
    return lesson;
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        delegate.processFinish(result);
    }
}