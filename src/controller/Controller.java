package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import view.game.TileView;
import model.game.GestionCollision;
import model.game.Map;
import model.game.Tiles;

public class Controller implements Initializable {

	private static final int gauche = -32, droite = 1952;

	private SimpleIntegerProperty absolute_x, absolute_y, absolute_charactX, absolute_charactY;

	private GestionCollision detecteur;

	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();

	@FXML
	private AnchorPane background;

	@FXML
	private Pane floor;

	@FXML
	private Pane charapane;

	private Map mapPrincipale = new Map("src/maps/grosseMap_sol.csv", "src/maps/grosseMap_environnement.csv");
	private MapView mv;
	private Timeline loop, loop2;

	int countRight = 32;
	int countLeft = 32;

	boolean jumping = false;

	private long temps;
	private BillView bill = new BillView("view/resources/personnages/right_static_bill.png");
	private String oldAnim = "tactac";
	int deleteLignLeft = 0;
	int deleteLignRight = 59;
	int addLignLeft = 299;
	int addLignRight = 60;
	boolean deleteLeft = false;
	boolean addLeft = false;
	ObservableList<Tiles> viewAbleSol;
	int relocated = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.getChildren().add(0, new ImageView(new Image("view/resources/tac.jpg")));
		detecteur = new GestionCollision(mapPrincipale);
		// absolute_x = new SimpleIntegerProperty(0);
		// absolute_y = new SimpleIntegerProperty(0);
		absolute_charactX = new SimpleIntegerProperty();
		absolute_charactY = new SimpleIntegerProperty();
		absolute_charactX.bind(bill.getChrac().getXProperty());
		absolute_charactY.bind(bill.getChrac().getYProperty());
		mv = new MapView(mapPrincipale);
		viewAbleSol = mv.getListViewSol();
		initAnimation();
		loop.play();
		bill.getChrac().setSpeed(4);

		charapane.getChildren().add(bill.getImage());
		floor.getChildren().addAll(mv.creerVue());

