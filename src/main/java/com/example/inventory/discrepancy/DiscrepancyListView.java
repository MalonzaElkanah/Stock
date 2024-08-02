package com.example.inventory.discrepancy;

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


public class DiscrepancyListView {
    private BorderPane root = new BorderPane();
    private TableView<Discrepancy> table = new TableView<Discrepancy>();
    private DiscrepancyController controller;
    private AdminView view;

	public DiscrepancyListView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new DiscrepancyController();

		init();
	}

	public void init() {
        // Discrepancy TABLE 
        Label label = new Label("Inventory Discrepancy List");
        label.setFont(new Font(25));
        label.setPadding(new Insets(10, 10, 10, 10));

        table.setItems(controller.getDiscrepancyList());

        TableColumn<Discrepancy, String> nameCol = new TableColumn<Discrepancy, String>("Product");
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Discrepancy, String>, ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Discrepancy, String> item) {
                return new ReadOnlyObjectWrapper(item.getValue().getProduct().getName());
            }
        });
        TableColumn<Discrepancy, String> reasonCol = new TableColumn<Discrepancy, String>("Reason");
        reasonCol.setCellValueFactory(new PropertyValueFactory<Discrepancy, String>("reason"));
        TableColumn<Discrepancy, Double> unitPriceCol = new TableColumn<Discrepancy, Double>("Unit Price");
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<Discrepancy, Double>("unitPrice"));
        TableColumn<Discrepancy, Integer> quantityCol = new TableColumn<Discrepancy, Integer>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<Discrepancy, Integer>("quantity"));
        TableColumn<Discrepancy, LocalDateTime> createdCol = new TableColumn<Discrepancy, LocalDateTime>("Discrepancy Date");
        createdCol.setCellValueFactory(new PropertyValueFactory<Discrepancy, LocalDateTime>("created"));
        
		table.getColumns().setAll(nameCol, reasonCol, unitPriceCol, quantityCol, createdCol);
        addViewButtonToTable();
        addUpdateButtonToTable();
        
        BorderPane centerPane = new BorderPane();
        centerPane.setTop(label);
        centerPane.setCenter(table);

        root.setCenter(centerPane);
	}

    private void addViewButtonToTable() {
        TableColumn<Discrepancy, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Discrepancy, Void>, TableCell<Discrepancy, Void>> cellFactory = new Callback<TableColumn<Discrepancy, 
            Void>, TableCell<Discrepancy, Void>>() {
            
            @Override
            public TableCell<Discrepancy, Void> call(final TableColumn<Discrepancy, Void> param) {
                final TableCell<Discrepancy, Void> cell = new TableCell<Discrepancy, Void>() {

                    private final Button btn = new Button("View Product");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Discrepancy data = getTableView().getItems().get(getIndex());

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
        TableColumn<Discrepancy, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Discrepancy, Void>, TableCell<Discrepancy, Void>> cellFactory = new Callback<TableColumn<Discrepancy, 
            Void>, TableCell<Discrepancy, Void>>() {
            
            @Override
            public TableCell<Discrepancy, Void> call(final TableColumn<Discrepancy, Void> param) {
                final TableCell<Discrepancy, Void> cell = new TableCell<Discrepancy, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Discrepancy data = getTableView().getItems().get(getIndex());
                            new DiscrepancyUpdateView(view, data);
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
