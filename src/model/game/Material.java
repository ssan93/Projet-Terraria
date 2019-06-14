package model.game;

public class Material extends InventoryItem {

	public Material(String name, int quantity, String desc) {
		super(name, quantity, desc);
	}
	
	public static String getNameByCode(int code) {
		switch (code) {
		case 1:
			return "bois";
		case 4 : 
			return "metal";
		default:
			break;
		}
		return "";
	}
	
	public static int codeByName(String name) {
		switch (name) {
		case "bois":
			return 1;
		case "metal" : 
			return 4;
		default:
			break;
		}
		return 0;
	}

}
