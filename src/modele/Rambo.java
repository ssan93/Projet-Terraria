package modele;

import vue.personnages.CharacterView;
import vue.personnages.RamboVue;

public class Rambo extends MovingCharacter{

	public Rambo(int x, int y, int vitesse) {
		super(x, y, vitesse);
		this.persoVue=new RamboVue(this, "vue/personnages/rambo_run_right.gif");
	}

}
