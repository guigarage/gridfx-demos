package com.guigarage.fx.grid.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.GlowBuilder;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import org.apache.commons.io.IOUtils;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import com.guigarage.fx.grid.demo.itunes.ITunesDataSource;
import com.guigarage.fx.grid.demo.itunes.ITunesDataSourceReader;
import com.guigarage.fx.grid.demo.itunes.ITunesMedia;
import com.guigarage.fx.grid.demo.itunes.ITunesMediaType;
import com.guigarage.fx.grid.demo.util.ImageFitSizeHelper;

public class JGridFXDemo5 extends Application {

	private GridView<ITunesMedia> myGrid;

	private BorderPane root;

	private File movieFile = new File("temp-trailer.m4v");

	@SuppressWarnings("rawtypes")
	private Future movieLoader;
	
	private MediaView mediaView;
	
	private Label loadingLabel;
	
	public static void main(String[] args) {
		JGridFXDemo4.launch();
	}

	private class MovieGridCell extends GridCell<ITunesMedia> {

		private ScaleTransition scaleInTransition;

		private ScaleTransition scaleOutTransition;
		
		private ImageView previewView;

		private Reflection reflection;
		
		private Label titleLabel;
		
		private Rectangle borderRectangle;
		
		private ImageFitSizeHelper helper;
		
		public MovieGridCell() {
			setCssDependency();
			previewView = new ImageView();
			previewView.setPreserveRatio(true);
			previewView.setSmooth(true);
			previewView.setStyle("-fx-border-color: white;");
			previewView.fitHeightProperty().bind(
					MovieGridCell.this.heightProperty());
			previewView.fitWidthProperty().bind(
					MovieGridCell.this.widthProperty());
			reflection = new Reflection();
			reflection.setFraction(0.05);
			reflection.setBottomOpacity(0);
			reflection.setTopOpacity(0.8);
	        previewView.setEffect(reflection);
	        helper = new ImageFitSizeHelper(previewView);
	        scaleXProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0,
						Number arg1, Number arg2) {
					helper.recalc();
				}
			});
	        scaleYProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0,
						Number arg1, Number arg2) {
					helper.recalc();
				}
			});
	        
	        Glow borderGlow = GlowBuilder.create().level(30).build();
	        BoxBlur blur = new BoxBlur();
	        blur.setWidth(2);
	        blur.setHeight(2);
	        blur.setIterations(1);
	        blur.setInput(borderGlow);
	        borderRectangle = RectangleBuilder.create().fill(Color.TRANSPARENT).strokeWidth(3).stroke(Color.BLUE.deriveColor(1, 1, 1, 0.8)).effect(blur).build();
	        borderRectangle.setOpacity(0.0);
	        borderRectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					if (getItem().getPreviewUrl() != null) {
						if(movieLoader != null) {
							movieLoader.cancel(true);
						}
						loadingLabel.setOpacity(1);
						movieLoader = Executors.newSingleThreadExecutor().submit(
								new Runnable() {
									public void run() {
										try {
											FileOutputStream out = new FileOutputStream(
													movieFile);
											IOUtils.copy(new URL(getItem()
													.getPreviewUrl())
													.openStream(), out);
											IOUtils.closeQuietly(out);
										} catch (Exception e) {
											e.printStackTrace();
										}
										Platform.runLater(new Runnable() {

											@Override
											public void run() {
												mediaView = new MediaView();
												Media myMedia = new Media(
														movieFile.toURI()
																.toString());
												MediaPlayer mediaPlayer = new MediaPlayer(
														myMedia);
												mediaView
														.setMediaPlayer(mediaPlayer);
												mediaView
														.setStyle("-fx-border-color: black;");
												mediaView
														.setPreserveRatio(true);
												root.setCenter(mediaView);
												mediaView.toBack();
												loadingLabel.setOpacity(0);
												mediaPlayer.play();
											}
										});
									}
								});
					} else {
						System.out.println("No Preview!");
					}
				}
			});

	        titleLabel = new Label();
	        titleLabel.setTextFill(Color.WHITE);
	        titleLabel.setText("Title");
	        titleLabel.setOpacity(0.0);
	        
			//TODO:NOT WORKING
//			BorderPane borderPane = new BorderPane();
//			Pane pane = new Pane();
//			pane.getChildren().add(previewView);
//			pane.getChildren().add(borderRectangle);
//			getChildren().add(borderPane);
//			borderPane.setCenter(pane);
//			borderPane.setBottom(titleLabel);

			//WORKING
	        setGraphic(previewView);
