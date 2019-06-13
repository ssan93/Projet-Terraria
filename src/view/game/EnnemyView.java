package view.game;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import model.game.Ennemy;

public class EnnemyView extends CharacterView{

	public EnnemyView(String imagePerso, int x, int y, int aggroRange) {
		super(imagePerso);
		this.charac=new Ennemy(x, y, 9, 3, aggroRange);
		this.imageViewCharac.relocate(x*32, y*32);
		this.animation();
	}
	public void animation() {
		this.charac.getAnimation().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch (newValue) {
				case "shoot":
					getImage().setImage(new Image("view/resources/personnages/heligun.gif"));
					break;
				case "stand":
					getImage().setImage(new Image("view/resources/personnages/heli.gif"));
					break;
				
				default:
					break;
				}
			}
		});
	}

	

}
