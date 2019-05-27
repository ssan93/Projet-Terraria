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

	public ArrayList<ImageView> creerVue() {

		ArrayList<ImageView> images = new ArrayList<>();
		ObservableList<Tiles> ListMid = m.getTilesListMid();
		for (Tiles tile : ListMid) {
			if (tile.getX() < 60 && tile.getY() < 40) {
				viewAbleMid.add(tile);
				TileView tv = new TileView(tile);
				tv.relocate(tile.getX() * 32, tile.getY() * 32);
				images.add(tv);
			}
		}

		ObservableList<Tiles> ListSol = m.getTilesListSol();
		for (Tiles tile : ListSol) {
			if (tile.getX() < 60 && tile.getY() < 40) {
				viewAbleSol.add(tile);
				TileView tv = new TileView(tile);
				tv.relocate(tile.getX() * 32, tile.getY() * 32);
				images.add(tv);

			}
		}
		return images;
	}
	public ObservableList<Tiles> getListViewSol(){
		return this.viewAbleSol;
	}
	public ObservableList<Tiles> getListViewMid(){
		return this.viewAbleMid;
	}

}
