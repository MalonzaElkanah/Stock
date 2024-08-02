package com.example.inventory.product;

import com.example.inventory.users.AdminView;
import com.example.inventory.utils.ViewUtil;
import com.example.inventory.discrepancy.Discrepancy;
import com.example.inventory.discrepancy.DiscrepancyController;
import com.example.inventory.discrepancy.DiscrepancyCreateView;
import com.example.inventory.discrepancy.DiscrepancyUpdateView;
import com.example.inventory.inventory.Inventory;
import com.example.inventory.inventory.InventoryController;
import com.example.inventory.inventory.Checkin;
import com.example.inventory.inventory.Checkout;
import com.example.inventory.inventory.CheckoutCreateView;
import com.example.inventory.inventory.CheckoutUpdateView;
import com.example.inventory.inventory.CheckinCreateView;
import com.example.inventory.inventory.CheckinUpdateView;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;

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

import javafx.beans.value.ObservableValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.List;


public class ProductDetailView {
    private BorderPane root = new BorderPane();
    private ProductController controller;
    private InventoryController inventoryController;
    private DiscrepancyController discrepancyController;
    private AdminView view;
    private Product product;
    private TabPane tabPane;

	public ProductDetailView(AdminView view, Product product) {
		this.view = view;
		this.view.setRoot(root);
        this.product = product;
		this.controller = new ProductController();
        this.inventoryController = new InventoryController();
        this.discrepancyController = new DiscrepancyController();

		init();
	}

