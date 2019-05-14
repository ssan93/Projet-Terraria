package view.game;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import model.game.Bill;

public class BillView extends CharacterView{

	public BillView(String imagePerso) {
		super(imagePerso);
		this.charac=new Bill(32*9, 32*11, 9);
		this.bind();
		this.animation();
	}

	public void animation() {
		this.charac.getAnimation().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("right")) {
					getImage().setImage(new Image("view/resources/personnages/rambo_run_right.gif"));
				}
				if(newValue.equals("left")) {
					getImage().setImage(new Image("view/resources/personnages/rambo_run_left.gif"));
				}
			}
		});
	}


}
