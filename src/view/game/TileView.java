package view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Tiles;

public class TileView extends ImageView{

	private Tiles tile;
	
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
		this.setImage(new Image("view/resources/tiles/tile"+t.getCode()+".png"));
	}
	
	public TileView(String url) {
		super(url);
	}

	public TileView(Image image) {
		super(image);
	}
}
