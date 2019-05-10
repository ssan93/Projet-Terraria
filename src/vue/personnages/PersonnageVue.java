package vue.personnages;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.PersonnageQuiBougent;

public class PersonnageVue {

	protected PersonnageQuiBougent perso;
	protected ImageView imageViewPerso;
	
	public PersonnageVue(PersonnageQuiBougent perso, String imagePerso) {
		this.perso=perso;
		this.imageViewPerso=new ImageView(new Image(imagePerso));
		this.imageViewPerso.translateXProperty().bind(this.perso.getXProperty());
		this.imageViewPerso.translateYProperty().bind(this.perso.getYProperty());
	}
	
	public ImageView getImage() {
		return this.imageViewPerso;
	}

}