		viewAbleSol.addListener(new ListChangeListener<Tiles>() {

			@Override
			public void onChanged(Change<? extends Tiles> c) {

				while (c.next()) {
					if (c.wasAdded()) {
						for (Tiles tileAdded : c.getAddedSubList()) {
							ImageView img = new TileView(tileAdded);
							if (addLeft)
								img.relocate(5, tileAdded.getY() * 32 + relocated);
							else
								img.relocate(59 * 32 - 5, tileAdded.getY() * 32 + relocated);
							floor.getChildren().add(img);

						}
					}
					if (c.wasRemoved()) {
						if (deleteLeft) {
							floor.getChildren().removeIf(img -> img.getLayoutX() < -32);
						} else {
							floor.getChildren().removeIf(img -> img.getLayoutX() > 60 * 32);
						}
					}
				}

			}

		});

	}

	public void actions() {
		if (keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT)) {
			// if (!stopSroll().equals("right stop")) {
			bill.getChrac().animation("RunRight");
			if (temps * Duration.millis(25).toMillis() % 40 == 0) {
				System.out.println(temps / 40);
				bill.getChrac().move("RunRight");
			}

			oldAnim = "RunRight";
			scroll("Right");

			/*
			 * departr += bill.getChrac().getSpeed(); if (departr % 960 / 32 == 29) {
			 * tileSol = new Map("src/maps/carte.txt", "src/maps/carte2.txt",
			 * "src/maps/carte3.txt"); mv = new MapView(tileSol);
			 * floor.getChildren().addAll(mv.creerVue()); departr = 13 * 32;
			 * 
			 * }
			 */

		}
		// }

		if (keyPressed.contains(KeyCode.Q) || keyPressed.contains(KeyCode.LEFT)) {
			// if (!stopSroll().equals("left stop")) {
			bill.getChrac().animation("RunLeft");
			if (temps * Duration.millis(25).toMillis() % 40 == 0) {
				System.out.println(temps / 40);
				bill.getChrac().move("RunLeft");
			}
			oldAnim = "RunLeft";
			scroll("Left");

		}

		// }

		if (keyPressed.contains(KeyCode.SPACE)) {
			if (!alreadyJumping()) {
				bill.getChrac().animation("jumpRight");
				bill.getChrac().move("Jump");
				oldAnim = "jumpRight";
				scroll("Up");
			}

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
					floor.getChildren().get(indiceFloor).getLayoutY() + bill.getChrac().getSpeed());
			break;
		case "Down":// 108
			floor.getChildren().get(indiceFloor).relocate(floor.getChildren().get(indiceFloor).getLayoutX(),
					floor.getChildren().get(indiceFloor).getLayoutY() - bill.getChrac().getSpeed());

			break;
		}
	}

	public void addImages(String direction) {
		ObservableList<Tiles> ListSol = mapPrincipale.getTilesListSol();
		switch (direction) {
		case "Right":
			addLeft = false;
			for (Tiles tile : ListSol)
				if (tile.getX() == addLignRight && tile.getY() < 40)
					viewAbleSol.add(tile);
			addLignRight++;
			addLignLeft++;
			if (addLignRight == 300)
				addLignRight = 0;
			if (addLignLeft == 300)
				addLignLeft = 0;

			break;

		case "Left":
			addLeft = true;
			for (Tiles tile : ListSol)
				if (tile.getX() == addLignLeft && tile.getY() < 40)
					viewAbleSol.add(tile);
			addLignLeft--;
			addLignRight--;
			if (addLignLeft == -1)
				addLignLeft = 299;
			if (addLignRight == -1)
				addLignRight = 299;
			break;

		}
		/*
		 * countLeft -= bill.getChrac().getSpeed(); departl
		 * +=bill.getChrac().getSpeed(); int tileLeft = departl % 960 / 32; if (tileLeft
		 * == 30) tileLeft = 0; int tLeft[][] = tileSol.getMap(0); if (countLeft <= 0) {
		 * for (int x = 0; x < tLeft.length; x++) { if (tLeft[x][tileLeft] != 0) {
		 * ImageView img = new ImageView(Tiles.selectionTuile(tLeft[x][tileLeft]));
		 * img.relocate(0, x * 32); floor.getChildren().add(img); } } countLeft += 32; }
		 * if (departr - bill.getChrac().getSpeed() >= 0) departr -=
		 * bill.getChrac().getSpeed();
		 */
	}

	public void deleteImages(String direction) {
		switch (direction) {
		case "Right":
			deleteLeft = false;
			viewAbleSol.removeIf(f -> f.getX() == deleteLignRight);
			deleteLignRight--;
			deleteLignLeft--;

			break;

		case "Left":
			deleteLeft = true;
			viewAbleSol.removeIf(f -> f.getX() == deleteLignLeft);
			deleteLignLeft++;
			deleteLignRight++;

			break;
		}
	}

	public void scroll(String direction) {
		switch (direction) {
		case "Right":

			for (int i = 0; i < floor.getChildren().size(); i++)
				relocateImages(direction, i);

			countRight -= bill.getChrac().getSpeed();
			countLeft += bill.getChrac().getSpeed();
			if (countLeft > 32)
				countLeft -= 32;
			if (countRight < 0) {
				addImages("Right");
				deleteImages("Left");
				countRight += 32;
			}

			break;

		case "Left":
			for (int i = floor.getChildren().size() - 1; i >= 0; i--)
				relocateImages(direction, i);

			countLeft -= bill.getChrac().getSpeed();
			countRight += bill.getChrac().getSpeed();

			if (countRight > 32)
				countRight -= 32;
			if (countLeft < 0) {
				addImages("Left");
				deleteImages("Right");
				countLeft += 32;
			}
			// if (floor.getChildren().get(i).getLayoutX() > 32 * 30) {
			// floor.getChildren().remove(floor.getChildren().get(i));
			// }s

			break;

		case "Up":
			loop.pause();
			jumping = true;

			loop2 = new Timeline();
			loop2.setCycleCount(16);

			loop2.setOnFinished(ev -> {loop.play();
			bill.getChrac().move("Up");
			temps=0;
			});

			loop2.getKeyFrames().add(new KeyFrame(Duration.millis(25), (ev -> {

				for (int i = 0; i < floor.getChildren().size(); i++) {
					floor.getChildren().get(i).relocate(floor.getChildren().get(i).getLayoutX(),
							floor.getChildren().get(i).getLayoutY() + 4);
				}
				relocated += bill.getChrac().getSpeed();
				
					
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
		temps = 0;
		loop.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(25), (ev -> {
			if (detecteur.verifUnder(bill.getChrac())) {
				for (int i = 0; i < floor.getChildren().size(); i++) {
					relocateImages("Down", i);
				}
				relocated -= bill.getChrac().getSpeed();
				if (temps * Duration.millis(25).toMillis() % 40 == 0)
					bill.getChrac().move("Down");
			} else
				jumping = false;

			if(jumping) {
				for (int i = 0; i < floor.getChildren().size(); i++) {
					floor.getChildren().get(i).relocate(floor.getChildren().get(i).getLayoutX(),
							floor.getChildren().get(i).getLayoutY() + 4);
				}
				relocated += bill.getChrac().getSpeed();
			}
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
