package model.game;

public class GestionCollision {
	
	public static boolean collide(Objet environnement, Objet character) {
		return environnement.rec.intersects(character.rec);
	}

}
