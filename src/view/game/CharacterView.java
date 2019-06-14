package view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Character;

public class CharacterView extends ImageView{

	protected Character charac;

	public CharacterView(String imagePerso) {
		this.charac = new Character(0, 0, 0, 1);
		this.setImage(new Image(imagePerso));
	}


	public Character getChrac() {
		return this.charac;
	}

}