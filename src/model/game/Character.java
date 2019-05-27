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
		//if (newAnim.equals("RunLeft") || newAnim.equals("RunRight"))
			//this.move(newAnim);

	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int vitesse) {
		this.speed = vitesse;
	}

	public void move(String direction) {
		if (direction.equals("RunLeft")) {
			this.x.set(this.x.get() - 1);
			if (this.getX()<=0)
				this.x.set(299);
		}
		if (direction.equals("RunRight")) {
			this.x.set(this.x.get() + 1);
			if (this.getX()>299)
				this.x.set(1);
		}
		if (direction.equals("Up"))
			this.y.set(this.y.get() - 1);
		if (direction.equals("Down"))
			this.y.set(this.y.get() + 1);
		
		
	}
}
