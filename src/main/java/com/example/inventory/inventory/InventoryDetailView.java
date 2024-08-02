package com.example.inventory.inventory;

import com.example.inventory.users.AdminView;

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


public class InventoryDetailView {
    private BorderPane root = new BorderPane();
    private InventoryController controller;
    private AdminView view;
    private Inventory inventory;

	public InventoryDetailView(AdminView view, Inventory inventory) {
		this.view = view;
		this.view.setRoot(root);
        this.inventory = inventory;
		this.controller = new InventoryController();

		init();
	}

	private void init() {
        Label label = new Label(inventory.getProduct().getName() + " Inventory");
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 40, 10));

        // Product TextInputs
        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);


        tile.getChildren().addAll(createFieldPane(inventory.getProduct().getName(), "Product: "),
            createFieldPane(inventory.getProduct().getUnitPrice().toString(), "Price per Unit: "),
            createFieldPane(inventory.getAllItems().toString(), "Total Stock: "),
            createFieldPane(inventory.getTotalBuyingPrice().toString(), "Total Buying Price: "),
            createFieldPane(inventory.getSoldItems().toString(), "Sold Stock: "),
            createFieldPane(inventory.getSoldSellingPrice().toString(), "Total Amount Sold: "),
            createFieldPane(inventory.getAvailableItems().toString(), "Available Stock: "),
        	createFieldPane(inventory.getAvailableSellingPrice().toString(), "Available Stock Value: "),
        	createFieldPane(inventory.getDiscrepancyItems().toString(), "Discrepancy Stock: "),
        	createFieldPane(inventory.getDiscrepancySellingPrice().toString(), "Discrepancy Value: "),
        	createFieldPane(inventory.getProduct().getReorderQuantity().toString(), "Reorder Quantity: "),
            createFieldPane(inventory.getStatus(), "Status: "));


        VBox vPane = new VBox(40);
        vPane.setAlignment(Pos.TOP_LEFT);
        vPane.getChildren().addAll(tile);

        root.setCenter(vPane);
        root.setTop(label);
        
        // Action Listeners
        // submitButton.setOnAction(e -> { });
    }

    private Pane createFieldPane(String text, String title) {
        // title label
        Label titleLabel = new Label(title);
        // titleLabel.setFont(Font.font("Monospace", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        titleLabel.setAlignment(Pos.CENTER_RIGHT);
        titleLabel.setPrefWidth(150); 
        titleLabel.setMaxHeight(40);
        titleLabel.setPadding(new Insets(10, 10, 10, 10));

        //data label
        TextField dataField = new TextField();
        dataField.setText(text);
        dataField.setFont(Font.font("Monospace", FontWeight.BLACK, FontPosture.REGULAR, 15));
        // dataField.setAlignment(Pos.CENTER_LEFT);
        dataField.setEditable(false);
        dataField.setDisable(true);
        dataField.setPrefWidth(250); 
        dataField.setMaxHeight(40);
        dataField.setPadding(new Insets(10, 10, 10, 10));
        dataField.setOpacity(0);
    	
        TilePane tile = new TilePane();
	    tile.setPrefColumns(2);
	    tile.getChildren().addAll(titleLabel, dataField);

	    return tile;
    }
}
