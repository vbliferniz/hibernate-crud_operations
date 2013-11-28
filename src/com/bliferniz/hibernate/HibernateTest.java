/**
 * 
 */
package com.bliferniz.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.bliferniz.dto.UserDetails;

public class HibernateTest {

	private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	public static void main(String[] args) {


		Session session = factory.openSession();
		session.beginTransaction();
		
		createUsers(session);
		
		//Read
		UserDetails user = readUserDetails(session, 6);
		
	
		System.out.println(user.getUserId() + " | " + user.getUserName());

		user.setUserName("User name updated");
		
		session.close();
		session = factory.openSession();
		session.beginTransaction();
		
		updateUserDetails(session, user);
		session.close();
		
		session = factory.openSession();
		session.beginTransaction();
		deleteUserDetails(session, user);
	
		session.close();
	}
	
	private static void createUsers(Session session){
		for(int i = 0; i < 10; i++){
			UserDetails user = new UserDetails();
			user.setUserName("User" + (i+1));
			session.save(user);
		}
		session.getTransaction().commit();
	}
	
	private static UserDetails readUserDetails(Session session, int userId){
		return (UserDetails) session.get(UserDetails.class, userId);
	}
	
	private static void deleteUserDetails(Session session, UserDetails user){
		session.delete(user);
		session.getTransaction().commit();
	}
	
	private static void updateUserDetails(Session session, UserDetails user){
		session.update(user);
		session.getTransaction().commit();
	}
}
