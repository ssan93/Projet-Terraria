package controller;

import java.io.File;
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
import model.game.Ennemy;
import model.game.GestionCollision;
import model.game.Inventory;
import model.game.InventoryItem;
import model.game.Map;
import model.game.Tiles;
import model.game.Tool;
import model.game.Weapon;
import model.game.radioChatter;

public class GameController extends Controller {

	private MediaPlayer effectPlayer;
	private radioChatter ra = new radioChatter();
	private GestionCollision detecteur;
	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();
	private boolean allowMouv = false;
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
	private InventoryItem focused = null;
	private ObservableList<Tiles> viewAbleSol;
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
	private Button utiliser;

	@FXML
	private Button jeter;

	@FXML
	private AnchorPane background;
	@FXML
	private ImageView heart, codec;
	@FXML
	private Pane floor;
	@FXML
	private Pane charapane;

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		changeRdi(null);
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
		inventaire.addToInventory(new Tool("pioche",
				"La pioche est un outil composé de deux pièces : une pièce de travail en acier fixée par l'intermédiaire d'un œil à un manche en bois dur. La pièce de métal forme un angle d'environ 90° avec le manche."));
		 inventaire.addToInventory(new InventoryItem("M16", 1,""));
		 play();
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
							@Override
							public void handle(MouseEvent event) {
								boolean in = event.getEventType().equals(MouseEvent.MOUSE_EXITED) ? false : true;
								if (in)
									img.setEffect(new DropShadow(35, 0, 0, Color.CORNFLOWERBLUE));
								else
									img.setEffect(null);
								if (event.isPrimaryButtonDown()) {
//									Node clickedNode = event.getPickResult().getIntersectedNode();
//									focRow = (int) GridPane.getRowIndex(clickedNode);
//									focCol = (int) GridPane.getColumnIndex(clickedNode);
									focused = inventaire.get(inventaire.contains(img.getId()));
									desc.setText(focused.toString());
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
						layoutInventory.getChildren().removeIf(f -> f.getId().equals(focused.getName()));
						desc.setText("");
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
		if(sfx.equals("src/menu-musics/marche.mp3")) {
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
		isPlaying=false;
	}

	public void isAlive() {

		bill.setLife(heart);
		if (bill.getChrac().getHp() == 0)
			isAlive.set(false);
		else
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

	public void actions() {
		if (allowMouv && ((keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT)))
				&& detecteur.verifRight(bill.getChrac(), jumping, falling)) {
			// if (!stopSroll().equals("right stop")) {
			bill.getChrac().animation("RunRight");

			if(!isPlaying) {
				effectPlay("src/menu-musics/marche.mp3");
			}
			oldAnim = "RunRight";
			scroll("Right");
		

		}
		if (allowMouv && ((keyPressed.contains(KeyCode.Q) || keyPressed.contains(KeyCode.LEFT)))
				&& detecteur.verifLeft(bill.getChrac(), jumping, falling)) {
			// if (!stopSroll().equals("left stop")) {
			bill.getChrac().animation("RunLeft");

			oldAnim = "RunLeft";
			scroll("Left");
			if(!isPlaying) {
				effectPlay("src/menu-musics/marche.mp3");
			}


		}

		if (!jumping && keyPressed.contains(KeyCode.SPACE) && allowMouv && detecteur.verifTop(bill.getChrac())) {
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
			break;

		case "Up":
			jumping = true;
			for (int i = 0; i < floor.getChildren().size(); i++)
				relocateImages("Up", i);
			relocated += bill.getChrac().getSpeed();
			CountAddDelete("Up");
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
				deleteImages("Down");
				countY += 32;
			}
			break;
		case "Down":
			countY += bill.getChrac().getSpeed();
			if (32 - countY < 0) {
				bill.getChrac().move("Down");
				addImages("Down");
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
		/*
		 * int [][] mapMid = mapPrincipale.getMapMid(); int [][] mapSol =
		 * mapPrincipale.getMapSol();
		 */
		switch (direction) {

		case "Right":
			add = "Right";

			/*
			 * for(int y = addLignTop; y <= addLignBot; y++) { if(mapMid[(addLignX + 61) %
			 * 300][y]!=0) { Tiles t = new Tiles ((addLignX + 61) % 300,y,mapMid[(addLignX +
			 * 61) % 300][y]); viewAbleSol.add(t); } if(mapSol[(addLignX + 61) % 300][y]!=0)
			 * { Tiles t = new Tiles ((addLignX + 61) % 300,y,mapSol[(addLignX + 61) %
			 * 300][y]); viewAbleSol.add(t); } }
			 */

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
			/*
			 * for(int x = 0; x < 60; x++) { if(mapMid[x][addLignBot]!=0) { Tiles t = new
			 * Tiles (x,addLignBot,mapMid[x][addLignBot]); viewAbleSol.add(t); }
			 * if(mapSol[x][addLignBot]!=0) { Tiles t = new Tiles
			 * (x,addLignBot,mapSol[x][addLignBot]); viewAbleSol.add(t); } }
			 */

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
			endEffectPlay();
			break;
		case "RunLeft":
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
			if (temps % 40 == 0 && bill.getChrac().getHp() > 0) {
				isAlive();
			}
			if (jumping && !falling && temps != 19 && detecteur.verifTop(bill.getChrac())) {
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
		inventaire.removeFromInventory(focused.getName(), focused.getQuantity());
	}

	@FXML
	void use(ActionEvent event) {
		bill.getChrac().setEquiped(focused);
	}

	public void test(MouseEvent k) {
		delete = "mouse";
		Node clicked = k.getPickResult().getIntersectedNode();
		int[][] tabSol = mapPrincipale.getMapSol();
		int coordX = ((bill.getChrac().getX() - 31 + (int) k.getX() / 32) + 300) % 300;
		int coordY = bill.getChrac().getY() - 16 + (int) k.getY() / 32;
		if (Math.abs(30 - (int) k.getX() / 32) <= 3 && Math.abs(16 - (int) k.getY() / 32) <= 4) {
			/*
			 * if (coordX < 0) coordX += 300; else if (coordX >= 300) coordX -= 300;
			 */

			if (k.isPrimaryButtonDown() && bill.getChrac().getEquiped() instanceof Tool && bill.getChrac().getEquiped().getName().equals("pioche")) {
				if (tabSol[coordX][coordY] != 0) {

					add = "background";
					if (clicked instanceof Pane) {
						if (clicked.getId().equals("charapane"))
							floor.getChildren()
									.removeIf(img -> img.getLayoutY() >= k.getY() - 32 && img.getLayoutY() < k.getY()
											&& img.getLayoutX() >= k.getX() - 32 && img.getLayoutX() < k.getX());
					} else
						floor.getChildren().removeIf(Tile -> Tile == clicked);
				}
			}
			if (k.isSecondaryButtonDown()) {
				add = "mouse";
				if (Math.abs(30 - (int) k.getX() / 32) != 0 || Math.abs(16 - (int) k.getY() / 32) != 0) {
					if (tabSol[coordX][coordY] == 0) {
						Tiles t = new Tiles(coordX, coordY, 2);
						TileView tv = new TileView(t);
						tv.relocate(((int) k.getX() / 32) * 32 - 32 + countX, ((int) k.getY() / 32) * 32 - countY);
						floor.getChildren().add(tv);
					}
				}
			}
		}

		// case "mouse":
		// ObservableList<Tiles> ListSol = mapPrincipale.getTilesListSol();
		// int[][] tabSol = mapPrincipale.getMapSol();
		// ListSol.remove(tile);
		// tabSol[tile.getX()][tile.getY()]=0;
		// System.out.println(tile.getX() + " "+ tile.getY());
		// floor.getChildren().removeIf(img -> (int)img.getLayoutX()/32== tile.getX() &&
		// (int)img.getLayoutY()/32 == tile.getY()-1);
		// break;
		// public void test(javafx.scene.input.MouseEvent k) {
		// delete="mouse";
		// System.out.println(viewAbleSol.size());
		// // System.out.println((int)k.getX()/32+" "+(int)k.getY()/32);
		// if(k.isPrimaryButtonDown())
		// viewAbleSol.removeIf(img -> img.getY() == (int)k.getY()/32);
		// System.out.println(viewAbleSol.size());
		//
		// }
	}

	public boolean aroundBill() {
		return true;
	}

}
