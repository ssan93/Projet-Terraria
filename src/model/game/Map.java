package model.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class Map {

	private int[][] sol, milieu, bg;
	private static final int Largeur = 300, Hauteur = 100;
	

	private String fichierDuSol, fichierDuMilieu, fichierDuBg;

	/*
	 * 
	 */
	public Map(String carteSol, String carteMilieu, String carteBg) {
		this.fichierDuSol = carteSol;
		this.fichierDuMilieu = carteMilieu;
		this.fichierDuBg = carteBg;
		this.sol = new int[Hauteur][Largeur];// sol : terre, pierre, mine
		this.milieu = new int[Hauteur][Largeur];//environnement : arbre, caillou,etc..
		this.bg = new int[Hauteur][Largeur];// background
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
			System.out.println(e.getMessage());
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
