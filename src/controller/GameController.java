package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

public class GameController extends Controller {

	// private SimpleIntegerProperty absolute_x, absolute_y, absolute_charactX,
	// absolute_charactY;

	private GestionCollision detecteur;

	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();

	@FXML
	private AnchorPane background;
	@FXML
	private ImageView heart;
	@FXML
	private Pane floor;

	@FXML
	private Pane charapane;
	SimpleBooleanProperty isAlive;
	private Map mapPrincipale = new Map("src/maps/grosseMap_sol.csv", "src/maps/grosseMap_environnement.csv");
	private MapView mv;
	private Timeline loop, loop2;
	private int countX = 32, countY = 0, relocated = 0;
	private String delete, add;
	private int addLignTop = 0, addLignBot = 33, addLignX = 299;
	private int deleteLignX = 0, deleteLignY = 0;
	private boolean jumping = false, falling = true;

	private long temps;
	private BillView bill = new BillView("view/resources/personnages/right_static_bill.png");
	private String oldAnim = "tactac";

	ObservableList<Tiles> viewAbleSol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.getChildren().add(0, new ImageView(new Image("view/resources/tac.jpg")));
		isAlive = new SimpleBooleanProperty(true);
		detecteur = new GestionCollision(mapPrincipale);
		// absolute_x = new SimpleIntegerProperty(0);
		// absolute_y = new SimpleIntegerProperty(0);
		// absolute_charactX = new SimpleIntegerProperty();
		// absolute_charactY = new SimpleIntegerProperty();
		// absolute_charactX.bind(bill.getChrac().getXProperty());
		// absolute_charactY.bind(bill.getChrac().getYProperty());
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
							switch (add) {
							case "Left":
								img.relocate(5, tileAdded.getY() * 32 + relocated);
								break;
							case "Right":
								img.relocate(59 * 32 - 5, tileAdded.getY() * 32 + relocated);
								break;
							case "Up":
								img.relocate(tileAdded.getX() * 32, 0);
								break;
							case "Down":
								img.relocate(tileAdded.getX() * 32, 33 * 32);
								break;
							}
							floor.getChildren().add(img);
						}
					}
					if (c.wasRemoved()) {
						switch (delete) {
						case "Left":
							floor.getChildren().removeIf(img -> img.getLayoutX() < -32);
							break;
						case "Right":
							floor.getChildren().removeIf(img -> img.getLayoutX() > 60 * 32);
							break;
						case "Up":
							floor.getChildren().removeIf(img -> img.getLayoutY() < 0);
							break;
						case "Down":
							floor.getChildren().removeIf(img -> img.getLayoutY() > 33 * 32);
							break;
						}

					}
				}

			}

		});

	}

	public void isAlive() {

		bill.setLife(heart);
		if (bill.getChrac().getHp() == 0)
			isAlive.set(false);
		else
			isAlive.set(true);

	}

	public SimpleBooleanProperty getIsAlive() {
		return this.isAlive;
	}

	public void actions() {
		if ((keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT))
				&& detecteur.verifRight(bill.getChrac())) {
			// if (!stopSroll().equals("right stop")) {
			bill.getChrac().animation("RunRight");

			oldAnim = "RunRight";
			scroll("Right");

		}
		if ((keyPressed.contains(KeyCode.Q) || keyPressed.contains(KeyCode.LEFT))
				&& detecteur.verifLeft(bill.getChrac())) {
			// if (!stopSroll().equals("left stop")) {
			bill.getChrac().animation("RunLeft");

			oldAnim = "RunLeft";
			scroll("Left");

		}
		if (!jumping && keyPressed.contains(KeyCode.SPACE)) {
			bill.getChrac().animation(oldAnim.contains("Right") ? "jumpRight" : "jumpLeft");
			oldAnim = oldAnim.contains("Right") ? "jumpRight" : "jumpLeft";
			scroll("Up");
		}
	}

	public void scroll(String direction) {
		switch (direction) {
		case "Right":

			for (int i = 0; i < floor.getChildren().size(); i++)
				relocateImages(direction, i);
			CountAddDelete("Right");
			break;

		case "Left":
			for (int i = floor.getChildren().size() - 1; i >= 0; i--)
				relocateImages(direction, i);
			CountAddDelete("Left");
			// if (floor.getChildren().get(i).getLayoutX() > 32 * 30) {
			// floor.getChildren().remove(floor.getChildren().get(i));
			// }s

			break;

		case "Up":
			jumping = true;
			for (int i = 0; i < floor.getChildren().size(); i++)
				relocateImages("Up", i);
			relocated += bill.getChrac().getSpeed();
			CountAddDelete("Up");

			// loop2 = new Timeline();
			// loop2.setCycleCount(16);
			// loop2.setOnFinished(ev -> {falling = true;
			// jumping=false;
			// });
			// loop2.getKeyFrames().add(new KeyFrame(Duration.millis(25), (ev -> jumping =
			// true)));
			// loop2.play();

			break;
		case "Down":
			relocated -= bill.getChrac().getSpeed();
			for (int i = 0; i < floor.getChildren().size(); i++)
				relocateImages("Down", i);
			CountAddDelete("Down");

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
					floor.getChildren().get(indiceFloor).getLayoutY() + bill.getChrac().getSpeed());
			break;
		case "Down":// 108
			floor.getChildren().get(indiceFloor).relocate(floor.getChildren().get(indiceFloor).getLayoutX(),
					floor.getChildren().get(indiceFloor).getLayoutY() - bill.getChrac().getSpeed());

			break;
		}
	}

	/**
	 * gere l'ajout et la suppression des images au bord co
	 * 
	 * @param Direction
	 */
	public void CountAddDelete(String Direction) {
		switch (Direction) {
		case "Right":
			countX -= bill.getChrac().getSpeed();
			if (countX < 0) {
				bill.getChrac().move("RunRight");
				addImages("Right");
				deleteImages("Left");
				countX += 32;
			}
			break;

		case "Left":
			countX += bill.getChrac().getSpeed();
			if (32 - countX < 0) {
				bill.getChrac().move("RunLeft");
				addImages("Left");
				deleteImages("Right");
				countX -= 32;
			}
			break;

		case "Up":

			countY -= bill.getChrac().getSpeed();
			if (countY < 0) {
				bill.getChrac().move("Up");
				addImages("Up");
				// deleteImages("Down");
				countY += 32;
			}
			break;
		case "Down":
			countY += bill.getChrac().getSpeed();
			if (32 - countY <= 0) {
				bill.getChrac().move("Down");
				addImages("Down");
				// deleteImages("Up");
				countY -= 32;
			}

			break;
		}
	}

	/**
	 * ajoute les images au bord correspondant
	 * 
	 * @param direction
	 */
	public void addImages(String direction) {
		ObservableList<Tiles> ListSol = mapPrincipale.getTilesListSol();
		ObservableList<Tiles> ListMid = mapPrincipale.getTilesListMid();
		switch (direction) {

		case "Right":
			add = "Right";

			for (Tiles tile : ListMid)
				if (tile.getX() == (addLignX + 61) % 300 && addLignTop <= tile.getY() && tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			for (Tiles tile : ListSol)
				if (tile.getX() == (addLignX + 61) % 300 && addLignTop <= tile.getY() && tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			addLignX++;

			break;

		case "Left":
			add = "Left";
			for (Tiles tile : ListMid)
				if (tile.getX() == addLignX % 300 && addLignTop <= tile.getY() && tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			for (Tiles tile : ListSol)
				if (tile.getX() == addLignX % 300 && addLignTop <= tile.getY() && tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			addLignX--;

			break;

		case "Up":
			add = "Up";
			for (Tiles tile : ListSol)
				if (tile.getX() < 60 && tile.getY() == addLignTop) {
					viewAbleSol.add(tile);
				}
			addLignBot--;
			break;

		case "Down":
			add = "Down";
			for (Tiles tile : ListSol)
				if (tile.getX() < 60 && tile.getY() == addLignBot) {
					viewAbleSol.add(tile);
				}
			addLignBot++;

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

	public void deleteImages(String direction) {
		switch (direction) {
		case "Right":
			delete = "Right";
			viewAbleSol.removeIf(f -> f.getX() == deleteLignX + 59);
			deleteLignX--;
			break;

		case "Left":
			delete = "Left";
			viewAbleSol.removeIf(f -> f.getX() == deleteLignX);
			deleteLignX++;
			break;
		case "Up":
			delete = "Up";
			viewAbleSol.removeIf(f -> f.getX() == deleteLignY);
			deleteLignY++;
			break;
		case "Down":
			delete = "Down";
			viewAbleSol.removeIf(f -> f.getX() == deleteLignY + 32);
			deleteLignY++;
			break;
		}
	}

	public String stopSroll() {

		if (floor.getChildren().get(1).getLayoutX() == charapane.getLayoutX() + bill.getChrac().getX() + 64) {
			return "left stop";
		}

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

			if (temps % 40 == 0 && bill.getChrac().getHp() > 0) {
				// bill.getChrac().damage(2);
				// System.out.println(bill.getChrac().getHp());
				isAlive();
			}
			if (jumping && !falling && temps != 19) {
				scroll("Up");
				temps++;
			} else if (detecteur.verifUnder(bill.getChrac())) {
				falling = true;
				scroll("Down");
				temps = 0;
			} else {
				jumping = false;
				falling = false;
			}
			actions();
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
