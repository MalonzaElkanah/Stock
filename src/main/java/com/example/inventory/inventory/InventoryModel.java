package com.example.inventory.inventory;

import com.example.inventory.utils.ModelUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InventoryModel {
	SessionFactory sessionFactory;

	public InventoryModel() {
		this.sessionFactory = ModelUtil.sessionFactory();
		// init();
	}

	public void init() {
		// initialize inventory model
	}

	public List<Inventory> findAll() {
		return sessionFactory.fromTransaction(
			session -> session.createSelectionQuery("from Inventory",
            	Inventory.class).getResultList());
	}

	public Inventory findById(Integer id) {
		return sessionFactory.fromTransaction(
			session -> session.find(Inventory.class, id));
	}

	public void create(Inventory inventory) {
		sessionFactory.inTransaction(session -> {
			session.persist(inventory);
		});
	}

	public void update(Inventory inventory) {
		sessionFactory.inTransaction(
			session -> {
				session.merge(inventory);
			});
	}

	public void delete(Inventory inventory) {
		sessionFactory.inTransaction(session -> session.remove(inventory));
	}
}