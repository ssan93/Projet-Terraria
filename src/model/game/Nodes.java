package model.game;

public class Nodes {
	private Tiles tile;
	private Tiles fromTile;
	private int cost;
	public Nodes (Tiles t1, Tiles from, int n) {
		this.tile = t1;
		this.fromTile = from;
		this.cost = n;
	}
	
	public int getCost() {
		return cost;
	}
	public Tiles getTile() {
		return tile;
	}


	public Tiles getFromTile() {
		return fromTile;
	}

	public void setTile(Tiles tile) {
		this.tile = tile;
	}
	public void setFromTile(Tiles fromTile) {
		this.fromTile = fromTile;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	public String toString() {
		return this.tile.toString() + " | " + this.fromTile.toString() + " | " + this.cost;
	}
	
}
