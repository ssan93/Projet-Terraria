package app;

import java.io.File;
import java.net.URL;
import controller.GameController;
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Main extends Application {

	public void changeGame(Stage primaryStage, double soundValue) {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/view/game/view.fxml").toURI().toURL();
			loader.setLocation(url);
			AnchorPane root = new AnchorPane();
			root = loader.load();
			GameController c = loader.getController();
			Scene scene = new Scene(root, root.getMaxWidth(), root.getMaxHeight());
			scene.getStylesheets().add("view/game/menu.css");

			// quand une touche est pressé
			scene.setOnKeyPressed(e -> {
				c.addKeyCode(e.getCode());
				e.consume();
			});
			// quand une touche est relaché
			scene.setOnKeyReleased(e -> {
				c.removeKeyCode(e.getCode());
				c.stopAction();
				e.consume();
			});
			primaryStage.setTitle("Last man in Vietnam");
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.show();
			c.getIsAlive().addListener((observable, oldValue, newValue) -> {
				changeMenu(primaryStage);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeMenu(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/view/game/menu.fxml").toURI().toURL();
			loader.setLocation(url);
			Pane root = new Pane();
			root = new BorderPane();
			root = loader.load();
			Controller c = loader.getController();
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			scene.getStylesheets().add("view/game/menu.css");
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			c.getInGame().addListener((observable, oldValue, newValue) -> {
				double soundValue = 13;
				changeGame(primaryStage, soundValue);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//src/view/game/intro.mp4
	@Override
	public void start(Stage primaryStage) {
		Pane root = new Pane();

		MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("../view/game/intro.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(player);
        mediaView.setFitWidth(1600);
        mediaView.setFitHeight(950);
        root.getChildren().add( mediaView);
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        player.play();
        player.setOnEndOfMedia(() -> {
        	changeMenu(primaryStage);
        });
	}

	public static void main(String[] args) {
		launch(args);
	}

}