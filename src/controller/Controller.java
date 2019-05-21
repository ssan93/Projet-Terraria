package controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.game.BillView;
import view.game.MapView;
import model.game.Map;
import model.game.Tiles;

public class Controller implements Initializable {

	private static final int gauche = -32, droite = 1952;

	// private SimpleIntegerProperty absolute_x, absolute_y, absolute_charactX,
	// absolute_charactY;

	// liste observable ou liste simple ??

	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();

	@FXML
	private AnchorPane background;

	@FXML
	private Pane floor;

	@FXML
	private Pane charapane;

	private Map tileSol = new Map("src/maps/grosseMap_sol.csv", "src/maps/grosseMap_environnement.csv");
	private MapView mv;
	private Timeline loop;

	private Timeline loop2;

	int countRight = 32;
	int countLeft = 32;
	int departl = 0;
	int tailleCarte = 13 * 32;
	int departr = tailleCarte;

	boolean jumping = false;

	private int temps;
	private BillView bill = new BillView("view/resources/personnages/right_static_bill.png");
	private String oldAnim = "tactac";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.getChildren().add(0, new ImageView(new Image("view/resources/tac.jpg")));
		// absolute_x = new SimpleIntegerProperty(0);
		// absolute_y = new SimpleIntegerProperty(0);
		// absolute_charactX = new SimpleIntegerProperty();
		// absolute_charactY = new SimpleIntegerProperty();
		// absolute_charactX.bind(bill.getChrac().getXProperty());
		// absolute_charactY.bind(bill.getChrac().getYProperty());
		mv = new MapView(tileSol);
		initAnimation();
		loop.play();
		bill.getChrac().setSpeed(4);

		/*
		 * bill.getChrac().getXProperty().set(32 * 6);
		 * bill.getChrac().getYProperty().set(32 * 7 - 12);
		 */

		charapane.getChildren().add(bill.getImage());
		floor.getChildren().addAll(mv.creerVue());

