package modele;

import javafx.beans.property.SimpleStringProperty;
import vue.personnages.CharacterView;

public class MovingCharacter extends Character{

	private int speed;
	CharacterView persoVue;
	SimpleStringProperty anim;
	public MovingCharacter(int x, int y, int speed) {
		super(x, y);
		this.speed=speed;
		this.persoVue=new CharacterView(this, "vue/personnages/rambo_run_right.gif");
		this.anim = new SimpleStringProperty("repos");
	}
	
	public void setAnim(String anim) {
		this.anim.set(anim);
	}
	
	public  SimpleStringProperty getAnim() {
		return this.anim;
	}
	
	public int getVitesse() {
		return this.speed;
	}
	
	public void setVitesse(int vitesse) {
		this.speed=vitesse;
	}
	
	public CharacterView getPersoVue() {
		return this.persoVue;
	}

}
