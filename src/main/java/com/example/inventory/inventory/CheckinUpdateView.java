package com.example.inventory.inventory;

import com.example.inventory.users.AdminView;
import com.example.inventory.product.Product;
import com.example.inventory.product.ProductDetailView;
import com.example.inventory.utils.ViewUtil;
import com.example.inventory.utils.ValidatorUtil;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
// import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
// import javafx.geometry.Orientation;


public class CheckinUpdateView {
    private BorderPane root = new BorderPane();
    private InventoryController controller;
    private AdminView view;
    private Checkin item;

	public CheckinUpdateView(AdminView view, Checkin item) {
		this.view = view;
		this.view.setRoot(root);
        this.item = item;
		this.controller = new InventoryController();

		init();
	}

	private void init() {
        Product product = item.getProduct();

        Label label = new Label("Update " + product.getName() + " Check-In.");
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 40, 10));

        // Product TextInputs
        TextField name = ViewUtil.createTextFieldWithText(product.getName());
        name.setDisable(true);
        name.setEditable(false);
        TextField quantityField = ViewUtil.createTextFieldWithText(item.getQuantity().toString());
        TextField buyingPriceField = ViewUtil.createTextFieldWithText(item.getBuyingPrice().toString());

        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);

        tile.getChildren().addAll(ViewUtil.createFieldPane(name, "Product: "),
        	ViewUtil.createFieldPane(quantityField, "Quantity: "),
        	ViewUtil.createFieldPane(buyingPriceField, "Buying Price: "));

        // Submit button
        Button submitButton = new Button("UPDATE CHECK-IN");
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
            if (!quantityField.getText().isBlank() && 
                !buyingPriceField.getText().isBlank()) {
                try {
                    Integer quantity = Integer.parseInt(quantityField.getText());
                    Double amount = Double.parseDouble(buyingPriceField.getText());

                    item.setQuantity(quantity);
                    item.setBuyingPrice(amount);

                    // Validate Product
                    ValidatorUtil validator = new ValidatorUtil(item); 

                    if (validator.isValid()) {
                        controller.updateCheckin(item);
                        ProductDetailView detailView = new ProductDetailView(view, product);
                        detailView.setSelectedTab(2);
                    } else {
                        ViewUtil.errorAlert(validator.violation());
                    }
                } catch(NumberFormatException exception) {
                    ViewUtil.errorAlert("Error: Enter a number in Quantity and Price Fields!");
                }
            } else if (quantityField.getText().isBlank()) {
                ViewUtil.alert("ERROR: Quantity Field is EMPTY!");
            } else if (buyingPriceField.getText().isBlank()) {
                ViewUtil.alert("ERROR: Buying Price Field is EMPTY!");
            }
        });
    }
}