	private void init() {
        Label label = new Label(product.getName());
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 30, 10));

        VBox labelPane = new VBox();
        labelPane.setAlignment(Pos.CENTER);
        labelPane.getChildren().addAll(label);

        tabPane = new TabPane();

        // Product Details Tab
        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);
        tile.setPadding(new Insets(30, 0, 30, 0));

        tile.getChildren().addAll(createFieldPane(product.getId().toString(), "ID: "), 
            createFieldPane(product.getName(), "Name: "),
        	createFieldPane(product.getDescription(), "Description: "),
        	createFieldPane(product.getUnitPrice().toString(), "Unit Price: "),
        	// createFieldPane(product.getBuyingPrice().toString(), "Buying Price: "),
        	createFieldPane(product.getReorderQuantity().toString(), "Reorder Quantity: "),
            createFieldPane(product.getCreated().toString(), "Created: "),
            createFieldPane(product.getModified().toString(), "Modified: "));


        Tab tab1 = new Tab();
        tab1.setText("PRODUCT DETAIL");
        tab1.setContent(tile);
        tab1.setClosable(false);

        tabPane.getTabs().add(tab1);

        // Product Stocks tab
        Inventory inventory = new Inventory(product);

        Label inventoryLabel = new Label(product.getName() + " Stocks");
        inventoryLabel.setFont(new Font(20));
        inventoryLabel.setAlignment(Pos.CENTER);
        inventoryLabel.setPadding(new Insets(30, 10, 40, 10));

        TilePane inventoryTile = new TilePane();
        inventoryTile.setMaxWidth(1200);
        inventoryTile.setTileAlignment(Pos.CENTER_LEFT);
        inventoryTile.setVgap(30);
        inventoryTile.setHgap(10);
        inventoryTile.setPrefColumns(2);
        inventoryTile.setPadding(new Insets(30, 0, 30, 0));

        inventoryTile.getChildren().addAll(createFieldPane(inventory.getAllItems().toString(), "Total Stock: "),
            createFieldPane(inventory.getTotalBuyingPrice().toString(), "Total Buying Price: "),
            createFieldPane(inventory.getSoldItems().toString(), "Sold Stock: "),
            createFieldPane(inventory.getSoldSellingPrice().toString(), "Total Amount Sold: "),
            createFieldPane(inventory.getAvailableItems().toString(), "Available Stock: "),
            createFieldPane(inventory.getAvailableSellingPrice().toString(), "Available Stock Value: "),
            createFieldPane(inventory.getDiscrepancyItems().toString(), "Discrepancy Stock: "),
            createFieldPane(inventory.getDiscrepancySellingPrice().toString(), "Discrepancy Value: "),
            createFieldPane(inventory.getProduct().getReorderQuantity().toString(), "Reorder Quantity: "),
            createFieldPane(inventory.getStatus(), "Status: "));

        Tab tab2 = new Tab();
        tab2.setText("STOCK DETAILS");
        tab2.setContent(inventoryTile);
        tab2.setClosable(false);

        tabPane.getTabs().add(tab2);


        // Product Checkins
        ObservableList<Checkin> checkins = FXCollections.<Checkin>observableArrayList();
        checkins.addAll(inventory.getProductCheckins());

        TableView<Checkin> checkinTable = new TableView<Checkin>();
        checkinTable.setItems(checkins);

        TableColumn<Checkin, Double> buyingPriceCol = new TableColumn<Checkin, Double>("Buying Price");
        buyingPriceCol.setCellValueFactory(new PropertyValueFactory<Checkin, Double>("buyingPrice"));
        TableColumn<Checkin, Integer> quantityCol = new TableColumn<Checkin, Integer>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<Checkin, Integer>("quantity"));
        TableColumn<Checkin, LocalDateTime> createdCol = new TableColumn<Checkin, LocalDateTime>("Checkin Date");
        createdCol.setCellValueFactory(new PropertyValueFactory<Checkin, LocalDateTime>("created"));
        TableColumn<Checkin, Double> valueCol = new TableColumn<Checkin, Double>("Stock Value");
        valueCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Checkin, Double>, ObservableValue<Double>>(){
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Checkin, Double> item) {
                Double total = item.getValue().getBuyingPrice() * item.getValue().getQuantity();
                return new ReadOnlyObjectWrapper(total);
            }
        });
        
        checkinTable.getColumns().setAll(createdCol,
            buyingPriceCol, quantityCol, valueCol, getUpdateCheckinButton(), getDeleteCheckinButton());
        checkinTable.setPrefHeight(400);

        Tab tab3 = new Tab();
        tab3.setText("INVENTORY CHECKINS");
        tab3.setContent(checkinTable);
        tab3.setClosable(false);

        tabPane.getTabs().add(tab3);


        // Product Checkouts
        ObservableList<Checkout> checkouts = FXCollections.<Checkout>observableArrayList();
        checkouts.addAll(inventory.getProductCheckouts());

        TableView<Checkout> checkoutTable = new TableView<Checkout>();
        checkoutTable.setItems(checkouts);

        TableColumn<Checkout, Double> totalPriceCol = new TableColumn<Checkout, Double>("Total Price");
        totalPriceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Checkout, Double>, ObservableValue<Double>>(){
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Checkout, Double> item) {
                Double total = item.getValue().getUnitPrice() * item.getValue().getQuantity();
                return new ReadOnlyObjectWrapper(total);
            }
        });
        TableColumn<Checkout, Double> unitPriceCol = new TableColumn<Checkout, Double>("Unit Price");
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<Checkout, Double>("unitPrice"));
        TableColumn<Checkout, Integer> checkoutQuantityCol = new TableColumn<Checkout, Integer>("Quantity");
        checkoutQuantityCol.setCellValueFactory(new PropertyValueFactory<Checkout, Integer>("quantity"));
        TableColumn<Checkout, LocalDateTime> checkoutCreatedCol = new TableColumn<Checkout, LocalDateTime>("Checkout Date");
        checkoutCreatedCol.setCellValueFactory(new PropertyValueFactory<Checkout, LocalDateTime>("created"));
        
        checkoutTable.getColumns().setAll(checkoutCreatedCol,
            unitPriceCol, checkoutQuantityCol, totalPriceCol, 
            getUpdateCheckoutButton(), getDeleteCheckoutButton());

        Tab tab4 = new Tab();
        tab4.setText("SOLD PRODUCT");
        tab4.setContent(checkoutTable);
        tab4.setClosable(false);

        tabPane.getTabs().add(tab4);

        // Product Discrepancies
        ObservableList<Discrepancy> discrepancies = FXCollections.<Discrepancy>observableArrayList();
        discrepancies.addAll(inventory.getProductDiscrepancies());
        TableView<Discrepancy> discrepancyTable = new TableView<Discrepancy>();
        discrepancyTable.setItems(discrepancies);

        TableColumn<Discrepancy, String> reasonCol = new TableColumn<Discrepancy, String>("Reason");
        reasonCol.setCellValueFactory(new PropertyValueFactory<Discrepancy, String>("reason"));
        TableColumn<Discrepancy, Double> priceCol = new TableColumn<Discrepancy, Double>("Unit Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<Discrepancy, Double>("unitPrice"));
        TableColumn<Discrepancy, Integer> discrepancyQuantityCol = new TableColumn<Discrepancy, Integer>("Quantity");
        discrepancyQuantityCol.setCellValueFactory(new PropertyValueFactory<Discrepancy, Integer>("quantity"));
        TableColumn<Discrepancy, LocalDateTime> discrepancyCreatedCol = new TableColumn<Discrepancy, LocalDateTime>("Discrepancy Date");
        discrepancyCreatedCol.setCellValueFactory(new PropertyValueFactory<Discrepancy, LocalDateTime>("created"));
        
        discrepancyTable.getColumns().setAll(reasonCol,
            priceCol, discrepancyQuantityCol, discrepancyCreatedCol, 
            getDiscrepancyUpdateButton(), getDiscrepancyDeleteButton());
        
        Tab tab5 = new Tab();
        tab5.setText("DISCREPANCIES");
        tab5.setContent(discrepancyTable);
        tab5.setClosable(false);
        tabPane.getTabs().add(tab5);

        // Action Buttons
        Button checkinButton = createButton("CHECK-IN");
        Button checkoutButton = createButton("CHECK-OUT");
        Button discrepancyButton = createButton("ADD DISCREPANCY");
        Button updateButton = createButton("UPDATE PRODUCT");
        Button deleteButton = ViewUtil.createDeleteButton("DELETE PRODUCT");

        HBox buttonPane = new HBox(20);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        buttonPane.getChildren().addAll(updateButton,
            checkinButton, checkoutButton, discrepancyButton, deleteButton);
        buttonPane.setAlignment(Pos.CENTER_LEFT);

        root.setCenter(tabPane);
        root.setTop(labelPane);
        root.setBottom(buttonPane);
        
        // Action Listeners
        updateButton.setOnAction(e -> new ProductEditView(view, product) );
        checkinButton.setOnAction(e -> new CheckinCreateView(view, product) );
        checkoutButton.setOnAction(e -> new CheckoutCreateView(view, product) );
        discrepancyButton.setOnAction(e -> new DiscrepancyCreateView(view, product) );
        deleteButton.setOnAction(e -> {
            boolean isConfirm = ViewUtil.confirmAlert("DELETE '" + product.getName() + "' PRODUCT?");

            if (isConfirm) {
                controller.deleteProduct(product);
                ViewUtil.alert("Product DELETED!");
                new ProductListView(view);
            } else {
                ViewUtil.alert("Product Delete CANCELLED!");
            }
        });
    }

    public void setSelectedTab(Integer index) {
        tabPane.getSelectionModel().select(index);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(200); 
        button.setMaxHeight(40);
        button.setPadding(new Insets(10, 10, 10, 10));

        return button;
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

    private TableColumn getUpdateCheckinButton() {
        TableColumn<Checkin, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Checkin, Void>, TableCell<Checkin, Void>> cellFactory = new Callback<TableColumn<Checkin, 
            Void>, TableCell<Checkin, Void>>() {
            
            @Override
            public TableCell<Checkin, Void> call(final TableColumn<Checkin, Void> param) {
                final TableCell<Checkin, Void> cell = new TableCell<Checkin, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction(e -> {
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

        return colBtn;
    }

    private TableColumn getDeleteCheckinButton() {
        TableColumn<Checkin, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Checkin, Void>, TableCell<Checkin, Void>> cellFactory = new Callback<TableColumn<Checkin, 
            Void>, TableCell<Checkin, Void>>() {
            
            @Override
            public TableCell<Checkin, Void> call(final TableColumn<Checkin, Void> param) {
                final TableCell<Checkin, Void> cell = new TableCell<Checkin, Void>() {

                    private final Button btn = new Button("DELETE");

                    {
                        btn.setOnAction(e -> {
                            Checkin data = getTableView().getItems().get(getIndex());

                            boolean isConfirm = ViewUtil.confirmAlert("DELETE '" + product.getName() + "' CHECK-IN?");

                            if (isConfirm) {
                                inventoryController.deleteCheckin(data);
                                ViewUtil.alert("INVENTORY CHECK-IN DELETED!");
                                init();
                                // new ProductListView(view);
                            } else {
                                ViewUtil.alert("DELETE CANCELLED!");
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

        return colBtn;
    }

    private TableColumn getUpdateCheckoutButton() {
        TableColumn<Checkout, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Checkout, Void>, TableCell<Checkout, Void>> cellFactory = new Callback<TableColumn<Checkout, 
            Void>, TableCell<Checkout, Void>>() {
            
            @Override
            public TableCell<Checkout, Void> call(final TableColumn<Checkout, Void> param) {
                final TableCell<Checkout, Void> cell = new TableCell<Checkout, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction(e -> {
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

        return colBtn;
    }

    private TableColumn getDeleteCheckoutButton() {
        TableColumn<Checkout, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Checkout, Void>, TableCell<Checkout, Void>> cellFactory = new Callback<TableColumn<Checkout, 
            Void>, TableCell<Checkout, Void>>() {
            
            @Override
            public TableCell<Checkout, Void> call(final TableColumn<Checkout, Void> param) {
                final TableCell<Checkout, Void> cell = new TableCell<Checkout, Void>() {

                    private final Button btn = new Button("DELETE");

                    {
                        btn.setOnAction(e -> {
                            Checkout data = getTableView().getItems().get(getIndex());
                            boolean isConfirm = ViewUtil.confirmAlert("DELETE '" + product.getName() + "' CHECK-OUT?");

                            if (isConfirm) {
                                inventoryController.deleteCheckout(data);
                                ViewUtil.alert("INVENTORY CHECK-OUT DELETED!");
                                init();
                            } else {
                                ViewUtil.alert("DELETE CANCELLED!");
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

        return colBtn;
    }

    private TableColumn getDiscrepancyUpdateButton() {
        TableColumn<Discrepancy, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Discrepancy, Void>, TableCell<Discrepancy, Void>> cellFactory = new Callback<TableColumn<Discrepancy, 
            Void>, TableCell<Discrepancy, Void>>() {
            
            @Override
            public TableCell<Discrepancy, Void> call(final TableColumn<Discrepancy, Void> param) {
                final TableCell<Discrepancy, Void> cell = new TableCell<Discrepancy, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction(e -> {
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

        return colBtn;
    }

    private TableColumn getDiscrepancyDeleteButton() {
        TableColumn<Discrepancy, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Discrepancy, Void>, TableCell<Discrepancy, Void>> cellFactory = new Callback<TableColumn<Discrepancy, 
            Void>, TableCell<Discrepancy, Void>>() {
            
            @Override
            public TableCell<Discrepancy, Void> call(final TableColumn<Discrepancy, Void> param) {
                final TableCell<Discrepancy, Void> cell = new TableCell<Discrepancy, Void>() {

                    private final Button btn = new Button("DELETE");

                    {
                        btn.setOnAction(e -> {
                            Discrepancy data = getTableView().getItems().get(getIndex());
                            
                            boolean isConfirm = ViewUtil.confirmAlert("DELETE '" + product.getName() + "' DISCREPANCY?");

                            if (isConfirm) {
                                discrepancyController.deleteDiscrepancy(data);
                                ViewUtil.alert("INVENTORY DISCREPANCY DELETED!");
                                init();
                            } else {
                                ViewUtil.alert("DELETE CANCELLED!");
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

        return colBtn;
    }
}
