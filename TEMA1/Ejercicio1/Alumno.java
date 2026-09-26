

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





