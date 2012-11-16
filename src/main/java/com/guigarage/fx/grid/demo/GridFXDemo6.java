package com.guigarage.fx.grid.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import com.guigarage.fx.grid.cell.DefaultGridCell;
import com.guigarage.fx.grid.util.GridPaginationHelper;

public class GridFXDemo6 extends Application {

	public static void main(String[] args) {
		GridFXDemo1.launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JGridFX Demo 6");
		
		final ObservableList<String> list = FXCollections.<String>observableArrayList();
		for(int i=0; i < 10000; i++) {
			list.add(i + "");
		}
		

		Pagination pagination = new Pagination();
		Callback<GridView<String>, GridCell<String>> cellFactory = new Callback<GridView<String>, GridCell<String>>() {
			
			@Override
			public GridCell<String> call(GridView<String> arg0) {
				return new DefaultGridCell<String>();
			}
		};
		
		new GridPaginationHelper<>(pagination, list, cellFactory);
		Scene scene = new Scene(pagination, 540, 210);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
