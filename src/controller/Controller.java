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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.game.BillView;
import view.game.MapView;
import view.game.TileView;
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

	private Map mapPrincipale = new Map("src/maps/grosseMap_sol.csv", "src/maps/grosseMap_environnement.csv");
	private MapView mv;
	private Timeline loop;

	private Timeline loop2;

	int countRight = 32;
	int countLeft = 32;
	int countDown = 32;
	int countUp = 32;

	boolean jumping = false;

	private int temps;
	private BillView bill = new BillView("view/resources/personnages/right_static_bill.png");
	private String oldAnim = "tactac";
	int deleteLignLeft = 0;
	int deleteLignRight = 59;
	int deleteLignTop=0;
	int deleteLignBot=32;
	int addLignLeft = 299;
	int addLignRight = 60;
	int addLignTop = 0;
	int addLignBot = 33;
	String delete;
	String add;
	ObservableList<Tiles> viewAbleSol;
	int relocated = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.getChildren().add(0, new ImageView(new Image("view/resources/tac.jpg")));
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
		
		/*
		 * bill.getChrac().getXProperty().set(32 * 6);
		 * bill.getChrac().getYProperty().set(32 * 7 - 12);
		 */

		charapane.getChildren().add(bill.getImage());
		floor.getChildren().addAll(mv.creerVue());

		viewAbleSol.addListener(new ListChangeListener<Tiles>() {

			@Override
			public void onChanged(Change<? extends Tiles> c) {

				while (c.next()) {
					if (c.wasAdded()) {
						for (Tiles tileAdded : c.getAddedSubList()) {
							ImageView img = new TileView(tileAdded);
							switch(add) {
							case "Left":
								img.relocate(5, tileAdded.getY() * 32 + relocated);
								break;
							case "Right":
								img.relocate(59 * 32 - 5, tileAdded.getY() * 32 + relocated);
								break;
							case "Up" :
								img.relocate(tileAdded.getX()*32, 0);
								break;
							case "Down" : 
								img.relocate(tileAdded.getX()*32, 33*32);
								break;
							}
							floor.getChildren().add(img);
						}
					}
					if (c.wasRemoved()) {
						switch(delete) {
						case "Left":
							floor.getChildren().removeIf(img -> img.getLayoutX() < -32);
							break;
						case "Right":
							floor.getChildren().removeIf(img -> img.getLayoutX() > 60 * 32);
							break;
						case "Up" :
							floor.getChildren().removeIf(img -> img.getLayoutY() < 0);
							break;
						case "Down" : 
							floor.getChildren().removeIf(img -> img.getLayoutY() > 33 * 32);
							break;
						}
						
					}
				}

			}

		});

		testCollision();

	}
	
	

	public void actions() {
		if (keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT)) {
			// if (!stopSroll().equals("right stop")) {
			bill.getChrac().animation("RunRight");
			//bill.getChrac().move("RunRight");
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
			// bill.getChrac().move("RunLeft");
			oldAnim = "RunLeft";
			scroll("Left");

		}

		// }

		if (keyPressed.contains(KeyCode.SPACE)) {
			if (!alreadyJumping()) {
				bill.getChrac().animation("jumpRight");
				oldAnim = "jumpRight";
				scroll("Up");
			}

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
			relocated += 4;
			loop.pause();
			jumping = true;

			loop2 = new Timeline();
			loop2.setCycleCount(16);

			loop2.setOnFinished(ev -> {loop.play();});

			loop2.getKeyFrames().add(new KeyFrame(Duration.millis(25), (ev -> {
				for (int i = 0; i < floor.getChildren().size(); i++) 
					relocateImages("Up", i);		

				CountAddDelete("Up");	
				actions();
			})));

			loop2.play();
			loop2.getOnFinished();
			
			break;
		case "Down":
			relocated -= 4;
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
					floor.getChildren().get(indiceFloor).getLayoutY() + 4);
			
			break;
		case "Down":
			floor.getChildren().get(indiceFloor).relocate(floor.getChildren().get(indiceFloor).getLayoutX(),
					floor.getChildren().get(indiceFloor).getLayoutY() - 4);
			

			break;
		}
	}
	public void CountAddDelete(String Direction) {
		switch (Direction) {
		case "Right":
			countRight -= bill.getChrac().getSpeed();
			countLeft += bill.getChrac().getSpeed();
			if (countLeft > 32)
				countLeft -= 32;
			if (countRight <= 0) {
				addImages("Right");
				deleteImages("Left");
				countRight += 32;
			}
			break;

		case "Left":
			countLeft -= bill.getChrac().getSpeed();
			countRight += bill.getChrac().getSpeed();
			if (countRight > 32)
				countRight -= 32;
			if (countLeft <= 0) {
				addImages("Left");
				deleteImages("Right");
				countLeft += 32;
			}
			break;

		case "Up":
			countUp -=4;
			countDown +=4;
			if (countDown > 32)
				countDown -= 32;
			if(countUp <= 0) {
				addImages("Up");
				deleteImages("Down");
				countUp +=32;
			}

			break;
		case "Down":
			countDown -= 4;
			countUp += 4 ;
			if (countUp > 32)
				countUp -= 32;
			
			if(countDown <= 0) {
				addImages("Down");
				deleteImages("Up");
				countDown += 32;
			}
			
			break;
		}
	}
	
	public void addImages(String direction) {
		ObservableList<Tiles> ListSol = mapPrincipale.getTilesListSol();
		ObservableList<Tiles> ListMid = mapPrincipale.getTilesListMid();
		switch (direction) {
		
		case "Right":
			add = "Right";
			
			for(Tiles tile : ListMid)
				if (tile.getX() == addLignRight && addLignTop <= tile.getY() && tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			for (Tiles tile : ListSol)
				if (tile.getX() == addLignRight && addLignTop <= tile.getY() && tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			addLignRight++;
			addLignLeft++;
			if (addLignRight == 300)
				addLignRight = 0;
			if (addLignLeft == 300)
				addLignLeft = 0;

			break;

		case "Left":
			add = "Left";
			for (Tiles tile : ListMid)
				if (tile.getX() == addLignLeft && addLignTop <= tile.getY() && tile.getY() <= addLignBot )
					viewAbleSol.add(tile);
			for (Tiles tile : ListSol)
				if (tile.getX() == addLignLeft && addLignTop <= tile.getY() && tile.getY() <= addLignBot)
					viewAbleSol.add(tile);
			addLignLeft--;
			addLignRight--;
			if (addLignLeft == -1)
				addLignLeft = 299;
			if (addLignRight == -1)
				addLignRight = 299;
			break;
			
		case "Up":
			add = "Up" ;
			for (Tiles tile : ListSol)
				if (tile.getX() < 60 && tile.getY() == addLignTop) {
					viewAbleSol.add(tile);
				}			
			break;
			
		case "Down":
			add="Down";
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

	public void deleteImages(String direction) {
		switch (direction) {
		case "Right":
			delete = "Right";
			for (int i = 0; i < viewAbleSol.size(); i++)
				if (viewAbleSol.get(i).getX() == deleteLignRight)
					viewAbleSol.remove(i);
			deleteLignRight--;
			deleteLignLeft--;

			break;

		case "Left":

			delete = "Left";
			for (int i = 0; i < viewAbleSol.size(); i++)
				if (viewAbleSol.get(i).getX() == deleteLignLeft)
					viewAbleSol.remove(i);
			deleteLignLeft++;
			deleteLignRight++;

			break;
		case "Up":
			delete = "Up";
			for (int i = 0; i < viewAbleSol.size(); i++)
				if (viewAbleSol.get(i).getY() == deleteLignTop)
					viewAbleSol.remove(i);
			deleteLignTop++;
			deleteLignBot++;
			break;
		case "Down":
			delete = "Down";
			for (int i = 0; i < viewAbleSol.size(); i++)
				if (viewAbleSol.get(i).getY() == deleteLignBot)
					viewAbleSol.remove(i);
			deleteLignBot --;
			deleteLignTop --;
			break;
		}
	}
	/*public void CountAddDelete(BillView bill, String Direction) {
		int countDirection = this.countRight;
		int countOppositeDirection = this.countLeft;
		String OppositeDirection = "Left";
		switch(Direction) {
		case "Right":
			countDirection = this.countRight;
			countOppositeDirection = this.countLeft;
			OppositeDirection = "Left";
			break;
		case "Left":
			countDirection = this.countLeft;
			countOppositeDirection = this.countRight;
			OppositeDirection = "Right";
			break;
		
		case "Up":
			countDirection = this.countUp;
			countOppositeDirection = this.countDown;
			OppositeDirection = "Down";	
			break;
		case "Down":
			countDirection = this.countDown;
			countOppositeDirection = this.countUp;
			OppositeDirection = "Up";
			break;
		}
		countDirection -= bill.getChrac().getSpeed();
		countOppositeDirection -= bill.getChrac().getSpeed();
		if (countDirection <= 0) {
			addImages(Direction);
			deleteImages(OppositeDirection);
			countDirection=+32;
		}
		if (countOppositeDirection > 32)
			countOppositeDirection -= 32;
	}*/
	

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
				
				scroll("Down");
				
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
