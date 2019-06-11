package model.game;

public class Nodes {
	private int x;
	private int y;
	private int cost;
	public Nodes (int x, int y, int n) {
		this.x=x;
		this.y=y;
		this.cost = n;
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String toString() {
		return this.x + " " + this.y + " " + this.cost;
	}
	
}
