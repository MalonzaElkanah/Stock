package com.example.inventory.users;

// import com.example.inventory.users.AdminView;
import com.example.inventory.product.Product;
import com.example.inventory.utils.ViewUtil;
import com.example.inventory.utils.ValidatorUtil;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;

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
import java.util.Set;
import java.util.HashSet;


public class UserCreateView {
    private BorderPane root = new BorderPane();
    private UserController controller;
    private AdminView view;
    private Set<Role> roles = new HashSet<>();

	public UserCreateView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new UserController();

		init();
	}

	private void init() {
        Label label = new Label("ADD USER");
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 40, 10));

        // User TextInputs
        TextField nameField = ViewUtil.createTextField("Name");
        TextField userNameField = ViewUtil.createTextField("Username");
        TextField emailField = ViewUtil.createTextField("Email");
        TextField passwordField = ViewUtil.createTextField("Password");
        CheckBox enabledField = new CheckBox("Enable User");

        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);

        tile.getChildren().addAll(ViewUtil.createFieldPane(nameField, "Name: "),
            ViewUtil.createFieldPane(userNameField, "Username: "),
        	ViewUtil.createFieldPane(emailField, "Email: "),
            ViewUtil.createFieldPane(passwordField, "Password: "),
            ViewUtil.createFieldPane(enabledField, " "));

        // Submit button
        Button submitButton = new Button("ADD USER");
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
            if (!nameField.getText().isBlank() &&
                !emailField.getText().isBlank() &&
                !userNameField.getText().isBlank() &&
                !passwordField.getText().isBlank()) {

                boolean isEnabled = enabledField.isSelected();

                User user = new User(nameField.getText(),
                    emailField.getText(),
                    userNameField.getText(),
                    passwordField.getText(),
                    isEnabled,
                    roles);

                ValidatorUtil validator = new ValidatorUtil(user); 

                if (validator.isValid()) {
                    controller.createUser(user);
                    new UserListView(view);
                } else {
                    ViewUtil.errorAlert(validator.violation());
                }
                
            } else if (nameField.getText().isBlank()) {
                ViewUtil.errorAlert("ERROR: Name Field is EMPTY!");
            } else if (userNameField.getText().isBlank()) {
                ViewUtil.errorAlert("ERROR: UserName Field is EMPTY!");
            } else if (passwordField.getText().isBlank()) {
                ViewUtil.errorAlert("ERROR: Password Field is EMPTY!");
            }else if (emailField.getText().isBlank()) {
                ViewUtil.errorAlert("ERROR: Email Field is EMPTY!");
            }
        });
    }
}
