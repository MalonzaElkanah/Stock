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


public class CheckinListView {
    private BorderPane root = new BorderPane();
    private TableView<Checkin> table = new TableView<Checkin>();
    private InventoryController controller;
    private AdminView view;

	public CheckinListView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new InventoryController();

		init();
	}

	public void init() {
        // Checkin TABLE 
        Label label = new Label("Inventory CheckIn List");
        label.setFont(new Font(25));
        label.setPadding(new Insets(10, 10, 10, 10));

        table.setItems(controller.getCheckinList());

        TableColumn<Checkin, String> nameCol = new TableColumn<Checkin, String>("Product");
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Checkin, String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Checkin, String> item) {
                return new ReadOnlyObjectWrapper(item.getValue().getProduct().getName());
            }
        });
        TableColumn<Checkin, Double> buyingPriceCol = new TableColumn<Checkin, Double>("Buying Price");
        buyingPriceCol.setCellValueFactory(new PropertyValueFactory<Checkin, Double>("buyingPrice"));
        TableColumn<Checkin, Integer> quantityCol = new TableColumn<Checkin, Integer>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<Checkin, Integer>("quantity"));
        TableColumn<Checkin, LocalDateTime> createdCol = new TableColumn<Checkin, LocalDateTime>("Checkin Date");
        createdCol.setCellValueFactory(new PropertyValueFactory<Checkin, LocalDateTime>("created"));
        
		table.getColumns().setAll(nameCol, buyingPriceCol, quantityCol, createdCol);
        addViewButtonToTable();
        addUpdateButtonToTable();
        
        BorderPane centerPane = new BorderPane();
        centerPane.setTop(label);
        centerPane.setCenter(table);

        root.setCenter(centerPane);
	}

    private void addViewButtonToTable() {
        TableColumn<Checkin, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Checkin, Void>, TableCell<Checkin, Void>> cellFactory = new Callback<TableColumn<Checkin, 
            Void>, TableCell<Checkin, Void>>() {
            
            @Override
            public TableCell<Checkin, Void> call(final TableColumn<Checkin, Void> param) {
                final TableCell<Checkin, Void> cell = new TableCell<Checkin, Void>() {

                    private final Button btn = new Button("View Product");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Checkin data = getTableView().getItems().get(getIndex());

                            ProductDetailView detailView = new ProductDetailView(view, data.getProduct());
                            detailView.setSelectedTab(2);
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
        TableColumn<Checkin, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Checkin, Void>, TableCell<Checkin, Void>> cellFactory = new Callback<TableColumn<Checkin, 
            Void>, TableCell<Checkin, Void>>() {
            
            @Override
            public TableCell<Checkin, Void> call(final TableColumn<Checkin, Void> param) {
                final TableCell<Checkin, Void> cell = new TableCell<Checkin, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Checkin data = getTableView().getItems().get(getIndex());
                            new CheckinUpdateView(view, data);
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
