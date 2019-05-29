package view.game;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.game.Tiles;

public class TileView extends ImageView {

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
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("je clique");
				event.consume();
			}
		});
	}
	
	public TileView(String url) {
		super(url);
	}

	public TileView(Image image) {
		super(image);
	}
}
