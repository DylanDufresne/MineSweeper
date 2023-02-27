//File: FaceButton.java Author: Dylan Dufresne ID:300297157 Date: April 02/2020
//Description: Custom button class to hold different images and states related to the button in middle of header for minesweeper
package assignment6;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FaceButton extends Button {
	final int W = 40, H = 40;
	private ImageView imageSmile = new ImageView(new Image("file:Images/faceSmile.png"));
	private ImageView imageSun = new ImageView(new Image("file:Images/faceWin.png"));
	private ImageView imageDead = new ImageView(new Image("file:Images/faceDead.png"));
	private ImageView imageO = new ImageView(new Image("file:Images/faceO.png"));

	public FaceButton() {
		setMinWidth(W);
		setMaxWidth(W);
		setMinHeight(H);
		setMaxHeight(H);

		imageO.setFitHeight(H);
		imageO.setFitWidth(W);

		imageSmile.setFitHeight(H);
		imageSmile.setFitWidth(W);

		imageSun.setFitHeight(H);
		imageSun.setFitWidth(W);

		imageDead.setFitHeight(H);
		imageDead.setFitWidth(W);

		setGraphic(imageSmile);
	}

	public ImageView getImageO() {
		return imageO;
	}

	public ImageView getImageDead() {
		return imageDead;
	}

	public ImageView getImageSmile() {
		return imageSmile;
	}

	public ImageView getImageSun() {
		return imageSun;
	}
}