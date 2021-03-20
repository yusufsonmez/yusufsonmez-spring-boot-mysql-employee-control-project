package com.luv2code.springboot.thymeleafdemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.Review;
import com.luv2code.springboot.thymeleafdemo.entity.User;


@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public User findByUserName(String theUserName) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
		theQuery.setParameter("uName", theUserName);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public void save(User theUser) {
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// create the user ... finally LOL
		currentSession.saveOrUpdate(theUser);
	}	

	
	@Override
	public List<Review> getReviewById(long theId) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// now retrieve/read from database using username
		
		
		Query<Review> theQuery = currentSession.createQuery("from Review where user_id= :theId", Review.class);
		theQuery.setParameter("theId", theId);
		
	    List<Review> list = theQuery.getResultList();
		
		return list;

	}
	
	@Override
	public String createReview(Review theReview) {
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		
		org.springframework.security.core.userdetails.User theUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		String userName = theUser.getUsername();
		//custom username'i aldık. Yani hangi userla girilmisse onun username'i
		
		
		com.luv2code.springboot.thymeleafdemo.entity.User userForId = findByUserName(userName);
		
		//ilgili user'ın id'sini aldık. Sonra da o id'ye göre review'in user'ini set ediyoruz.
		
		
		theReview.setUser(userForId);
				
		
		currentSession.saveOrUpdate(theReview);		
		

		
		return "tamam";
	}

	@Override
	public List<Review> getReview() {

		// get the current hibernate session
				Session currentSession = entityManager.unwrap(Session.class);

				// now retrieve/read from database using username
				
				
				Query<Review> theQuery = currentSession.createQuery("from Review", Review.class);

				
			    List<Review> list = theQuery.getResultList();
				


		
		return list;
	}

}
