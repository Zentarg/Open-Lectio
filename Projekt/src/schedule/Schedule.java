package schedule;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import downloadLectio.GetSchedule;
import downloadLectio.ParseLesson;

public class Schedule {
	
	public static String[] module(int module) throws MalformedURLException, IOException {
		List<String> modules = GetSchedule.schedule(module);
		String[] lesson = new String[modules.size()];
		for (int i=0;i<modules.size();i++) {
			String lessonData = modules.get(i);
			String additionalContent = ParseLesson.getAdditionalContent(lessonData);
			String date = ParseLesson.getDate(lessonData);
			String room = ParseLesson.getRoom(lessonData);
			String teacher = ParseLesson.getTeacher(lessonData);
			String time = ParseLesson.getTime(lessonData);
			String team = ParseLesson.getTeam(lessonData);
			String note = ParseLesson.getNote(lessonData);
			String homework = ParseLesson.getHomework(lessonData);
			String title = ParseLesson.getTitle(lessonData);
			lesson[i] = time+"---"+team+"---"+room+"---"+teacher+"---"+note+"---"+additionalContent+"---"+homework+"---"+title;
		}
		return lesson;
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println(module(0)[0]);
		
	}

}
