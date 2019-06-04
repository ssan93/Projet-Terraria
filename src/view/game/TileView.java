package view.game;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Tiles;

public class TileView extends ImageView{
	
	private Tiles tile;
	
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
		this.tile = t;
		this.setImage(images.get(t.getCode()-1));
		
		
	}
	
	
	public static void inizImages(){
		images = new ArrayList<Image>();
		images.add(new Image("view/resources/tiles/tile1.png"));
		images.add(new Image("view/resources/tiles/tile2.png"));
		images.add(new Image("view/resources/tiles/tile3.png"));
		images.add(new Image("view/resources/tiles/tile4.png"));
		images.add(new Image("view/resources/tiles/tile5.png"));
		images.add(new Image("view/resources/tiles/tile6.png"));
		images.add(new Image("view/resources/tiles/tile7.png"));
		images.add(new Image("view/resources/tiles/tile8.png"));
		images.add(new Image("view/resources/tiles/tile9.png"));


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
