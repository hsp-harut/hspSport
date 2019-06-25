package am.chronograph.testing_hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * Testing Cache Levels.... first level and second level 
 * @author lusine
 *
 */
public class testingCacheLevel {
	
	public static void main(String[] args) {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query1 = session.createQuery("from UserDetailsTest user where user.Id = 1");
		query1.setCacheable(true);
		List users1 = query1.list();
		
		
		/*UserDetailsTest user1 = (UserDetailsTest) session.get(UserDetailsTest.class, 2);
		user1.setName("Update .... ");
		
		UserDetailsTest user2 = session.get(UserDetailsTest.class, 2);*/
		
		session.getTransaction().commit();
		session.close();
		
		
		
		
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		
		Query query2 = session2.createQuery("from UserDetailsTest user where user.Id = 1");
		List users2 = (List) query2.list();
		
		//use first cache level
		/*UserDetailsTest user3 = session.get(UserDetailsTest.class, 1);*/
		
		session2.getTransaction().commit();
		session2.close();
		
		
		
		
		
		
		
		
		
		
		
	}
	
	

}
