package model.game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;

public class Character extends AnimatedObject {
	private GestionCollision detecteur;
	private int speed, hp, aggroRange, move, moveLeft;
	SimpleIntegerProperty hpProperty;

	public Character(int x, int y, int speed, int hp) {
		super(x, y);
		this.speed = speed;
		this.hp = hp;
		this.hpProperty = new SimpleIntegerProperty(hp);

	}

	public Character(int x, int y, int speed, int hp, int aggro) {
		super(x, y);
		this.speed = speed;
		this.hp = hp;
		this.hpProperty = new SimpleIntegerProperty(hp);
		this.aggroRange = aggro;

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

	public void move(String direction, boolean pnj) {
		switch (direction) {
		case "Left":
			this.x.set(this.x.get() - 1);
			if (this.getX() <= 0 && !pnj)
				this.x.set(299);
			break;
		case "Right":
			this.x.set(this.x.get() + 1);
			if (this.getX() > 299 && !pnj)
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

	public int getAggroRange() {
		return this.aggroRange;
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

	public void randomMove(Character c, String character) {
		int random;
		if (moveLeft != 0)
			random = move;
		else {
			random = (int) (Math.random() * 3);
			this.moveLeft = 10;
		}
		switch (random) {
		case 0:// no move
			break;
		case 1:
			if (detecteur.verifRight(this, false, false, character)) {
				c.move("Right", true);
				animation("right_run_" + character);
			}
			break;
		case 2:
			if (detecteur.verifLeft(this, false, false, character)) {
				c.move("Left", true);
				animation("left_run_" + character);
			}
			break;

		case 3:
			animation("left_static_" + character);
			break;
		case 4:
			animation("right_static_" + character);
			break;
		}
		this.moveLeft--;
		this.move = random;
	}
	public void randomMove(int n, String character) {
		int random;
		if (moveLeft != 0)
			n = move;
		else {
			random = (int) (Math.random() * 3);
			this.moveLeft = 10;
		}
		switch (n) {
		case 0:// no move
			break;
		case 1:
			
				move("Right", true);
				animation("right_run_" + character);
			
			break;
		case 2:
			
				move("Left", true);
				animation("left_run_" + character);
			
			break;

		case 3:
			animation("left_static_" + character);
			break;
		case 4:
			animation("right_static_" + character);
			break;
		}
		this.moveLeft--;
		this.move = n;
	}

	public int getMove() {
		return move;
	}

	public int getMoveLeft() {
		return moveLeft;
	}
}
