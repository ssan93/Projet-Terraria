package model.game;

public class Weapon extends InventoryItem {

	private int damage;
	
	public Weapon(String name, String desc,int damage) {
		super(name, 1, desc);
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

}
