package com.example.inventory.product;

import com.example.inventory.users.AdminView;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import java.time.LocalDateTime;


public class ProductEditView {
    private BorderPane root = new BorderPane();
    private ProductController controller;
    private AdminView view;
    private Product product;

	public ProductEditView(AdminView view, Product product) {
		this.view = view;
		this.view.setRoot(root);
        this.product = product;
		this.controller = new ProductController();

		init();
	}

	private void init() {
        Label label = new Label("Product: " + product.getName());
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 40, 10));

        // Product TextInputs
        TextField name = ViewUtil.createTextFieldWithText(product.getName());
        TextField description = ViewUtil.createTextFieldWithText(product.getDescription());
        TextField unitPrice = ViewUtil.createTextFieldWithText(product.getUnitPrice().toString());
        TextField reorderQuantity = ViewUtil.createTextFieldWithText(product.getReorderQuantity().toString());

        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);


        tile.getChildren().addAll(ViewUtil.createFieldPane(name, "Name: "),
        	ViewUtil.createFieldPane(description, "Description: "),
        	ViewUtil.createFieldPane(unitPrice, "Unit Price: "),
        	ViewUtil.createFieldPane(reorderQuantity, "Reorder Quantity: "));

        // Submit button
        Button submitButton = new Button("UPDATE PRODUCT");
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
            try {
                product.setName(name.getText());
                product.setDescription(description.getText());
                product.setUnitPrice(Double.parseDouble(unitPrice.getText()));
                product.setReorderQuantity(Integer.parseInt(reorderQuantity.getText()));

                // Validate Product
                ValidatorUtil validator = new ValidatorUtil(product); 

                if (validator.isValid()) {
                    controller.updateProduct(product);
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
