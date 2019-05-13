package vue.personnages;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.MovingCharacter;

public class CharacterView {

	protected MovingCharacter perso;
	protected ImageView imageViewPerso;
	
	public CharacterView(MovingCharacter perso, String imagePerso) {
		this.perso=perso;
		this.imageViewPerso=new ImageView(new Image(imagePerso));
		this.imageViewPerso.translateXProperty().bind(this.perso.getXProperty());
		this.imageViewPerso.translateYProperty().bind(this.perso.getYProperty());
	}
	
	public ImageView getImage() {
		return this.imageViewPerso;
	}

	
}