package am.chronograph.testing_hibernate;

import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.itextpdf.text.List;

public class HibernateTest {
	public static void main(String[] args) {

		UserDetails user = new UserDetails();
		
		Address homeAddress = new Address();
		  homeAddress.setCity("Home City");
		  homeAddress.setStreet("Home Street");
		
		Address officeAddress = new Address();
		  officeAddress.setCity("Office City");
	      officeAddress.setStreet("Office Street");
		
		// user.setId(8);  when using @GenerateValue you can not pass this property
		user.setFirst_name("firstName");
		user.setLast_name("LastName");
		user.setSport_id(8);
		
		user.setHomeAddress(homeAddress);
		user.setOfficeAddress(officeAddress);
		
		// Have any list for many addresses and add collection new items.
		user.getListOfAddresses().add(homeAddress);
		user.getListOfAddresses().add(officeAddress);
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		session.close();
		
		// open second session
		user = null;
		session = sessionFactory.openSession();
		user = (UserDetails) session.get(UserDetails.class, 1);
		
				
		
		
		//Testing UserDetailsTest claass.....................................
		UserDetailsTest userTest = new UserDetailsTest();
		userTest.setName("First Name");
		
		//create new object for collection
		Vehicle vehicle1 = new Vehicle();	
		vehicle1.setVehicleName("BMW");
		
		//create new object for collection
		Vehicle vehicle2 = new Vehicle();	
		vehicle1.setVehicleName("Jeep");
		
		
		/*
		 *  Relationship ManyToMany
		 * 
		 * //add new element in user collection userTest.getVehicle().add(vehicle1);
		 * userTest.getVehicle().add(vehicle2);
		 */
			
		
		//Testing inheritance Vehicle class..... 
		//Create a Vehicle object...
		Vehicle vehicle = new Vehicle();	
		vehicle.setVehicleName("Jeep");
		
		//Create a TwoWheeler object...
		TwoWheeler bike = new TwoWheeler();
		bike.setVehicleName("bike....");
		bike.setSteeringHnadle("Bike Steering Handle");
		

		//Create a FourWheeler object...
		FourWheeler car = new FourWheeler();
		car.setVehicleName("Porshe....");
	    car.setSteeringWheel("Porshe steering wheel...");
		
		
		
       Session session_sec = sessionFactory.openSession();
       session.beginTransaction();
       
       
		/*
		 * //CRUD .... create new users 
		 * for (int i = 0; i < 5; i++) { UserDetailsTest
		 * newUser = new UserDetailsTest(); 
		 * newUser.setName("User" + i);
		 * session.save(user); }
		 * 
		 * //Read... get second element in data base. 
		 * 
		 * UserDetailsTest secondUser = (UserDetailsTest)session.get(UserDetailsTest.class, 4);
		 * System.out.println("second user " + secondUser.getName());
		 * 
		 * //delete second user...  
		 * session.delete(secondUser);
		 * 
		 * //update second user... secondUser.setName("Second");
		 * session.update(secondUser);
		 */
		
       
       /*
		 * //Use Hibernate query language (HQL)
		 *  Query query = session.createQuery("from UserDetailsTest"); 
		 *  List users = (List)query.list(); 
		 *  // System.out.println("Users list size:" + users.size());
		 * 
		 */  
       
       
		/*
		 * Criteria criteria = session.createCriteria(UserDetailsTest.class);
		 * criteria.add(Restrictions.or(Restrictions.between("userId", 0, 2),
		 * Restrictions.between("userId",4, 5)));
		 */
       
       
       
     //  session.save(userTest);
       
       
       session.save(vehicle);
       session.save(bike);
       session.save(car);
       
       session.getTransaction().commit();
       
	   	
		
		
		
	}
}
