package com.example.inventory.inventory;

import com.example.inventory.users.AdminView;
import com.example.inventory.product.Product;
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


public class CheckoutUpdateView {
    private BorderPane root = new BorderPane();
    private InventoryController controller;
    private AdminView view;
    private Checkout item;

	public CheckoutUpdateView(AdminView view, Checkout item) {
		this.view = view;
		this.view.setRoot(root);
        this.item = item;
		this.controller = new InventoryController();

		init();
	}

	private void init() {
        Product product = item.getProduct();
        Inventory inventory = new Inventory(product);

        Label label = new Label("Check-out " + product.getName() + " from Inventory");
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 40, 10));

        // Product TextInputs
        TextField name = ViewUtil.createTextFieldWithText(product.getName());
        name.setDisable(true);
        name.setEditable(false);

        TextField unitPriceField = ViewUtil.createTextFieldWithText(item.getUnitPrice().toString());
        unitPriceField.setDisable(true);
        unitPriceField.setEditable(false);

        Double total = item.getUnitPrice() * item.getQuantity();
        TextField totalPriceField = ViewUtil.createTextFieldWithText(total.toString());
        totalPriceField.setDisable(true);
        totalPriceField.setEditable(false);

        TextField quantityField = ViewUtil.createTextFieldWithText(item.getQuantity().toString());

        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);

        tile.getChildren().addAll(ViewUtil.createFieldPane(name, "Product: "),
            ViewUtil.createFieldPane(unitPriceField, "Unit Price: "),
            ViewUtil.createFieldPane(totalPriceField, "Total Price: "),
        	ViewUtil.createFieldPane(quantityField, "Quantity: "));

        // Submit button
        Button submitButton = new Button("CHECK-OUT PRODUCT");
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
        quantityField.setOnKeyTyped(e -> {
            if (!quantityField.getText().isBlank()) {
                try {
                    Integer quantity = Integer.parseInt(quantityField.getText());
                    Double totalPrice = item.getUnitPrice() * quantity;
                    totalPriceField.setDisable(false);
                    totalPriceField.setEditable(true);
                    totalPriceField.setText(totalPrice.toString());
                    totalPriceField.setDisable(true);
                    totalPriceField.setEditable(false);
                } catch(NumberFormatException exception) { totalPriceField.setText("0.0"); }
            } else {
                totalPriceField.setText("0.0");
            }
        });

        submitButton.setOnAction(e -> {
            if (!quantityField.getText().isBlank()) {
                try {
                    Integer quantity = Integer.parseInt(quantityField.getText()); 
                    Integer oldQuantity = item.getQuantity();
                    item.setQuantity(quantity);
                    // Validate Product
                    ValidatorUtil validator = new ValidatorUtil(item); 

                    if (validator.isValid()) {
                        if (quantity > 0 
                            && quantity <= (inventory.getAvailableItems() + oldQuantity)) {
                            controller.updateCheckout(item);
                            // new CheckoutListView(view);
                            ProductDetailView detailView = new ProductDetailView(view, product);
                            detailView.setSelectedTab(3);
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
