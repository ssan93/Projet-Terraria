package model.game;

import javafx.beans.property.SimpleStringProperty;

public class AnimatedObject extends Object {

	SimpleStringProperty anim;

	public AnimatedObject(int x, int y) {
		super(x, y);
		anim = new SimpleStringProperty("idle");
	}

	public SimpleStringProperty getAnimation() {
		return this.anim;
	}

	public void animation(String newAnim) {
		anim.set(newAnim);
	}

}
