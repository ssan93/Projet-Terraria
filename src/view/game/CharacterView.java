package view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Character;

public class CharacterView {

	protected Character perso;
	protected ImageView imageViewPerso;
	
	public CharacterView(String imagePerso) {
		this.perso= new Character(0, 0, 0);
		this.imageViewPerso=new ImageView(new Image(imagePerso));
		this.imageViewPerso.translateXProperty().bind(this.perso.getXProperty());
		this.imageViewPerso.translateYProperty().bind(this.perso.getYProperty());
	}
	
	public ImageView getImage() {
		return this.imageViewPerso;
	}

	
}