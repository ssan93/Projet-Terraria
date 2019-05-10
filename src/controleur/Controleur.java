package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import modele.Carte;
import modele.Tuiles;

public class Controleur implements Initializable {
	@FXML
	private Pane sol;
	Carte tileSol = new Carte("src/carte.txt", "src/carte2.txt", "src/carte3.txt");	
	int temps;
	private Timeline loop;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		creerVue(tileSol, sol);
		initAnimation();
		loop.play();

	}

	public void creerVue(Carte map, Pane lePane) {
		int t2[][] = map.getMapBg();
		for (int x = 0; x < t2.length; x++) {
			for (int y = 0; y < t2[x].length; y++) {
				if (t2[x][y] != 0) {
				ImageView img = new ImageView(Tuiles.selectionTuile(t2[x][y]));
				img.relocate(y * 32, x * 32);
				sol.getChildren().add(img);}
			}
		}
		
		int t1[][] = map.getMapMilieu();
		for (int x = 0; x < t1.length; x++) {
			for (int y = 0; y < t1[x].length; y++) {
				if (t1[x][y] != 0) {
				ImageView img = new ImageView(Tuiles.selectionTuile(t1[x][y]));
				img.relocate(y * 32, x * 32);
				sol.getChildren().add(img);}
			}
		}
		int t[][] = map.getMapSol();
		for (int x = 0; x < t.length; x++) {
			for (int y = 0; y < t[x].length; y++) {
				if (t[x][y] != 0) {
				ImageView img = new ImageView(Tuiles.selectionTuile(t[x][y]));
				img.relocate(y * 32, x * 32);
				sol.getChildren().add(img);}
			}
		}
	}

	public void initAnimation() {
		loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);
		System.out.println(temps);

		KeyFrame kf = new KeyFrame(
			Duration.seconds(0.033), (ev ->{
				if(temps%30==0) {
					//sol.relocate(sol.getLayoutX()-(48*5),sol.getLayoutY()-(48*5));
					System.out.println("tac");
				}
				temps++;
			})
		);
		loop.getKeyFrames().add(kf);
	}
	
}
