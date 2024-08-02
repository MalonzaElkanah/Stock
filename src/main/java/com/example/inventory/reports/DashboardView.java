package com.example.inventory.reports;

import com.example.inventory.users.AdminView;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
// import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
// import javafx.scene.layout.VBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.collections.ObservableList;


public class DashboardView {
    private BorderPane root = new BorderPane();
    private ReportController controller;
    private AdminView view;

	public DashboardView(AdminView view) {
		this.view = view;
		this.view.setRoot(root);
		this.controller = new ReportController();

		init();
	}

	public void init() {
        
        Label label = new Label("Dasboard");
        label.setFont(new Font(25));
        label.setPadding(new Insets(10, 10, 10, 10));

        // MAIN DATA CHART
        final CategoryAxis xAxisMain = new CategoryAxis();
        xAxisMain.setLabel("Days");
        final NumberAxis yAxisMain = new NumberAxis();
        final LineChart<String, Number> lineChartMain = new LineChart<String, Number>(xAxisMain, yAxisMain);
        lineChartMain.setTitle("Daily Sales");
        lineChartMain.getData().addAll(controller.getSaleCountChartDataList("DAILY"));
        lineChartMain.setPrefHeight(400);

        HBox mainSalePane = new HBox(10);
        mainSalePane.setPadding(new Insets(10, 10, 10, 10));
        mainSalePane.getChildren().addAll(lineChartMain);
        mainSalePane.setAlignment(Pos.CENTER_LEFT);

        // Pie-charts
        ObservableList<PieChart.Data> salePieChartData = controller.getSalePieChartDataList();
        final PieChart salePieChart = new PieChart(salePieChartData);
        salePieChart.setTitle("Sales Amount");

        ObservableList<PieChart.Data> saleCountPieChartData = controller.getSaleCountPieChartDataList();
        final PieChart saleCountPieChart = new PieChart(saleCountPieChartData);
        saleCountPieChart.setTitle("Sales Item Count");

        HBox piePane = new HBox(10);
        piePane.setPadding(new Insets(10, 10, 10, 10));
        piePane.getChildren().addAll(salePieChart, saleCountPieChart);
        piePane.setAlignment(Pos.CENTER_LEFT);

        // Mini graphs
        final CategoryAxis xAxisMonthly = new CategoryAxis();
        xAxisMonthly.setLabel("Months");
        final NumberAxis yAxisMonthly = new NumberAxis();
        final LineChart<String, Number> lineChartMonthly = new LineChart<String, Number>(xAxisMonthly, yAxisMonthly);
        lineChartMonthly.setTitle("Monthly Sales");
        lineChartMonthly.getData().addAll(controller.getSaleCountChartDataList("MONTHLY"));

        final CategoryAxis xAxisYearly = new CategoryAxis();
        xAxisYearly.setLabel("Yearly");
        final NumberAxis yAxisYearly = new NumberAxis();
        final LineChart<String, Number> lineChartYearly = new LineChart<String, Number>(xAxisYearly, yAxisYearly);
        lineChartYearly.setTitle("Yearly Sales");
        lineChartYearly.getData().addAll(controller.getSaleCountChartDataList("YEARLY"));

        HBox miniPane = new HBox(10);
        miniPane.setPadding(new Insets(10, 10, 10, 10));
        miniPane.getChildren().addAll(lineChartMonthly, lineChartYearly);
        miniPane.setAlignment(Pos.CENTER_LEFT);

        // Inventory TABLE 
        
        TilePane tile = new TilePane();
        tile.setMaxHeight(600);
        tile.setTileAlignment(Pos.CENTER_LEFT);
	    tile.setVgap(30);
	    tile.setHgap(10);
	    tile.setPrefColumns(1);
        tile.getChildren().addAll(mainSalePane, piePane, miniPane);

        root.setCenter(tile);
        root.setTop(label);
	}

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane pane) {
    	view.setRoot(pane);
    }

}