//			getChildren().add(previewView);
//			getChildren().add(borderRectangle);
//			getChildren().add(titleLabel);

			itemProperty().addListener(new ChangeListener<ITunesMedia>() {

				@Override
				public void changed(
						ObservableValue<? extends ITunesMedia> arg0,
						ITunesMedia arg1, ITunesMedia arg2) {
					if(arg2.getArtworkUrl100() != null) {
					previewView.setImage(new Image(arg2.getArtworkUrl100()
							.replace("100x100", "400x400"), true));
					}
					titleLabel.setText(arg2.getTrackName());
					
					borderRectangle.widthProperty().bind(helper.imageWidthInViewProperty().multiply(MovieGridCell.this.getScaleX()));
			        borderRectangle.heightProperty().bind(helper.imageHeightInViewProperty().multiply(MovieGridCell.this.getScaleY()));
				}
			});

			addEventHandler(MouseEvent.MOUSE_ENTERED,
					new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							toFront();
							if (scaleOutTransition != null) {
								scaleOutTransition.stop();
							}
							scaleInTransition = ScaleTransitionBuilder.create()
									.fromX(MovieGridCell.this.getScaleX())
									.toX(1.8)
									.fromY(MovieGridCell.this.getScaleY())
									.toY(1.8)
									.interpolator(Interpolator.EASE_IN)
									.duration(Duration.millis(200))
									.node(MovieGridCell.this).build();
							scaleInTransition.play();
							borderRectangle.setOpacity(1.0);
							titleLabel.setOpacity(1.0);
							previewView.setEffect(null);
						}
					});

			addEventHandler(MouseEvent.MOUSE_EXITED,
					new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							if (scaleInTransition != null) {
								scaleInTransition.stop();
							}
							scaleOutTransition = ScaleTransitionBuilder
									.create()
									.fromX(MovieGridCell.this.getScaleX())
									.toX(1.0)
									.fromY(MovieGridCell.this.getScaleY())
									.toY(1.0)
									.interpolator(Interpolator.EASE_OUT)
									.duration(Duration.millis(120))
									.node(MovieGridCell.this).build();
							scaleOutTransition.play();
							borderRectangle.setOpacity(0.0);
							titleLabel.setOpacity(0.0);
							previewView.setEffect(reflection);
						}
					});
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("JGridFX Demo 4");

		myGrid = new GridView<>();
		myGrid.cellWidthProperty().set(200);
		myGrid.cellHeightProperty().set(200);
		myGrid.setCellFactory(new Callback<GridView<ITunesMedia>, GridCell<ITunesMedia>>() {

			@Override
			public GridCell<ITunesMedia> call(GridView<ITunesMedia> arg0) {
				return new MovieGridCell();
			}
		});
		root = new BorderPane();
		root.setStyle("-fx-background-color: black;");
		root.setCenter(myGrid);

		HBox searchBox = new HBox();
		final TextField searchField = new TextField();
		searchBox.getChildren().add(searchField);
		loadingLabel = LabelBuilder.create().text("loading...").textFill(Color.WHITE).opacity(0).build();
		searchBox.getChildren().add(loadingLabel);
		searchField.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					if(mediaView != null) {
						mediaView.getMediaPlayer().stop();
					}
					if(movieLoader != null) {
						movieLoader.cancel(true);
					}
					root.setCenter(myGrid);
					myGrid.toBack();
					ITunesDataSourceReader reader = new ITunesDataSourceReader(
							searchField.getText().replace(" ", "+"));
					reader.setLimit(200);
					reader.setMediaType(ITunesMediaType.MOVIE);
					ITunesDataSource dataSource = new ITunesDataSource(reader);
					dataSource.setResultList(myGrid.getItems());
					dataSource.retrieve();
					loadingLabel.setOpacity(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		searchBox.setPadding(new Insets(10, 10, 10, 10));
		root.setTop(searchBox);

		Slider columnWidthSlider = SliderBuilder.create().min(10).max(512)
				.build();
		columnWidthSlider.valueProperty().bindBidirectional(
				myGrid.cellWidthProperty());
		columnWidthSlider.valueProperty().bindBidirectional(
				myGrid.cellHeightProperty());
		root.setBottom(columnWidthSlider);

		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}