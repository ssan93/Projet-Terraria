package app;


import java.io.File;

import java.net.URL;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
		FXMLLoader loader = new FXMLLoader(); 											
		URL url = new File("src/view/game/view.fxml").toURI().toURL();		
		loader.setLocation(url);													
		System.out.println(loader.getLocation());							
		Pane root = new Pane(); 									
		root = loader.load();
		Controller c= loader.getController();
     	Scene scene = new Scene(root,root.getPrefWidth(),root.getPrefHeight());	
     	
     	scene.setOnKeyPressed(e->c.actions(e));
     	scene.setOnKeyReleased(e->c.negationdaction(e));
     	primaryStage.setScene(scene);												
		//primaryStage.setFullScreen(true);
     	primaryStage.show();															
		} catch (Exception e) {
			e.printStackTrace();													
		}
	}

	public static void main(String[] args) {
		launch(args);																	
	}
	
}