package com.guigarage.fx.grid.demo;

import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import com.guigarage.fx.grid.demo.util.ColoredAndAnimatedGridCell;
import com.guigarage.fx.grid.demo.util.JGridControl;

public class GridFXDemo4 extends Application {

	private GridView<Color> myGrid;
	
	public static void main(String[] args) {
		GridFXDemo4.launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JGridFX Demo 4");
		
		final ObservableList<Color> list = FXCollections.<Color>observableArrayList();
		Callback<GridView<Color>, GridCell<Color>> cellFactory = new Callback<GridView<Color>, GridCell<Color>>() {
			
			@Override
			public GridCell<Color> call(GridView<Color> arg0) {
				return new ColoredAndAnimatedGridCell();
			}
		};

		//WRONG
//		myGrid = GridViewBuilder.create(Color.class).cellHeight(35).items(list).cellFactory(cellFactory).build();
		
		//WORKING
//		myGrid = new GridView<>(list);
//		myGrid.setCellFactory(cellFactory);
//		myGrid.setCellHeight(20);
//		myGrid.setCellWidth(20);
		
		//WRONG
		myGrid = new GridView<>();
		myGrid.setItems(list);
		myGrid.setCellFactory(cellFactory);
		
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < 100; i++) {
			list.add(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1.0));
		}

		final BorderPane root = new BorderPane();
		root.setTop(myGrid);
		root.setBottom(new JGridControl(myGrid));
		Scene scene = new Scene(root, 540, 210);		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}