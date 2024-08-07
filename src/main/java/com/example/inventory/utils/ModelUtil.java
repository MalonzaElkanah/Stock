package com.example.inventory.utils;

import com.example.inventory.product.Product;
import com.example.inventory.inventory.Checkin;
import com.example.inventory.inventory.Checkout;
import com.example.inventory.discrepancy.Discrepancy;
import com.example.inventory.users.User;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class ModelUtil {

    private static final SessionFactory sessionFactory = getSessionFactory();

    private static SessionFactory getSessionFactory() {
        // A SessionFactory is set up once for an application!
        // final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        //    .build();

        try {
            // sessionFactory = new MetadataSources(registry)
            //    .addAnnotatedClass(User.class)
            //    .addAnnotatedClass(Product.class)
            //    .addAnnotatedClass(Inventory.class)
            //    .buildMetadata()
            //    .buildSessionFactory();

            SessionFactory factory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Checkout.class)
                .addAnnotatedClass(Checkin.class)
                .addAnnotatedClass(Discrepancy.class)
                .buildSessionFactory();

            return factory;
        } catch (Exception e) {
            System.out.println(e);
            /// StandardServiceRegistryBuilder.destroy(registry);

            return null;
        }

    }

	public static SessionFactory sessionFactory() {
        return sessionFactory;
	}
} 
