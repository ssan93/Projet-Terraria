package model.game;

import java.util.HashMap;

public class Craft {

	public static HashMap<String, InventoryItem> objetÀcraft = new HashMap<>();
	
	public static void inizCraft() {
		objetÀcraft.put("1 bois, 2 metal", new Weapon("couteauBowie", "Le couteau Bowie, parfois appelé cure-dent de l'Arkansas (« Arkansas toothpick »), est un genre de couteau dont la popularité vient de son usage intensif faite par le colonel James Bowie, qui en créa la légende et le démocratisa. ",5));
		objetÀcraft.put("1 couteau, 2 planche en bois", new Weapon("lance", "La lance est une arme d'hast dotée d'un fer emmanché sur une hampe ou long bois et, pour certaines d'entre elles, d'un talon métallique qui sert en général à équilibrer l'arme tenue en main et à la planter dans le sol.",7));
		objetÀcraft.put("3 ficelle, 1 planche en bois", new Weapon("arc", "Un arc est une arme de trait de tir tirant des flèches.", 10));
		objetÀcraft.put("10 fer, 10 plastique, 10 cuivre, etabli", new Weapon("M16", "Le M16 est le fusil d'assaut standard de l'armée américaine. Actuellement l'U.S. Army utilise la version A1.", 20));
	}
	
//	public static ArrayList<InventoryItem> craftable(ArrayList<InventoryItem> inventaire){
//		if(inventaire.isEmpty())
//			return null;
//		ArrayList<InventoryItem> result = new ArrayList<>();
//		return ;
//	}
}
