package view.game;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import model.game.Map;
import model.game.Tiles;

public class MapView {

	private Map m;

	public MapView(Map m) {
		this.m = m;

	}

	public ArrayList<ImageView> creerVue() {
		ArrayList<ImageView> images = new ArrayList<>();
		ObservableList<Tiles> ListMid = m.getTilesListMid();
		for(Tiles test : ListMid) {
			if (test.getX()<60 && test.getY()<34) {
				TileView tv = new TileView(test);
				tv.relocate(test.getX()*32, test.getY()*32);
				images.add(tv);
			}
		}

		ObservableList<Tiles> ListSol = m.getTilesListSol();
		for(Tiles test : ListSol) {
			if (test.getX()<60 && test.getY()<34) {
				TileView tv = new TileView(test);
				tv.relocate(test.getX()*32, test.getY()*32);
				images.add(tv);
			}
		}
		
		return images;
	}

}
