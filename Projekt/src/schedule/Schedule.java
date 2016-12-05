package schedule;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import downloadLectio.GetSchedule;
import downloadLectio.parseLesson;

public class Schedule {
	
	public static String[] module(int module) throws MalformedURLException, IOException {
		List<String> modules = GetSchedule.schedule(module);
		String length = ""+modules.size();
		length = length.toString();
		String[] lesson = new String[modules.size()];
		lesson[0] = length;
		for (int i=1;i<modules.size();i++) {
			String lessonData = modules.get(i);
			String date = parseLesson.getDate(lessonData);
			String room = parseLesson.getRoom(lessonData);
			String teacher = parseLesson.getTeacher(lessonData);
			String time = parseLesson.getTime(lessonData);
			String team = parseLesson.getTeam(lessonData);
			lesson[i] = date+"---"+time+"---"+team+"---"+room+"---"+teacher;
		}
		return lesson;
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println(module(1));

	}

}
