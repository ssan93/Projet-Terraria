package model.game;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Character {

	private SimpleIntegerProperty x,y;
	
	public Character(int x, int y) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
	}

	public int getX() {
		return this.x.getValue();
	}
	/////ezagvbaq

	public int getY() {
		return this.y.getValue();
	}
	
	public void setX(int x) {
		this.x.setValue(x);
	}

	public void setY(int y) {
		this.y.setValue(y);
	}
	
	public SimpleIntegerProperty getXProperty() {
		return this.x;
	}
	
	public SimpleIntegerProperty getYProperty() {
		return this.y;
	}
}
