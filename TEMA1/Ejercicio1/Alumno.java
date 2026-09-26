import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class Alumno {

	private String nombre;
	private int nota;

	public Alumno(String nombre, int nota) {
		this.nombre = nombre;
		this.nota = nota;
	}

	public String getNombre() {
		return nombre;
	}
	
	public int getNota() {
		return nota;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNota(int nota) {
		if (nota >= 0 && nota <= 10) {
        this.nota = nota;}

	}

	public boolean esAprobado() {
		return nota >= 5;
	}
}

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



