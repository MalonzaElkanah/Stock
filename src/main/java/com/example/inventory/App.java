package com.example.inventory;

// import com.example.inventory.product.ProductController;
import com.example.inventory.users.UserModel;
import com.example.inventory.users.User;
import com.example.inventory.users.Role;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.time.LocalDateTime;

public class App extends Application {

    public void start(Stage stage) {
        AppView view = new AppView();

        Scene scene = new Scene(view.getRoot());
        JMetro metro = new JMetro(scene, Style.LIGHT);
        stage.setTitle("Malone DBMS");
        stage.setScene(scene);
        stage.show();

        try {
            UserModel model = new UserModel();
            model.create(new User("Admin",
                "admin@app.com",
                "admin",
                "admin",
                true,
                Role.ADMIN));
            // model.create(new User("Elkanah",
            //    "elkanah@app.com",
            //    "elkanah.app",
            //    "Pass1234",
            //    false,
            //    Role.STOREKEEPER));
            // System.out.println("\n Users: \n");
            // System.out.println(model.findAll());
        } catch (Exception e) {
            System.out.println("ERROR RUNNING APPLICATION: ");
            System.out.println(e);
        }
    }

    public static void main( String[] args ) {
        System.out.println("JavaFX Application Initializing...!");
        launch(args);
    }
}
