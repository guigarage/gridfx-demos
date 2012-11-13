package com.guigarage.fx.grid.demo;

import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import com.guigarage.fx.grid.cell.ColorGridCell;

public class JGridFXDemo3 extends Application {

	private GridView<Color> myGrid;
	
	public static void main(String[] args) {
		JGridFXDemo3.launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JGridFX Demo 3");
		
		final ObservableList<Color> list = FXCollections.<Color>observableArrayList();
		myGrid = new GridView<>(list);
		
		myGrid.setCellFactory(new Callback<GridView<Color>, GridCell<Color>>() {
			
			@Override
			public GridCell<Color> call(GridView<Color> arg0) {
				return new ColorGridCell();
			}
		});
		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < 100; i++) {
			list.add(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1.0));
		}

		final BorderPane root = new BorderPane();
		root.setTop(myGrid);
		root.setBottom(new JGridControl(myGrid));
		
		Scene scene = new Scene(root, 540, 210);

		
		root.addEventHandler(ZoomEvent.ANY, new EventHandler<ZoomEvent>() {

			@Override
			public void handle(ZoomEvent arg0) {
				System.out.println("Zoom");
			}
		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}