package com.example.inventory.inventory;

import com.example.inventory.product.Product;
import com.example.inventory.product.ProductModel;
import com.example.inventory.discrepancy.DiscrepancyModel;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public class InventoryController {
	InventoryModel model;
	CheckinModel checkinModel;
	CheckoutModel checkoutModel;
	ProductModel productModel;
	DiscrepancyModel discrepancyModel;

	public InventoryController() {
		this.model = new InventoryModel();
		this.checkinModel = new CheckinModel();
		this.checkoutModel = new CheckoutModel();
		this.productModel = new ProductModel();
		this.discrepancyModel = new DiscrepancyModel();
	}

	public ObservableList<Inventory> getInventoryList() {
		ObservableList<Inventory> inventories = FXCollections.<Inventory>observableArrayList();
		List<Product> products = productModel.findAll();
		//List<Inventory> inventoryList = new ArrayList();

		for (Product product: products) {
			// Inventory inv = ;
			inventories.add(new Inventory(product));
		}

		// inventories.setAll(model.findAll());
		return inventories;
	}

	public Inventory getInventory(Product product) {
		return new Inventory(product);
	}

	public void checkinInventory(Checkin item) {
		// Checkin item = new Checkin(product, quantity, amount);
		checkinModel.create(item);
	}

	public void checkinInventory(Product product, Integer quantity, Double amount) {
		Checkin item = new Checkin(product, quantity, amount);
		checkinModel.create(item);
	}

	public ObservableList<Checkin> getCheckinList() {
		ObservableList<Checkin> items = FXCollections.<Checkin>observableArrayList();
		items.setAll(checkinModel.findAll());
		return items;
	}

	public void updateCheckin(Checkin item) {
		checkinModel.update(item);
	}

	public void deleteCheckin(Checkin item) {
		checkinModel.delete(item);
	}

	public void checkoutInventory(Checkout item) {
		// Checkout item = new Checkout(product, quantity);
		checkoutModel.create(item);
	}

	public void checkoutInventory(Product product, Integer quantity) {
		Checkout item = new Checkout(product, quantity);
		checkoutModel.create(item);
	}

	public ObservableList<Checkout> getCheckoutList() {
		ObservableList<Checkout> items = FXCollections.<Checkout>observableArrayList();
		items.setAll(checkoutModel.findAll());
		return items;
	}

	public void updateCheckout(Checkout item) {
		checkoutModel.update(item);
	}

	public void deleteCheckout(Checkout item) {
		checkoutModel.delete(item);
	}
}
