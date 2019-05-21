package view.game;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import model.game.Map;
import model.game.Tiles;

public class MapView {

	private ObservableList<ImageView> images;//34*60

	private Map m;

	public MapView(Map m) {
		images = FXCollections.observableArrayList();
		this.m = m;

	}

	public ObservableList<ImageView> creerVue() {
//		int t2[][] = m.getMapBg();
//		for (int x = 0; x < t2.length; x++) {
//			for (int y = 0; y < t2[x].length; y++) {
//				if (t2[x][y] != 0) {
//
//					ImageView img = new ImageView(Tiles.selectionTuile(t2[x][y]));
//					img.relocate(y * 32, x * 32);
//					images.add(img);
//				}
//			}
//		}

		int tabMilieu[][] = m.getMapMilieu();
		for (int x = 0; x < tabMilieu.length; x++) {
			for (int y = 0; y < tabMilieu[x].length; y++) {
				if (tabMilieu[x][y] != 0) {
					Tiles t = new Tiles(x, y);
					TileView tv = new TileView(Tiles.selectionTuile(tabMilieu[x][y]), t,2);
					tv.relocate(y * 32, x * 32);
					images.add(tv);

//					 ImageView img = new ImageView(Tiles.selectionTuile(t1[x][y]));
//					 img.relocate(y * 32, x * 32);
//					 images.add(img);
				}
			}
		}
		int tabSol[][] = m.getMapSol();
		for (int x = 0; x < tabSol.length; x++) {
			for (int y = 0; y < tabSol[x].length; y++) {
				if (tabSol[x][y] != 0) {
					Tiles t = new Tiles(x, y);
					TileView tv = new TileView(Tiles.selectionTuile(tabSol[x][y]), t,1);
					tv.relocate(y * 32, x * 32);
					images.add(tv);
//					 ImageView img = new ImageView(Tiles.selectionTuile(t3[x][y]));
//					 img.relocate(y * 32, x * 32);
//					 images.add(img);
				}
			}
		}
		return images;
	}

}
