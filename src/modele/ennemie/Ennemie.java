package modele.ennemie;

import javafx.beans.property.SimpleIntegerProperty;
import modele.Character;

public class Ennemie extends Character{

	SimpleIntegerProperty pv, pa;
	public Ennemie(int x, int y, int pv, int pa) {
		super(x, y);
		this.pv= new SimpleIntegerProperty(pv);
		this.pa= new SimpleIntegerProperty(pa);
	}
	public int getPv() {
		return this.pv.getValue();
	}
	public int getPa() {
		return this.pa.getValue();
	}

}
