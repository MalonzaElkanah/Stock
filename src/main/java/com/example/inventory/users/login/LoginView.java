package com.example.inventory.users.login;

import com.example.inventory.AppView;
// import com.example.inventory.users.UserController;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import javafx.scene.text.Font;

import javafx.geometry.Pos;
import javafx.geometry.Insets;


public class LoginView {
    private BorderPane root = new BorderPane();
    private Label statusLabel = new Label("Please Login");
    private Button loginButton = new Button("Login");
    private TextField userNameField = new TextField();
    private PasswordField passwordField = new PasswordField();

    private LoginController controller;
    private AppView view;

	public LoginView(AppView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new LoginController(this);

		init();
	}

	public void init() {
		statusLabel.setFont(new Font(16));

		userNameField.setPromptText("Username");
		userNameField.setMaxWidth(300); 
		userNameField.setMaxHeight(40);
		userNameField.setPadding(new Insets(10, 10, 10, 10));

		passwordField.setPromptText("Password");
		passwordField.setMaxWidth(300); 
		passwordField.setMaxHeight(40);
		passwordField.setPadding(new Insets(10, 10, 10, 10));

		loginButton.setMaxWidth(100); 
		loginButton.setMaxHeight(40);
		loginButton.setPadding(new Insets(10, 10, 10, 10));

		VBox pane = new VBox(40);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(statusLabel,
        	userNameField,
        	passwordField,
    		loginButton);

        Label titleLabel = new Label("Inventory Management System");
        titleLabel.setFont(new Font(35));

        VBox headerPane = new VBox(50);
        headerPane.setAlignment(Pos.CENTER);
        headerPane.setPadding(new Insets(30, 0, 0, 0));
        headerPane.getChildren().addAll(titleLabel);

        root.setTop(headerPane);
		root.setCenter(pane);

		// Action Listeners
		loginButton.setOnAction(a -> {
            controller.login(
            	userNameField.getText(),
        		passwordField.getText());
        });
	}

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane pane) {
    	view.setRoot(pane);
    }

    public AppView getAppView() {
    	return view;
    }
}
