package org.motor_reglas;

import org.motor_reglas.ReglasFactory.Opcion;

public class ReglaA implements Regla{
	private Regla siguienteRegla;

	public ReglaA(Regla siguienteRegla) {
		this.siguienteRegla = siguienteRegla;
	}

	public void accion(OpcionesJugador opcionesParaJugador, Opcion jugadaDelCliente) {
		System.out.println("Soy regla A");
		if(jugadaDelCliente == Opcion.A) {
			opcionesParaJugador.getOpciones().add("a");
			System.out.println("Entro aqui");
		}
		if(siguienteRegla != null) {
			siguienteRegla.accion(opcionesParaJugador, jugadaDelCliente);
		}
		System.out.println("Terminaron las reglas");
	}
}
