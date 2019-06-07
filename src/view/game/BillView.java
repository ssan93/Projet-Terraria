package view.game;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.game.Bill;

public class BillView extends CharacterView {

	public BillView(String imagePerso) {
		super(imagePerso);
<<<<<<< HEAD
		this.charac = new Bill(30, 16, 9);
		this.imageViewCharac.relocate(708, 400);
		this.imageViewCharac.relocate(708, 405);
=======

		this.charac = new Bill(31, 16, 9);
		this.imageViewCharac.relocate(0, 0);
		// this.imageViewCharac.layoutXProperty().bind(charac.getXProperty());

>>>>>>> refs/remotes/origin/ssan
		this.animation();
	}

	public void animation() {
		this.charac.getAnimation().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch (newValue) {
				case "RunRight":
					getImage().setImage(new Image("view/resources/personnages/right_run_bill.gif"));
					break;
				case "RunLeft":
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
