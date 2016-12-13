package org.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.common.Server;

public class App 
{
    public static void main( String[] args )
    {
    	try {
			System.setProperty("java.security.policy", "file:///Users/Guz/CEI/DDA/java.policy");
			LocateRegistry.createRegistry(1099);
			ServerImpl obj = new ServerImpl();
			Server stub = (Server) UnicastRemoteObject.exportObject(obj, 0);
			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.bind("server", stub);

			System.out.println("Server ready");
			
			/*EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaDS");
			EntityManager em = (EntityManager) emf.createEntityManager();

			Query q = em.createNativeQuery("SELECT * FROM jugadores WHERE nombre = ?1", Jugador.class);
			q.setParameter(1, "guz3");
			Jugador results = null;
			results = (Jugador)q.getSingleResult();
			
			if(results == null){
				System.out.println("Result == null");
			}
			else{
				System.out.println(results.getNombre());	
			}*/
			
			
			
			/*em.getTransaction().begin();
			Jugador j1 = new Jugador("pepe", "pepe", 3, 4);
			Jugador j2 = new Jugador("tomi", "tomi", 3, 3);
			Jugador j3 = new Jugador("guz", "guz", 9, 2);
			Jugador j4 = new Jugador("rodri", "rodri", 2, 5);
			
			em.persist(j1);
			em.persist(j2);
			em.persist(j3);
			em.persist(j4);
			
			em.getTransaction().commit();*/
			
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
    }
}
