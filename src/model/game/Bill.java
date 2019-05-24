package model.game;

import javafx.geometry.Rectangle2D;

public class Bill extends Character{

	public Bill(int x, int y, int vitesse) {
		super(x, y, vitesse);
		this.rec =new Rectangle2D(x, y+28, 28, 50);
		
	}

}
