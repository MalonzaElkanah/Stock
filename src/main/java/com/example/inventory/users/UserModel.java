package com.example.inventory.users;

import com.example.inventory.utils.ModelUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserModel {
	SessionFactory sessionFactory;

	public UserModel() {
		this.sessionFactory = ModelUtil.sessionFactory();
		init();
	}

	public void init() {
		// sessionFactory.inTransaction(session -> {   
		//    session.persist(new User(null, "ADMIN", LocalDateTime.now(), LocalDateTime.now()));   
		//    session.persist(new User(null, "STORE", LocalDateTime.now(), LocalDateTime.now()));
		// });

	}

	public List<User> findAll() {
		return sessionFactory.fromTransaction(
			session -> session.createSelectionQuery("from User",
            	User.class).getResultList());
	}

	public User findById(Integer id) {
		return sessionFactory.fromTransaction(
			session -> session.find(User.class, id));
	}

	public Optional<User> findByUserName(String userName) {
		return sessionFactory.fromTransaction(session -> session
			.byNaturalId(User.class)
			.using("userName", userName)
			.loadOptional());
	}

	// public Optional<User> findByEmail(String email) {
	//	return sessionFactory.fromTransaction(session -> session
	//		.byNaturalId(User.class)
	//		.using("email", email)
	//		.loadOptional());
	// }

	public void create(User user) {
		sessionFactory.inTransaction(session -> session.persist(user));
	}

	public void update(User user) {
		sessionFactory.inTransaction(
			session -> { session.merge(user); });
	}

	public void delete(User user) {
		sessionFactory.inTransaction(session -> session.remove(user));
	}
	
}


