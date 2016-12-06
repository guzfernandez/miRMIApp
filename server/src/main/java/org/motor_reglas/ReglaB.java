package org.motor_reglas;

import org.motor_reglas.ReglasFactory.Opcion;

public class ReglaB implements Regla {
	public void accion(OpcionesJugador opcionesParaJugador, Opcion jugadaDelCliente) {
		System.out.println("soy la ultima regla con el value: " + jugadaDelCliente);
	}
}
