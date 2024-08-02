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

class RoleModel {
	SessionFactory sessionFactory;

	public RoleModel() {
		this.sessionFactory = ModelUtil.sessionFactory();
		init();
	}

	public void init() {
		sessionFactory.inTransaction(session -> {   
		    session.persist(new Role(null, "ADMIN", LocalDateTime.now(), LocalDateTime.now()));   
		    session.persist(new Role(null, "STORE", LocalDateTime.now(), LocalDateTime.now()));
		});

	}

	public List<Role> findAll() {
		return sessionFactory.fromTransaction(
			session -> session.createSelectionQuery("from Role",
            	Role.class).getResultList());
	}

	public Role findById(Integer id) {
		return sessionFactory.fromTransaction(
			session -> session.find(Role.class, id));
	}
	
}


