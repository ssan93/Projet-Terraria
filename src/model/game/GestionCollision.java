package model.game;

import java.util.ArrayList;
import java.util.Queue;

public class GestionCollision {

	private Map map;
	ArrayList<Tiles> tileList = new ArrayList<>();

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
		return (map.getMapSol()[ch.getX() - 1][ch.getY() + 1] == 0 && map.getMapSol()[ch.getX() - 1][ch.getY()] == 0);
	}

	/**
	 * 
	 * @param ch
	 * @return true si il y a rien a droite
	 */
	public boolean verifRight(Character ch, boolean jumping, boolean falling) {

		boolean verifDefault = map.getMapSol()[ch.getX()][ch.getY()] == 0
				&& map.getMapSol()[ch.getX()][ch.getY() + 1] == 0;
		if (falling)
			return verifDefault && map.getMapSol()[(ch.getX() + 1) % 300][ch.getY() + 2] == 0;
		if (jumping)
			return verifDefault && map.getMapSol()[ch.getX()][ch.getY() + 2] == 0;

		return verifDefault;
	}

	/**
	 * 
	 * @param ch
	 * @return true si il y a rien a gauche
	 */
	public boolean verifLeft(Character ch, boolean jumping, boolean falling) {
		boolean verifDefault = map.getMapSol()[ch.getX() - 1][ch.getY()] == 0
				&& map.getMapSol()[ch.getX() - 1][ch.getY() + 1] == 0;
		if (falling)
			return verifDefault && map.getMapSol()[(ch.getX() - 2 + 300) % 300][ch.getY() + 2] == 0; // %300 for map
																										// boundaries
		if (jumping)
			return verifDefault && map.getMapSol()[(ch.getX() - 1 + 300) % 300][ch.getY() + 2] == 0;
		return verifDefault;
	}

	public static boolean collide(Objet environnement, Objet character) {
		return environnement.getRectangle2D().intersects(character.getRectangle2D());
	}

	// public void astar(Character c, Character c2) {
	// ArrayList<Nodes>file=new ArrayList<Nodes>();
	// ArrayList<Nodes>from=new ArrayList<Nodes>();
	// ArrayList<Nodes>cost=new ArrayList<Nodes>();
	// file.add(new Nodes(c.getX(),c.getY(),0));
	// from.add(new Nodes(c.getX(),c.getY(),0));
	// cost.add(new Nodes(c.getX(),c.getY(),0));
	//
	// while(file.size()>0) {
	// Nodes current = file.remove(0);
	// if (current.getX() == c2.getX() && current.getY() == c2.getY())
	// break;
	// for(Nodes no : neighbors(current)) {
	//
	// int newCost = cost.get(0).getCost() +no.getCost();
	// boolean contain=false;
	// for(Nodes ns : cost) {
	// if(ns.getX() == no.getX() && ns.getY() == no.getY())
	// contain=true;
	// }
	// int index = cost.indexOf(no);
	// if(/*!cost.contains(no)*/!contain || newCost < cost.get(index+1).getCost()) {
	// no.setCost(newCost);
	// if(!contain)
	// cost.add(no);
	// else
	// cost.set(index, no);
	// int prio = newCost + heuristic(c2, no);
	// file.add(new Nodes (no.getX(), no.getY(), prio));
	// from.add(current);
	//
	//
	// }
	// }
	// }
	// for(Nodes n : from) {
	// System.out.println(n.toString()+" ,n");
	// }
	//
	//
	// }
	public void astar(Tiles t1, Tiles t2) {

		ArrayList<Nodes> file = new ArrayList<Nodes>();
		ArrayList<Nodes> from = new ArrayList<Nodes>();
		ArrayList<Nodes> path = new ArrayList<Nodes>();
		file.add(new Nodes(t1, t1, 0));
		// from.add(new Nodes(c.getX(),c.getY(),0));
		// cost.add(new Nodes(c.getX(),c.getY(),0));
		from.add(file.get(0));
		while (file.size() > 0) {
			Nodes current = file.remove(0);
			path.add(current);
			if (current.getTile().compareTo(t2) == 0) {
				System.out.println("found");
				break;
			}
			for (Nodes no : neighbors(current)) {
				int newCost = current.getCost() + 1;
				boolean contain = false;
				int index = 0;
				while (index < from.size() && from.get(index).getTile().compareTo(no.getTile()) != 0)
					index++;
				if (index < from.size() && from.get(index).getTile().compareTo(no.getTile()) == 0)
					contain = true;
				if (/* !cost.contains(no) */!contain || newCost < from.get(index).getCost()) {
					no.setCost(newCost);
					if (!contain) {
						from.add(no);
					} else {
						from.set(index, no);
					}
					int prio = newCost + heuristic(t2, no);

					insert(new Nodes(no.getTile(), current.getTile(), prio), file);

				}
			}
		}
		pathFinding(path, t1, t2);
		tileList.add(0, t1);
		for (Tiles t : tileList)
			System.out.println(t);
		// for(Nodes n : path)
		// System.out.println(n);

	}

	public void insert(Nodes n, ArrayList<Nodes> file) {
		int i = 0;

		while (file.size() > i && file.get(i).getCost() < n.getCost()) {
			i++;
		}
		file.add(i, n);
	}

	public ArrayList<Nodes> neighbors(Nodes n) {
		ArrayList<Nodes> neighborsList = new ArrayList<Nodes>();
		int[][] tabSol = map.getMapSol();
//		if (tabSol[n.getTile().getX()][n.getTile().getY() + 1] != 0) {
//			Tiles down = new Tiles(n.getTile().getX(), n.getTile().getY() + 1);
//			neighborsList.add(new Nodes(down, n.getTile(), 100));
//		}
//		if (tabSol[n.getTile().getX()][n.getTile().getY() - 1] != 0) {
//			Tiles up = new Tiles(n.getTile().getX(), n.getTile().getY() - 1);
//			neighborsList.add(new Nodes(up, n.getTile(), 100));
//		}
//		if (tabSol[n.getTile().getX() + 1][n.getTile().getY()] != 0) {
//			Tiles right = new Tiles(n.getTile().getX() + 1, n.getTile().getY());
//			neighborsList.add(new Nodes(right, n.getTile(), 100));
//		}
//		if (tabSol[n.getTile().getX() - 1][n.getTile().getY()] != 0) {
//			Tiles left = new Tiles(n.getTile().getX() - 1, n.getTile().getY());
//			neighborsList.add(new Nodes(left, n.getTile(), 100));
//		}
		for (int x = -1; x <= 2; x += 2) {
			int cx = n.getTile().getX();
			int cy = n.getTile().getY();
			//if (cx + x > 0 && cx + x <300 && tabSol[cx + x][cy] == 0)
				neighborsList.add(new Nodes(new Tiles(cx + x, cy), n.getTile(), 100));
//			if (cy + x > 0 && cy + x < 100 && tabSol[cx][cy + x] == 0)
				neighborsList.add(new Nodes(new Tiles(cx, cy + x), n.getTile(), 100));
		}

		return neighborsList;
	}

	public int heuristic(Tiles goal, Nodes nextNode) {
		return Math.abs(goal.getX() - nextNode.getTile().getX()) + Math.abs(goal.getY() - nextNode.getTile().getY());
	}

	public void pathFinding(ArrayList<Nodes> path, Tiles start, Tiles goal) {

		for (Nodes n : path) {
			if (!goal.equals(start) && n.getTile().compareTo(goal) == 0) {
				tileList.add(0, n.getTile());
				pathFinding(path, start, n.getFromTile());
			}
		}
	}

}
