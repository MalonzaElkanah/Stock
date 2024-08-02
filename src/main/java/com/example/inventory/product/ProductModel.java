package com.example.inventory.product;

import com.example.inventory.utils.ModelUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductModel {
	SessionFactory sessionFactory;

	public ProductModel() {
		this.sessionFactory = ModelUtil.sessionFactory();
		// init();
	}

	public void init() {
		sessionFactory.inTransaction(session -> {   
		    System.out.println(session);

		    session.persist(new Product(null, "Vagabond",
		    	"A true warrior!", 260.0, 26));
		    session.flush();
		});

	}

	public List<Product> findAll() {
		return sessionFactory.fromTransaction(
			session -> session.createSelectionQuery("from Product",
            	Product.class).getResultList());
	}

	public Product findById(Integer id) {
		return sessionFactory.fromTransaction(
			session -> session.find(Product.class, id));
	}

	public void create(Product product) {
		sessionFactory.inTransaction(session -> {
			session.persist(product);
		});
	}

	public void update(Product product) {
		sessionFactory.inTransaction(
			session -> {
				// Product product = session.byId(Product.class, id);
				//product = (Product) session.merge(product);
				session.merge(product);
			});
	}

	public void delete(Product product) {
		sessionFactory.inTransaction(session -> session.remove(product));
	}
	
}


