package model.game;

import java.util.HashMap;

public class Craft {

	public static HashMap<String, InventoryItem> objetÀcraft = new HashMap<>();
	
	/**
	 * fill the HashMap
	 */
	public static void inizCraft() {
		objetÀcraft.put("1 bois, 2 metal", new ColdSteel("couteauBowie", "Le couteau Bowie, parfois appelé cure-dent de l'Arkansas (« Arkansas toothpick »), est un genre de couteau dont la popularité vient de son usage intensif faite par le colonel James Bowie, qui en créa la légende et le démocratisa. ",5));
		objetÀcraft.put("1 bois, 1 metal", new ColdSteel("crayon", "Un crayon est un instrument de dessin et d'écriture. Il est constitué d'une petite baguette servant de gaine à une mine de la même longueur, l'extrémité de la baguette étant parfois recouverte d'une gomme à effacer. Lorsque la mine est usée, on taille le bois au moyen d’un couteau Bowie.",2));
		objetÀcraft.put("3 ficelle, 1 planche en bois", new Weapon("arc", "Un arc est une arme de trait de tir tirant des flèches.", 10));
		objetÀcraft.put("10 fer, 10 plastique, 10 cuivre, etabli", new Weapon("M16", "Le M16 est le fusil d'assaut standard de l'armée américaine. Actuellement l'U.S. Army utilise la version A1.", 20));
	}
	
	public static String[] getNeed(InventoryItem item) {
		switch (item.getName()) {
		case "couteauBowie":
			return new String[]{"1","bois","2", "metal"};
		case "crayon" : 
			return new String[] {"1","bois","1","metal"};
		default:
			break;
		}
		return null;
	}
	
	
}
