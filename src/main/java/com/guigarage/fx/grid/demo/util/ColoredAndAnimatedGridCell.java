package com.guigarage.fx.grid.demo.util;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import com.guigarage.fx.grid.cell.ColorGridCell;

public class ColoredAndAnimatedGridCell extends ColorGridCell {
	
	private RotateTransition rotateInTransition;
	
	private ScaleTransition scaleInTransition;
	
	private RotateTransition rotateOutTransition;
	
	private ScaleTransition scaleOutTransition;
	
	
	public ColoredAndAnimatedGridCell() {
		addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				toFront();
				if(rotateOutTransition != null) {
					rotateOutTransition.stop();
				}
				if(scaleOutTransition != null) {
					scaleOutTransition.stop();
				}
				rotateInTransition = RotateTransitionBuilder.create().fromAngle(ColoredAndAnimatedGridCell.this.getRotate()).toAngle(16).interpolator(Interpolator.EASE_IN).duration(Duration.millis(120)).node(ColoredAndAnimatedGridCell.this).build();
				scaleInTransition = ScaleTransitionBuilder.create().fromX(ColoredAndAnimatedGridCell.this.getScaleX()).toX(1.3).fromY(ColoredAndAnimatedGridCell.this.getScaleY()).toY(1.3).interpolator(Interpolator.EASE_IN).duration(Duration.millis(120)).node(ColoredAndAnimatedGridCell.this).build();
				rotateInTransition.play();
				scaleInTransition.play();
			}
		});
		
		addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(rotateInTransition != null) {
					rotateInTransition.stop();
				}
				if(scaleInTransition != null) {
					scaleInTransition.stop();
				}
				rotateOutTransition = RotateTransitionBuilder.create().fromAngle(ColoredAndAnimatedGridCell.this.getRotate()).toAngle(0).interpolator(Interpolator.EASE_OUT).duration(Duration.millis(340)).node(ColoredAndAnimatedGridCell.this).build();
				scaleOutTransition = ScaleTransitionBuilder.create().fromX(ColoredAndAnimatedGridCell.this.getScaleX()).toX(1.0).fromY(ColoredAndAnimatedGridCell.this.getScaleY()).toY(1.0).interpolator(Interpolator.EASE_OUT).duration(Duration.millis(340)).node(ColoredAndAnimatedGridCell.this).build();
				rotateOutTransition.play();
				scaleOutTransition.play();
			}
		});
	}

}
