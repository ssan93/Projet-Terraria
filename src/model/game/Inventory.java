package model.game;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Inventory {

	private ObservableList<InventoryItem> IL;

	public Inventory() {
		this.IL = FXCollections.observableArrayList();
	}

	public void addToInventory(String object, int quantity) {
		
		
		int contain = this.contains(object);
		if (contain != -1)
			IL.get(contain).addQuantity(quantity);
		else
			IL.add(new InventoryItem(object, quantity));
	}

	public boolean removeFromInventory(String object, int quantity) {
		return IL.get(contains(object)).removeQuantity(quantity);
	}

	public int contains(String name) {
		for (int i = 0; i < IL.size(); i++) {
			if (IL.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}
	
	public void addListener(ListChangeListener<? super InventoryItem> listener) {
		IL.addListener(listener);
	}
}
