import java.util.ArrayList;

public class Students{
	private ArrayList<Student> students = new ArrayList<>();

	public void AddStudent(Student student) {
		students.add(student);
	}

	public Student getStudent(int index) {
		if (index >= 0 && index < students.size()) {
        return students.get(index);
		}

		return null;

	}

	public float notaMedia() {
		if (students.isEmpty()) {
        return 0;
		}

		float media = 0;

		for (Student student : students) {
			media += student.getNote();
		}

		return media / students.size();

		}

	}
