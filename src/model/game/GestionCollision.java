package model.game;

import java.util.ArrayList;

public class GestionCollision {

	private Map map;

	public GestionCollision(Map m) {
		this.map = m;
	}

	/**
	 * 
	 * @param ch
	 *            personnage
	 * @return true si il y a rien en dessous du personnage.
	 */
	public boolean verifUnder(Character ch) {
		return (map.getMapSol()[ch.getX() - 1][ch.getY() + 2] == 0 && map.getMapSol()[ch.getX()][ch.getY() + 2] == 0);
	}
	
	/**
	 * 
	 * @param ch
	 *            personnage
	 * @return true si il y a rien au dessus du personnage.
	 */
	public boolean verifTop(Character ch) {
		// System.out.println(ch.getX() +"\t"+ch.getY());
		return (map.getMapSol()[ch.getX()-1][ch.getY() +1] == 0 && map.getMapSol()[ch.getX()-1][ch.getY() ] == 0);
	}

	/**
	 * 
	 * @param ch
	 * @return true si il y a rien a droite
	 */
	public boolean verifRight(Character ch, boolean jumping, boolean falling) {
		
		boolean verifDefault = map.getMapSol()[ch.getX()][ch.getY()] == 0 && map.getMapSol()[ch.getX()][ch.getY() + 1] == 0;
		if(falling)
			return verifDefault && map.getMapSol()[(ch.getX()+1)%300][ch.getY() +2] == 0 ;
		if (jumping)
			return verifDefault && map.getMapSol()[ch.getX()][ch.getY() +2] == 0;
		
		return verifDefault;
	}

	/**
	 * 
	 * @param ch
	 * @return true si il y a rien a gauche
	 */
	public boolean verifLeft(Character ch, boolean jumping, boolean falling) {
		boolean verifDefault = map.getMapSol()[ch.getX() - 1][ch.getY()] == 0 && map.getMapSol()[ch.getX() - 1][ch.getY() + 1] == 0;
		if(falling)
			return verifDefault && map.getMapSol()[(ch.getX()-2+300)%300][ch.getY() +2] == 0 ; //%300 for map boundaries
		if (jumping)
			return verifDefault && map.getMapSol()[(ch.getX()-1+300)%300][ch.getY() +2] == 0;
		return verifDefault;
	}
	
	public static boolean collide(Objet environnement, Objet character) {
		return environnement.getRectangle2D().intersects(character.getRectangle2D());
	}
	
	public void astar(Character c, Character c2) {
		ArrayList<Nodes>file=new ArrayList<Nodes>();
		ArrayList<Nodes>from=new ArrayList<Nodes>();
		ArrayList<Nodes>cost=new ArrayList<Nodes>();
		file.add(new Nodes(c.getX(),c.getY(),0));
		from.add(new Nodes(c.getX(),c.getY(),0));
		cost.add(new Nodes(c.getX(),c.getY(),0));
		
		while(file.size()>0) {
			Nodes current = file.remove(0);
			if (current.getX() == c2.getX() && current.getY() == c2.getY())
				break;
			for(Nodes no : neighbors(current)) {
				
				int newCost = cost.get(0).getCost() +no.getCost();
				boolean contain=false;
				for(Nodes ns : cost) {
					if(ns.getX() == no.getX() && ns.getY() == no.getY())
						contain=true;
				}
				int index = cost.indexOf(no);
				if(/*!cost.contains(no)*/!contain || newCost < cost.get(index+1).getCost()) {
					no.setCost(newCost);
					if(!contain)
						cost.add(no);
					else
						cost.set(index, no);
					int prio = newCost + heuristic(c2, no);
					file.add(new Nodes (no.getX(), no.getY(), prio));
					from.add(current);
					
					
				}
			}
		}
		for(Nodes n : from) {
			System.out.println(n.toString()+" ,n");
		}
		
		
	}
	public ArrayList<Nodes> neighbors (Nodes n){
		ArrayList<Nodes> neighborsList = new ArrayList<Nodes>();
		neighborsList.add(new Nodes(n.getX(), n.getY()+1, 1));
		neighborsList.add(new Nodes(n.getX(), n.getY()-1, 1));
		neighborsList.add(new Nodes(n.getX()+1, n.getY(), 1));
		neighborsList.add(new Nodes(n.getX()-1, n.getY(), 1));
		
		return neighborsList;
	}
	
	public int heuristic(Character goal , Nodes nextNode) {
		return Math.abs(goal.getX()-nextNode.getX()) + Math.abs(goal.getY()-nextNode.getY());
	}
}
