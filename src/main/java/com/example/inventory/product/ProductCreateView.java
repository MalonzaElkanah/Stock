package com.example.inventory.product;

import com.example.inventory.users.AdminView;
import com.example.inventory.inventory.Inventory;
import com.example.inventory.utils.ViewUtil;
import com.example.inventory.utils.ValidatorUtil;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import javafx.scene.text.Font;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

public class ProductCreateView {
    private BorderPane root = new BorderPane();
    private ProductController controller;
    private AdminView view;

	public ProductCreateView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new ProductController();

		init();
	}

	private void init() {
        Label label = new Label("Add Inventory Product");
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 40, 10));

        // Product TextInputs
        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);
	    // tile.setPrefRows(2);

	    TextField name = ViewUtil.createTextField("Name");
        TextField description = ViewUtil.createTextField("description");
        TextField unitPrice = ViewUtil.createTextField("Unit Price");
        TextField buyingPrice = ViewUtil.createTextField("Buying Price");
        TextField reorderQuantity = ViewUtil.createTextField("ReOrder Quantity");
        TextField availableQuantity = ViewUtil.createTextField("Available Quantity");

        tile.getChildren().addAll(ViewUtil.createFieldPane(name, "Name: "),
        	ViewUtil.createFieldPane(description, "Description: "),
        	ViewUtil.createFieldPane(unitPrice, "Unit Price: "),
        	// ViewUtil.createFieldPane(buyingPrice, "Buying Price: "),
        	ViewUtil.createFieldPane(reorderQuantity, "Reorder Quantity: ")
            // ViewUtil.createFieldPane(availableQuantity, "Available Quantity: ")
            );

        // Submit button
        Button submitButton = new Button("CREATE PRODUCT");
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
        	Product product = new Product();
        	
            try {
                product.setName(name.getText());
                product.setDescription(description.getText());
        	    product.setUnitPrice(Double.parseDouble(unitPrice.getText()));
                // product.setBuyingPrice(Double.parseDouble(buyingPrice.getText()));
                product.setReorderQuantity(Integer.parseInt(reorderQuantity.getText()));
                
                // Validate Product
                ValidatorUtil validator = new ValidatorUtil(product); 

                if (validator.isValid()) {
                    controller.createProduct(product);
                    new ProductDetailView(view, product);
                } else {
                    ViewUtil.errorAlert(validator.violation());
                }
            } catch(NumberFormatException exception) {
                ViewUtil.errorAlert("Error: Enter a number in Quantity and Price Fields!");
            }
            
        });
    }
}
