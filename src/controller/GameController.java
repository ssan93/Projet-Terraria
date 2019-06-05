package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.game.BillView;
import view.game.MapView;
import view.game.TileView;
import model.game.GestionCollision;
import model.game.Inventory;
import model.game.InventoryItem;
import model.game.Map;
import model.game.Tiles;

public class GameController extends Controller {

	private GestionCollision detecteur;

	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();

	private Inventory inventaire;

	@FXML
	private Pane effect;

	@FXML
	private Pane inventoryContainer;

	@FXML
	private GridPane layoutInventory;

	@FXML
	private Button utiliser;

	@FXML
	private Button jeter;

	@FXML
	private AnchorPane background;
	@FXML
	private ImageView heart;
	@FXML
	private Pane floor;
	@FXML
	private Pane charapane;

	private SimpleBooleanProperty isAlive;
	private Map mapPrincipale = new Map("src/maps/grosseMap_sol.csv", "src/maps/grosseMap_environnement.csv");
	private MapView mv;
	private Timeline loop;
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
		effect.visibleProperty().bind(inventoryContainer.visibleProperty());
		inventoryContainer.setVisible(false);
		TileView.inizImages();
		inventaire = new Inventory();
		background.getChildren().add(0, new ImageView(new Image("view/resources/tac.jpg")));
		isAlive = new SimpleBooleanProperty(true);
		detecteur = new GestionCollision(mapPrincipale);
		mv = new MapView(mapPrincipale);
		viewAbleSol = mv.getListViewSol();
		initAnimation();
		loop.play();
		bill.getChrac().setSpeed(4);
		charapane.getChildren().add(bill.getImage());
		floor.getChildren().addAll(mv.creerVue());
		addListen();
		inventaire.addToInventory("pioche", 1);
		inventaire.addToInventory("M16", 1);
		inventaire.addToInventory("cuivre", 5);
		inventaire.addToInventory("plastique", 1);
		inventaire.addToInventory("metal", 5);
	}

	public void addListen() {
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
		floor.getChildren().addListener(new ListChangeListener<Node>() {

			@Override
			public void onChanged(Change<? extends Node> c) {
				// TODO Auto-generated method stub
				while (c.next()) {
					if (c.wasRemoved()) {
					}
				}
			}

		});

		inventaire.addListener(new ListChangeListener<InventoryItem>() {

			int lastColumn = 0, lastRow = 0;

			@Override
			public void onChanged(Change<? extends InventoryItem> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						ImageView img = new ImageView(
								"view/resources/Inventaire/" + c.getAddedSubList().get(0).getName() + "_Inv.png");
						img.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								boolean in = event.getEventType().equals(MouseEvent.MOUSE_EXITED) ? false :true;
								if (in)
									img.setEffect(new DropShadow(35, 0, 0, Color.CORNFLOWERBLUE));
								else
									img.setEffect(null);
							}
						});
						layoutInventory.add(img, lastRow, lastColumn);
						if (lastRow == 3) {
							lastColumn++;
							lastRow = 0;
						} else
							lastRow++;
					}
					if (c.wasRemoved()) {
					}
				}
			}

		});

		inventoryContainer.visibleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				inventoryContainer.setVisible(true);
				loop.pause();
			} else {
				inventoryContainer.setVisible(false);
				loop.play();
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
	 * gere l'ajout et la suppression des images au bord et fait bouger le
	 * perso(modele) au bon moment
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
			if (32 < countX) {
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
			if (32 < countY) {
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
	}

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

	// public String stopSroll() {
	//
	// if (floor.getChildren().get(1).getLayoutX() == charapane.getLayoutX() +
	// bill.getChrac().getX() + 64) {
	// return "left stop";
	// }
	//
	// if (floor.getChildren().get(floor.getChildren().size() - 1).getLayoutX() ==
	// charapane.getLayoutX()
	// + bill.getChrac().getX() + 32) {
	// return "right stop";
	// }
	// stopAction();
	// return "";
	// }

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

	private void initAnimation() {
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

	public Pane getInventoryContainer() {
		return inventoryContainer;
	}

	@FXML
	void drop(ActionEvent event) {

	}

	@FXML
	void use(ActionEvent event) {

	}

	public void clickGrid(javafx.scene.input.MouseEvent event) {
		Node clickedNode = event.getPickResult().getIntersectedNode();
		System.out.println(clickedNode);
		if (clickedNode != layoutInventory) {
			// click on descendant node
			Integer colIndex = GridPane.getColumnIndex(clickedNode);
			Integer rowIndex = GridPane.getRowIndex(clickedNode);
			System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);
		}
	}

}
