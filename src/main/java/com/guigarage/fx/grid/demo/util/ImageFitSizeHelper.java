package com.guigarage.fx.grid.demo.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageFitSizeHelper {

	private DoubleProperty imageWidthInView;

	private DoubleProperty imageHeightInView;

	private ChangeListener<Number> recalcListener;

	private ImageView imageView;

	public ImageFitSizeHelper(final ImageView imageView) {
		this.imageView = imageView;
		recalcListener = new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				recalc();
			}
		};

		if (imageView.getImage() != null) {
			imageView.getImage().widthProperty().addListener(recalcListener);
			imageView.getImage().heightProperty().addListener(recalcListener);
		}

		imageView.imageProperty().addListener(new ChangeListener<Image>() {

			@Override
			public void changed(ObservableValue<? extends Image> arg0,
					Image arg1, Image arg2) {
				if (arg1 != null) {
					arg1.widthProperty().removeListener(recalcListener);
					arg1.heightProperty().removeListener(recalcListener);
				}
				if (arg2 != null) {
					arg2.widthProperty().addListener(recalcListener);
					arg2.heightProperty().addListener(recalcListener);
				}
			}
		});
	}

	public DoubleProperty imageHeightInViewProperty() {
		if (imageHeightInView == null) {
			imageHeightInView = new SimpleDoubleProperty(this,
					"imageHeightInView");
		}
		return imageHeightInView;
	}

	public DoubleProperty imageWidthInViewProperty() {
		if (imageWidthInView == null) {
			imageWidthInView = new SimpleDoubleProperty(this,
					"imageWidthInView");
		}
		return imageWidthInView;
	}

	public void recalc() {
		if (imageView.getImage() != null) {
			double imageRatio = imageView.getImage().getHeight()
					/ imageView.getImage().getWidth();
			if (imageRatio > 1) {
				double scale = imageView.getFitHeight()
						/ imageView.getImage().getHeight();
				imageWidthInViewProperty().set(
						imageView.getImage().getWidth() * scale);
				imageHeightInViewProperty().set(
						imageView.getImage().getHeight() * scale);
			} else if (imageRatio < 1) {
				double scale = imageView.getFitWidth()
						/ imageView.getImage().getWidth();
				imageHeightInViewProperty().set(
						imageView.getImage().getWidth() * scale);
				imageHeightInViewProperty().set(
						imageView.getImage().getHeight() * scale);
			}
		}
	}
}
