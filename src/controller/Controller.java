package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Controller implements Initializable {

	// music
	private MediaPlayer player;
	private String actualMusic, oldMusic="";
	

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
		player.stop();
		inGame.set(true);
	}

	@FXML
	void quitGame(ActionEvent event) {
		System.exit(0);
	}

	public SimpleBooleanProperty getInGame() {
		return inGame;
	}
	
	public void setPlayerVolume(double soundValue) {
		this.player.setBalance(soundValue);
	}
	
	public double getPlayerVolume() {
		return player.getBalance();
	}

	public MediaPlayer randomMusic() {
		//The actual list of musics for the munu
		ArrayList<String> musics = new ArrayList<>();
		musics.add("src/menu-musics/menu1.mp3");
		musics.add("src/menu-musics/menu2.mp3");
		musics.add("src/menu-musics/menu3.mp3");
		musics.add("src/menu-musics/menu4.mp3");
		musics.add("src/menu-musics/menu5.mp3");
		
		do {
			actualMusic= musics.get((int) (Math.random() * 5));
		}
		while(oldMusic.equals(actualMusic));
		oldMusic= actualMusic;

		//Return a brand new MediaPlayer with a random music from the lists
		return new MediaPlayer(new Media(new File(actualMusic).toURI().toString()));
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
