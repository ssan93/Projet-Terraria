package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.game.Character;
import model.game.Tiles;
import view.game.BillView;
import model.game.Carte;

public class Controller implements Initializable {
	@FXML
    private Pane sol;
    
	Carte tileSol = new Carte("src/maps/carte.txt", "src/maps/carte2.txt", "src/maps/carte3.txt");	
	int temps;
	private Timeline loop;
	BillView b = new BillView("view/resources/personnages/right_static_bill.png");	
	String oldAnim="tactac";
	
	int coordMapXRight = 32*30;
	int count = 32;
	int depart = 0;
	int map=0;int ok = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		creerVue(tileSol, sol);
		//initAnimation();
		//loop.play();
		b.getChrac().setSpeed(3);
		b.getChrac().getXProperty().set(32*5);
		b.getChrac().getYProperty().set(32*11);
		sol.getChildren().add(b.getImage());
		
	}

	public void creerVue(Carte map, Pane lePane) {
		int t2[][] = map.getMapBg();
		for (int x = 0; x < t2.length; x++) {
			for (int y = 0; y < t2[x].length; y++) {
				if (t2[x][y] != 0) {					
					//sol.relocate(sol.getLayoutX()-(48*5),sol.getLayoutY()-(48*5));

				ImageView img = new ImageView(Tiles.selectionTuile(t2[x][y]));
				img.relocate(y * 32, x * 32);
				sol.getChildren().add(img);}
			}
		}
		
		int t1[][] = map.getMapMilieu();
		for (int x = 0; x < t1.length; x++) {
			for (int y = 0; y < t1[x].length; y++) {
				if (t1[x][y] != 0) {
				ImageView img = new ImageView(Tiles.selectionTuile(t1[x][y]));
				img.relocate(y * 32, x * 32);
				sol.getChildren().add(img);}
			}
		}
		int t[][] = map.getMapSol();
		for (int x = 0; x < t.length; x++) {
			for (int y = 0; y < t[x].length; y++) {
				if (t[x][y] != 0) {
				ImageView img = new ImageView(Tiles.selectionTuile(t[x][y]));
				img.relocate(y * 32, x * 32);
				sol.getChildren().add(img);}
			}
		}
	}
	
	
	public void actions(KeyEvent a) {
		if(a.getCode() == KeyCode.D || a.getCode()==KeyCode.RIGHT) {
			sol.relocate(sol.getLayoutX()-b.getChrac().getSpeed(),sol.getLayoutY());
			b.getChrac().animation("RunRight");
			oldAnim="RunRight";
			
			
			//int t[][] = tileSol.getMapSol();
			//int t1[][] = tileSol.getMapBg();
			count -=3;
			
			
			
			depart+=b.getChrac().getSpeed();
			int tile = depart%960/32-1;
			if (tile > 5) ok =1;
			if (tile == 0 && ok == 1) {
				ok = 0;
				map++;
			}
			int t[][] = tileSol.getMap(map);
			if (count <= 0) {
			for (int x =0 ; x < t.length ; x++) {
				//sol.getChildren().remove(x*29);
				if (t[x][tile] != 0) {
					ImageView img = new ImageView(Tiles.selectionTuile(t[x][tile]));
					img.relocate(coordMapXRight, x*32);
					sol.getChildren().add(img);
				}
			}
			coordMapXRight+=32;
			count += 32;}
			
		}
						
		if(a.getCode()==KeyCode.Q|| a.getCode()==KeyCode.LEFT) {
			sol.relocate(sol.getLayoutX()+b.getChrac().getSpeed(),sol.getLayoutY());
			b.getChrac().animation("RunLeft");
			oldAnim="RunLeft";
		}	
	}

	public void initAnimation() {
		loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);
		System.out.println(temps);

		KeyFrame kf = new KeyFrame(
			Duration.seconds(0.033), (ev ->{
				if(temps%30==0) {
					System.out.println("tac");
				}
				temps++;
			})
		);
		loop.getKeyFrames().add(kf);
	}

	public void negationdaction(KeyEvent e) {
		if(oldAnim.equals("RunRight")) {
			b.getChrac().animation("idleRight");
			oldAnim="idleRight";
		}
		if(oldAnim.equals("RunLeft")){
			b.getChrac().animation("idleLeft");
			oldAnim="idleLeft";
		}
	}
	
}
