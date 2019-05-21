package app;

import java.io.File;

import java.net.URL;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/view/game/view.fxml").toURI().toURL();
			loader.setLocation(url);
			System.out.println(loader.getLocation());
			AnchorPane root = new AnchorPane();
			root = loader.load();
			Controller c = loader.getController();
			Scene scene = new Scene(root, root.getMaxWidth(), root.getMaxHeight());

			// quand une touche est pressé
			scene.setOnKeyPressed(e -> {
				c.addKeyCode(e.getCode());
			});
			// quand une touche est relaché
			scene.setOnKeyReleased(e -> {
				c.removeKeyCode(e.getCode());
				c.stopAction();
			});
			primaryStage.setTitle("Last man in Vietnam");
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}