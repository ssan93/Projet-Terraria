package view.game;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import model.game.Map;
import model.game.Tiles;

public class MapView {

	private ArrayList<ImageView> images;

	private Map m;

	public MapView(Map m) {
		images = new ArrayList<>();
		this.m = m;
	}

	public ArrayList<ImageView> creerVue() {
		int t2[][] = m.getMapBg();
		for (int x = 0; x < t2.length; x++) {
			for (int y = 0; y < t2[x].length; y++) {
				if (t2[x][y] != 0) {
					// sol.relocate(sol.getLayoutX()-(48*5),sol.getLayoutY()-(48*5));

					ImageView img = new ImageView(Tiles.selectionTuile(t2[x][y]));
					img.relocate(y * 32, x * 32);
					images.add(img);
				}
			}
		}

		int t1[][] = m.getMapMilieu();
		for (int x = 0; x < t1.length; x++) {
			for (int y = 0; y < t1[x].length; y++) {
				if (t1[x][y] != 0) {
					ImageView img = new ImageView(Tiles.selectionTuile(t1[x][y]));
					img.relocate(y * 32, x * 32);
					images.add(img);
				}
			}
		}
		int t[][] = m.getMapSol();
		for (int x = 0; x < t.length; x++) {
			for (int y = 0; y < t[x].length; y++) {
				if (t[x][y] != 0) {
					ImageView img = new ImageView(Tiles.selectionTuile(t[x][y]));
					img.relocate(y * 32, x * 32);
					images.add(img);
				}
			}
		}
		return images;
	}

}
