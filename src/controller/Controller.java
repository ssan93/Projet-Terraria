package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Controller implements Initializable {

	// music
	private MediaPlayer player;

	@FXML
	private Button play;

	@FXML
	private Button options;

	@FXML
	private Button quit;

	private SimpleBooleanProperty inGame;

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

	public MediaPlayer randomMusic() {
		//The actual list of musics for the munu
		ArrayList<String> musics = new ArrayList<>();
		musics.add("src/mgs.mp3");
		musics.add("src/AHHHHH.mp3");
		musics.add("src/menu.mp3");
		musics.add("src/rip.wav");
		//Return a brand new MediaPlayer with a random music from the lists
		return new MediaPlayer(new Media(new File(musics.get((int) (Math.random() * 4))).toURI().toString()));
	}

	public void play() {
		//the player is set with a random music
		this.player = randomMusic();
		//Launch the player
		player.play();
		//reset the player as it ends it's music 
		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				play();
			}
		});
	}

	public void initialize(URL location, ResourceBundle resources) {
		inGame = new SimpleBooleanProperty(false);
		play();
	}
}
