package modele;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
public class Carte {

	private int [][] sol, milieu;
	
	private String fichierDuSol, fichierDuMilieu;

	public Carte(String carteSol, String carteMilieu) {
		this.fichierDuSol=carteSol;
		this.fichierDuMilieu=carteMilieu;
		this.sol = new int [30][30];
		this.milieu = new int [30][30];
		this.initialiseMap(this.sol, fichierDuSol);
		this.initialiseMap(this.milieu, fichierDuMilieu);
	}

	public void initialiseMap(int[][] carte, String fichier) {
		
		String ligne = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			int x=0;
			do {
				int y = 0;
				ligne=br.readLine();
				if(ligne!=null) {
					StringTokenizer s = new StringTokenizer(ligne, ",");
					while (s.hasMoreTokens()) {
						carte[x][y]=Integer.parseInt(s.nextToken());
						y++;
					}
					x++;
				}
				
			} while (ligne!=null);
		} catch (Exception e) {
		}
	}

	public int [][] getMapSol(){
		return this.sol;
	}
	
	public int [][] getMapMilieu(){
		return this.milieu;
	}
}
