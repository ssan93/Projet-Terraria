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

		assertTrue(gc.verifLeft(c, false, false, "bill"), "dans le cas ou le personnage passe");	
		assertTrue(gc.verifRight(c, false, false, "bill"), "dans le cas ou le personnage ne passe pas");	
		c = new Character(19, 22, 2, 10);
		assertFalse(gc.verifRight(c, false, false, "bill"), "dans le cas ou le personnage ne passe pas");	
		c = new Character(20, 22, 2, 10);
		assertFalse(gc.verifLeft(c, false, false, "bill"), "dans le cas ou le personnage passe à gauche");	
		assertFalse(gc.verifUnder(c, "bill"), "vérifie si on ne peut pas descendre");
		c = new Character(20, 20, 2, 10);
		assertTrue(gc.verifUnder(c, "bill"), "vérifie si on peut descendre");
		c = new Character(11, 27, 2, 10);		
		assertTrue(gc.verifTop(c), "vérifie si on peut sauter d'un cran");
		c = new Character(11, 28, 2, 10);		
		assertFalse(gc.verifTop(c), "vérifie si on peut sauter d'un cran");
	}
}
