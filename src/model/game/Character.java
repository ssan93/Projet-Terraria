package model.game;

public class Character extends AnimatedObject {

	private int speed, hp;

	public Character(int x, int y, int speed, int hp) {
		super(x, y);
		this.speed = speed;
		this.hp=hp;
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
		if (direction.equals("RunLeft"))
			this.x.set(this.x.get() - this.speed);
		if (direction.equals("RunRight"))
			this.x.set(this.x.get() + this.speed);
	}
	
	public int getHp() {
		return this.hp;
	}
	
	public void setHp(int hp) {
		this.hp=hp;
	}
	
	public void damage(int dp) {
		this.hp-=dp;
	}
}
