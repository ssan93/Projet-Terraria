package model;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Object {
	
	SimpleIntegerProperty x, y;
	
	public Object(int x, int y) {
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
