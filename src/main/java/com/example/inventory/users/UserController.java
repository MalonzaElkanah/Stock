package com.example.inventory.users;

import com.example.inventory.utils.ViewUtil;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public class UserController {
	UserModel model;

	public UserController() {
		this.model = new UserModel();

		try {
			System.out.println("\n Users: \n");
			System.out.println(model.findAll());
		} catch (Exception e) {
			System.out.println(e);
		}		
	}

	public boolean login(String userName, String password) {
		Optional<User> user = model.findByUserName(userName);

		if (user.isEmpty()) {
			ViewUtil.errorAlert("Error: Invalid username or password!");
		} else if (!user.get().isEnabled()) {
			ViewUtil.errorAlert("Error: User not Activated! Please contact admin.");
		} else if (user.get().checkPassword(password)) {
			System.out.println("User login");
			return true;
		} else {
			ViewUtil.errorAlert("Error: Invalid username or password!");
		}
		
		return false;
	}

	public ObservableList<User> getUserList() {
		ObservableList<User> users = FXCollections.<User>observableArrayList();
		users.setAll(model.findAll());
		return users;
	}

	public void createUser(User user) {
		// todo validate User
		model.create(user);
	}

	public void updateUser(User user) {
		// todo validate User
		model.update(user);
	}

	public void activateUser(User user) {
		user.setEnabled(true);
		model.update(user);
	}

	public void deactivateUser(User user) {
		user.setEnabled(false);
		model.update(user);
	}

	public void changeUserPassword(User user, String password) {
		user.setPassword(password);
		model.update(user);
	}
}
