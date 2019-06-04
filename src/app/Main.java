package app;

import java.io.File;

import java.net.URL;

import controller.GameController;
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
				c.addKeyCode(e.getCode());e.consume();
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

	@Override
	public void start(Stage primaryStage) {
		BorderPane root;
		try {
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/view/game/menu.fxml").toURI().toURL();
			loader.setLocation(url);
			root = new BorderPane();
			root = loader.load();
			Controller c = loader.getController();
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			scene.getStylesheets().add("view/game/menu.css");
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.show();
			c.getInGame().addListener((observable, oldValue, newValue) -> {
				double soundValue = 13;
				changeGame(primaryStage, soundValue);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}