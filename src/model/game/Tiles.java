package model.game;

import javafx.scene.image.Image;

public class Tiles extends Objet{

	private int code;
	
	public Tiles(int x, int y) {
		super(x, y);
	}
	
	/**
	 * 
	 * @param x position x
	 * @param y position y
	 * @param c	code image
	 */
	public Tiles(int x, int y,int c) {
		super(x, y);
		this.code = c;
	}
	
	public int getCode() {
		return code;
	}
	public static Image selectionTuile(int i) {
		return new Image("view/resources/tiles/tile"+i+".png");
	}
	
}
