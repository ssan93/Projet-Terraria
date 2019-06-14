package view.game;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Bill;

public class BillView extends CharacterView {

	public BillView(String imagePerso) {
		super(imagePerso);
		this.charac = new Bill(31, 16, 9);
		this.imageViewCharac.relocate(0, 0);
		this.animation();
	}

	public void animation() {
		this.charac.getAnimation().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch (newValue) {
				case "Right":
					getImage().setImage(new Image("view/resources/personnages/right_run_bill.gif"));
					break;
				case "Left":
					getImage().setImage(new Image("view/resources/personnages/left_run_bill.gif"));
					break;
				case "idleLeft":
					getImage().setImage(new Image("view/resources/personnages/left_static_bill.png"));
					break;
				case "idleRight":
					getImage().setImage(new Image("view/resources/personnages/right_static_bill.png"));
					break;
				case "jumpRight":
					getImage().setImage(new Image("view/resources/personnages/right_jump_bill.gif"));
					break;
				case "jumpLeft":
					getImage().setImage(new Image("view/resources/personnages/left_jump_bill.gif"));
					break;
				default:
					break;
				}
			}
		});
	}

	public void setLife(ImageView heart) {
		if (charac.getHp() % 5 == 0)
			heart.setImage(new Image("view/resources/personnages/heart/" + charac.getHp() + "pv.png"));
	}

}
