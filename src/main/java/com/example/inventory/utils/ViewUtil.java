package com.example.inventory.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;

import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;

import javafx.scene.paint.Color;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.util.Optional;

public class ViewUtil {
	public static void alert(String info) {
		new Alert(Alert.AlertType.INFORMATION, info).show();
	}
	public static void errorAlert(String error) {
		new Alert(Alert.AlertType.ERROR, error).show();
	}

	public static void exceptionAlert(String exception) {
        new Alert(Alert.AlertType.ERROR, exception).show();
        System.err.println(exception);
    }

    public static boolean confirmAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, info);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        
        return false; 
    }

    public static TextField createTextField(String text) {
        TextField dataField = new TextField();
        dataField.setPromptText(text);
        dataField.setFont(Font.font("Monospace", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        // dataField.setAlignment(Pos.CENTER_LEFT);
        dataField.setPrefWidth(250); 
        dataField.setMaxHeight(40);
        dataField.setPadding(new Insets(10, 10, 10, 10));

        return dataField;
    }

    public static TextField createTextFieldWithText(String text) {
        TextField dataField = new TextField();
        dataField.setText(text);
        dataField.setFont(Font.font("Monospace", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        // dataField.setAlignment(Pos.CENTER_LEFT);
        dataField.setPrefWidth(250); 
        dataField.setMaxHeight(40);
        dataField.setPadding(new Insets(10, 10, 10, 10));

        return dataField;
    }

    public static Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(200); 
        button.setMaxHeight(40);
        button.setPadding(new Insets(10, 10, 10, 10));

        return button;
    }

    public static Button createDeleteButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(200); 
        button.setMaxHeight(40);
        button.setPadding(new Insets(10, 10, 10, 10));

        button.setBackground(new Background(
            new BackgroundFill(Color.RED, 
                CornerRadii.EMPTY, new Insets(10, 10, 10, 10))
        ));

        return button;
    }

    public static Pane createFieldPane(Control dataField, String title) {
        // title label
        Label titleLabel = new Label(title);
        // titleLabel.setFont(Font.font("Monospace", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        titleLabel.setAlignment(Pos.CENTER_RIGHT);
        titleLabel.setPrefWidth(150); 
        titleLabel.setMaxHeight(40);
        titleLabel.setPadding(new Insets(10, 10, 10, 10));
    	
        TilePane tile = new TilePane();
	    tile.setPrefColumns(2);
	    tile.getChildren().addAll(titleLabel, dataField);

	    return tile;
    }
}