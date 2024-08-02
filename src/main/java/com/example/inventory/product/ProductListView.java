package com.example.inventory.product;

import com.example.inventory.users.AdminView;
import com.example.inventory.inventory.CheckinCreateView;

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

import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.UUID;


public class ProductListView {
    private BorderPane root = new BorderPane();
    private TableView<Product> table = new TableView<Product>();
    private ProductController controller;
    private AdminView view;

	public ProductListView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new ProductController();

		init();
	}

	public void init() {
        // PRODUCT TABLE 
        Label label = new Label("My Products");
        label.setFont(new Font(25));
        label.setPadding(new Insets(10, 10, 10, 10));

        table.setItems(controller.getProductList());

        //TableColumn<Product, UUID> idCol = new TableColumn<Product, UUID>("Id");
        //idCol.setCellValueFactory(new PropertyValueFactory<Product, UUID>("id"));
        TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        TableColumn<Product, String> descriptionCol = new TableColumn<Product, String>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        TableColumn<Product, Double> unitPriceCol = new TableColumn<Product, Double>("Unit Price");
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("unitPrice"));
        // TableColumn<Product, Double> buyingPriceCol = new TableColumn<Product, Double>("Buying Price");
        // buyingPriceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("buyingPrice"));
        TableColumn<Product, Integer> reorderQuantityCol = new TableColumn<Product, Integer>("Reorder Quantity");
        reorderQuantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("reorderQuantity"));
        // TableColumn<Product, LocalDateTime> createdCol = new TableColumn<Product, LocalDateTime>("Created Date");
        // createdCol.setCellValueFactory(new PropertyValueFactory<Product, LocalDateTime>("created"));
        // TableColumn<Product, LocalDateTime> modifiedCol = new TableColumn<Product, LocalDateTime>("Modfied Date");
        // modifiedCol.setCellValueFactory(new PropertyValueFactory<Product, LocalDateTime>("modified"));

		table.getColumns().setAll(//idCol,
			nameCol, descriptionCol,
			unitPriceCol, // buyingPriceCol, 
			reorderQuantityCol //, createdCol, modifiedCol
		);

		addEditButtonToTable();
		addViewButtonToTable();
        addCheckinButtonToTable();
        
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

    private void addViewButtonToTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                    private final Button btn = new Button("View");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product data = getTableView().getItems().get(getIndex());
                            new ProductDetailView(view, data);
                            System.out.println("selectedProduct: " + data);
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

    private void addEditButtonToTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedProduct: " + data);
                            new ProductEditView(view, data);
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

    private void addCheckinButtonToTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

                    private final Button btn = new Button("Check-In");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedProduct: " + data);
                            new CheckinCreateView(view, data);
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
