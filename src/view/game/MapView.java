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
		// int t2[][] = m.getMapBg();
		// for (int x = 0; x < t2.length; x++) {
		// for (int y = 0; y < t2[x].length; y++) {
		// if (t2[x][y] != 0) {
		//
		// ImageView img = new ImageView(Tiles.selectionTuile(t2[x][y]));
		// img.relocate(y * 32, x * 32);
		// images.add(img);
		// }
		// }
		// }
		ObservableList<Tiles> ListMid = m.getTilesListMid();
		for(Tiles test : ListMid) {
			if (test.getX()<60 && test.getY()<34) {
				TileView tv = new TileView(test);
				tv.relocate(test.getX()*32, test.getY()*32);
				images.add(tv);

			}
		}

//		for (int i = 0; i < ListMid.size(); i++) {
//
//			TileView tv = new TileView(ListMid.get(i));
//			tv.relocate(ListMid.get(i).getX(), ListMid.get(i).getY());
//			images.add(tv);
//		}

		/*
		 * int tabMilieu[][] = m.getMapMilieu(); for (int x = 0; x < tabMilieu.length;
		 * x++) { for (int y = 0; y < tabMilieu[x].length; y++) { if (tabMilieu[x][y] !=
		 * 0) { TileView tv = new TileView(Tiles.selectionTuile(tabMilieu[x][y]), t,2);
		 * tv.relocate(y * 32, x * 32); images.add(tv);
		 */

		// ImageView img = new ImageView(Tiles.selectionTuile(t1[x][y]));
		// img.relocate(y * 32, x * 32);
		// images.add(img);

		// int tabSol[][] = m.getMapSol();
		// for (int x = 0; x < tabSol.length; x++) {
		// for (int y = 0; y < tabSol[x].length; y++) {
		// if (tabSol[x][y] != 0) {
		// Tiles t = new Tiles(x, y);
		// TileView tv = new TileView(Tiles.selectionTuile(tabSol[x][y]), t,1);
		// tv.relocate(y * 32, x * 32);
		// images.add(tv);
		//// ImageView img = new ImageView(Tiles.selectionTuile(t3[x][y]));
		//// img.relocate(y * 32, x * 32);
		//// images.add(img);
		// }
		// }
		// }
		ObservableList<Tiles> ListSol = m.getTilesListSol();
		for(Tiles test : ListSol) {
			if (test.getX()<60 && test.getY()<34) {
				TileView tv = new TileView(test);
				tv.relocate(test.getX()*32, test.getY()*32);
				images.add(tv);

			}
		}

		
		//test+=32*14;

		

//		for (int i = 0; i < ListSol.size(); i++) {
//			TileView tv = new TileView(ListSol.get(i));
//			tv.relocate(ListSol.get(i).getX(), ListSol.get(i).getY());
//			images.add(tv);
//		}

		return images;
	}

}
