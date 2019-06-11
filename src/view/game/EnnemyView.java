package view.game;

import model.game.Ennemy;

public class EnnemyView extends CharacterView{

	public EnnemyView(String imagePerso) {
		super(imagePerso);
		this.charac=new Ennemy(31, 20, 9, 3);
		this.imageViewCharac.relocate(31*32, 20*32);
	}
	

}
