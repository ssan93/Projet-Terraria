package vue.personnages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import modele.MovingCharacter;

public class RamboVue extends CharacterView{

	public RamboVue(MovingCharacter perso, String imagePerso) {
		super(perso, imagePerso);
		animations();
	}
	
	public void animations() {
		perso.getAnim().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("right")) {
					getImage().setImage(new Image("vue/personnages/rambo_run_right.gif"));
				}
				if(newValue.equals("left")) {
					getImage().setImage(new Image("vue/personnages/rambo_run_left.gif"));
				}
			}
		});
	}

}
