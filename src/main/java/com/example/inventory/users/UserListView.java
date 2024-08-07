package com.example.inventory.users;

import com.example.inventory.utils.ViewUtil;
// import com.example.inventory.users.AdminView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;

import javafx.event.ActionEvent;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.UUID;


public class UserListView {
    private BorderPane root = new BorderPane();
    private TableView<User> table = new TableView<User>();
    private UserController controller;
    private AdminView view;
    private ObservableList<User> users;

	public UserListView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new UserController();
        this.users = controller.getUserList();

		init();
	}

	public void init() {
        // User TABLE 
        Label label = new Label("Users List");
        label.setFont(new Font(25));
        label.setPadding(new Insets(10, 10, 10, 10));

        table.setItems(users);

        TableColumn<User, String> nameCol = new TableColumn<User, String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        TableColumn<User, String>emailCol = new TableColumn<User, String>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        TableColumn<User, String> userNameCol = new TableColumn<User, String>("User Name");
        userNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));

        TableColumn<User, Role> roleCol = new TableColumn<User, Role>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<User, Role>("role"));

        TableColumn<User, LocalDateTime> createdCol = new TableColumn<User, LocalDateTime>("Added Date");
        createdCol.setCellValueFactory(new PropertyValueFactory<User, LocalDateTime>("created"));
        
		table.getColumns().setAll(nameCol, emailCol, userNameCol, roleCol, createdCol);
        table.setEditable(true);
        addActiveCheckboxToTable();
        addUpdateButtonToTable();
        
        BorderPane centerPane = new BorderPane();
        centerPane.setTop(label);
        centerPane.setCenter(table);

        root.setCenter(centerPane);
	}

    private void addActiveCheckboxToTable() {
        TableColumn<User, Boolean> colCheckBox = new TableColumn<User, Boolean>("Activated");

        Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory = new Callback<TableColumn<User, 
            Boolean>, TableCell<User, Boolean>>() {
            
            @Override
            public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
                final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {

                    private final CheckBox cb = new CheckBox();

                    {
                        cb.setOnAction(e -> {
                            User user = getTableView().getItems().get(getIndex());

                            if (cb.isSelected() && !user.isEnabled()) {
                                controller.activateUser(user);
                            } else if (!cb.isSelected() && user.isEnabled()) {
                                controller.deactivateUser(user);
                            }

                        });
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            cb.setIndeterminate(false);
                            cb.setSelected(item);

                            setGraphic(cb);
                        }
                    }
                };
                return cell;
            }
        };

        colCheckBox.setCellFactory(cellFactory);
        colCheckBox.setCellValueFactory(new PropertyValueFactory<User, Boolean>("enabled"));

        table.getColumns().add(colCheckBox);
    }

    private void addUpdateButtonToTable() {
        TableColumn<User, Void> colBtn = new TableColumn("");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, 
            Void>, TableCell<User, Void>>() {
            
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            User data = getTableView().getItems().get(getIndex());
                            new UserUpdateView(view, data);
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

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane pane) {
    	view.setRoot(pane);
    }
}
