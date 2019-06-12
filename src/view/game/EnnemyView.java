package view.game;

import model.game.Ennemy;

public class EnnemyView extends CharacterView{

	public EnnemyView(String imagePerso, int x, int y) {
		super(imagePerso);
		this.charac=new Ennemy(x, y, 9, 3);
		this.imageViewCharac.relocate(x*32, y*32);
	}
	

}