		testCollision();

	}

	public void actions() {
		if (keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT)) {
			if (!stopSroll().equals("right stop")) {
				bill.getChrac().animation("RunRight");
				oldAnim = "RunRight";
				removeImages("Right");
				/*
				 * departr += bill.getChrac().getSpeed(); if (departr % 960 / 32 == 29) {
				 * tileSol = new Map("src/maps/carte.txt", "src/maps/carte2.txt",
				 * "src/maps/carte3.txt"); mv = new MapView(tileSol);
				 * floor.getChildren().addAll(mv.creerVue()); departr = 13 * 32;
				 * 
				 * }
				 */

				addImages("Right");
			}
		}

		if (keyPressed.contains(KeyCode.Q) || keyPressed.contains(KeyCode.LEFT)) {
			if (!stopSroll().equals("left stop")) {
				bill.getChrac().animation("RunLeft");
				oldAnim = "RunLeft";
				removeImages("Left");
				addImages("Left");
			}

		}

		if (keyPressed.contains(KeyCode.SPACE)) {
			if (!alreadyJumping()) {
				bill.getChrac().animation("jumpRight");
				oldAnim = "jumpRight";
				removeImages("Up");
			}

		}
	}

	public void addImages(String direction) {
		switch (direction) {
		case "Right":
			
			break;

		case "Left":
			countLeft -= bill.getChrac().getSpeed();
			departl += bill.getChrac().getSpeed();
			int tileLeft = departl % 960 / 32;
			if (tileLeft == 30)
				tileLeft = 0;
			int tLeft[][] = tileSol.getMap(0);
			if (countLeft <= 0) {
				for (int x = 0; x < tLeft.length; x++) {
					if (tLeft[x][tileLeft] != 0) {
						ImageView img = new ImageView(Tiles.selectionTuile(tLeft[x][tileLeft]));
						img.relocate(0, x * 32);
						floor.getChildren().add(img);
					}
				}
				countLeft += 32;
			}
			if (departr - bill.getChrac().getSpeed() >= 0)
				departr -= bill.getChrac().getSpeed();

			break;
		}

	}

	public void relocateImages(String direction, int indiceFloor) {
		switch (direction) {
		case "Right":
			floor.getChildren().get(indiceFloor).relocate(
					floor.getChildren().get(indiceFloor).getLayoutX() - bill.getChrac().getSpeed(),
					floor.getChildren().get(indiceFloor).getLayoutY());
			break;
		case "Left":
			floor.getChildren().get(indiceFloor).relocate(
					floor.getChildren().get(indiceFloor).getLayoutX() + bill.getChrac().getSpeed(),
					floor.getChildren().get(indiceFloor).getLayoutY());
			break;
		case "Up":
			floor.getChildren().get(indiceFloor).relocate(floor.getChildren().get(indiceFloor).getLayoutX(),
					floor.getChildren().get(indiceFloor).getLayoutY() + 4);
			break;
		case "Down":
			floor.getChildren().get(indiceFloor).relocate(floor.getChildren().get(indiceFloor).getLayoutX(),
					floor.getChildren().get(indiceFloor).getLayoutY() - 4);
			break;
		}
	}

	public void removeImages(String direction) {
		switch (direction) {
		case "Right":

			for (int i = 0; i < floor.getChildren().size(); i++) {

				relocateImages(direction, i);
				// if (floor.getChildren().get(i).getLayoutX() < -32) {
				// floor.getChildren().remove(floor.getChildren().get(i));
				// }

			}

			break;

		case "Left":
			for (int i = floor.getChildren().size() - 1; i >= 0; i--) {

				relocateImages(direction, i);
				// if (floor.getChildren().get(i).getLayoutX() > 32 * 30) {
				// floor.getChildren().remove(floor.getChildren().get(i));
				// }s
			}

			break;

		case "Up":
			loop.pause();
			jumping = true;

			loop2 = new Timeline();
			loop2.setCycleCount(16);

			loop2.setOnFinished(ev -> {
				loop.play();

			});

			loop2.getKeyFrames().add(new KeyFrame(Duration.millis(25), (ev -> {

				for (int i = 0; i < floor.getChildren().size(); i++) {

					floor.getChildren().get(i).relocate(floor.getChildren().get(i).getLayoutX(),
							floor.getChildren().get(i).getLayoutY() + 4);
				}
				actions();
				// System.out.println(
				// floor.getChildren().get(30 * 20 - 30 * bill.getChrac().getY() / 32 + 2 +
				// 13).getLayoutY());
			})));

			loop2.play();
			loop2.getOnFinished();
			break;
		}

	}

	public void testCollision() {
		int tile = bill.getChrac().getY() / 32 + 2;
		floor.getChildren().get(30 * 20 - 30 * tile + 13);
	}

	public boolean alreadyJumping() {
		return jumping;
	}

	public String stopSroll() {

		if (floor.getChildren().get(1).getLayoutX() == charapane.getLayoutX() + bill.getChrac().getX() + 64) {
			return "left stop";
		}
		// System.out.println(floor.getChildren().size());

		if (floor.getChildren().get(floor.getChildren().size() - 1).getLayoutX() == charapane.getLayoutX()
				+ bill.getChrac().getX() + 32) {
			return "right stop";
		}
		return "";
	}

	public void stopAction() {
		switch (oldAnim) {
		case "RunRight":
			bill.getChrac().animation("idleRight");
			oldAnim = "idleRight";
			break;
		case "RunLeft":
			bill.getChrac().animation("idleLeft");
			oldAnim = "idleLeft";
			break;
		}
	}

	public void initAnimation() {
		loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);

		KeyFrame kf = new KeyFrame(Duration.millis(25), (ev -> {

			if (floor.getChildren().get(30 * 20 - 30 * bill.getChrac().getY() / 32 + 2 + 13).getLayoutY() > 476
					+ 32 * 4) {
				for (int i = 0; i < floor.getChildren().size(); i++) {
					relocateImages("Down", i);
				}
			} else
				jumping = false;

			actions();
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
