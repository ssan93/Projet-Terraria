package modele;

import vue.personnages.PersonnageVue;

public class PersonnageQuiBougent extends Personnage{

	private int vitesse;
	PersonnageVue persoVue;
	public PersonnageQuiBougent(int x, int y, int vitesse) {
		super(x, y);
		this.vitesse=vitesse;
		this.persoVue=new PersonnageVue(this, "vue/personnages/rambo.png");
	}
	
	public int getVitesse() {
		return this.vitesse;
	}
	
	public void setVitesse(int vitesse) {
		this.vitesse=vitesse;
	}
	
	public PersonnageVue getPersoVue() {
		return this.persoVue;
	}

}
