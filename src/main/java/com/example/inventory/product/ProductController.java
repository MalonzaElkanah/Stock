package com.example.inventory.product;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public class ProductController {
	ProductModel model;

	public ProductController() {
		this.model = new ProductModel();
	}

	public ObservableList<Product> getProductList() {
		ObservableList<Product> products = FXCollections.<Product>observableArrayList();
		products.setAll(model.findAll());
		return products;
	}

	public void createProduct(Product product) {
		model.create(product);
	}

	public void updateProduct(Product product) {
		// todo validate product
		model.update(product);
	}

	public void deleteProduct(Product product) {
		model.delete(product);
	}
}
