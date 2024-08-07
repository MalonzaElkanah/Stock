package com.example.inventory.users.login;

import com.example.inventory.users.UserModel;
import com.example.inventory.users.User;
import com.example.inventory.users.AdminView;
import com.example.inventory.utils.ViewUtil;

import java.util.Optional;

public class LoginController {
	UserModel model;
	LoginView view;

	public LoginController(LoginView view) {
		this.model = new UserModel();
		this.view = view;

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

			new AdminView(view.getAppView(), user.get());
			return true;
		} else {
			ViewUtil.errorAlert("Error: Invalid username or password!");
		}
		
		return false;
	} 
}
