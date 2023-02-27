//File: MineButton.java Author: Dylan Dufresne ID:300297157 Date: April 02/2020
//Description: custom button class for handling everything related to the grid of buttons in a minesweeper game
package assignment6;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MineButton extends Button {
	final int W = 40, H = 40;
	private int state; // -1: Bomb 0: Clear 1: Numbered
	private Boolean flagged = false;
	private Boolean nonBomb = false;
	private int surroundingBombs = 0;
	private ImageView imageCover, imageClear, imageBomb, imageBlown, imageFlag, imageWrong;

	public MineButton() {
		state = 1;
		imageCover = new ImageView(new Image("file:Images/squareCover.png"));
		imageClear = new ImageView(new Image("file:Images/squareClear.png"));
		imageBlown = new ImageView(new Image("file:Images/mineExplosion.png"));
		imageBomb = new ImageView(new Image("file:Images/mineSafe.png"));
		imageFlag = new ImageView(new Image("file:Images/squareFlag.png"));
		imageWrong = new ImageView(new Image("file:Images/mineWrong.png"));

		this.setMinHeight(H);
		this.setMaxHeight(H);
		this.setMinWidth(W);
		this.setMaxWidth(W);

		imageCover.setFitHeight(H);
		imageCover.setFitWidth(W);

		imageClear.setFitHeight(H);
		imageClear.setFitWidth(W);

		imageBlown.setFitHeight(H);
		imageBlown.setFitWidth(W);

		imageBomb.setFitHeight(H);
		imageBomb.setFitWidth(W);

		imageFlag.setFitHeight(H);
		imageFlag.setFitWidth(W);

		imageWrong.setFitHeight(H);
		imageWrong.setFitWidth(W);

		setGraphic(imageCover);

	}

	public Boolean getNonBomb() {
		return nonBomb;
	}

	public void setNonBomb() {
		nonBomb = true;
	}

	public ImageView getWrong() {
		return imageWrong;
	}

	public ImageView getCover() {
		return imageCover;
	}

	public ImageView getClear() {
		return imageClear;
	}

	public ImageView getBlown() {
		return imageBlown;
	}

	public ImageView getBomb() {
		return imageBomb;
	}

	public ImageView getFlag() {
		return imageFlag;
	}

	public int getState() {
		return state;
	}

	public boolean getFlagged() {
		return flagged;
	}

	public int getSurroundingBombs() {
		return this.surroundingBombs;
	}

	public void setState(int state) {
		this.state = state;
		if (state == 1)
			this.surroundingBombs++;

	}

	public void setFlag() {
		if (flagged)
			flagged = false;
		else
			flagged = true;
	}

}
