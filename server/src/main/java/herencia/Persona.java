package herencia;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="type")

//@Inheritance(strategy = InheritanceType.JOINED)

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Persona implements Serializable{
	
	@Id
	//@GeneratedValue( strategy = GenerationType.AUTO )
	private int ci;
	private String nombre;
	
	public Persona() {
		super();
	}

	public Persona(int ci, String nombre) {
		super();
		this.ci = ci;
		this.nombre = nombre;
	}

	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
