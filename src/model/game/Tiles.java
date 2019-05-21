package model.game;

import javafx.scene.image.Image;

public class Tiles extends Objet{

	public Tiles(int x, int y) {
		super(x, y);
	}

	public static Image selectionTuile(int i) {
		return new Image("view/resources/tiles/tile"+i+".png");
	}
	
}
