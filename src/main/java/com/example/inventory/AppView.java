package com.example.inventory;

import com.example.inventory.users.login.LoginView;

//import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AppView {
	private BorderPane root = new BorderPane();

	public AppView() {
        LoginView loginView = new LoginView(this);

		root.setPrefSize(1450, 730);
        // root.setCenter(loginView.getRoot());
	}

	public void setRoot(Pane pane) {
		root.setCenter(pane);
	}

	public Pane getRoot() {
		return root;
	}
} 