package com.guigarage.fx.grid.demo;

import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import com.guigarage.fx.grid.cell.ColorGridCell;
import com.guigarage.fx.grid.util.GridPaginationHelper;

public class GridFXDemo7 extends Application {

	public static void main(String[] args) {
		GridFXDemo1.launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JGridFX Demo 7");
		
		final ObservableList<Color> list = FXCollections.<Color>observableArrayList();
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < 10000; i++) {
			list.add(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1.0));
		}

		

		Pagination pagination = new Pagination();
		Callback<GridView<Color>, GridCell<Color>> cellFactory = new Callback<GridView<Color>, GridCell<Color>>() {
			
			@Override
			public GridCell<Color> call(GridView<Color> arg0) {
				return new ColorGridCell();
			}
		};
		
		GridPaginationHelper<Color, GridView<Color>> paginationHelper = new GridPaginationHelper<>(pagination, list, cellFactory);
		
		Slider columnWidthSlider = SliderBuilder.create().min(10).max(512).build();
		columnWidthSlider.valueProperty().bindBidirectional(paginationHelper.cellWidthProperty());
		columnWidthSlider.valueProperty().bindBidirectional(paginationHelper.cellHeightProperty());
		
		BorderPane pane = new BorderPane();
		pane.setCenter(pagination);
		pane.setTop(HBoxBuilder.create().children(new Label("cellsize"), columnWidthSlider).build());
		
		Scene scene = new Scene(pane, 540, 210);
		primaryStage.setScene(scene);
		primaryStage.show();
	}}
