package model.game;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Inventory {

	private ObservableList<InventoryItem> IL;

	public Inventory() {
		this.IL = FXCollections.observableArrayList();
	}

	/**
	 * 
	 * @param i
	 * @return the item at the index i
	 */
	public InventoryItem get(int i) {
		return IL.get(i);
	}
	/**
	 * 
	 * @param item
	 */
	public void addToInventory(InventoryItem item) {
		int contain = this.indexOf(item.getName());
		if (contain != -1)
			IL.get(contain).addQuantity(item.getQuantity());
		else
			IL.add(item);
	}

	public boolean removeFromInventory(String object, int quantity) {
		InventoryItem item = IL.get(indexOf(object));
		if(quantity == item.getQuantity())
			return IL.remove(item);
		return item.removeQuantity(quantity);
	}

	public int indexOf(String name) {
		for (int i = 0; i < IL.size(); i++)
			if (IL.get(i).getName().equals(name))
				return i;
		return -1;
	}
	
	public void addListener(ListChangeListener<? super InventoryItem> listener) {
		IL.addListener(listener);
	}
	
	public void removeIf(Predicate<? super InventoryItem> filter) {
		this.IL.removeIf(filter);
	}
}
