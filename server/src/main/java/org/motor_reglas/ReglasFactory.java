package org.motor_reglas;

public class ReglasFactory {
	
	public static Regla crear() {
		Regla reglaB = new ReglaB();
		Regla reglaA = new ReglaA(reglaB);
		
		return reglaA;
	}
	
	public static void main(String[] args) {
		Regla r = ReglasFactory.crear();
		OpcionesJugador opcionesParaJugador = new OpcionesJugador();
		
		Opcion h = Opcion.A;
		r.accion(opcionesParaJugador, h);
		
		if(h == Opcion.A) {
			
		}
	}
	
	enum Opcion {
		A(1),B(2),C(3);
		private int value;
		private Opcion(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}	
	}
}
