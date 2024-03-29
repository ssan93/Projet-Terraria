package controller;

import java.io.File;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import view.game.BillView;
import view.game.EnnemyView;
import view.game.MapView;
import view.game.TileView;
import model.game.Craft;
import model.game.Character;
import model.game.ColdSteel;
import model.game.CollisionManager;
import model.game.Inventory;
import model.game.InventoryItem;
import model.game.Map;
import model.game.Material;
import model.game.Tiles;
import model.game.Tool;
import model.game.Weapon;
import model.game.radioChatter;

public class GameController extends Controller {

	private MediaPlayer effectPlayer;
	private radioChatter ra = new radioChatter();
	private CollisionManager detecteur;
	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();
	private boolean allowMouv = false;
	private InventoryItem focused = null;
	private ObservableList<Tiles> viewAbleSol;
	private ObservableList<InventoryItem> selected;
	private ObservableList<EnnemyView> enemies;
	private Inventory craftable;
	private InventoryItem equiped;
	private boolean isPlaying = false;

	@FXML
	private Label desc;
	@FXML
	private Label radio;
	@FXML
	private Button ok;

	private Inventory inventaire;

	@FXML
	private Pane effect;

	@FXML
	private Pane inventoryContainer;

	@FXML
	private GridPane layoutInventory;

	@FXML
	private Button equiper;

	@FXML
	private Button jeter;

	@FXML
	private AnchorPane background;
	@FXML
	private ImageView heart, codec;
	@FXML
	private Pane floor;
	@FXML
	private Pane pnjPane;
	@FXML
	private Pane charapane;
	@FXML
	private ImageView equip;

	private SimpleBooleanProperty isAlive;
	private Map mapPrincipale = new Map("src/maps/grosseMap_sol.csv", "src/maps/grosseMap_environnement.csv");
	private MapView mv;
	private Timeline loop;
	private int countX = 32, countY = 0, relocated = 0;
	private String delete, add;
	private int addLignTop = 0, addLignBot = 35, addLignX = 0;
	private int deleteLignX = 0, deleteLignY = 0;
	private boolean jumping = false, falling = true;
	private long temps;
	private BillView bill = new BillView("view/resources/personnages/right_static_bill.png");
	private String oldAnim = "tactac";
	@FXML
	private Pane craftContainer;

	@FXML
	private GridPane layoutCraft;

	@FXML
	private Button craft;

