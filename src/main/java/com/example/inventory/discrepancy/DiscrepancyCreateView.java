package com.example.inventory.discrepancy;

import com.example.inventory.users.AdminView;
import com.example.inventory.product.Product;
import com.example.inventory.inventory.Inventory;
import com.example.inventory.product.ProductDetailView;
import com.example.inventory.utils.ViewUtil;
import com.example.inventory.utils.ValidatorUtil;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import java.time.LocalDateTime;


public class DiscrepancyCreateView {
    private BorderPane root = new BorderPane();
    private DiscrepancyController controller;
    private AdminView view;
    private Product product;

	public DiscrepancyCreateView(AdminView view, Product product) {
		this.view = view;
		this.view.setRoot(root);
        this.product = product;
		this.controller = new DiscrepancyController();

		init();
	}

	private void init() {
        Inventory inventory = new Inventory(product);

        Label label = new Label("ADD " + product.getName() + " to DISCREPANCY");
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 40, 10));

        // Product TextInputs
        TextField name = ViewUtil.createTextFieldWithText(product.getName());
        name.setDisable(true);
        name.setEditable(false);

        TextField unitPriceField = ViewUtil.createTextFieldWithText(product.getUnitPrice().toString());
        unitPriceField.setDisable(true);
        unitPriceField.setEditable(false);

        TextField quantityField = ViewUtil.createTextFieldWithText("1");
        TextField reasonField = ViewUtil.createTextField("Reason for Discrepancy");

        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);

        tile.getChildren().addAll(ViewUtil.createFieldPane(name, "Product: "),
            ViewUtil.createFieldPane(unitPriceField, "Unit Price: "),
        	ViewUtil.createFieldPane(quantityField, "Quantity: "),
            ViewUtil.createFieldPane(reasonField, "Reason: "));

        // Submit button
        Button submitButton = new Button("ADD DISCREPANCY");
        submitButton.setMaxWidth(200); 
        submitButton.setMaxHeight(40);
        submitButton.setPadding(new Insets(10, 10, 10, 10));

        HBox buttonPane = new HBox(10);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        buttonPane.getChildren().addAll(submitButton);
        buttonPane.setAlignment(Pos.CENTER_LEFT);

        VBox vPane = new VBox(40);
        vPane.setAlignment(Pos.TOP_LEFT);
        vPane.getChildren().addAll(tile, buttonPane);

        root.setCenter(vPane);
        root.setTop(label);
        
        // Action Listeners
        submitButton.setOnAction(e -> {
            if (!quantityField.getText().isBlank()) {
                try {
                    Integer quantity = Integer.parseInt(quantityField.getText());
                    String reason = reasonField.getText();
                    Discrepancy discrepancy = new Discrepancy(product, quantity, reason);
                    ValidatorUtil validator = new ValidatorUtil(discrepancy); 

                    if (validator.isValid()) {
                        if (quantity > 0 && quantity <= inventory.getAvailableItems()) {
                            controller.createDiscrepancy(discrepancy);
                            ProductDetailView detailView = new ProductDetailView(view, product);
                            detailView.setSelectedTab(4);
                        } else {
                            ViewUtil.errorAlert("ERROR: You have " + 
                                inventory.getAvailableItems().toString() + " available " +
                                product.getName() + "!");
                        }
                    } else {
                        ViewUtil.errorAlert(validator.violation());
                    }
                } catch(NumberFormatException exception) {
                    ViewUtil.errorAlert("Error: Enter a number in Quantity Fields!");
                }
            } else if (!quantityField.getText().isBlank()) {
                ViewUtil.errorAlert("ERROR: Quantity Field is EMPTY!");
            }
        });
    }
}
