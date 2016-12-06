package herencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
//@DiscriminatorValue(value="F")

//@PrimaryKeyJoinColumn(referencedColumnName="ci")
public class Funcionario extends Persona{
	
	private int sueldo;

	public Funcionario() {
		super();
	}

	public Funcionario(int ci, String nombre, int sueldo) {
		super(ci, nombre);
		this.sueldo = sueldo;
	}

	public int getSueldo() {
		return sueldo;
	}

	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}
	
	
	
}
