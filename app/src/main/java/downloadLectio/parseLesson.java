package downloadLectio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;

public class parseLesson {

	public static String getAdditionalContent(String lessonData) {
		// Gets the additional content from a lesson.
		Pattern dateRegex = Pattern.compile("(?<=Øvrigt indhold:)(?s)(.*?)(?=$)");
		Matcher dateMatcher = dateRegex.matcher(lessonData);
		
		if(dateMatcher.find()){
			return dateMatcher.group(0);
		}
		return null;
	}
	
	public static String getDate(String lessonData) {
		// Gets the date from the data it's fed, only matches dates with Lectio or Lectio-like date syntax.
		Pattern dateRegex = Pattern.compile("([1-9]|1[0-9]|2[0-9]|3[0-1])/([1-9]|1[0-2])-[0-9][0-9][0-9][0-9]");
		Matcher dateMatcher = dateRegex.matcher(lessonData);
		
		if(dateMatcher.find()){
			return dateMatcher.group(0);
		}
		return null;
	}
	
	public static String getHomework(String lessonData) {
		// Gets the homework section of a lesson from Lectio.
		Pattern homeworkRegex = Pattern.compile("(?<=Lektier:)(?s)(.*?)(?=Note:|ﾃ�vrigt indhold:|$)");
		Matcher homeworkMatcher = homeworkRegex.matcher(lessonData);
		
		if(homeworkMatcher.find()){
			return homeworkMatcher.group(0);
		}
		return null;
	}
	
	public static String getNote(String lessonData) {
		// Gets the note section of a lesson from Lectio.
		Pattern noteRegex = Pattern.compile("(?<=Note:)(?s)(.*?)(?=Øvrigt indhold:|$)");
		Matcher noteMatcher = noteRegex.matcher(lessonData);
		
		if(noteMatcher.find()){
			return noteMatcher.group(0);
		}
		return null;
	}
	
	public static String getRoom(String lessonData) {
		// Gets the room of a lesson from Lectio.
		Pattern roomRegex = Pattern.compile("(?<=Lokale: |Lokaler: )(?s)(.*?)(?=§-§|$)");
		Matcher roomMatcher = roomRegex.matcher(lessonData);
		
		if(roomMatcher.find()){
			return roomMatcher.group(0);
		}
		return null;
	}
	
	public static String getTeacher(String lessonData) {
		// Gets the teacher of a lesson from Lectio.
		Pattern teacherRegex = Pattern.compile("(?<=Lærer: |Lærere: )(?s)(.*?)(?=§-§)");
		Matcher teacherMatcher = teacherRegex.matcher(lessonData);
        Pattern teacherRegex2 = Pattern.compile("(.*?)(\\s.*=?)(?=\\()(.*$)");

		if(teacherMatcher.find()) {
            if (teacherMatcher.group(0).contains(",")) {
                String teacher = null;
                String[] teachers = teacherMatcher.group(0).split(",");
                for (int i=0;i<teachers.length;i++) {
                    Matcher teacherMatcher2 = teacherRegex2.matcher(teachers[i]);
                    if (teacherMatcher2.find()) {
                        teacher = teacher+ "," +teacherMatcher2.group(1) + " " + teacherMatcher2.group(3);
                    } else {
						return teacherMatcher.group(0);
					}
                }
				teacher = teacher.replace("null,","");
                return teacher;
                } else {
                Matcher teacherMatcher3 = teacherRegex2.matcher(teacherMatcher.group(0));
                if (teacherMatcher3.find()) {
                    return teacherMatcher3.group(1) + " " + teacherMatcher3.group(3);
				} else {
					return teacherMatcher3.group(0);
				}
            }
        }
		return null;
	}

	public static String getTeam(String lessonData) {
		// Gets the teams participating in a lesson from Lectio.
		Pattern teamRegex = Pattern.compile("(?<=Hold: )(?s)(.*?)(?=§-§)"); // Won't separate the teams if there are more than one, but I won't waste devtime unless it becomes a problem.
		Matcher teamMatcher = teamRegex.matcher(lessonData);
		
		if(teamMatcher.find()){
			return teamMatcher.group(0);
		}
		return null;
	}

	public static String getTime(String lessonData) {
		// Gets the time interval where a lesson is happening in Lectio.
		Pattern timeRegex = Pattern.compile("(([1-9]|1[0-9]|2[0-9]|3[0-1])/([1-9]|1[0-2])-[0-9][0-9][0-9][0-9] [0-9][0-9]:[0-9][0-9] til [0-9][0-9]:[0-9][0-9])|(([1-9]|1[0-9]|2[0-9]|3[0-1])/([1-9]|1[0-2])-[0-9][0-9][0-9][0-9] [0-9][0-9]:[0-9][0-9] til ([1-9]|1[0-9]|2[0-9]|3[0-1])/([1-9]|1[0-2])-[0-9][0-9][0-9][0-9] [0-9][0-9]:[0-9][0-9])"); 
		Matcher timeMatcher = timeRegex.matcher(lessonData);
		
		if(timeMatcher.find()){
			return timeMatcher.group(0);
		}
		return null;
	}

	public static String getTitle(String lessonData) {
		// Gets the title of a lesson in Lectio.
		Pattern titleRegex = Pattern.compile("(?<=Ændret!|Aflyst!|^)(.*?)(?=(§-§[1-9]|1[0-9]|2[0-9]|3[0-1])/([1-9]|1[0-2])-[0-9][0-9][0-9][0-9])"); 
		Matcher titleMatcher = titleRegex.matcher(lessonData);
		
		if(titleMatcher.find()){
			return titleMatcher.group(0);
		}
		return null;
	}

	public static long runtime(String lessonData) {
		// Gets the runtime of a module

		Date lessonStart = null;
		Date lessonEnd   = null;

		String interval = getTime(lessonData);
		interval = interval.substring(interval.length() - 15); //remove 15 because that's how long the hh:mm part is
		String[] splitInterval = interval.split(" til ");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		try {
			lessonStart = timeFormat.parse(splitInterval[0]);
			lessonEnd   = timeFormat.parse(splitInterval[1]);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (lessonEnd.getTime() / 60000) - (lessonStart.getTime() / 60000);
	}

	public static Date lessonStart(String lessonData) {
		// Gets the starting time of the module in minutes

		Date lessonStart = null;

		String interval = getTime(lessonData);
		interval = interval.substring(interval.length() - 15); //remove 15 because that's how long the hh:mm part is
		String[] splitInterval = interval.split(" til ");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		try {
			lessonStart = timeFormat.parse(splitInterval[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return lessonStart;
	}

	public static Date lessonEnd(String lessonData) {
		// Gets the starting time of the module in minutes

		Date lessonEnd = null;

		String interval = getTime(lessonData);
		interval = interval.substring(interval.length() - 15); //remove 15 because that's how long the hh:mm part is
		String[] splitInterval = interval.split(" til ");

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		try {
			lessonEnd = timeFormat.parse(splitInterval[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return lessonEnd;
	}
}

