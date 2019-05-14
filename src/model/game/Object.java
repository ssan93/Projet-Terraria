
package model.game;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Object {
	
	protected SimpleIntegerProperty x, y;
	protected int id;
	private static int lastId=0;
	
	public Object(int x, int y) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.id=++lastId;
	}
	
	public int getX() {
		return this.x.getValue();
	}
	
	public int getY() {
		return this.y.getValue();
	}
}
