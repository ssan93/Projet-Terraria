package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Controller implements Initializable{
	@FXML
    private Button play;

    @FXML
    private Button options;

    @FXML
    private Button quit;
    
	private SimpleBooleanProperty inGame ;


    @FXML
    void goOptions(ActionEvent event) {

    }

    @FXML
    void playGame(ActionEvent event) {
    	inGame.set(true);
    }

    @FXML
    void quitGame(ActionEvent event) {
    	System.exit(0);
    }
    
    public SimpleBooleanProperty getInGame() {
		return inGame;
	}

	public void initialize(URL location, ResourceBundle resources) {
		inGame = new SimpleBooleanProperty(false);
	}
}
