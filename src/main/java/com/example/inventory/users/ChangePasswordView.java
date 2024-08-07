package com.example.inventory.users;

// import com.example.inventory.users.AdminView;
import com.example.inventory.users.login.LoginView;
import com.example.inventory.product.Product;
import com.example.inventory.utils.ViewUtil;
import com.example.inventory.utils.ValidatorUtil;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;

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


public class ChangePasswordView {
    private BorderPane root = new BorderPane();
    private UserController controller;
    private AdminView view;
    private User user;

	public ChangePasswordView(AdminView view) {
		this.view = view;
        this.user = view.getUser();
		this.view.setRoot(root);
		this.controller = new UserController();

		init();
	}

	private void init() {
        Label label = new Label("CHANGE PASSWORD");
        label.setFont(new Font(25));
        label.setPadding(new Insets(30, 10, 40, 10));

        // User TextInputs
        PasswordField currentPasswordField = ViewUtil.createPasswordField("Current Password");
        PasswordField newPasswordField = ViewUtil.createPasswordField("New Password");
        PasswordField confirmPasswordField = ViewUtil.createPasswordField("Confirm New Password");

        TilePane tile = new TilePane();
        tile.setMaxWidth(1200);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(2);

        tile.getChildren().addAll(ViewUtil.createFieldPane(currentPasswordField, "Current Password: "),
            ViewUtil.createFieldPane(newPasswordField, "New Password: "),
        	ViewUtil.createFieldPane(confirmPasswordField, "Confirm New Password: "));

        // Submit button
        Button submitButton = new Button("CHANGE");
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
            if (!currentPasswordField.getText().isBlank() &&
                !newPasswordField.getText().isBlank() &&
                !confirmPasswordField.getText().isBlank()) {

                if (newPasswordField.getText().equals(confirmPasswordField.getText())) {
                    if (user.checkPassword(currentPasswordField.getText())) {
                        if (!newPasswordField.getText().equals(currentPasswordField.getText())) {
                            controller.changeUserPassword(user, newPasswordField.getText());
                            new LoginView(view.getAppView());
                        } else {
                            ViewUtil.errorAlert(
                                "Current and New Password should be different!");
                        }
                    } else {
                        ViewUtil.errorAlert("Current Password is Wrong!");
                    }
                } else {
                    ViewUtil.errorAlert(
                        "New and Confirm Password don't Match!");
                }
                
            } else if (currentPasswordField.getText().isBlank()) {
                ViewUtil.errorAlert("ERROR: Current Password Field is EMPTY!");
            } else if (newPasswordField.getText().isBlank()) {
                ViewUtil.errorAlert("ERROR: New Password Field is EMPTY!");
            } else if (confirmPasswordField.getText().isBlank()) {
                ViewUtil.errorAlert("ERROR: Confirm Password Field is EMPTY!");
            } else {
                ViewUtil.errorAlert("ERROR: Unexpected error occured!");
            }
        });
    }
}
