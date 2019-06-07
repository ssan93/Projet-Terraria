package view.game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Tiles;

<<<<<<< HEAD
public class TileView extends ImageView {

	
	private static final int nbImage = 9;
=======
public class TileView extends ImageView{
	
	private Tiles tile;
>>>>>>> refs/remotes/origin/ssan
	
	private static ArrayList<Image> images;// image deja chargé
	
	public TileView() {
		super();
	}

	/**
	 * 
	 * @param t Tiles
	 */
	public TileView(Tiles t) {
		super();
		this.setImage(images.get(t.getCode()-1));
<<<<<<< HEAD
=======
		
		
>>>>>>> refs/remotes/origin/ssan
	}
	
	
	public static void inizImages(){
		images = new ArrayList<Image>();
		for (int i = 1; i <= nbImage; i++) {
			images.add(new Image("view/resources/tiles/tile"+i+".png"));
		}
	}
	public TileView(String url) {
		super(url);
	}

	public TileView(Image image) {
		super(image);
	}
	public Tiles getTile() {
		return this.tile;
	}
}
