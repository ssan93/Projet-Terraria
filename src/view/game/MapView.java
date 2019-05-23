package view.game;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import model.game.Map;
import model.game.Tiles;

public class MapView {

	// private ObservableList<ImageView> images;//34*60

	private Map m;
	public static int test = 0;

	public MapView(Map m) {
		// images = FXCollections.observableArrayList();
		this.m = m;

	}

	public ArrayList<ImageView> creerVue() {

		ArrayList<ImageView> images = new ArrayList<>();

		ObservableList<Tiles> ListMid = m.getTilesListMid();
		for (Tiles tile : ListMid) {
			if (tile.getX() < 60 && tile.getY() < 40) {
				TileView tv = new TileView(tile);
				tv.relocate(tile.getX() * 32, tile.getY() * 32);
				images.add(tv);

			}
		}

		ObservableList<Tiles> ListSol = m.getTilesListSol();
		for (Tiles tile : ListSol) {
			if (tile.getX() < 60 && tile.getY() < 40) {
				TileView tv = new TileView(tile);
				tv.relocate(tile.getX() * 32, tile.getY() * 32);
				images.add(tv);

			}
		}

		return images;
	}
	

}
