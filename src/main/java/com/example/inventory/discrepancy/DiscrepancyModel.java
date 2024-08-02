package com.example.inventory.discrepancy;

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

public class DiscrepancyModel {
	SessionFactory sessionFactory;

	public DiscrepancyModel() {
		this.sessionFactory = ModelUtil.sessionFactory();
		// init();
	}

	public void init() {
		// initialize inventory model
	}

	public List<Discrepancy> findAll() {
		return sessionFactory.fromTransaction(
			session -> session.createSelectionQuery("from Discrepancy",
            	Discrepancy.class).getResultList());
	}

	public Discrepancy findById(Integer id) {
		return sessionFactory.fromTransaction(
			session -> session.find(Discrepancy.class, id));
	}

	public void create(Discrepancy item) {
		sessionFactory.inTransaction(session -> {
			session.persist(item);
		});
	}

	public void update(Discrepancy item) {
		sessionFactory.inTransaction(
			session -> {
				session.merge(item);
			});
	}

	public void delete(Discrepancy item) {
		sessionFactory.inTransaction(session -> session.remove(item));
	}

	public List<Discrepancy> findByProduct(Product product) {
		return sessionFactory.fromTransaction(
			session -> session.createQuery("select d " +
				"from Discrepancy d " +
				"where d.product = :product", Discrepancy.class)
			.setParameter("product", product)
			.getResultList());
	}
}
