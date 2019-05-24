package view.game;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
			}
		});
	}
	
	public void setLife(ImageView heart) {
		if(charac.getHp()%5==0)
			heart.setImage(new Image("view/resources/personnages/heart/" +charac.getHp()+"pv.png"));
	}


}
