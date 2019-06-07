package model.game;

public class InventoryItem {

	private int quantity;
	private String name;

	public InventoryItem(String name, int quantity) {
		this.quantity = quantity;
		this.name = name;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void addQuantity(int more) {
		this.quantity += more;
	}

	public boolean removeQuantity(int less) {
		if (this.quantity >= less) {
			this.quantity -= less;
			return true;
		}
		return false;
	}

	public String getName() {
		return this.name;
	}
}
