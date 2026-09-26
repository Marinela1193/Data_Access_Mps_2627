import java.util.ArrayList;

public class Alumnos{
	private ArrayList<Alumno> alumnos = new ArrayList<>();

	public void AgregarAlumno(Alumno alumno) {
		alumnos.add(alumno);
	}

	public Alumno getAlumno(int index) {
		if (index >= 0 && index < alumnos.size()) {
        return alumnos.get(index);
		}

		return null;

	}

	public float notaMedia() {
		if (alumnos.isEmpty()) {
        return 0;
		}

		float media = 0;

		for (Alumno alumno : alumnos) {
			media += alumno.getNota();
		}

		return media / alumnos.size();

		}

	}
