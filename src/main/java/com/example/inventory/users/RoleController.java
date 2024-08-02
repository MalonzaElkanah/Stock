package com.example.inventory.users;

public class RoleController {
	RoleModel model;

	public RoleController() {
		this.model = new RoleModel();

		try {
			System.out.println("\n ROLES: \n");
			System.out.println(model.findAll());
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
}
