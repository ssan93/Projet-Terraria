package view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Tiles;

public class TileView extends ImageView{

	private Tiles tile;
	private int layer; // 1 for ground , 2 for middleground
	
	public TileView() {
		super();
	}

	public TileView(String url) {
		super(url);
	}

	public TileView(Image image) {
		super(image);
	}
	
	/**
	 * 
	 */
	public TileView(Image image, Tiles t,int l) {
		super(image);
		this.layer = l;
		this.tile = t;
	}

}
