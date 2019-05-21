package view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Tiles;

public class TileView extends ImageView{

	private Tiles tile;
	
	public TileView() {
		super();
	}

	public TileView(String url) {
		super(url);
	}

	public TileView(Image image) {
		super(image);
	}
	
	public TileView(Image image, Tiles t) {
		super(image);
		this.tile = t;
	}

}
