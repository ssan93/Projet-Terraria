package model.game;

public class InventoryItem {

	private int quantity;
	private String name;
	private String description;

	/**
	 * 
	 * @param name
	 * @param quantity
	 * @param desc
	 */
	public InventoryItem(String name, int quantity, String desc) {
		this.quantity = quantity;
		this.name = name;
		this.description = desc;
	}

	public String getDescription() {
		return description;
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
	
	@Override
	public String toString() {
		return description+"\n"
				+ "Vous avez "+ this.quantity+" "+this.name;
		
	}
}
