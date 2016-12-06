package org.motor_reglas;

import org.motor_reglas.ReglasFactory.Opcion;

public interface Regla {
	public void accion(OpcionesJugador opcionesParaJugador, Opcion jugadaDelCliente);
}
