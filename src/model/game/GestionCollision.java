package model.game;

public class GestionCollision {
	
	private Map map;
	
	public GestionCollision(Map m) {
		this.map = m;
	}
	
//	public boolean collisionMap(AnimatedObject ch) {
//		switch (ch.getAnimation().get()) {
//		case "Right":
//			
//			break;
//
//		default:
//			break;
//		}
//	}
	/**
	 * 
	 * @param ch personnage
	 * @return true si il y a rien en dessous du personnage.
	 */
	public boolean verifUnder(Character ch) {
		return (map.getMapSol()[ch.getX()-1][ch.getY()+2] == 0 && map.getMapSol()[ch.getX()][ch.getY()+2] == 0);
	}
	/**
	 * 
	 * @param ch
	 * @return true si il y a rien a droite
	 */
	public boolean verifRight(Character ch) {
		return (map.getMapSol()[ch.getX()][ch.getY()] == 0 && map.getMapSol()[ch.getX()][ch.getY()+1] == 0);
	}
	/**
	 * 
	 * @param ch
	 * @return true si il y a rien a gauche
	 */
	public boolean verifLeft(Character ch) {
		return (map.getMapSol()[ch.getX()-1][ch.getY()] == 0 && map.getMapSol()[ch.getX()-1][ch.getY()+1] == 0);
	}	
	public static boolean collide(Objet environnement, Objet character) {
		return environnement.getRectangle2D().intersects(character.getRectangle2D());
	}

}
