package model.game;

public class Weapon extends InventoryItem {

	private int damage;
	
	public Weapon(String name, String desc,int degat) {
		super(name, 1, desc);
		this.damage = degat;
	}

	public int getDamage() {
		return damage;
	}

}
