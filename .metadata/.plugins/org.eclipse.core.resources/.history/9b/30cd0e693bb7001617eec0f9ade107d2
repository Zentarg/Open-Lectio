import java.util.regex.*;

public class parseLesson {
	
	public static String getDate(String lessonData) {
		// Gets the date from the data it's fed, only matches dates with Lectio or Lectio-like date syntax.
		Pattern dateRegex = Pattern.compile("([1-9]|1[0-9]|2[0-9]|3[0-1])/([1-9]|1[0-2])-[0-9][0-9][0-9][0-9]");
		Matcher dateMatcher = dateRegex.matcher(lessonData);
		
		return dateMatcher.group();
	}
	
	public static String getHomework(String lessonData) {
		// Gets the homework section of a lesson from Lectio.
		Pattern homeworkRegex = Pattern.compile("(?<=Lektier:)(?s)(.*?)(?=Note:|Øvrigt indhold:|$)");
		Matcher homeworkMatcher = homeworkRegex.matcher(lessonData);
		
		return homeworkMatcher.group();
	}
	
	public static String getNote(String lessonData) {
		// Gets the note section of a lesson from Lectio.
		Pattern noteRegex = Pattern.compile("(?<=Note:)(?s)(.*?)(?=Øvrigt indhold:|$)");
		Matcher noteMatcher = noteRegex.matcher(lessonData);
		
		return noteMatcher.group();
	}
	
	public static String getRoom(String lessonData) {
		// Gets the room of a lesson from Lectio.
		Pattern roomRegex = Pattern.compile("(?<=Lokale: )(?s)(.*?)(?=\n|,|=)");
		Matcher roomMatcher = roomRegex.matcher(lessonData);
		
		return roomMatcher.group();
	}
	
	public static String getTeacher(String lessonData) {
		// Gets the teacher of a lesson from Lectio.
		Pattern teacherRegex = Pattern.compile("(?<=Lærer: )(?s)(.*?)(?=\n)");
		Matcher teacherMatcher = teacherRegex.matcher(lessonData);
		
		return teacherMatcher.group();
	}

	public static String getTeam(String lessonData) {
		// Gets the teams participating in a lesson from Lectio.
		Pattern teamRegex = Pattern.compile("(?<=Hold: )(?s)(.*?)(?=\n)"); // Won't separate the teams if there are more than one, but I won't waste devtime unless it becomes a problem.
		Matcher teamMatcher = teamRegex.matcher(lessonData);
		
		return teamMatcher.group();
	}

	public static String getTime(String lessonData) {
		// Gets the time interval where a lesson is happening in Lectio.
		Pattern timeRegex = Pattern.compile("[0-9][0-9]:[0-9][0-9] til [0-9][0-9]:[0-9][0-9]"); // Won't separate the teams if there are more than one, but I won't waste devtime unless it becomes a problem.
		Matcher timeMatcher = timeRegex.matcher(lessonData);
		
		return timeMatcher.group();
	}

	public static String getTitle(String lessonData) {
		// Gets the title of a lesson in Lectio.
		Pattern titleRegex = Pattern.compile("(?<=\n)(.*?)(?=(\n[1-9]|1[0-9]|2[0-9]|3[0-1])/([1-9]|1[0-2])-[0-9][0-9][0-9][0-9])"); 
		Matcher titleMatcher = titleRegex.matcher(lessonData);
		
		return titleMatcher.group();
	}
}

