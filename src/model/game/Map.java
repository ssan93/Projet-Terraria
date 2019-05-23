package model.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Map {

//	private int[][] sol, mid, bg;
	private ObservableList<Tiles> tilesListSol, tilesListMid;

	private static final int Largeur = 300, Hauteur = 100;

	private String fichierDuSol, fichierDuMilieu/*, fichierDuBg*/;

	/**
	 * 
	 * @param carteSol url ground file
	 * @param carteMilieu mid file
	 * 
	 */
	public Map(String carteSol, String carteMilieu/*, String carteBg*/) {
		this.fichierDuSol = carteSol;
		this.fichierDuMilieu = carteMilieu;
//		this.fichierDuBg = carteBg;
		this.tilesListSol = FXCollections.observableArrayList();
		this.tilesListMid = FXCollections.observableArrayList();
		/*
		 * this.sol = new int[Hauteur][Largeur];// sol : terre, pierre, mine this.milieu
		 * = new int[Hauteur][Largeur];//environnement : arbre, caillou,etc.. this.bg =
		 * new int[Hauteur][Largeur];// background
		 */
		this.initialiseMap(/*this.sol,*/ fichierDuSol, true);
		this.initialiseMap(/*this.mid,*/ fichierDuMilieu, false);
	}

	/**
	 * 
	 * @param carte
	 * @param fichier
	 *            Url
	 * @param sol
	 *            ground or mid
	 */
	private void initialiseMap(/*int[][] carte,*/ String fichier, boolean sol) {

		String ligne = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			int y = 0;
			do {
				int x = 0;
				ligne = br.readLine();
				if (ligne != null) {
					StringTokenizer s = new StringTokenizer(ligne, ",");
					while (s.hasMoreTokens()) {
						int token = Integer.parseInt(s.nextToken());
						if (token != 0) {
							if (sol)
								tilesListSol.add(new Tiles(x, y, token));
							else
								tilesListMid.add(new Tiles(x, y, token));
						}
						x++;
					}
					y++;
				}

			} while (ligne != null);
			br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param map
	 * @return sol or background
	 */
//	public int[][] getMap(int map) {
//		if (map % 2 == 0)
//			return this.sol;
//		return this.bg;
//	}
//
//	public int[][] getMapSol() {
//		return this.sol;
//	}
//
//	public int[][] getMapMilieu() {
//		return this.mid;
//	}
//
//	public int[][] getMapBg() {
//		return this.bg;
//	}

	public ObservableList<Tiles> getTilesListSol() {
		return tilesListSol;
	}

	public ObservableList<Tiles> getTilesListMid() {
		return tilesListMid;
	}
}
