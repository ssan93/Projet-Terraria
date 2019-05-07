package modele;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Objets {
	
	SimpleIntegerProperty x, y;
	
	public Objets(int x, int y) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
	}
	
	public int getX() {
		return this.x.getValue();
	}
	
	public int getY() {
		return this.y.getValue();
	}
}
