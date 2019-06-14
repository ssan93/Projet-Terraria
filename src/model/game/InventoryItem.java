package model.game;

public class InventoryItem {

	private int quantity;
	protected String name;
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

	/**
	 * 
	 * @return the description of the item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @return the quantity of item
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * add more item
	 * @param more
	 */
	public void addQuantity(int more) {
		this.quantity += more;
	}

	/**
	 * remove rmqty item
	 * @param rmqty
	 * @return if the operation has succeeded
	 */
	public boolean removeQuantity(int rmqty) {
		if (this.quantity > rmqty) {
			this.quantity -= rmqty;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return the name of the item as a String
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return description+"\n"
				+ "Vous avez "+ this.quantity+" "+this.name;
		
	}
}
