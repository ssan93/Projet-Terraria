package modele;

import javafx.beans.property.SimpleStringProperty;
import vue.personnages.PersonnageVue;

public class PersonnageQuiBougent extends Personnage{

	private int vitesse;
	PersonnageVue persoVue;
	SimpleStringProperty anim;
	public PersonnageQuiBougent(int x, int y, int vitesse) {
		super(x, y);
		this.vitesse=vitesse;
		this.persoVue=new PersonnageVue(this, "vue/personnages/rambo_run_right.gif");
		this.anim = new SimpleStringProperty("repos");
	}
	
	public void setAnim(String anim) {
		this.anim.set(anim);
	}
	
	public  SimpleStringProperty getAnim() {
		return this.anim;
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
