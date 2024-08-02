package com.example.inventory.discrepancy;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public class DiscrepancyController {
	DiscrepancyModel model;

	public DiscrepancyController() {
		this.model = new DiscrepancyModel();
	}

	public ObservableList<Discrepancy> getDiscrepancyList() {
		ObservableList<Discrepancy> items = FXCollections.<Discrepancy>observableArrayList();
		items.setAll(model.findAll());
		return items;
	}

	public void createDiscrepancy(Discrepancy item) {
		// todo validate Discrepancy
		model.create(item);
	}

	public void updateDiscrepancy(Discrepancy item) {
		// todo validate Discrepancy
		model.update(item);
	}

	public void deleteDiscrepancy(Discrepancy item) {
		model.delete(item);
	}
}
