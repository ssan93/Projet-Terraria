package model.game;

public class GestionCollision {

	private Map map;

	public GestionCollision(Map m) {
		this.map = m;
	}

	/**
	 * 
	 * @param ch
	 *            personnage
	 * @return true si il y a rien en dessous du personnage.
	 */
	public boolean verifUnder(Character ch) {
		return (map.getMapSol()[ch.getX() - 1][ch.getY() + 2] == 0 && map.getMapSol()[ch.getX()][ch.getY() + 2] == 0);
	}
	
	/**
	 * 
	 * @param ch
	 *            personnage
	 * @return true si il y a rien au dessus du personnage.
	 */
	public boolean verifTop(Character ch) {
		// System.out.println(ch.getX() +"\t"+ch.getY());
		return (map.getMapSol()[ch.getX()-1][ch.getY() +1] == 0 && map.getMapSol()[ch.getX()-1][ch.getY() ] == 0);
	}

	/**
	 * 
	 * @param ch
	 * @return true si il y a rien a droite
	 */
	public boolean verifRight(Character ch, boolean jumping, boolean falling) {
		boolean verifDefault = map.getMapSol()[ch.getX()][ch.getY()] == 0 && map.getMapSol()[ch.getX()][ch.getY() + 1] == 0;
		if(falling)
			return verifDefault && map.getMapSol()[ch.getX()+1][ch.getY() +2] == 0 ;
		if (jumping)
			return verifDefault && map.getMapSol()[ch.getX()][ch.getY() +2] == 0;
		
		return verifDefault;
	}

	/**
	 * 
	 * @param ch
	 * @return true si il y a rien a gauche
	 */
	public boolean verifLeft(Character ch, boolean jumping, boolean falling) {
		boolean verifDefault = map.getMapSol()[ch.getX() - 1][ch.getY()] == 0 && map.getMapSol()[ch.getX() - 1][ch.getY() + 1] == 0;
		if(falling)
			return verifDefault && map.getMapSol()[ch.getX()-2][ch.getY() +2] == 0 ;
		if (jumping)
			return verifDefault && map.getMapSol()[ch.getX()-1][ch.getY() +2] == 0;
		return verifDefault;
	}
	
	public static boolean collide(Objet environnement, Objet character) {
		return environnement.getRectangle2D().intersects(character.getRectangle2D());
	}

}
