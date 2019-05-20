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
				if(newValue.equals("RunRight")) {
					getImage().setImage(new Image("view/resources/personnages/right_run_bill.gif"));
				}
				if(newValue.equals("RunLeft")) {
					getImage().setImage(new Image("view/resources/personnages/left_run_bill.gif"));
				}
				if(newValue.equals("idleLeft")) {
					getImage().setImage(new Image("view/resources/personnages/left_static_bill.png"));
				}
				if(newValue.equals("idleRight")) {
					getImage().setImage(new Image("view/resources/personnages/right_static_bill.png"));
				}
				if(newValue.equals("jumpRight")) {
					getImage().setImage(new Image("view/resources/personnages/right_jump_bill.gif"));
				}
			}
		});
	}


}
