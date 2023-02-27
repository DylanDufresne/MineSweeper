//File: Game.java Author: Dylan Dufresne ID:300297157 Date: April 02/2020
//Description: creates and runs all methods relating to a minesweeper game
package assignment6;

import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Game extends VBox {
//Difficulty 1: Beginner 2: Intermediate 3:Expert
	private int difficulty = 1;
	private int gameWidth, gameHeight;
	final int W = 40, H = 40;
	MineButton buttons[][];
	private HBox pane;
	private boolean firstClick = false;
	private boolean gamePlay = true;
	VBox vb = new VBox();
	private int totalBombs;
	FaceButton faceButton;

//method to create h pane for top add label, button, label
	public Game() {
		buttons = new MineButton[getGameWidth()][getGameHeight()];
		faceButton = new FaceButton();
		vb.getChildren().add(0, menu());
		vb.getChildren().add(1, header());
		vb.getChildren().add(2, gridPane());

	}

	// method to reset all variables to create a new game
	public void newGame() {
		gamePlay = true;
		firstClick = false;
		vb.getChildren().clear();
		buttons = new MineButton[getGameWidth()][getGameHeight()];
		vb.getChildren().add(menu());
		vb.getChildren().add(header());
		faceButton.setGraphic(faceButton.getImageSmile());
		vb.getChildren().add(gridPane());
	}

	// method to set and get the game width(amount of buttons in the array)
	public int getGameWidth() {
		setGameSize();
		return gameWidth;
	}

	// method to get the game height(amount of buttons in the array)
	public int getGameHeight() {
		return gameHeight;
	}

	// method to set the game width and height based on the difficulty
	public void setGameSize() {
		// Beginner
		if (difficulty == 1) {
			gameWidth = 8;
			gameHeight = 8;
			totalBombs = 10;
		}

		// Intermediate
		if (difficulty == 2) {
			gameWidth = 16;
			gameHeight = 16;
			totalBombs = 40;
		}

		// Expert
		if (difficulty == 3) {
			gameWidth = 32;
			gameHeight = 16;
			totalBombs = 99;
		}
	}

	// method to build and return the menubar
	public MenuBar menu() {
		Menu menu = new Menu("Difficulty");
		MenuItem item1 = new MenuItem("Beginner");
		item1.setOnAction(e -> {
			difficulty = 1;
			newGame();
			System.out.println("New Beginner game");
		});
		MenuItem item2 = new MenuItem("Intermediate");
		item2.setOnAction(e -> {
			difficulty = 2;
			newGame();
			System.out.println("New intermediate game");
		});
		MenuItem item3 = new MenuItem("Expert");
		item3.setOnAction(e -> {
			difficulty = 3;
			newGame();
			System.out.println("New expert game");
		});
		menu.getItems().addAll(item1, item2, item3);
		MenuBar mb = new MenuBar(menu);
		return mb;
	}

	// method to build and return the header, which holds the two labels and the
	// faceButton
	public HBox header() {
		int labelWidth = ((gameWidth * W) - W) / 2;
		pane = new HBox();

		faceButton.setOnMouseClicked(e -> {
			newGame();
		});

		Label bombLabel = new Label("" + totalBombs);
		bombLabel.setMinWidth(labelWidth);
		bombLabel.setMaxWidth(labelWidth);
		bombLabel.setMinHeight(H);
		bombLabel.setMaxHeight(H);
		bombLabel.setFont(new Font("Arial", H));
		bombLabel.setAlignment(Pos.CENTER);
		bombLabel.setTextFill(Color.RED);
		bombLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		Label timeLabel = new Label("T");
		timeLabel.setMinWidth(labelWidth);
		timeLabel.setMaxWidth(labelWidth);
		timeLabel.setMinHeight(H);
		timeLabel.setMaxHeight(H);
		timeLabel.setFont(new Font("Arial", H));
		timeLabel.setAlignment(Pos.CENTER);
		timeLabel.setTextFill(Color.RED);
		timeLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		pane.getChildren().addAll(bombLabel, faceButton, timeLabel);
		return pane;
	}

	// method to build and return the gridpane which holds the buttons
	public GridPane gridPane() {
		GridPane gp = new GridPane();
		for (int col = 0; col < gameHeight; col++) {
			for (int row = 0; row < gameWidth; row++) {
				buttons[row][col] = new MineButton();
				MineButton b = buttons[row][col];
				buttons[row][col].setState(0);
				buttons[row][col].setOnMouseClicked(e -> {
					if (gamePlay) {
						int firstX = GridPane.getColumnIndex(b);
						int firstY = GridPane.getRowIndex(b);
						if (b.getGraphic() != b.getFlag())
							if (e.getButton() == MouseButton.PRIMARY) {
								if (firstClick == false) {
									setBombs(totalBombs, firstX, firstY);
									checkBlanks(firstX, firstY);
								} else {
									if (b.getState() == -1) {// if bomb
										getOtherBombs();
										b.setGraphic(b.getBlown());
										faceButton.setGraphic(faceButton.getImageDead());
										gamePlay = false;
									}

									else {
										// is a numbered tile
										if (b.getState() == 1) {
											if (b.getGraphic() != null) {
												b.setGraphic(null);
												b.setText("" + b.getSurroundingBombs());
												if (getNumberOfClicks()) {
													faceButton.setGraphic(faceButton.getImageSun());
													gamePlay = false;
												}

											} else {
												checkNumberedTiles(b);
											}
										} else
										// is a blank tile
										if (b.getState() == 0) {
											checkBlanks(GridPane.getColumnIndex(b), GridPane.getRowIndex(b));
										}
									}

								}
							}
						if (e.getButton() == MouseButton.SECONDARY) {
							if (b.getGraphic() == b.getFlag()) {
								if (totalBombs >= 0) {
									b.setGraphic(b.getCover());
									totalBombs++;
									vb.getChildren().remove(pane);
									vb.getChildren().add(1, header());
								}

							} else {

								if (totalBombs > 0 && b.getGraphic() == b.getCover()) {
									b.setGraphic(b.getFlag());
									totalBombs--;
									vb.getChildren().remove(pane);
									vb.getChildren().add(1, header());

								}
							}
							b.setFlag();
						}
					}
				});

				buttons[row][col].setOnMousePressed(e -> {
					if (gamePlay) {
						if (e.getButton() == MouseButton.PRIMARY)
							if (b.getGraphic() != b.getFlag()) {
								faceButton.setGraphic(faceButton.getImageO());
							}
					}
				});

				buttons[row][col].setOnMouseReleased(e -> {
					if (gamePlay) {
						if (b.getState() == 1 || b.getState() == 0) {
							faceButton.setGraphic(faceButton.getImageSmile());

						}

					}
				});

				gp.add(buttons[row][col], row, col);

			}
		}
		return gp;
	}

	// method to check the surrounding tiles of a numbered tile when the proper
	// number of flags are surrounding
	public void checkNumberedTiles(MineButton button) {
		int x = GridPane.getRowIndex(button);
		int y = GridPane.getColumnIndex(button);
		int flagged = 0;
		boolean allFlagged = true;
		try {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int o = y - 1; o <= y + 1; o++) {

					if (buttons[o][i].getGraphic() == buttons[o][i].getFlag()) {
						flagged++;
						if (buttons[o][i].getState() != -1) {
							allFlagged = false;
						}
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {

		}
		if (flagged == button.getSurroundingBombs() && allFlagged) {
			try {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int o = y - 1; o <= y + 1; o++) {
					if (buttons[o][i].getState() == 1) {
						buttons[o][i].setGraphic(null);
						buttons[o][i].setText("" + buttons[o][i].getSurroundingBombs());

					}
					if (buttons[o][i].getState() == 0) {
						checkBlanks(o, i);
					}
				}
			}
		}
			catch (IndexOutOfBoundsException e) {
				
			}
		} else {
			if (flagged == button.getSurroundingBombs() && !allFlagged) {
				getOtherBombs();
				faceButton.setGraphic(faceButton.getImageDead());
				gamePlay = false;
				try {
					for (int i = x - 1; i <= x + 1; i++) {
						for (int o = y - 1; o <= y + 1; o++) {
							if (buttons[o][i].getState() == 1) {
								if (buttons[o][i].getGraphic() == buttons[o][i].getFlag()) {
									buttons[o][i].setGraphic(buttons[o][i].getWrong());
								}
							}
							if (buttons[o][i].getState() == -1) {
								buttons[o][i].setGraphic(buttons[o][i].getBlown());
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {

				}
				try {
					for (int i = x + 1; i >= x - 1; i--) {
						for (int o = y - 1; o <= y + 1; o++) {
							if (buttons[o][i].getState() == 1) {
								if (buttons[o][i].getGraphic() == buttons[o][i].getFlag()) {
									buttons[o][i].setGraphic(buttons[o][i].getWrong());
								}
							}
							if (buttons[o][i].getState() == -1) {
								buttons[o][i].setGraphic(buttons[o][i].getBlown());
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {

				}
				try {
					for (int o = y - 1; o <= y + 1; o++) {
						for (int i = x - 1; i <= x + 1; i++) {
							if (buttons[o][i].getState() == 1) {
								if (buttons[o][i].getGraphic() == buttons[o][i].getFlag()) {
									buttons[o][i].setGraphic(buttons[o][i].getWrong());
								}
							}
							if (buttons[o][i].getState() == -1) {
								buttons[o][i].setGraphic(buttons[o][i].getBlown());
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {

				}
				try {
					for (int o = y + 1; o >= y - 1; o--) {
						for (int i = x + 1; i >= x - 1; i--) {
							if (buttons[o][i].getState() == 1) {
								if (buttons[o][i].getGraphic() == buttons[o][i].getFlag()) {
									buttons[o][i].setGraphic(buttons[o][i].getWrong());
								}
							}
							if (buttons[o][i].getState() == -1) {
								buttons[o][i].setGraphic(buttons[o][i].getBlown());
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {

				}
			}
		}
	}

	public void checkBlanks(int x, int y) {
		if (x < 0 || x > gameWidth - 1 || y < 0 || y > gameHeight - 1)
			return;
		else {
			if (buttons[x][y].getSurroundingBombs() == 0 && buttons[x][y].getGraphic() == buttons[x][y].getCover()) {
				buttons[x][y].setGraphic(null);
				if (buttons[x][y].getSurroundingBombs() != 0)
					buttons[x][y].setText("" + buttons[x][y].getSurroundingBombs());
				checkBlanks(x + 1, y);
				checkBlanks(x - 1, y);
				checkBlanks(x, y + 1);
				checkBlanks(x, y - 1);
			}
			if (buttons[x][y].getSurroundingBombs() != 0 && buttons[x][y].getGraphic() == buttons[x][y].getCover()) {
				buttons[x][y].setGraphic(null);
				buttons[x][y].setText("" + buttons[x][y].getSurroundingBombs());
			} else
				return;
		}
	}

	// method to randomly set the bombs for the game
	public void setBombs(int bombs, int firstX, int firstY) {
		int mines = 0;
		java.util.Random random = new java.util.Random();
		while (mines < totalBombs) {
			int j = random.nextInt(gameHeight);
			int i = random.nextInt(gameWidth);
			try {
				if (firstClick == false) {
					if (firstX != gameWidth - 1 && firstY != gameHeight - 1 && firstX != 0 && firstY != 0) {
						for (int index = firstX - 1; index <= firstX + 1; index++) {
							for (int yIndex = firstY - 1; yIndex <= firstY + 1; yIndex++) {
								buttons[index][yIndex].setNonBomb();
							}
						}
					} else {
						if (firstX == gameWidth - 1) {
							if (firstY == 0) {
								for (int x = firstX - 1; x <= firstX; x++) {
									for (int y = firstY + 1; y >= firstY; y--) {
										buttons[x][y].setNonBomb();
									}
								}
							}
							if (firstY == gameHeight - 1) {
								for (int x = firstX - 1; x <= firstX; x++) {
									for (int y = firstY - 1; y <= firstY; y++) {
										buttons[x][y].setNonBomb();
									}
								}
							}
							if (firstY != 0 && firstY != gameHeight - 1) {
								for (int x = firstX - 1; x <= firstX; x++) {
									for (int y = firstY - 1; y <= firstY + 1; y++) {
										buttons[x][y].setNonBomb();
									}
								}
							}
						}

						if (firstX == 0) {
							if (firstY == 0) {
								for (int x = firstX + 1; x >= firstX; x--) {
									for (int y = firstY + 1; y >= firstY; y--) {
										buttons[x][y].setNonBomb();
									}
								}
							}
							if (firstY == gameHeight - 1) {
								for (int x = firstX + 1; x >= firstX; x--) {
									for (int y = firstY - 1; y <= firstY; y++) {
										buttons[x][y].setNonBomb();
									}
								}
							}
							if (firstY != 0 && firstY != gameHeight - 1) {
								for (int x = firstX + 1; x >= firstX; x--) {
									for (int y = firstY - 1; y <= firstY + 1; y++) {
										buttons[x][y].setNonBomb();
									}
								}
							}
						}

						if (firstY == gameHeight - 1 && firstX != 0 && firstX != gameWidth - 1) {
							for (int x = firstX + 1; x >= firstX - 1; x--) {
								for (int y = firstY - 1; y <= firstY; y++) {
									buttons[x][y].setNonBomb();
								}
							}
						}
						if (firstY == 0 && firstX != 0 && firstX != gameWidth - 1) {
							for (int x = firstX + 1; x >= firstX - 1; x--) {
								for (int y = firstY + 1; y >= firstY; y--) {
									buttons[x][y].setNonBomb();
								}
							}
						}
					}
					firstClick = true;
				}
				if (buttons[i][j].getState() != -1 && buttons[i][j].getNonBomb() == false) {
					buttons[i][j].setState(-1);
					mines++;
					setNumbers(i, j);

				}

			} catch (IndexOutOfBoundsException e) {
			}
		}
		for (int i = 0; i < gameHeight; i++) {
			for (int j = 0; j < gameWidth; j++) {
				if (buttons[j][i].getNonBomb()) {
					checkBlanks(j, i);
					buttons[j][i].setGraphic(null);
					if (buttons[j][i].getSurroundingBombs() != 0)
						buttons[j][i].setText("" + buttons[j][i].getSurroundingBombs());
				}
			}
		}
	}

	// Mthod to set the surrounding number tiles around a bomb
	public void setNumbers(int x, int y) {
		try {
			if (x == 0) {
				if (y == 0) {
					if (buttons[0][1].getState() != -1)
						if (buttons[0][1].getState() != 1) {
							buttons[0][1].setState(1);
						} else
							buttons[0][1].setState(1);
					if (buttons[1][0].getState() != -1)
						if (buttons[1][0].getState() != 1) {
							buttons[1][0].setState(1);
						} else
							buttons[1][0].setState(1);
					if (buttons[1][1].getState() != -1)
						if (buttons[1][1].getState() != 1) {
							buttons[1][1].setState(1);
						} else
							buttons[1][1].setState(1);
				}
				if (y == (gameHeight - 1)) {
					if (buttons[0][(gameHeight - 2)].getState() != -1)
						if (buttons[0][(gameHeight - 2)].getState() != 1) {
							buttons[0][(gameHeight - 2)].setState(1);
						} else
							buttons[0][(gameHeight - 2)].setState(1);
					if (buttons[1][(gameHeight - 1)].getState() != -1)
						if (buttons[1][(gameHeight - 1)].getState() != 1) {
							buttons[1][(gameHeight - 1)].setState(1);
						} else
							buttons[1][(gameHeight - 1)].setState(1);
					if (buttons[1][(gameHeight - 2)].getState() != -1)
						if (buttons[1][(gameHeight - 2)].getState() != 1) {
							buttons[1][(gameHeight - 2)].setState(1);
						} else
							buttons[1][(gameHeight - 2)].setState(1);
				} else {
					for (int i = (x + 1); i >= (x); i--) {
						for (int l = (y - 1); l <= (y + 1); l++) {
							if (buttons[i][l].getState() != -1) {
								if (buttons[i][l].getState() != 1) {
									buttons[i][l].setState(1);
								} else
									buttons[i][l].setState(1);
							}
						}
					}
				}
			}
			if (x == (gameWidth - 1)) {
				if (y == 0) {
					if (buttons[(gameWidth - 1)][1].getState() != -1)
						if (buttons[(gameWidth - 1)][1].getState() != 1) {
							buttons[(gameWidth - 1)][1].setState(1);
						} else
							buttons[(gameWidth - 1)][1].setState(1);
					if (buttons[(gameWidth - 2)][0].getState() != -1)
						if (buttons[(gameWidth - 2)][0].getState() != 1) {
							buttons[(gameWidth - 2)][0].setState(1);
						} else
							buttons[(gameWidth - 2)][0].setState(1);
					if (buttons[(gameWidth - 2)][1].getState() != -1)
						if (buttons[(gameWidth - 2)][1].getState() != 1) {
							buttons[(gameWidth - 2)][1].setState(1);
						} else
							buttons[(gameWidth - 2)][1].setState(1);
				}
				if (y == (gameHeight - 1)) {
					if (buttons[(gameHeight - 1)][(gameHeight - 2)].getState() != -1)
						if (buttons[(gameHeight - 1)][(gameHeight - 2)].getState() != 1) {
							buttons[(gameHeight - 1)][(gameHeight - 2)].setState(1);
						} else
							buttons[(gameHeight - 1)][(gameHeight - 2)].setState(1);
					if (buttons[(gameWidth - 2)][(gameHeight - 2)].getState() != -1)
						if (buttons[(gameWidth - 2)][(gameHeight - 2)].getState() != 1) {
							buttons[(gameWidth - 2)][(gameHeight - 2)].setState(1);
						} else
							buttons[(gameWidth - 2)][(gameHeight - 2)].setState(1);
					if (buttons[(gameWidth - 2)][(gameHeight - 1)].getState() != -1)
						if (buttons[(gameWidth - 2)][(gameHeight - 1)].getState() != 1) {
							buttons[(gameWidth - 2)][(gameHeight - 1)].setState(1);
						} else
							buttons[(gameWidth - 2)][(gameHeight - 1)].setState(1);
				} else {
					for (int l = (y - 1); l <= (y + 1); l++) {
						for (int i = (x - 1); i <= (x); i++) {
							if (buttons[i][l].getState() != -1) {
								if (buttons[i][l].getState() != 1) {
									buttons[i][l].setState(1);
								} else
									buttons[i][l].setState(1);

							}
						}
					}
				}
			}
			if (y == 0 && x != 0 && x != (gameWidth - 1)) {
				for (int l = (y + 1); l >= (y); l--) {
					for (int i = (x - 1); i <= (x + 1); i++) {
						if (buttons[i][l].getState() != -1) {
							if (buttons[i][l].getState() != 1) {
								buttons[i][l].setState(1);
							} else
								buttons[i][l].setState(1);
						}
					}
				}
			}
			if (y == (gameHeight - 1) && x != 0 && x != (gameWidth - 1)) {
				for (int l = (y - 1); l <= (y); l++) {
					for (int i = (x - 1); i <= (x + 1); i++) {
						if (buttons[i][l].getState() != -1) {
							if (buttons[i][l].getState() != 1) {
								buttons[i][l].setState(1);
							} else
								buttons[i][l].setState(1);
						}
					}
				}
			}
			if (x != 0 && x != (gameWidth - 1) && y != 0 && y != (gameHeight - 1)) {
				for (int l = (y - 1); l <= (y + 1); l++) {
					for (int i = (x - 1); i <= (x + 1); i++) {
						if (buttons[i][l].getState() != -1) {
							if (buttons[i][l].getState() != 1) {
								buttons[i][l].setState(1);
							} else
								buttons[i][l].setState(1);
						}
					}
				}
			}

		} catch (IndexOutOfBoundsException e) {
		}
		getNumberOfClicks();
	}

	// method to uncover all bombs as grey bombs, set blown bombs after this
	private void getOtherBombs() {
		for (int row = 0; row < gameWidth; row++) {

			for (int col = 0; col < gameHeight; col++) {
				if (buttons[row][col].getState() == -1) {
					buttons[row][col].setGraphic(buttons[row][col].getBomb());
				}
			}
		}
	}

	// method to check if all numbered tiles are uncovered
	private boolean getNumberOfClicks() {
		Boolean clickBool = true;
		for (int row = 0; row < gameWidth; row++) {

			for (int col = 0; col < gameHeight; col++) {
				if (buttons[row][col].getState() == 1)
					if (buttons[row][col].getGraphic() != null) {
						clickBool = false;
					}
			}
		}
		return clickBool;
	}
}