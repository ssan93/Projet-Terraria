package model.game;

import java.util.ArrayList;

public class Inventory {

	ArrayList<InventoryItem> IL;
	public Inventory() {
		this.IL=new ArrayList<>();
	}
	
	public void addToInventory(String object, int quantity) {		
		for (int i = 0; i < IL.size(); i++) {
			if(IL.get(i).getName().equals(object)){
				IL.get(i).addQuantity(quantity);
			}
			else {
				IL.add(new InventoryItem(object, quantity));
			}
		}
	}
	
	public boolean removeFromInventory(String object, int quantity) {
		for (int i = 0; i < IL.size(); i++) {
			if(IL.get(i).getName().equals(object)){
				IL.get(i).removeQuantity(quantity);
				return true;
			}
		}
		return false;
	}
}
