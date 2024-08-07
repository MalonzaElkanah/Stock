package com.example.inventory.users;

import com.example.inventory.AppView;
import com.example.inventory.users.login.LoginView;
import com.example.inventory.product.ProductListView;
import com.example.inventory.product.ProductCreateView;
import com.example.inventory.inventory.InventoryListView;
import com.example.inventory.inventory.CheckinListView;
import com.example.inventory.inventory.CheckoutListView;
import com.example.inventory.discrepancy.DiscrepancyListView;
import com.example.inventory.reports.ReportView;
import com.example.inventory.reports.DashboardView;
import com.example.inventory.utils.ViewUtil;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToolBar;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AdminView {
    private BorderPane root = new BorderPane();
    private BorderPane centerPane = new BorderPane(); 
    private UserController controller = new UserController();
    private AppView view;

    private User user;

	public AdminView(AppView view, User user) {
        // check role is admin
        if (user.checkRole(Role.ADMIN)) {
            this.user = user;
            this.view = view;
            this.view.setRoot(root);

            init();
            new DashboardView(this);
        } else {
            ViewUtil.errorAlert("Error: Admin Login Only!");
            new LoginView(view);
        }
	}

	public void init() {
		// title
        Label titleLabel = new Label("Inventory Management System");
        titleLabel.setFont(new Font(35));

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPrefHeight(1);
        separator.setPrefWidth(1500);
        separator.setBorder(new Border(new BorderStroke(null, null, Color.web("#d9d9d9"), null,
            BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, 
            BorderStrokeStyle.NONE,
            null, 
            BorderStroke.MEDIUM,
            new Insets(0, 0, 0, 0))));

        VBox headerPane = new VBox();
        headerPane.setAlignment(Pos.CENTER);
        headerPane.setPadding(new Insets(0, 0, 0, 0));

        VBox titlePane = new VBox(10);
        titlePane.setAlignment(Pos.CENTER);
        titlePane.setPadding(new Insets(30, 0, 0, 0));
        titlePane.getChildren().addAll(titleLabel, separator);

        headerPane.getChildren().addAll(getMenuBar(), titlePane);
        /*
		HBox pane = new HBox(40);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(userButton,
        	productButton,
        	inventoryButton,
    		reportButton);
        */
        VBox pane = new VBox(20);
        Label label = new Label("Dashboard");
        label.setFont(new Font(25));
        label.setPadding(new Insets(10, 10, 10, 10));

        pane.getChildren().addAll(label);

        centerPane.setCenter(pane);

        TreeView tree = treeView();

        SplitPane splitter = new SplitPane(tree, centerPane);
        splitter.setOrientation(Orientation.HORIZONTAL);
        splitter.setDividerPositions(0.2);

        root.setCenter(splitter);

        root.setTop(headerPane);
		// root.setCenter(pane);

		// Action Listeners
	}

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane pane) {
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        this.centerPane.setCenter(scrollPane);
    }

    public User getUser() {
        return this.user;
    }

    public AppView getAppView() {
        return this.view;
    }

    private Button navButton(String name, String image) {
        Button button;
        try {
            ImageView imageView = new ImageView(new Image(
            	new FileInputStream(image)));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

        	button = new Button(name, imageView);
        } catch (FileNotFoundException e) {
            button = new Button(name);
            System.out.println("FileNotFoundException: " + image);
            System.out.println(e);
        }
        button.setContentDisplay(ContentDisplay.TOP);
        button.setFont(Font.font("Courier New", FontWeight.BOLD, 25));
		button.setMinWidth(200); 
		button.setMinHeight(200);
		// button.setWrapText(true);
		button.setPadding(new Insets(10, 10, 10, 10));

        return button;
    }

    private TreeItem navTreeItem(String name, String image) {
        TreeItem item;

        try {
            ImageView imageView = new ImageView(new Image(
                new FileInputStream(image)));
            imageView.setFitWidth(30);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            item = new TreeItem(name, imageView);
        } catch (FileNotFoundException e) {
            item = new TreeItem(name);
            System.out.println("FileNotFoundException: " + image);
            System.out.println(e);
        }

        return item;
    }

    private TreeView treeView() {
        TreeItem rootNode = new TreeItem("Root");
        TreeItem homeNode = navTreeItem("Home", "src/main/resources/icon/home.png");
        // products
        TreeItem productNode = navTreeItem("Products", "src/main/resources/icon/food.png");
        TreeItem listProductNode = new TreeItem("List Products");
        TreeItem createProductNode = new TreeItem("Create Product");
        productNode.getChildren().addAll(listProductNode, createProductNode);
        productNode.setExpanded(true);

        // Inventory
        TreeItem invNode = navTreeItem("Inventory", "src/main/resources/icon/bill.png");
        TreeItem checkinListNode = new TreeItem("Checkin Stock");
        TreeItem checkoutListNode = new TreeItem("Sold Stock");
        TreeItem discrepancyListNode = new TreeItem("Discrepancy Stock ");
        invNode.getChildren().addAll(checkinListNode, checkoutListNode, discrepancyListNode);
        invNode.setExpanded(true);

        //users
        TreeItem userNode = navTreeItem("Users", "src/main/resources/icon/manager.png");
        TreeItem createUserNode = new TreeItem("Create User");
        TreeItem userListNode = new TreeItem("List User");
        TreeItem changePasswordNode = new TreeItem("Change Password");
        TreeItem logoutNode = new TreeItem("Logout");
        userNode.getChildren().addAll(userListNode, createUserNode, changePasswordNode, logoutNode);
        userNode.setExpanded(true);

        // Reports
        TreeItem reportNode = navTreeItem("Reports", "src/main/resources/icon/information.png");

        rootNode.getChildren().addAll(homeNode, 
            productNode, invNode, userNode, reportNode
        );

        TreeView treeView = new TreeView(rootNode);
        treeView.setShowRoot(false);
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SelectionModel<TreeItem> selectionModel = treeView.getSelectionModel();
        selectionModel.selectedIndexProperty().addListener(
            new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                TreeItem selectedTreeItem = selectionModel.getSelectedItem();

                if (selectedTreeItem == rootNode) {
                    System.out.println("Root");
                } else if (selectedTreeItem == homeNode) {
                    System.out.println("Home");
                    new AdminView(view, user);
                } else if (selectedTreeItem == productNode) {
                    System.out.println("Product");
                    new ProductListView(AdminView.this);
                } else if (selectedTreeItem == createProductNode) {
                    new ProductCreateView(AdminView.this);
                } else if (selectedTreeItem == listProductNode) {
                    new ProductListView(AdminView.this);
                } else if (selectedTreeItem == invNode) {
                    System.out.println("Inventory");
                    new InventoryListView(AdminView.this);
                } else if (selectedTreeItem == checkinListNode){
                    new CheckinListView(AdminView.this);
                } else if (selectedTreeItem == checkoutListNode){
                    new CheckoutListView(AdminView.this);
                } else if (selectedTreeItem == discrepancyListNode) {
                    new DiscrepancyListView(AdminView.this);
                } else if (selectedTreeItem == userNode) {
                    new UserListView(AdminView.this);
                } else if (selectedTreeItem == userListNode) {
                    new UserListView(AdminView.this);
                } else if (selectedTreeItem == createUserNode) {
                    new UserCreateView(AdminView.this);
                } else if (selectedTreeItem == changePasswordNode) {
                    new ChangePasswordView(AdminView.this);
                } else if (selectedTreeItem == logoutNode) {
                    new LoginView(view);
                }  else if (selectedTreeItem == reportNode) {
                    new ReportView(AdminView.this);
                    System.out.println("Report");
                } else {
                    System.out.println("Ops: No data here!!!");
                }
            }
        });

        return treeView;
    }

    private MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu productMenu = new Menu("Product");
        MenuItem listProductMenuItem = new MenuItem("List Product");
        MenuItem createProductMenuItem = new MenuItem("Create Product");
        
        productMenu.getItems().addAll(
            listProductMenuItem,
            createProductMenuItem
        );

        Menu userMenu = new Menu("Users");
        MenuItem listUserMenuItem = new MenuItem("List User");
        MenuItem createUserMenuItem = new MenuItem("Create User");
        userMenu.getItems().addAll(listUserMenuItem, createUserMenuItem);

        Menu profileMenu = new Menu("Profile");
        MenuItem changePasswordMenuItem = new MenuItem("Change Password");
        MenuItem logoutMenuItem = new MenuItem("Logout");
        profileMenu.getItems().addAll(changePasswordMenuItem, logoutMenuItem);

        Menu invMenu = new Menu("Inventory");
        MenuItem listInvMenuItem = new MenuItem("My Inventory");
        MenuItem checkinInvMenuItem = new MenuItem("Checkin Stocks");
        MenuItem checkoutInvMenuItem = new MenuItem("Sold Stocks");
        MenuItem discrepancyInvMenuItem = new MenuItem("Stock Discrepancies");
        invMenu.getItems().addAll(listInvMenuItem,
            checkinInvMenuItem, checkoutInvMenuItem, discrepancyInvMenuItem);

        Menu otherMenu = new Menu("Others");
        MenuItem homeMenuItem = new MenuItem("Home");
        MenuItem reportMenuItem = new MenuItem("Report");
        otherMenu.getItems().addAll(homeMenuItem, reportMenuItem);

        // Action Listeners
        createProductMenuItem.setOnAction(a -> new ProductCreateView(AdminView.this));
        listProductMenuItem.setOnAction(a -> new ProductListView(AdminView.this));

        listUserMenuItem.setOnAction(a -> new UserListView(AdminView.this));
        createUserMenuItem.setOnAction(a -> new UserCreateView(AdminView.this));

        changePasswordMenuItem.setOnAction(a -> new ChangePasswordView(AdminView.this));
        logoutMenuItem.setOnAction(a -> new LoginView(view));

        listInvMenuItem.setOnAction(a -> new InventoryListView(AdminView.this));
        checkinInvMenuItem.setOnAction(a -> new CheckinListView(AdminView.this));
        checkoutInvMenuItem.setOnAction(a -> new CheckoutListView(AdminView.this));
        discrepancyInvMenuItem.setOnAction(a -> new DiscrepancyListView(AdminView.this));

        homeMenuItem.setOnAction(a -> new AdminView(view, user));
        reportMenuItem.setOnAction(a -> new ReportView(AdminView.this));

        menuBar.getMenus().addAll(productMenu, invMenu, userMenu, profileMenu, otherMenu);

        return menuBar;
    }
}
