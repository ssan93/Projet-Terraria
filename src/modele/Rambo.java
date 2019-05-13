package modele;

import vue.personnages.PersonnageVue;
import vue.personnages.RamboVue;

public class Rambo extends PersonnageQuiBougent{

	public Rambo(int x, int y, int vitesse) {
		super(x, y, vitesse);
		this.persoVue=new RamboVue(this, "vue/personnages/rambo_run_right.gif");
	}

}
