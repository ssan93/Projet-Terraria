package model.game;

public class Character extends AnimatedObject {

	private int speed;

	public Character(int x, int y, int speed) {
		super(x, y);
		this.speed = speed;
	}

	@Override
	public void animation(String newAnim) {
		anim.set(newAnim);
		if (newAnim.equals("left") || newAnim.equals("right"))
			this.move(newAnim);

	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int vitesse) {
		this.speed = vitesse;
	}

	public void move(String direction) {
		if (direction.equals("left"))
			this.x.set(this.x.get() - this.speed);
		if (direction.equals("right"))
			this.x.set(this.x.get() + this.speed);
	}
}