	private EnnemyView buffalo = new EnnemyView("view/resources/personnages/buffalo.gif", 1, 20, 10);
	private EnnemyView chicken = new EnnemyView("view/resources/personnages/Chicken.gif", 42 * 4, 22, 10);
	private EnnemyView oculus = new EnnemyView("view/resources/personnages/oculus.gif", 43 * 4, 22, 10);
	private EnnemyView fly = new EnnemyView("view/resources/personnages/heli1.gif", 42, 15, 10);
	private int temps2 = 0;
	int moveBuffalo = 1;
	int moveChicken = 1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Craft.inizCraft();
		TileView.inizImages();
		craftable = new Inventory();
		selected = FXCollections.observableArrayList();
		enemies = FXCollections.observableArrayList();
		changeRdi(null);
		effect.visibleProperty().bind(inventoryContainer.visibleProperty());
		inventoryContainer.setVisible(false);
		inventaire = new Inventory();
		background.getChildren().add(0, new ImageView(new Image("view/resources/tac.jpg")));
		isAlive = new SimpleBooleanProperty(true);
		detecteur = new CollisionManager(mapPrincipale);
		mv = new MapView(mapPrincipale);
		viewAbleSol = mv.getListViewSol();
		initAnimation();
		loop.play();
		bill.getChrac().setSpeed(4);
		charapane.getChildren().add(bill);
		floor.getChildren().addAll(mv.creerVue());
		addListen();
		inventaire.addToInventory(new Tool("pioche",
				"La pioche est un outil composé de deux pièces : une pièce de travail en acier fixée par l'intermédiaire d'un œil à un manche en bois dur. La pièce de métal forme un angle d'environ 90° avec le manche."));
		focused = inventaire.get(0);
		equiped = focused;
		bill.getChrac().setEquiped(equiped);
		equip.setImage(new Image("view/resources/Inventaire/" + focused.getName() + "_Inv.png"));
		inventaire.addToInventory(Craft.objetÀcraft.get("10 fer, 10 plastique, 10 cuivre, etabli"));
		play();
		inventaire.addToInventory(new Material("bois", 1, ""));
		inventaire.addToInventory(new Material("metal", 2, ""));
		enemies.add(buffalo);
		enemies.add(chicken);
		enemies.add(fly);
		enemies.add(oculus);
		charapane.setDisable(true);
		pnjPane.setDisable(true);
		buffalo.layoutXProperty().bind(buffalo.getChrac().getXProperty().multiply(32 / 4));
		buffalo.layoutYProperty().bind(buffalo.getChrac().getYProperty().multiply(32));
		chicken.layoutXProperty().bind(chicken.getChrac().getXProperty().multiply(32 / 4));
		chicken.layoutYProperty().bind(chicken.getChrac().getYProperty().multiply(32));
		oculus.layoutXProperty().bind(oculus.getChrac().getXProperty().multiply(32 / 4));
		oculus.layoutYProperty().bind(oculus.getChrac().getYProperty().multiply(32));
		fly.layoutXProperty().bind(fly.getChrac().getXProperty().multiply(32).subtract(32));
		fly.layoutYProperty().bind(fly.getChrac().getYProperty().multiply(32).subtract(32));
		fly.getChrac().getHpProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() <= 0)
				pnjPane.getChildren().remove(fly);
		});
		detecteur.astar(new Tiles(23, 23), new Tiles(25, 23));
		craftable.addToInventory(Craft.objetÀcraft.get("1 bois, 2 metal"));
		craftable.addToInventory(Craft.objetÀcraft.get("1 bois, 1 metal"));
	}

	/**
	 * ajoute tout les ecouteurs nécessaires
	 */
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
								img.relocate(5 - 32, tileAdded.getY() * 32 + relocated);
								break;
							case "Right":
								img.relocate(60 * 32 - 5, tileAdded.getY() * 32 + relocated);
								break;
							case "Up":
								img.relocate(tileAdded.getX() * 32, 0);
								break;
							case "Down":
								img.relocate((tileAdded.getX() * 32 - addLignX * 32 - 32 + countX) % (Map.WIDTH * 32),
										34 * 32);
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
							floor.getChildren().removeIf(img -> img.getLayoutY() > 34 * 32);
							break;
						case "mouse":

							break;
						}
					}
				}
			}
		});
		floor.getChildren().addListener(new ListChangeListener<Node>() {

			@Override
			public void onChanged(Change<? extends Node> c) {
				while (c.next()) {
					if (c.wasRemoved()) {
						if (delete == "mouse") {
							for (Node t : c.getRemoved()) {
								if (t instanceof TileView) {
									ObservableList<Tiles> ListSol = mapPrincipale.getTilesListSol();

									int[][] tabSol = mapPrincipale.getMapSol();

									TileView tView = (TileView) t;
									Tiles tile = tView.getTile();
									ListSol.remove(tile);
									viewAbleSol.remove(tile);
									tabSol[tile.getX()][tile.getY()] = 0;

								}
							}
						}
					}
					if (c.wasAdded()) {
						if (add == "mouse") {
							for (Node t : c.getAddedSubList()) {
								if (t instanceof TileView) {
									ObservableList<Tiles> ListSol = mapPrincipale.getTilesListSol();
									TileView tView = (TileView) t;
									Tiles tile = tView.getTile();
									ListSol.add(tile);
									// viewAbleSol.add(tile);
									mapPrincipale.setTileSol(tile.getX(), tile.getY(), tile.getCode());
								}
							}

						}
					}
				}
			}

		});

		inventaire.addListener(new ListChangeListener<InventoryItem>() {

			int lastRow = 0, lastCol = 0;

			@Override
			public void onChanged(Change<? extends InventoryItem> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						ImageView img = new ImageView(
								"view/resources/Inventaire/" + c.getAddedSubList().get(0).getName() + "_Inv.png");
						img.setId(c.getAddedSubList().get(0).getName());
						img.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
							InventoryItem item = c.getAddedSubList().get(0);

							@Override
							public void handle(MouseEvent event) {
								boolean in = event.getEventType().equals(MouseEvent.MOUSE_EXITED) ? false : true;
								if (in && !selected.contains(item))
									img.setEffect(new DropShadow(35, 0, 0, Color.CORNFLOWERBLUE));
								else if (!in && !selected.contains(item))
									img.setEffect(null);
								if (event.isPrimaryButtonDown()) {
									focused = inventaire.get(inventaire.indexOf(img.getId()));
									desc.setText(focused.toString());
									if (keyPressed.contains(KeyCode.CONTROL) || keyPressed.contains(KeyCode.SHIFT))
										if (selected.contains(focused)) {
											selected.remove(focused);
											img.setEffect(null);
										} else {
											selected.add(focused);
											img.setEffect(new DropShadow(35, 0, 0, Color.ORANGERED));
										}
								}
							}
						});
						layoutInventory.add(img, lastCol, lastRow);
						if (lastCol == 3) {
							lastRow++;
							lastCol = 0;
						} else
							lastCol++;
					}
					if (c.wasRemoved()) {
						lastCol = 0;
						lastRow = 0;
						c.getRemoved().forEach(
								i -> layoutInventory.getChildren().removeIf(f -> f.getId().equals(i.getName())));
						layoutInventory.getChildren().forEach(img -> {
							GridPane.clearConstraints(img);
							GridPane.setConstraints(img, lastCol, lastRow);
							if (lastCol == 3) {
								lastRow++;
								lastCol = 0;
							} else
								lastCol++;
						});
						desc.setText("");
						focused = inventaire.get(0);
						equiped = focused;
						equip.setImage(new Image("view/resources/Inventaire/" + focused.getName() + "_Inv.png"));
						equip.setId(focused.getName());
						// ici
					}
				}
			}

		});

		inventoryContainer.visibleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue)
				loop.pause();
			else {
				selected.clear();
				loop.play();
			}
			equiper.setText("Equiper");
		});

		craftContainer.visibleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue)
				loop.pause();
			else {
				selected.clear();
				loop.play();
			}
		});

		craftable.addListener(new ListChangeListener<InventoryItem>() {
			int lastRow = 0, lastCol = 0;

			@Override
			public void onChanged(Change<? extends InventoryItem> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						ImageView img = new ImageView(
								"view/resources/Inventaire/" + c.getAddedSubList().get(0).getName() + "_Inv.png");
						img.setId(c.getAddedSubList().get(0).getName());
						img.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
							InventoryItem item = c.getAddedSubList().get(0);

							@Override
							public void handle(MouseEvent event) {
								boolean in = event.getEventType().equals(MouseEvent.MOUSE_EXITED) ? false : true;
								if (in)
									img.setEffect(new DropShadow(35, 0, 0, Color.CORNFLOWERBLUE));
								else if (!in)
									img.setEffect(null);
								if (event.isPrimaryButtonDown()) {
									focused = item;
									desc.setText(focused.toString());
								}
							}
						});
						layoutCraft.add(img, lastCol, lastRow);
						if (lastCol == 3) {
							lastRow++;
							lastCol = 0;
						} else
							lastCol++;
					}
				}
			}
		});
		enemies.addListener(new ListChangeListener<EnnemyView>() {

			@Override
			public void onChanged(Change<? extends EnnemyView> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						// ici
						pnjPane.getChildren().addAll(c.getAddedSubList());
						c.getAddedSubList().forEach(
								en -> en.getChrac().getHpProperty().addListener((observable, oldValue, newValue) -> {
									if (newValue.intValue() <= 0) {
										pnjPane.getChildren().remove(en);
										enemies.remove(en);
									}
									
								}));
					}
				}
			}
		});
	}

	public void play() {
		// the player is set with a random music
		this.player = new MediaPlayer(new Media(new File("src/menu-musics/background.mp3").toURI().toString()));
		player.play();
		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				play();
			}
		});
	}

	public void effectPlay(String sfx) {
		this.effectPlayer = new MediaPlayer(new Media(new File(sfx).toURI().toString()));
		this.effectPlayer.play();
		isPlaying = true;
		if (sfx.equals("src/menu-musics/marche.mp3")) {
			effectPlayer.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					effectPlay(sfx);
				}
			});
		}
	}

	public void endEffectPlay() {
		this.effectPlayer.dispose();
		isPlaying = false;
	}

	public void isAlive() {

		bill.setLife(heart);
		if (bill.getChrac().getHp() == 0) {
			loop.stop();
			System.gc();
			isAlive.set(false);

		} else
			isAlive.set(true);

	}

	@FXML
	void changeRdi(ActionEvent event) {
		String buff = ra.getCall();
		if (!buff.equals("endoffile"))
			radio.setText(buff);
		else {
			ok.setDisable(true);
			radio.setVisible(false);
			ok.setVisible(false);
			codec.setVisible(false);
			codec.getParent().prefHeight(100);
			allowMouv = true;
		}
	}

	public SimpleBooleanProperty getIsAlive() {
		return this.isAlive;
	}

	/**
	 * toute les actions(touche clavier)
	 */
	public void actions() {

		if (allowMouv && ((keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT)))
				&& detecteur.verifRight(bill.getChrac(), jumping, falling, "bill")) {
			bill.getChrac().animation("Right");
			if (isPlaying && jumping) {
				endEffectPlay();
			}
			if (!isPlaying && !falling && !jumping) {
				effectPlay("src/menu-musics/marche.mp3");
			}
			oldAnim = "Right";
			scroll("Right");
		}
		if (allowMouv && ((keyPressed.contains(KeyCode.Q) || keyPressed.contains(KeyCode.LEFT)))
				&& detecteur.verifLeft(bill.getChrac(), jumping, falling, "bill")) {
			bill.getChrac().animation("Left");
			if (isPlaying && jumping || falling) {
				endEffectPlay();
			}
			if (!isPlaying && !falling && !jumping) {
				effectPlay("src/menu-musics/marche.mp3");
			}
			oldAnim = "Left";
			scroll("Left");
		}

		if (!jumping && keyPressed.contains(KeyCode.SPACE) && allowMouv && detecteur.verifTop(bill.getChrac())) {
			bill.getChrac().animation(oldAnim.contains("Right") ? "jumpRight" : "jumpLeft");
			oldAnim = oldAnim.contains("Right") ? "jumpRight" : "jumpLeft";
			scroll("Up");
			if (isPlaying && jumping) {
				effectPlay("src/menu-musics/saut.mp3");
			}
			if (!isPlaying && jumping) {
				effectPlay("src/menu-musics/saut.mp3");

			}

		}
	}

	public void scroll(String direction) {
		switch (direction) {
		case "Right":

			for (int i = 0; i < floor.getChildren().size(); i++)
				relocateImages(direction, i);
			pnjPane.relocate(pnjPane.getLayoutX() - bill.getChrac().getSpeed(), pnjPane.getLayoutY());
			CountAddDelete("Right");
			break;

		case "Left":
			for (int i = floor.getChildren().size() - 1; i >= 0; i--)
				relocateImages(direction, i);
			pnjPane.relocate(pnjPane.getLayoutX() + bill.getChrac().getSpeed(), pnjPane.getLayoutY());
			CountAddDelete("Left");
			break;

		case "Up":
			jumping = true;
			for (int i = 0; i < floor.getChildren().size(); i++)
				relocateImages("Up", i);
			pnjPane.relocate(pnjPane.getLayoutX(), pnjPane.getLayoutY() + bill.getChrac().getSpeed());
			relocated += bill.getChrac().getSpeed();
			CountAddDelete("Up");
			break;

		case "Down":
			relocated -= bill.getChrac().getSpeed();
			for (int i = 0; i < floor.getChildren().size(); i++)
				relocateImages("Down", i);
			pnjPane.relocate(pnjPane.getLayoutX(), pnjPane.getLayoutY() - bill.getChrac().getSpeed());
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
	 * gere l'ajout et la suppression des images au bord et fait bouger bill(modele)
	 * au bon moment
	 * 
	 * @param Direction
	 */
	public void CountAddDelete(String Direction) {
		switch (Direction) {
		case "Right":
			countX -= bill.getChrac().getSpeed();
			if (countX < 0) {
				bill.getChrac().move("Right", false);
				addImages("Right");
				deleteImages("Left");
				countX += 32;
			}
			break;

		case "Left":
			countX += bill.getChrac().getSpeed();
			if (32 < countX) {
				bill.getChrac().move("Left", false);
				addImages("Left");
				deleteImages("Right");
				countX -= 32;
			}
			break;

		case "Up":

			countY -= bill.getChrac().getSpeed();
			if (countY < 0) {
				bill.getChrac().move("Up", false);
				addImages("Up");
				deleteImages("Down");
				countY += 32;
			}
			break;
		case "Down":
			countY += bill.getChrac().getSpeed();
			if (32 - countY <= 0) {
				bill.getChrac().move("Down", false);
				addImages("Down");
				countY -= 32;
			}

			break;
		}
	}

	/**
	 * add images at the corresponding edge
	 * 
	 * @param direction
	 *            of the scroll
	 */
	public void addImages(String direction) {
		ObservableList<Tiles> ListSol = mapPrincipale.getTilesListSol();
		ObservableList<Tiles> ListMid = mapPrincipale.getTilesListMid();
		switch (direction) {

		case "Right":
			add = "Right";
			for (Tiles tile : ListMid)
				if (tile.getX() == (addLignX + 61) % Map.WIDTH && addLignTop <= tile.getY()
						&& tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			for (Tiles tile : ListSol)
				if (tile.getX() == (addLignX + 61) % Map.WIDTH && addLignTop <= tile.getY()
						&& tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			addLignX++;

			break;

		case "Left":
			add = "Left";
			for (Tiles tile : ListMid)
				if (tile.getX() == (addLignX - 1 + Map.WIDTH) % Map.WIDTH && addLignTop <= tile.getY()
						&& tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			for (Tiles tile : ListSol)
				if (tile.getX() == (addLignX - 1 + Map.WIDTH) % Map.WIDTH && addLignTop <= tile.getY()
						&& tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			addLignX--;

			break;

		case "Up":
			add = "Up";
			for (Tiles tile : ListSol)
				if (tile.getX() >= addLignX && tile.getX() < addLignX + 60 && tile.getY() == addLignTop)
					viewAbleSol.add(tile);

			addLignBot--;
			break;

		case "Down":
			add = "Down";
			int leftBorn = (addLignX - 1 + Map.WIDTH) % Map.WIDTH;
			int rightBorn = (addLignX + 61 + Map.WIDTH) % Map.WIDTH;
			for (Tiles tile : ListMid)
				if ((leftBorn > rightBorn
						&& ((tile.getX() >= leftBorn || tile.getX() < rightBorn) && tile.getY() == addLignBot))
						|| (tile.getX() >= leftBorn && tile.getX() < rightBorn && tile.getY() == addLignBot))
					viewAbleSol.add(tile);
			for (Tiles tile : ListSol)
				if ((leftBorn > rightBorn
						&& ((tile.getX() >= leftBorn || tile.getX() < rightBorn) && tile.getY() == addLignBot))
						|| (tile.getX() >= leftBorn && tile.getX() < rightBorn && tile.getY() == addLignBot))
					viewAbleSol.add(tile);
			addLignBot++;
			break;

		}
	}

	/**
	 * delete images according to the direction
	 * 
	 * @param direction
	 *            of the scroll
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
			viewAbleSol.removeIf(f -> f.getX() == deleteLignY + 34);
			deleteLignY++;
			break;
		}
	}

	public void stopAction() {

		switch (oldAnim) {
		case "Right":
			bill.getChrac().animation("idleRight");
			oldAnim = "idleRight";
			endEffectPlay();
			break;
		case "Left":
			bill.getChrac().animation("idleLeft");
			oldAnim = "idleLeft";
			endEffectPlay();
			break;
		}
	}

	private void initAnimation() {
		loop = new Timeline();
		temps = 0;
		loop.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(25), (ev -> {
			isAlive();
			if (jumping && !falling && temps != 19 && detecteur.verifTop(bill.getChrac())) {
				scroll("Up");
				temps++;

			} else if (detecteur.verifUnder(bill.getChrac(), "bill")) {
				falling = true;
				scroll("Down");
				temps = 0;
			} else {
				jumping = false;
				falling = false;
			}
			temps2++;
			actions();
			flyMove();
			randomMove(buffalo.getChrac(), "buffalo");
			randomMove(chicken.getChrac(), "chicken");
			animalFalling();
			oculusMove();

		}));
		loop.getKeyFrames().add(kf);
	}

	public void animalFalling() {
		if (detecteur.verifUnder(chicken.getChrac(), "chicken"))
			chicken.getChrac().move("Down", true);
		if (detecteur.verifUnder(buffalo.getChrac(), "buffalo"))
			buffalo.getChrac().move("Down", true);
		if (detecteur.verifUnder(oculus.getChrac(), "chicken"))
			oculus.getChrac().move("Down", true);
	}

	public void randomMove(Character ch, String character) {
		if (temps2 % ((int) Math.random() * 11 + 10) == 0) {
			moveBuffalo = (int) (Math.random() * 5) + 1;
			moveChicken = (int) (Math.random() * 5) + 1;
		}
		if (character.equals("buffalo"))
			switchMove(ch, character, moveBuffalo);
		else if (character.equals("chicken"))
			switchMove(ch, character, moveChicken);

	}

	public void switchMove(Character ch, String character, int move) {
		switch (move) {
		case 1:
			if (detecteur.verifRight(ch, false, false, character))
				ch.move("Right", true);
			else if (detecteur.verifTopRight(ch, character)) {
				ch.move("Up", true);
				ch.move("Right", true);
			}
			ch.animation("right_run_" + character);
			break;
		case 2:

			if (detecteur.verifLeft(ch, false, false, character))
				ch.move("Left", true);
			else if (detecteur.verifTopLeft(ch, character)) {
				ch.move("Up", true);
				ch.move("Left", true);
			}
			ch.animation("left_run_" + character);
			break;
		case 3:
			ch.animation("right_static_" + character);
			break;
		case 4:
			ch.animation("left_static_" + character);
			break;
		}
	}

	public void oculusMove() {
		if (enemies.contains(oculus)) {
			if (bill.getChrac().getX() < oculus.getChrac().getX() / 4) {
				if (detecteur.verifLeft(oculus.getChrac(), false, false, "chicken"))
					oculus.getChrac().move("Left", true);
				else if (detecteur.verifTopLeft(oculus.getChrac(), "chicken")) {
					oculus.getChrac().move("Up", true);
					oculus.getChrac().move("Left", true);
				} 
			}
			else if (bill.getChrac().getX() == oculus.getChrac().getX() / 4 && bill.getChrac().getY() == oculus.getChrac().getY())
				bill.getChrac().damage(1);

			 else if (bill.getChrac().getX() > oculus.getChrac().getX() / 4) {
				if (detecteur.verifRight(oculus.getChrac(), false, false, "chicken"))
					oculus.getChrac().move("Right", true);
				else if (detecteur.verifTopRight(oculus.getChrac(), "chicken")) {
					oculus.getChrac().move("Up", true);
					oculus.getChrac().move("Right", true);
				}
			}
		}
	}

	public void flyMove() {
		if (enemies.contains(fly)) {
			int[][] tabSol = mapPrincipale.getMapSol();
			int flyX = fly.getChrac().getX();
			int flyY = fly.getChrac().getY();
			int billX = bill.getChrac().getX();
			int billY = bill.getChrac().getY();
			if (tabSol[flyX][flyY] == 0 && temps2 % 10 == 0) {
				Tiles t = new Tiles(flyX, flyY);
				Tiles t2 = new Tiles(billX + 1, billY - 1);
				if (detecteur.heuristic(t, t2) == 0) {
					 bill.getChrac().damage(10);
					fly.getChrac().animation("shoot");
				} else if (tabSol[(billX + 1 + Map.WIDTH) % Map.WIDTH][billY - 1] != 0
						&& detecteur.heuristic(t, t2) < fly.getChrac().getAggroRange()) {

				} else if (detecteur.heuristic(t, t2) < fly.getChrac().getAggroRange()
						&& detecteur.heuristic(new Tiles(43, 15), t) < 30) {
					astarMove(fly.getChrac(), t, t2);
					fly.getChrac().animation("stand");
				}

				else if (fly.getChrac().getX() <= 45 && fly.getChrac().getY() == 15) {
					astarMove(fly.getChrac(), t, new Tiles(45, 16));
					fly.getChrac().animation("stand");

				} else {
					astarMove(fly.getChrac(), t, new Tiles(39, 15));
					fly.getChrac().animation("stand");

				}
			}
		}
	}

	public void astarMove(Character c, Tiles t1, Tiles t2) {
		detecteur.astar(t1, t2);
		int x = detecteur.getTileList().get(1).getX();
		int y = detecteur.getTileList().get(1).getY();
		if (c.getX() < x) // à refaire
			c.move("Right", true);
		else if (c.getX() > x)
			c.move("Left", true);
		else if (c.getY() < y)
			c.move("Down", true);
		else if (c.getY() > y)
			c.move("Up", true);
	}

	public void addKeyCode(KeyCode k) {
		if (!keyPressed.contains(k))
			keyPressed.add(k);
	}

	public void removeKeyCode(KeyCode k) {
		keyPressed.remove(k);
	}

	public void setVisibilityInventoryContainer() {
		inventoryContainer.setVisible(inventoryContainer.isVisible() ? false : true);
	}

	@FXML
	void drop(ActionEvent event) {
		if (!selected.isEmpty())
			selected.forEach(i -> inventaire
					.removeIf(f -> !i.getName().equals("pioche") && i.getName().equals(f.getName()) && i != equiped));
		else if (!focused.getName().equals("pioche")) {
			inventaire.removeFromInventory(focused.getName(), focused.getQuantity());
			if (equiped == focused) {
				focused = inventaire.get(0);
				equip.setImage(new Image("view/resources/Inventaire/" + focused.getName() + "_Inv.png"));
				equip.setId(focused.getName());
			}
		}
		selected.clear();
	}

	@FXML
	void use(ActionEvent event) {
		bill.getChrac().setEquiped(focused);
		equip.setImage(new Image("view/resources/Inventaire/" + focused.getName() + "_Inv.png"));
		equip.setId(focused.getName());
		equiped = focused;
		if (!(equiped instanceof Weapon)) {
			floor.setDisable(false);
			pnjPane.setDisable(true);
		} else {
			floor.setDisable(true);
			pnjPane.setDisable(false);
		}
		selected.clear();
	}

	public void clickAction(MouseEvent k) {
		delete = "mouse";
		Node clicked = k.getPickResult().getIntersectedNode();
		int[][] tabSol = mapPrincipale.getMapSol();
		int coordX = ((bill.getChrac().getX() - 31 + (int) k.getX() / 32) + 300) % 300;
		int coordY = bill.getChrac().getY() - 16 + (int) k.getY() / 32;
		if (Math.abs(30 - (int) k.getX() / 32) <= 3 && Math.abs(16 - (int) k.getY() / 32) <= 4) {
			if (k.isPrimaryButtonDown()) {
				if (tabSol[coordX][coordY] != 0 && equiped.getName().equals("pioche")) {
					TileView mat = (TileView) clicked;
					add = "background";
					inventaire.addToInventory(new Material(Material.getNameByCode(mat.getTile().getCode()), 1, ""));
					floor.getChildren().removeIf(Tile -> Tile == clicked);
				} else if (equiped instanceof Weapon && clicked instanceof EnnemyView) {
					if (equiped instanceof ColdSteel) {
						EnnemyView en = (EnnemyView) clicked;
						ColdSteel kn = (ColdSteel) equiped;
						en.getChrac().damage(kn.getDamage());
					}
				}
			}
			if (k.isSecondaryButtonDown() && equiped instanceof Material && !equiped.getName().equals("pioche")) {
				add = "mouse";
				if (Math.abs(30 - (int) k.getX() / 32) != 0 || Math.abs(16 - (int) k.getY() / 32) != 0) {
					if (tabSol[coordX][coordY] == 0 && blocPosable(coordX, coordY)) {
						Tiles t = new Tiles(coordX, coordY, Material.codeByName(equiped.getName()));
						TileView tv = new TileView(t);
						tv.relocate(((int) k.getX() / 32) * 32 - 32 + countX, ((int) k.getY() / 32) * 32 - countY);
						floor.getChildren().add(tv);
						inventaire.removeFromInventory(equiped.getName(), 1);
					}
				}
			}
		}
	}

	public void setVisibleCraftPane() {
		craftContainer.setVisible(!craftContainer.isVisible());
	}

	@FXML
	void Crafting(ActionEvent event) {
		boolean ok = true;
		String[] need = Craft.getNeed(focused);
		if (need != null) {
			int i = 0;
			while (i < need.length && ok) {
				int index = inventaire.indexOf(need[i + 1]);
				if (index != -1) {
					if (inventaire.get(index).getQuantity() >= Integer.parseInt(need[i])) {
						ok = true;
					} else
						ok = false;
				} else
					ok = false;
				i += 2;
			}

			if (ok) {
				inventaire.addToInventory(focused);
				for (int j = 0; j < need.length; j += 2) {
					inventaire.removeFromInventory(need[j + 1], Integer.parseInt(need[j]));
				}
			}
		}
	}

	public boolean blocPosable(int x, int y) {
		int[][] tabSol = mapPrincipale.getMapSol();
		for (int i = -1; i <= 2; i += 2)
			if (tabSol[x + i][y] != 0 || tabSol[x][y + i] != 0)
				return true;
		return false;
	}
}