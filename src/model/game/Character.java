package model.game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;

public class Character extends AnimatedObject {

	private int speed, hp;
	SimpleIntegerProperty hpProperty;

	public Character(int x, int y, int speed, int hp) {
		super(x, y);
		this.speed = speed;
		this.hp = hp;
		this.hpProperty = new SimpleIntegerProperty(hp);

	}

	@Override
	public void animation(String newAnim) {
		anim.set(newAnim);
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int vitesse) {
		this.speed = vitesse;
	}

	public void move(String direction) {
		switch (direction) {
		case "RunLeft":
			this.x.set(this.x.get() - 1);
			if (this.getX() <= 0)
				this.x.set(299);
			break;
		case "RunRight":
			this.x.set(this.x.get() + 1);
			if (this.getX() > 299)
				this.x.set(1);
			break;
		case "Up":
			this.y.set(this.y.get() - 1);
			break;
		case "Down":
			this.y.set(this.y.get() + 1);
			break;
		default:
			break;
		}
	}

	public Rectangle2D getRectangle2D() {
		return new Rectangle2D(getX(), getY(), 27, 50);
	}

	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
		setHpProperty(hp);
	}

	public void damage(int dp) {
		this.hp -= dp;
	}
	
	public void setHpProperty(int hp) {
		this.hpProperty.set(hp);
	}
	public SimpleIntegerProperty getHpProperty() {
		return this.hpProperty;
	}
}
