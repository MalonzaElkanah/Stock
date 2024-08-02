package com.example.inventory.inventory;

import com.example.inventory.users.AdminView;
import com.example.inventory.product.ProductDetailView;
import com.example.inventory.discrepancy.DiscrepancyCreateView;
import com.example.inventory.utils.ViewUtil;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import java.util.Optional;


public class InventoryListView {
    private BorderPane root = new BorderPane();
    private TableView<Inventory> table = new TableView<Inventory>();
    private InventoryController controller;
    private AdminView view;

	public InventoryListView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new InventoryController();

		init();
	}

	public void init() {
        // Inventory TABLE 
        Label label = new Label("All Inventory Items");
        label.setFont(new Font(25));
        label.setPadding(new Insets(10, 10, 10, 10));

        table.setItems(controller.getInventoryList());

        TableColumn<Inventory, String> nameCol = new TableColumn<Inventory, String>("Name");
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>(){
        	public ObservableValue<String> call(TableColumn.CellDataFeatures<Inventory, String> inventory) {
        		return new ReadOnlyObjectWrapper(inventory.getValue().getProduct().getName());
        	}
        });

        TableColumn<Inventory, Double> unitPriceCol = new TableColumn<Inventory, Double>("Unit Price");
        unitPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inventory, Double>, ObservableValue<Double>>(){
        	public ObservableValue<Double> call(TableColumn.CellDataFeatures<Inventory, Double> inventory) {
        		return new ReadOnlyObjectWrapper(inventory.getValue().getProduct().getUnitPrice());
        	}
        });

        TableColumn<Inventory, Integer> allItemsCol = new TableColumn<Inventory, Integer>("All Items");
        allItemsCol.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("allItems"));

        TableColumn<Inventory, Integer> soldItemsCol = new TableColumn<Inventory, Integer>("Sold Items");
        soldItemsCol.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("soldItems"));

        TableColumn<Inventory, Integer> discrepancyItemsCol = new TableColumn<Inventory, Integer>("Discrepancies");
        discrepancyItemsCol.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("discrepancyItems"));

        TableColumn<Inventory, Integer> availableItemsCol = new TableColumn<Inventory, Integer>("Available Items");
        availableItemsCol.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("availableItems"));

        TableColumn<Inventory, Integer> reorderQuantityCol = new TableColumn<Inventory, Integer>("Reorder Quantity");
        reorderQuantityCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inventory, Integer>, ObservableValue<Integer>>(){
        	public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Inventory, Integer> inventory) {
        		return new ReadOnlyObjectWrapper(inventory.getValue().getProduct().getReorderQuantity());
        	}
        });

        TableColumn<Inventory, String> statusCol = new TableColumn<Inventory, String>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<Inventory, String>("status"));

		table.getColumns().setAll(nameCol, allItemsCol,
			soldItemsCol, discrepancyItemsCol, availableItemsCol,
            unitPriceCol, reorderQuantityCol, statusCol);

        // addDiscrepancyButtonToTable();
        addViewButtonToTable();
        addCheckInButtonToTable();
        addCheckOutButtonToTable();
        
        BorderPane centerPane = new BorderPane();
        centerPane.setTop(label);
        centerPane.setCenter(table);

        root.setCenter(centerPane);
	}

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane pane) {
    	view.setRoot(pane);
    }

    private void addCheckInButtonToTable() {
        TableColumn<Inventory, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Inventory, Void>, TableCell<Inventory, Void>> cellFactory = new Callback<TableColumn<Inventory, 
            Void>, TableCell<Inventory, Void>>() {
            
            @Override
            public TableCell<Inventory, Void> call(final TableColumn<Inventory, Void> param) {
                final TableCell<Inventory, Void> cell = new TableCell<Inventory, Void>() {

                    private final Button btn = new Button("Checkin");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Inventory data = getTableView().getItems().get(getIndex());

                            new CheckinCreateView(view, data.getProduct());
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

    private void addCheckOutButtonToTable() {
        TableColumn<Inventory, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Inventory, Void>, TableCell<Inventory, Void>> cellFactory = new Callback<TableColumn<Inventory, 
        	Void>, TableCell<Inventory, Void>>() {
            
            @Override
            public TableCell<Inventory, Void> call(final TableColumn<Inventory, Void> param) {
                final TableCell<Inventory, Void> cell = new TableCell<Inventory, Void>() {

                    private final Button btn = new Button("Checkout");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Inventory data = getTableView().getItems().get(getIndex());

                            if (data.getAvailableItems() > 0) {
                                new CheckoutCreateView(view, data.getProduct());
                            } else {
                                ViewUtil.errorAlert(
                                    "ERROR: You have 0 available " + 
                                    data.getProduct().getName() + "!");
                            }

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

    private void addViewButtonToTable() {
        TableColumn<Inventory, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Inventory, Void>, TableCell<Inventory, Void>> cellFactory = new Callback<TableColumn<Inventory, 
            Void>, TableCell<Inventory, Void>>() {
            
            @Override
            public TableCell<Inventory, Void> call(final TableColumn<Inventory, Void> param) {
                final TableCell<Inventory, Void> cell = new TableCell<Inventory, Void>() {

                    private final Button btn = new Button("Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Inventory data = getTableView().getItems().get(getIndex());
                            ProductDetailView detailView = new ProductDetailView(
                                view,
                                data.getProduct());

                            detailView.setSelectedTab(1);
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

    private void addDiscrepancyButtonToTable() {
        TableColumn<Inventory, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Inventory, Void>, TableCell<Inventory, Void>> cellFactory = new Callback<TableColumn<Inventory, 
            Void>, TableCell<Inventory, Void>>() {
            
            @Override
            public TableCell<Inventory, Void> call(final TableColumn<Inventory, Void> param) {
                final TableCell<Inventory, Void> cell = new TableCell<Inventory, Void>() {

                    private final Button btn = new Button("Add Discrepancy");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Inventory data = getTableView().getItems().get(getIndex());
                            new DiscrepancyCreateView(view, data.getProduct());
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

}

