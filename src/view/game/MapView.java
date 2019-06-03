package view.game;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import model.game.Map;
import model.game.Tiles;

public class MapView {

	private ObservableList<Tiles> viewAbleSol,viewAbleMid;//34*60
	private Map m;
	public static int test = 0;

	public MapView(Map m) {
		viewAbleSol = FXCollections.observableArrayList();
		viewAbleMid = FXCollections.observableArrayList();
		this.m = m;
	}

	
	/**
	 * Initialise la vue de la map à utiliser au début du chargement de la map
	 * @return l'arraylist d'image de la map
	 */
	public ArrayList<ImageView> creerVue() {

		ArrayList<ImageView> images = new ArrayList<>();
		/*ObservableList<Tiles> ListMid = m.getTilesListMid();
		for (Tiles tile : ListMid) {
			if (tile.getX() < 60 && tile.getY() < 40) {
				viewAbleMid.add(tile);
				TileView tv = new TileView(tile);
				tv.relocate(tile.getX() * 32, tile.getY() * 32);
				images.add(tv);
			}
		}*/
		int [][] tabMid = m.getMapMid();
		int x = 0;
		while(x<60) {
			int y=0;
			while(y<40) {
				if(tabMid[x][y]!=0) {
				Tiles t = new Tiles (x,y,tabMid[x][y]);
				viewAbleSol.add(t);
				TileView tv = new TileView(t);
				tv.relocate(t.getX() * 32, t.getY() * 32);
				images.add(tv);}
				y++;
			}
			x++;
		}

		//ObservableList<Tiles> ListSol = m.getTilesListSol();
		int [][] test = m.getMapSol();
		 x = 0;
		while(x<60) {
			int y=0;
			while(y<40) {
				if(test[x][y]!=0) {
				Tiles t = new Tiles (x,y,test[x][y]);
				viewAbleSol.add(t);
				TileView tv = new TileView(t);
				tv.relocate(t.getX() * 32, t.getY() * 32);
				images.add(tv);}
				y++;
			}
			x++;
		}
		/*
		for (Tiles tile : ListSol) {
			if (tile.getX() < 60 && tile.getY() < 40) {
				viewAbleSol.add(tile);
				TileView tv = new TileView(tile);
				tv.relocate(tile.getX() * 32, tile.getY() * 32);
				images.add(tv);

			}
		}*/
		return images;
	}
	
	/**
	 * 
	 * @return la liste d'image du sol
	 */
	public ObservableList<Tiles> getListViewSol(){
		return this.viewAbleSol;
	}
	
	/**
	 * 
	 * @return la liste d'image de l'environnement
	 */
	public ObservableList<Tiles> getListViewMid(){
		return this.viewAbleMid;
	}

}
