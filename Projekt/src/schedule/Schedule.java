package schedule;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import downloadLectio.GetSchedule;
import downloadLectio.parseLesson;

public class Schedule {
	
	public static String[] module(int module) throws MalformedURLException, IOException {
		List<String> modules = GetSchedule.schedule(module);
		String[] lesson = new String[modules.size()];
		for (int i=0;i<modules.size();i++) {
			String lessonData = modules.get(i);
			String additionalContent = parseLesson.getAdditionalContent(lessonData);
			String room = parseLesson.getRoom(lessonData);
			String teacher = parseLesson.getTeacher(lessonData);
			String time = parseLesson.getTime(lessonData);
			String team = parseLesson.getTeam(lessonData);
			String note = parseLesson.getNote(lessonData);
			String homework = parseLesson.getHomework(lessonData);
			String title = parseLesson.getTitle(lessonData);
			lesson[i] = time+"---"+team+"---"+room+"---"+teacher+"---"+note+"---"+additionalContent+"---"+homework+"---"+title;
		}
		return lesson;
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println(module(0)[0]);
		
	}

}
