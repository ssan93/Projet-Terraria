package view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Character;

public class CharacterView {

	protected Character charac;
	protected ImageView imageViewCharac;
	
	public CharacterView(String imagePerso) {
		this.charac= new Character(0, 0, 0);
		this.imageViewCharac=new ImageView(new Image(imagePerso));
		this.bind();
	}
	
	public void bind() {
		this.imageViewCharac.translateXProperty().bind(this.charac.getXProperty());
		this.imageViewCharac.translateYProperty().bind(this.charac.getYProperty());
	}
	
	public ImageView getImage() {
		return this.imageViewCharac;
	}
	
	public Character getChrac() {
		return this.charac;
	}
	
}