package model.game;

public class Character extends AnimatedObject {

	private int speed, hp;

	public Character(int x, int y, int speed, int hp) {
		super(x, y);
		this.speed = speed;
		this.hp = hp;
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
			if (this.getX() <= 0)
				this.x.set(299);
			this.x.set(this.x.get() - 1);
			break;
		case "RunRight":
			if (this.getX() > 299)
				this.x.set(0);
			this.x.set(this.x.get() + 1);
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

		// if (direction.equals("RunLeft")) {
		// this.x.set(this.x.get() - 1);
		// if (this.getX()<=0)
		// this.x.set(299);
		// }
		// if (direction.equals("RunRight")) {
		// this.x.set(this.x.get() + 1);
		// if (this.getX()>299)
		// this.x.set(0);
		// }
		// if (direction.equals("Up"))
		// this.y.set(this.y.get() - 1);
		// if (direction.equals("Down"))
		// this.y.set(this.y.get() + 1);
	}

	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void damage(int dp) {
		this.hp -= dp;
	}
}
