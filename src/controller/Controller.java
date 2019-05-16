package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.game.Tiles;
import view.game.BillView;
import model.game.Carte;

public class Controller implements Initializable {

	private static final int gauche = -32, droite = 1952;

	// liste observable ou liste simple ??
	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();

	@FXML
	private AnchorPane background;

	@FXML
	private Pane floor;

	@FXML
	private Pane charapane;

	Carte tileSol = new Carte("src/maps/carte.txt", "src/maps/carte2.txt", "src/maps/carte3.txt");
	int temps;
	private Timeline loop;
	BillView bill = new BillView("view/resources/personnages/right_static_bill.png");
	String oldAnim = "tactac";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		creerVue(tileSol, floor);
		// initAnimation();
		// loop.play();
		bill.getChrac().setSpeed(4);
		bill.getChrac().getXProperty().set(32 * 5);
		bill.getChrac().getYProperty().set(32 * 11);
		charapane.getChildren().add(bill.getImage());

	}

	public void creerVue(Carte map, Pane lePane) {
		int t2[][] = map.getMapBg();
		for (int x = 0; x < t2.length; x++) {
			for (int y = 0; y < t2[x].length; y++) {
				if (t2[x][y] != 0) {
					// sol.relocate(sol.getLayoutX()-(48*5),sol.getLayoutY()-(48*5));

					ImageView img = new ImageView(Tiles.selectionTuile(t2[x][y]));
					img.relocate(y * 32, x * 32);
					floor.getChildren().add(img);
				}
			}
		}

		int t1[][] = map.getMapMilieu();
		for (int x = 0; x < t1.length; x++) {
			for (int y = 0; y < t1[x].length; y++) {
				if (t1[x][y] != 0) {
					ImageView img = new ImageView(Tiles.selectionTuile(t1[x][y]));
					img.relocate(y * 32, x * 32);
					floor.getChildren().add(img);
				}
			}
		}
		int t[][] = map.getMapSol();
		for (int x = 0; x < t.length; x++) {
			for (int y = 0; y < t[x].length; y++) {
				if (t[x][y] != 0) {
					ImageView img = new ImageView(Tiles.selectionTuile(t[x][y]));
					img.relocate(y * 32, x * 32);
					floor.getChildren().add(img);
				}
			}
		}
	}

	public void actions() {
		if (keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT)) {
			// floor.relocate(floor.getLayoutX() - bill.getChrac().getSpeed(),
			// floor.getLayoutY());
			bill.getChrac().animation("RunRight");
			oldAnim = "RunRight";
			for (int i = 0; i < floor.getChildren().size(); i++) {
				floor.getChildren().get(i).relocate(
						floor.getChildren().get(i).getLayoutX() - bill.getChrac().getSpeed(),
						floor.getChildren().get(i).getLayoutY());
				if (floor.getChildren().get(i).getLayoutX() < -32) {
					floor.getChildren().remove(floor.getChildren().get(i));
				}

			}

		}

		if (keyPressed.contains(KeyCode.Q) || keyPressed.contains(KeyCode.LEFT)) {

			// floor.relocate(floor.getLayoutX() + bill.getChrac().getSpeed(),
			// floor.getLayoutY());
			bill.getChrac().animation("RunLeft");
			oldAnim = "RunLeft";
			for (int i = floor.getChildren().size() - 1; i >= 0; i--) {

				floor.getChildren().get(i).relocate(
						floor.getChildren().get(i).getLayoutX() + bill.getChrac().getSpeed(),
						floor.getChildren().get(i).getLayoutY());
				if (floor.getChildren().get(i).getLayoutX() > 32 * 30) {
					floor.getChildren().remove(floor.getChildren().get(i));
				}
			}
		}
	}

	public void negationdaction() {
		switch (oldAnim) {
		case "RunRight":
			bill.getChrac().animation("idleRight");
			oldAnim = "idleRight";
			break;
		case "RunLeft":
			bill.getChrac().animation("idleLeft");
			oldAnim = "idleLeft";
			break;
		default:
			break;
		}
		
//		if (oldAnim.equals("RunRight")) {
//			bill.getChrac().animation("idleRight");
//			oldAnim = "idleRight";
//		}
//		if (oldAnim.equals("RunLeft")) {
//			bill.getChrac().animation("idleLeft");
//			oldAnim = "idleLeft";
//		}
	}

	public void initAnimation() {
		loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);
		System.out.println(temps);

		KeyFrame kf = new KeyFrame(Duration.seconds(0.033), (ev -> {
			if (temps % 30 == 0) {
				System.out.println("tac");
			}
			temps++;
		}));
		loop.getKeyFrames().add(kf);
	}

	public void addKeyCode(KeyCode k) {
		if (!keyPressed.contains(k))
			keyPressed.add(k);
	}

	public void removeKeyCode(KeyCode k) {
		keyPressed.remove(k);
	}

}
