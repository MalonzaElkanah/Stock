package com.example.inventory.inventory;

import com.example.inventory.product.Product;
import com.example.inventory.utils.ModelUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CheckinModel {
	SessionFactory sessionFactory;

	public CheckinModel() {
		this.sessionFactory = ModelUtil.sessionFactory();
		// init();
	}

	public void init() {
		// initialize inventory model
	}

	public List<Checkin> findAll() {
		return sessionFactory.fromTransaction(
			session -> session.createSelectionQuery("from Checkin",
            	Checkin.class).getResultList());
	}

	public Checkin findById(Integer id) {
		return sessionFactory.fromTransaction(
			session -> session.find(Checkin.class, id));
	}

	public void create(Checkin item) {
		sessionFactory.inTransaction(session -> {
			session.persist(item);
		});
	}

	public void update(Checkin item) {
		sessionFactory.inTransaction(
			session -> {
				session.merge(item);
			});
	}

	public void delete(Checkin item) {
		sessionFactory.inTransaction(session -> session.remove(item));
	}

	public List<Checkin> findByProduct(Product product) {
		return sessionFactory.fromTransaction(
			session -> session.createQuery("select c " +
				"from Checkin c " +
				"where c.product = :product", Checkin.class)
			.setParameter("product", product)
			.getResultList());
	}
}
