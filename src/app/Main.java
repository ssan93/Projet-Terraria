package app;

import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import controller.GameController;
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
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

			// quand une touche est pressé
			scene.setOnKeyPressed(e -> {
				c.addKeyCode(e.getCode());
				if (e.getCode() == KeyCode.I)
					c.getInventoryContainer().setVisible(c.getInventoryContainer().isVisible() ? false : true);
				e.consume();
			});
		
			
			// quand une touche est relaché
			scene.setOnKeyReleased(e -> {
				c.removeKeyCode(e.getCode());
				c.stopAction();
				e.consume();
			});
			scene.setOnMousePressed(e -> {
				c.test(e);	
				if (c.getInventoryContainer().isVisible() && e.isPrimaryButtonDown())
					c.clickGrid(e);
			});
			primaryStage.setTitle("Last man in Vietnam");
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.show();
			c.getIsAlive().addListener((observable, oldValue, newValue) -> {
				changeMenu(primaryStage);
			});
		} catch (Exception e) {
			e.printStackTrace();//
		}
	}

	public void changeMenu(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/view/menu/menu.fxml").toURI().toURL();
			loader.setLocation(url);
			Pane root = new Pane();
			root = new BorderPane();
			root = loader.load();
			Controller c = loader.getController();
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			scene.getStylesheets().add("view/menu/menu.css");
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
		MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("../view/intro/intro.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(player);
        Label info = new Label("espace pour passer");
        mediaView.setFitWidth(Toolkit.getDefaultToolkit().getScreenSize().width);
        mediaView.setFitHeight(Toolkit.getDefaultToolkit().getScreenSize().height);
        root.getChildren().add( mediaView);
        root.getChildren().add(info);
        info.setLayoutX(1400);
        info.setLayoutY(880);
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        player.play();
        scene.setOnKeyPressed(e -> {
			if(e.getCode()== KeyCode.SPACE) {
				player.stop(); 
				changeMenu(primaryStage);
			}
		});
       player.setOnEndOfMedia(() -> {
        	changeMenu(primaryStage);
        	System.out.println("fin");
        });
	}

	public static void main(String[] args) {
		launch(args);
	}

}