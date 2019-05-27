package model.game;

public class InventoryItem {

	private int quantity;
	private String name;
	
	public InventoryItem(String name, int quantity) {
		this.quantity=quantity;
		this.name=name;
	}
	public int getQuantity() {
		return this.quantity;
	}
	public void addQuantity(int more) {
		this.quantity+=more;
	}
	public void removeQuantity(int less) {
		this.quantity-=less;
	}
	public String getName() {
		return this.name;
	}
}
