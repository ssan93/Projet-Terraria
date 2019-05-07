package modele;

public class PersonnageQuiBougent extends Personnage{

	private int vitesse;
	public PersonnageQuiBougent(int x, int y, int vitesse) {
		super(x, y);
		this.vitesse=vitesse;
	}
	
	public int getVitesse() {
		return this.vitesse;
	}
	
	public void setVitesse(int vitesse) {
		this.vitesse=vitesse;
	}

}
