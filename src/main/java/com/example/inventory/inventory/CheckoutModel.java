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

public class CheckoutModel {
	SessionFactory sessionFactory;

	public CheckoutModel() {
		this.sessionFactory = ModelUtil.sessionFactory();
		// init();
	}

	public void init() {
		// initialize inventory model
	}

	public List<Checkout> findAll() {
		return sessionFactory.fromTransaction(
			session -> session.createSelectionQuery("from Checkout",
            	Checkout.class).getResultList());
	}

	public Checkout findById(Integer id) {
		return sessionFactory.fromTransaction(
			session -> session.find(Checkout.class, id));
	}

	public void create(Checkout item) {
		sessionFactory.inTransaction(session -> {
			session.persist(item);
		});
	}

	public void update(Checkout item) {
		sessionFactory.inTransaction(
			session -> {
				session.merge(item);
			});
	}

	public void delete(Checkout item) {
		sessionFactory.inTransaction(session -> session.remove(item));
	}

	public List<Checkout> findByProduct(Product product) {
		return sessionFactory.fromTransaction(
			session -> session.createQuery("select c " +
				"from Checkout c " +
				"where c.product = :product", Checkout.class)
			.setParameter("product", product)
			.getResultList());
	}
}
