package com.example.inventory.inventory;

import com.example.inventory.users.AdminView;
import com.example.inventory.product.ProductDetailView;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.UUID;


public class CheckoutListView {
    private BorderPane root = new BorderPane();
    private TableView<Checkout> table = new TableView<Checkout>();
    private InventoryController controller;
    private AdminView view;

	public CheckoutListView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new InventoryController();

		init();
	}

	public void init() {
        // Checkout TABLE 
        Label label = new Label("Inventory Checkout List");
        label.setFont(new Font(25));
        label.setPadding(new Insets(10, 10, 10, 10));

        table.setItems(controller.getCheckoutList());

        TableColumn<Checkout, String> nameCol = new TableColumn<Checkout, String>("Product");
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Checkout, String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Checkout, String> item) {
                return new ReadOnlyObjectWrapper(item.getValue().getProduct().getName());
            }
        });
        TableColumn<Checkout, Double> totalPriceCol = new TableColumn<Checkout, Double>("Total Price");
        totalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Checkout, Double>, ObservableValue<Double>>(){
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Checkout, Double> item) {
                Double total = item.getValue().getUnitPrice() * item.getValue().getQuantity();
                return new ReadOnlyObjectWrapper(total);
            }
        });
        TableColumn<Checkout, Double> unitPriceCol = new TableColumn<Checkout, Double>("Unit Price");
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<Checkout, Double>("unitPrice"));
        TableColumn<Checkout, Integer> quantityCol = new TableColumn<Checkout, Integer>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<Checkout, Integer>("quantity"));
        TableColumn<Checkout, LocalDateTime> createdCol = new TableColumn<Checkout, LocalDateTime>("Checkout Date");
        createdCol.setCellValueFactory(new PropertyValueFactory<Checkout, LocalDateTime>("created"));
        
		table.getColumns().setAll(nameCol, unitPriceCol, quantityCol, totalPriceCol, createdCol);
        addViewButtonToTable();
        addUpdateButtonToTable();
        
        BorderPane centerPane = new BorderPane();
        centerPane.setTop(label);
        centerPane.setCenter(table);

        root.setCenter(centerPane);
	}

    private void addViewButtonToTable() {
        TableColumn<Checkout, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Checkout, Void>, TableCell<Checkout, Void>> cellFactory = new Callback<TableColumn<Checkout, 
            Void>, TableCell<Checkout, Void>>() {
            
            @Override
            public TableCell<Checkout, Void> call(final TableColumn<Checkout, Void> param) {
                final TableCell<Checkout, Void> cell = new TableCell<Checkout, Void>() {

                    private final Button btn = new Button("View Product");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Checkout data = getTableView().getItems().get(getIndex());

                            ProductDetailView detailView = new ProductDetailView(view, data.getProduct());
                            detailView.setSelectedTab(3);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);
    }

    private void addUpdateButtonToTable() {
        TableColumn<Checkout, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Checkout, Void>, TableCell<Checkout, Void>> cellFactory = new Callback<TableColumn<Checkout, 
            Void>, TableCell<Checkout, Void>>() {
            
            @Override
            public TableCell<Checkout, Void> call(final TableColumn<Checkout, Void> param) {
                final TableCell<Checkout, Void> cell = new TableCell<Checkout, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Checkout data = getTableView().getItems().get(getIndex());
                            new CheckoutUpdateView(view, data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);
    }


    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane pane) {
    	view.setRoot(pane);
    }
}
