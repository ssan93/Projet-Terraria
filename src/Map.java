package model.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class Map {

	private int[][] sol, milieu, bg;

	private String fichierDuSol, fichierDuMilieu, fichierDuBg;

	public Map(String carteSol, String carteMilieu, String carteBg) {
		this.fichierDuSol = carteSol;
		this.fichierDuMilieu = carteMilieu;
		this.fichierDuBg = carteBg;
		this.sol = new int[20][30];
		this.milieu = new int[20][30];//
		this.bg = new int[20][30];// background
		this.initialiseMap(this.sol, fichierDuSol);
		this.initialiseMap(this.milieu, fichierDuMilieu);
		this.initialiseMap(this.bg, fichierDuBg);
	}

	public void initialiseMap(int[][] carte, String fichier) {

		String ligne = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			int x = 0;
			do {
				int y = 0;
				ligne = br.readLine();
				if (ligne != null) {
					StringTokenizer s = new StringTokenizer(ligne, ",");
					while (s.hasMoreTokens()) {
						carte[x][y] = Integer.parseInt(s.nextToken());
						y++;
					}
					x++;
				}

			} while (ligne != null);
		} catch (Exception e) {
		}
	}

	public int[][] getMap(int map) {
		if (map % 2 == 0)
			return this.sol;
		return this.bg;
	}

	public int[][] getMapSol() {
		return this.sol;
	}

	public int[][] getMapMilieu() {
		return this.milieu;
	}

	public int[][] getMapBg() {
		return this.bg;
	}
}
