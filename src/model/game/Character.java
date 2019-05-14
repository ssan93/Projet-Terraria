package model.game;

import javafx.beans.property.SimpleStringProperty;
public class Character extends AnimatedObject{

	private int vitesse;
	SimpleStringProperty anim;
	public Character(int x, int y, int vitesse) {
		super(x, y);
		this.vitesse=vitesse;
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
}
