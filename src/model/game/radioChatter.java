package model.game;

import java.util.ArrayList;

public class radioChatter {
	private int n;
	ArrayList<String> radioCalls;
	public radioChatter() {
		n=0;
		radioCalls=new ArrayList<>();
		radioCalls.add("BILL, ICI LE COLONEL JACK APPUI SUR ENTREE SI TU MENTEND !!");
		radioCalls.add("BIEN, BILL TA MISSION EST D'ELIMINER REEEETY HUGHE JOHN ...");
		radioCalls.add("POUR TE DEPLACER UTILISE Q & D ...");
		radioCalls.add("POUR SAUTER UTIlISE SPACE ...");
		radioCalls.add("PARFAIT BILL, POUR UTILISER TON INVENTAIRE APPUI SUR I");
		radioCalls.add("PARFAIT BILL, POUR CRAFTER APPUI SUR C");
		radioCalls.add("C'EST TOUT BILL, BONNE CHANCE.");
	}

	public String getCall() {
		if(n<radioCalls.size()) {
			String r= radioCalls.get(n);
			n++;
			return r;
		}
		else 
			return "endoffile";
	}
}
