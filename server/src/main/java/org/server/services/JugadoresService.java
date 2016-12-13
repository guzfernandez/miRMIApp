package org.server.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.common.Jugador;

public class JugadoresService {

	private static final JugadoresService instance = new JugadoresService();
	private EntityManager em;
	
	public static JugadoresService getInstance() {
		return instance;
	}

	private JugadoresService() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaDS");
		em = (EntityManager) emf.createEntityManager();
	}

	public Jugador login(String username, String password){
		Jugador jugador = null;
		
		Query q = em.createNativeQuery("SELECT * FROM jugadores WHERE nombre = ?1 AND password = ?2", Jugador.class);
		q.setParameter(1, username);
		q.setParameter(2, password);
		
		try {
			jugador = (Jugador)q.getSingleResult();	
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return jugador;
	}
}
