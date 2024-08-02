package com.example.inventory.reports;

import com.example.inventory.inventory.Inventory;
import com.example.inventory.inventory.Inventories;
import com.example.inventory.inventory.CheckinModel;
import com.example.inventory.inventory.CheckoutModel;
import com.example.inventory.inventory.Checkout;
import com.example.inventory.product.Product;
import com.example.inventory.product.ProductModel;
import com.example.inventory.discrepancy.DiscrepancyModel;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class ReportController {
	CheckinModel checkinModel;
	CheckoutModel checkoutModel;
	ProductModel productModel;
	DiscrepancyModel discrepancyModel;

	List<Product> products;
	Inventories inventories;

	public ReportController() {
		this.checkinModel = new CheckinModel();
		this.checkoutModel = new CheckoutModel();
		this.productModel = new ProductModel();
		this.discrepancyModel = new DiscrepancyModel();
		
		this.products = productModel.findAll();

		List<Inventory> inventoryList = new ArrayList();
		for (Product product: this.products) {
			inventoryList.add(new Inventory(product));
		}

		this.inventories = new Inventories(inventoryList);
	}

	public ObservableList<PieChart.Data> getSalePieChartDataList() {
		ObservableList<PieChart.Data> chartData = FXCollections.<PieChart.Data>observableArrayList();

		for (Inventory inventory: inventories.getInventoryList()) {
			double pieValue = 0.0;

			try {
				pieValue = Math.rint((inventory.getSoldSellingPrice() / inventories.getTotalSoldSellingPrice()) * 100);
			} catch (ArithmeticException e) { }

			String name = inventory.getProduct().getName() + " (" + pieValue + "%)";

			chartData.add(new PieChart.Data(name, pieValue));			
		}
		
		return chartData;
	}

	public ObservableList<PieChart.Data> getSaleCountPieChartDataList() {
		ObservableList<PieChart.Data> chartData = FXCollections.<PieChart.Data>observableArrayList();

		for (Inventory inventory: inventories.getInventoryList()) {
			int pieValue = 0;

			try {
				pieValue = (inventory.getSoldItems() * 100) / inventories.getTotalSoldItems();
			} catch (ArithmeticException e) { }

			String name = inventory.getProduct().getName() + " (" + pieValue + "%)";

			chartData.add(new PieChart.Data(name, pieValue));			
		}
		
		return chartData;
	}

	public List<XYChart.Series<String, Number>> getSaleCountChartDataList(String frequency) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

		if (frequency.equals("DAILY")) {
			formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		} else if (frequency.equals("MONTHLY")) {
			formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		} else if (frequency.equals("YEARLY")) {
			formatter = DateTimeFormatter.ofPattern("yyyy");
		} else if (frequency.equals("WEEKLY")) {
			formatter = DateTimeFormatter.ofPattern("w ''yy");
		}

		List<XYChart.Series<String, Number>> dataList = new ArrayList();

		for (Inventory inventory: inventories.getInventoryList()) {
			XYChart.Series<String, Number> series = new XYChart.Series();
			series.setName(inventory.getProduct().getName());

			ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.<XYChart.Data<String, Number>>observableArrayList();
			Double value = 0.0;
			String name = "";

			for (Checkout sale: inventory.getProductCheckouts()) {
				if (name.equals(sale.getCreated().format(formatter))) {
					value += (sale.getUnitPrice() * sale.getQuantity());
					// chartData.remove(chartData.size() - 1);
					// chartData.add(new XYChart.Data<String, Number>(name, value));
				} else if (!name.isBlank()) {
					chartData.add(new XYChart.Data<String, Number>(name, value));

					value = (sale.getUnitPrice() * sale.getQuantity());
					name = sale.getCreated().format(formatter);				
				} else {
					value = (sale.getUnitPrice() * sale.getQuantity());
					name = sale.getCreated().format(formatter);	
				}			
			}

			if (!name.isBlank()) {
				chartData.add(new XYChart.Data<String, Number>(name, value));
			}
			
			series.getData().addAll(chartData);
			dataList.add(series);	
		}
		
		return dataList;
	}

}
