package model.game;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Inventory {

	private ObservableList<InventoryItem> IL;

	public Inventory() {
		this.IL = FXCollections.observableArrayList();
	}

	public InventoryItem get(int i) {
		return IL.get(i);
	}
	public void addToInventory(InventoryItem item) {
		
		
		int contain = this.contains(item.getName());
		if (contain != -1)
			IL.get(contain).addQuantity(item.getQuantity());
		else
			IL.add(item);
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
