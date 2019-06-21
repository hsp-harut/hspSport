package am.chronograph.testing_hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
	public static void main(String[] args) {

		UserDetails user = new UserDetails();
		
		user.setId(8);
		user.setFirst_name("firstName");
		user.setLast_name("LastName");
		user.setSport_id(8);
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		/*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();*/
		
				
	}
}
