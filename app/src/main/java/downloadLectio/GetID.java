package downloadLectio;

public class GetID {

	public static String ID() {
		//String Gym = new String (GetGyms.GymID());
		String Student = new String(GetStudents.StudentID());
		String ID = "523-"+Student;
		return ID;	
	}
	public static void main(String[] args) {
	}
}