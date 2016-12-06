package herencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
//@DiscriminatorValue(value = "C")

//@PrimaryKeyJoinColumn(referencedColumnName="ci")
public class Cliente extends Persona{
	
	private String direccion;

	public Cliente(int ci, String nombre, String direccion) {
		super(ci, nombre);
		this.direccion = direccion;
	}

	public Cliente() {
		super();
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
}
