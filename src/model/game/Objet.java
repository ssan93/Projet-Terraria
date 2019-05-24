
package model.game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;

public abstract class Objet {

	protected SimpleIntegerProperty x, y;
	protected int id;
	protected Rectangle2D rec;
	private static int lastId = 0;

	public Objet(int x, int y) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.rec = new Rectangle2D(x, y, 32, 32);
		this.id = ++lastId;
	}
	
	public Objet(int x, int y,int width,int height) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.rec = new Rectangle2D(x, y, width, height);
		this.id = ++lastId;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return this.x.getValue();
	}

	public int getY() {
		return this.y.getValue();
	}

	public SimpleIntegerProperty getXProperty() {
		return this.x;
	}

	public SimpleIntegerProperty getYProperty() {
		return this.y;
	}
	public Rectangle2D getRectangle2D() {
		return this.rec;
	}
}
