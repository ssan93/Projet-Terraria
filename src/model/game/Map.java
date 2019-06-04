package model.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Map {

	private int[][] tabSol, mid;
	private ObservableList<Tiles> tilesListSol, tilesListMid;

	private static final int Largeur = 300, Hauteur = 100;

	private String fichierDuSol, fichierDuMilieu/* , fichierDuBg */;

	/**
	 * 
	 * @param carteSol
	 *            url ground file
	 * @param carteMilieu
	 *            mid file
	 * 
	 */
	public Map(String carteSol, String carteMilieu) {
		this.fichierDuSol = carteSol;
		this.fichierDuMilieu = carteMilieu;
		this.tilesListSol = FXCollections.observableArrayList();
		this.tilesListMid = FXCollections.observableArrayList();

		this.tabSol = new int[Largeur][Hauteur];// sol : terre, pierre, mine this.milieu
		this.mid = new int[Largeur][Hauteur];// environnement : arbre, caillou,etc.. this.bg =

		this.initialiseMap(fichierDuSol, true);
		this.initialiseMap(fichierDuMilieu, false);
	}

	/**
	 * 
	 * @param carte
	 * @param fichier
	 *            Url
	 * @param sol
	 *            ground or mid
	 */
	private void initialiseMap(/* int[][] carte, */ String fichier, boolean sol) {

		String ligne = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			int x, y = 0;
			do {
				x = 0;
				ligne = br.readLine();
				if (ligne != null) {
					StringTokenizer s = new StringTokenizer(ligne, ",");
					while (s.hasMoreTokens()) {
						int token = Integer.parseInt(s.nextToken());
						if (token != 0) {
							if (sol) {
								tilesListSol.add(new Tiles(x, y, token));
								this.tabSol[x][y] = token;
							}
							else {
								tilesListMid.add(new Tiles(x, y, token));
								this.mid[x][y] = token;
							}
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

	 public int[][] getMapSol() {
	 return this.tabSol;
	 }
	 public int[][] getMapMid() {
		 return this.mid;
		 }
	// public int[][] getMapMilieu() {
	// return this.mid;
	// }
	 public void removeTile(int x , int y) {
		 this.tabSol[x][y]=0;
	 }
	 public void setTileSol(int x , int y, int value) {
		 this.tabSol[x][y]=value;
	 }
	 

	public ObservableList<Tiles> getTilesListSol() {
		return tilesListSol;
	}

	public ObservableList<Tiles> getTilesListMid() {
		return tilesListMid;
	}
}
