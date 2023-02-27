//File: Driver.java Author: Dylan Dufresne ID:300297157 Date: April 02/2020
//Description: driver class for minesweeper
package assignment6;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage theStage) throws Exception {
		Game mineSweeper = new Game();
		theStage.setScene(new Scene(mineSweeper.vb));
		theStage.setTitle("Minesweeper");
		theStage.setResizable(true);
		theStage.show();

	}

}
