package herencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainClass {

	public static void main(String[] args) {
		/*EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaDS");
		EntityManager em = (EntityManager) emf.createEntityManager();
		em.getTransaction().begin();
		
		Funcionario f1 = new Funcionario(2345, "Pedro", 5000);
		Funcionario f2 = new Funcionario(6789, "Marcos", 5700);
		
		Cliente c1 = new Cliente(1928, "Felipe", "Miami");
		Cliente c2 = new Cliente(3746, "Maria", "Sto. de Chile");
		
		em.persist(f1);
		em.persist(f2);
		em.persist(c1);
		em.persist(c2);
		em.getTransaction().commit();
		em.close();*/
		
		// Single crea una tabla con algunos valores NULL
		// Joined crea tablas separadas y las une con una fk <-- LA MEJOR
		// Multiple crea tablas que hereden de la clase padre con atributos de la clase padre
		
	}

}
