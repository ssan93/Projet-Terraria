package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.game.Character;
import model.game.GestionCollision;
import model.game.Map;

class GestionCollisionsTest {

	private Map mapPrincipale = new Map("src/maps/grosseMap_sol.csv", "src/maps/grosseMap_environnement.csv");
	GestionCollision gc = new GestionCollision(mapPrincipale);
	Character c = new Character(17, 22, 2, 10);
	
	@Test
	void test() {
		/*
		assertTrue(gc.verifTop(c), "vérifie si on peut sauter d'un cran");
		assertFalse(gc.verifTop(c), "vérifie si on peut sauter d'un cran");
		 */
		
		
		assertTrue(gc.verifLeft(c, false, false), "dans le cas ou le personnage passe");	
		assertTrue(gc.verifRight(c, false, false), "dans le cas ou le personnage ne passe pas");	
		c.move("RunRight");
		c.move("RunRight");
		assertFalse(gc.verifRight(c, false, false), "dans le cas ou le personnage ne passe pas");	
		c.move("RunRight");
		assertFalse(gc.verifLeft(c, false, false), "dans le cas ou le personnage passe à gauche");	
		assertFalse(gc.verifUnder(c), "vérifie si on ne peut pas descendre");
		assertTrue(gc.verifTop(c), "vérifie si on peut sauter d'un cran");
		c.move("Up");
		c.move("Up");
		assertTrue(gc.verifUnder(c), "vérifie si on peut descendre");
		

		
	}
	

}
