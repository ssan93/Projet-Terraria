package app;

import java.io.File;

import java.net.URL;

import controller.GameController;
import controller.Controller;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	
	public void changeGame(Stage primaryStage) {
		try {
			System.out.println("test");
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/view/game/view.fxml").toURI().toURL();
			loader.setLocation(url);
			System.out.println(loader.getLocation());
			Pane root = new Pane();
			root = loader.load();
			GameController c = loader.getController();
			Scene scene = new Scene(root, root.getMaxWidth(), root.getMaxHeight());
			
			//quand une touche est pressé
			scene.setOnKeyPressed(e -> {
				c.addKeyCode(e.getCode());
				c.actions();
			});
			//quand une touche est relaché
			scene.setOnKeyReleased(e -> {
				c.removeKeyCode(e.getCode());
				c.stopAction();
			});
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.show();
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
			System.out.println(loader.getLocation());
			root = new BorderPane();
			root = loader.load();
			Controller c = loader.getController();
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			scene.getStylesheets().add("view/game/menu.css");
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setFullScreen(true);
			primaryStage.show();
			c.getInGame().addListener((observable, oldValue, newValue) -> {
				changeGame(primaryStage);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}