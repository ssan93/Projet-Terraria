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
	Carte tileSol = new Carte("src/carte.txt", "src/carte2.txt");	
	int temps;
	private Timeline loop;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		creerVue(tileSol, sol);
		initAnimation();
		loop.play();

	}

	public void creerVue(Carte map, Pane lePane) {
		int t[][] = map.getMapSol();
		for (int x = 0; x < t.length; x++) {
			for (int y = 0; y < t[x].length; y++) {
				ImageView img = new ImageView(Tuiles.selectionTuile(t[x][y]));
				img.relocate(y * 48, x * 48);
				sol.getChildren().add(img);
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
					sol.relocate(sol.getLayoutX()-(48*5),sol.getLayoutY()-(48*5));
					System.out.println("tac");
				}
				temps++;
			})
		);
		loop.getKeyFrames().add(kf);
	}
	
}
