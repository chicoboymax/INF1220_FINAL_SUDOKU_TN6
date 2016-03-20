import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/*
 * Modifié le 20 Mars 2016 par Maxime Drouin
 *
 *
 */

public class Sudoku extends Thread {

	int grille[][];
	int n;
	int placements = 0;
	int complexite = 0;
	int[][] tableauFinal;
	private ArrayList<Case> historiquePlacements = new ArrayList<>();

	public Sudoku(int n) {
		this.n = n;
		grille = new int[n][n];
		init();
	}

	public void init() {
		for (int i = 0; i < grille.length; i++) {
			for (int e = 0; e < grille[i].length; e++) {
				grille[i][e] = 0;
			}
		}
		placements = 0;
		complexite = 0;
	}

	public String ecrire() {
		String temp = "";
		for (int i = 0; i < grille.length; i++) {
			for (int e = 0; e < grille[i].length; e++) {
				temp += Integer.toString(grille[i][e]);
			}
		}
		temp += " // " + Integer.toString(complexite);

		return temp;
	}

	public void generateur() {
		// ON SAUT DE QUADRAN EN QUADRAN
		for (int i = 0; i < n; i += 3) {
			for (int e = 0; e < n; e += 3) {

				int[] quadran = quadran(i, e);
				int ligneDebut = quadran[0];
				int colonneDebut = quadran[1];

				int compte = 0;
				while (compte != 5) {
					int chiffre = random(1, 9);
					int ligne = random(ligneDebut, 3);
					int colonne = random(colonneDebut, 3);

					// MAUVAIS CHOIX
					while (!chercheQuadran(ligne, colonne, grille, chiffre)
							|| !chercherHB(ligne, colonne, grille, chiffre)
							|| !chercherDG(ligne, colonne, grille, chiffre)) {
						chiffre = random(1, 9);
						ligne = random(ligneDebut, 3);
						colonne = random(colonneDebut, 3);
					}
					grille[ligne][colonne] = chiffre;
					compte++;

				}
			}
		}
		resolution();
	}

	public int random(int inf, int sup) {
		return (int) (inf + ((Math.random() * sup)));
	}

	public void resolution() {
		// afficher(grille);

		// on Commence la routine par 1
		int chiffre = 1;
		int colonne = prochaineColonne(chiffre, grille);
		while (colonne == -1) {
			chiffre++;
		}
		placement(colonne, grille, chiffre);
	}

	public void placement(int colonne, int[][] tableau, int chiffre) {

		// if(complexite<5000000){
		// BOUCLAGE SUR LES LIGNES
		for (int i = 0; i < n; i++) {

			// ICI ON TEST AVEC LE CHIFFRE
			if (libre(i, colonne, tableau, chiffre)) {// chiffre
				complexite++;

				if (tableau[i][colonne] == 0) {
					int[][] tab = clonage(tableau);
					tab[i][colonne] = chiffre;// chiffre

					int prochainneColonne = prochaineColonne(chiffre, tab);// chiffre
					if (prochainneColonne != -1) {
						placement(prochainneColonne, tab, chiffre);

					}

					else {
						// ON EST SUR LA DERNIERE COLONNE POSSIBLE
						if (chiffre + 1 <= n) {
							int prochainchiffre = chiffre + 1;
							prochainneColonne = prochaineColonne(
									prochainchiffre, tab);// chiffre
							// Il faut sauter le chiffre ou la grille est
							// completement placée
							while (prochainchiffre + 1 <= n
									&& prochainneColonne == -1) {
								prochainchiffre++;
								prochainneColonne = prochaineColonne(
										prochainchiffre, tab);
							}
							if (prochainneColonne != -1) {
								placement(prochainneColonne, tab,
										prochainchiffre);
							}

						} else {
							placements++;
							tableauFinal = tab;
						}
					}

				}

			}
		}

	}

	private int prochainChiffre() {
		int temp = -1;

		return temp;
	}

	private int prochaineColonne(int chiffre, int[][] tab) {
		int temp = -1;

		for (int i = 0; i < n; i++) {
			if (chercherHB(0, i, tab, chiffre)) {
				temp = i;
				break;
			}
		}

		return temp;
	}

	public int[][] clonage(int[][] tab) {
		int[][] temp = new int[tab.length][tab.length];
		for (int i = 0; i < tab.length; i++) {
			for (int e = 0; e < tab[i].length; e++) {
				temp[i][e] = tab[i][e];
			}
		}
		return temp;
	}

	public boolean libre(int ligne, int colonne, int[][] tab, int chiffre) {
		boolean libre = false;

		if (chercherDG(ligne, colonne, tab, chiffre)
				&& chercheQuadran(ligne, colonne, tab, chiffre)) {
			libre = true;

		}

		return libre;

	}

	private boolean chercheQuadran(int ligne, int colonne, int[][] tab,
			int chiffre) {
		int[] quadran = quadran(ligne, colonne);

		if (chercherChiffreQuadran(chiffre, quadran, tab)) {
			return true;
		} else {
			return false;
		}
	}

	private int[] quadran(int ligne, int colonne) {
		int[] quadran = new int[4];
		switch (ligne) {
		case 0:
		case 1:
		case 2:
			switch (colonne) {
			case 0:
			case 1:
			case 2:
				quadran[0] = 0;
				quadran[1] = 0;
				quadran[2] = 2;
				quadran[3] = 2;
				break;
			case 3:
			case 4:
			case 5:
				quadran[0] = 0;
				quadran[1] = 3;
				quadran[2] = 2;
				quadran[3] = 5;
				break;
			case 6:
			case 7:
			case 8:
				quadran[0] = 0;
				quadran[1] = 6;
				quadran[2] = 2;
				quadran[3] = 8;
				break;
			}
			break;
		case 3:
		case 4:
		case 5:
			switch (colonne) {
			case 0:
			case 1:
			case 2:
				quadran[0] = 3;
				quadran[1] = 0;
				quadran[2] = 5;
				quadran[3] = 2;
				break;
			case 3:
			case 4:
			case 5:
				quadran[0] = 3;
				quadran[1] = 3;
				quadran[2] = 5;
				quadran[3] = 5;
				break;
			case 6:
			case 7:
			case 8:
				quadran[0] = 3;
				quadran[1] = 6;
				quadran[2] = 5;
				quadran[3] = 8;
				break;
			}
			break;
		case 6:
		case 7:
		case 8:
			switch (colonne) {
			case 0:
			case 1:
			case 2:
				quadran[0] = 6;
				quadran[1] = 0;
				quadran[2] = 8;
				quadran[3] = 2;
				break;
			case 3:
			case 4:
			case 5:
				quadran[0] = 6;
				quadran[1] = 3;
				quadran[2] = 8;
				quadran[3] = 5;
				break;
			case 6:
			case 7:
			case 8:
				quadran[0] = 6;
				quadran[1] = 6;
				quadran[2] = 8;
				quadran[3] = 8;
				break;
			}
			break;

		}

		return quadran;
	}

	public boolean chercherChiffreQuadran(int chiffre, int[] quadran,
			int[][] tab) {
		boolean test = true;

		int ligneDebut = quadran[0];
		int colonneDebut = quadran[1];
		int ligneFin = quadran[2];
		int colonneFin = quadran[3];
		// ATTENTION IL Y A EGALITE DANS LES BOUCLES
		for (int ligne = ligneDebut; ligne <= ligneFin; ligne++) {
			for (int colonne = colonneDebut; colonne <= colonneFin; colonne++) {
				// MEME CHIFFRE DANS LE QUADRAN DONC FAUX
				if (tab[ligne][colonne] == chiffre) {
					test = false;
					break;
				}
			}
		}

		return test;
	}

	private boolean chercherDG(int ligne, int colonne, int[][] tab, int chiffre) {
		boolean test = true;

		for (int index = 0; index < n; index++) {
			if ((colonne + index) < n) {

				if (tab[ligne][colonne + index] == chiffre) {
					test = false;
					break;
				}
			}

			if ((colonne - index) >= 0) {
				if (tab[ligne][colonne - index] == chiffre) {
					test = false;
					break;
				}
			}
		}
		return test;
	}

	private boolean chercherHB(int ligne, int colonne, int[][] tab, int chiffre) {
		boolean test = true;

		for (int index = 0; index < n; index++) {
			if ((ligne - index) >= 0) {
				if (tab[ligne - index][colonne] == chiffre) {
					test = false;
					break;
				}
			}

			if ((ligne + index) < n) {
				if (tab[ligne + index][colonne] == chiffre) {
					test = false;
					break;
				}
			}
		}
		return test;
	}

	public void afficher(int[][] tab) {
		for (int i = 0; i < tab.length; i++) {
			for (int e = 0; e < tab[i].length; e++) {

				System.out.print(tab[i][e] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * @return Renvoie complexite.
	 */
	public int getComplexite() {
		return complexite;
	}

	/**
	 * @return Renvoie grille.
	 */
	public int[][] getGrille() {
		return grille;
	}

	public void setGrille(int i, int e, int donnee) {
		grille[i][e] = donnee;
	}

	/**
	 * @return Renvoie n.
	 */
	public int getN() {
		return n;
	}

	/**
	 * @param n
	 *            
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * @return Renvoie placements.
	 */
	public int getPlacements() {
		return placements;
	}

	/**
	 * @param placements
	 *            placements a définir.
	 */
	public void setPlacements(int placements) {
		this.placements = placements;
	}

	/**
	 * @param tableaufinal
	 *            tableaufinal à définir.
	 */
	public void setTableaufinal(int[][] tableaufinal) {
		this.tableauFinal = tableaufinal;
	}

	public boolean grilleRemplie() {
		boolean solution = true;
		if (grille != null) {
			for (int i = 0; i < grille.length; i++) {
				for (int e = 0; e < grille[i].length; e++) {
					if (grille[i][e] == 0) {
						solution = false;
						break;
					}
				}
			}
		} else {
			solution = false;
		}
		return solution;
	}

	public boolean testerValidite() {
		boolean solution = true;

		int nbElements = 0;
		for (int i = 0; i < grille.length; i++) {
			// ON PREND LE PREMIER ELEMENT DE CHAQUE LIGNE POUR ETRE TESTER
			// AVEC LES AUTRES ELEMENTS DE LA MEME LIGNE
			int max = grille[i][0];
			// ON COMMENCE A 1 DONC
			for (int e = 1; e < grille[i].length; e++) {

				if (grille[i][e] == max && max != 0) {
					solution = false;
					break;
				}
				if (grille[i][e] != 0) {
					nbElements++;
				}

				max = grille[i][e];
			}
		}
		// LE NB ELEMENTS NON NULS DOIT ETRE PLUS GRAND QUE 15
		if (solution && nbElements < 16) {
			solution = false;
		}
		return solution;
	}

	/**
	 * @param grille
	 *            grille à définir.
	 */
	public void setGrille(int[][] grille) {

		this.grille = grille;
	}

	/**
	 * @return Renvoie tableaufinal.
	 */
	public int[][] getTableaufinal() {
		return tableauFinal;
	}

	/*********************************************************************************/
	/**
	 * @return Renvoie historiquePlacements.
	 */
	/*********************************************************************************/
	public ArrayList<Case> getHistoriquePlacements() {
		return historiquePlacements;
	}

	/*********************************************************************************/
	/**
	 * @param grille
	 *            ArrayList de Cases historiquePlacements
	 */
	/*********************************************************************************/
	public void setHistoriquePlacements(ArrayList<Case> historiquePlacements) {
		this.historiquePlacements = historiquePlacements;
	}

	/*********************************************************************************/
	/**
	 * Vérifie si la valeur est déjà présente sur la ligne de la grille.
	 * 
	 * @param valeur
	 *            - La valeur que l'on veut vérifier.
	 * 
	 * @param ligne
	 *            - La ligne sur laquelle on veut effectuer la vérification.
	 * 
	 * @return boolean - Return true si la valeur est sur la ligne.
	 */
	/*********************************************************************************/
	public boolean estSurLigne(int valeur, int ligne) {
		boolean surLigne = false;
		for (int i = 0; i < grille[ligne].length; i++) {
			if (grille[ligne][i] == valeur) {
				surLigne = true;
				ligne +=1;
				JOptionPane.showMessageDialog(null,
						"Erreur, la grille comporte déjà la valeur " + valeur
								+ " sur la ligne " + ligne);
			}
		}
		return surLigne;
	}

	/*********************************************************************************/
	/**
	 * Vérifie si la valeur est déjà présente sur la colonne de la grille.
	 * 
	 * @param valeur
	 *            - La valeur que l'on veut vérifier.
	 * 
	 * @param colonne
	 *            - La colonne sur laquelle on veut effectuer la vérification.
	 * 
	 * @return boolean - Return true si la valeur est sur la colonne.
	 */
	/*********************************************************************************/
	public boolean estSurColonne(int valeur, int colonne) {
		boolean surColonne = false;
		for (int i = 0; i < grille[colonne].length; i++) {
			if (grille[i][colonne] == valeur) {
				surColonne = true;
				colonne+=1;
				JOptionPane.showMessageDialog(null,
						"Erreur, la grille comporte déjà la valeur " + valeur
								+ " sur la colonne " + colonne);
			}
		}
		return surColonne;
	}

	/*********************************************************************************/
	/**
	 * Vérifie si la valeur est déjà présente dans le bloc de la grille.
	 * 
	 * @param valeur
	 *            - La valeur que l'on veut vérifier.
	 * 
	 * @param ligne
	 *            - La ligne sur laquelle est située la case.
	 * 
	 * @param colonne
	 *            - La colonne sur laquelle est située la case.
	 * 
	 * @return boolean - Return true si la valeur est sur la ligne.
	 */
	/*********************************************************************************/
	public boolean estDansLeBloc(int valeur, int ligne, int colonne) {
		boolean dansLeBloc = false;
		// Détermine le début de la région en divisant les valeurs des lignes et
		// colonnes par 3 et ensuite en les multipliants par 3.
		int x1 = 3 * (ligne / 3);
		int y1 = 3 * (colonne / 3);
		// Détermine la fin des régions en ajoutant 2 aux valeurs obtenues.
		int x2 = x1 + 2;
		int y2 = y1 + 2;
		// Vérifier si la valeur est dans la région.
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				if (grille[x][y] == valeur) {
					dansLeBloc = true;
					ligne+=1;
					colonne+=1;
					JOptionPane.showMessageDialog(null,
							"Erreur, la grille comporte déjà la valeur "
									+ valeur
									+ " dans le bloc représentant la case ("
									+ ligne + "," + colonne + ").");
				}
			}

		}
		return dansLeBloc;
	}

	/*********************************************************************************/
	/**
	 * Permet de valider le placement en appellant toutes les méthodes de
	 * vérification.
	 * 
	 * @param valeur
	 *            - La valeur à vérifier.
	 * 
	 * @param ligne
	 *            - La ligne sur laquelle est située la case.
	 * 
	 * @param colonne
	 *            - La colonne sur laquelle est située la case.
	 * 
	 * @return boolean - Retourne false si le placement n'est pas valide.
	 */
	/*********************************************************************************/
	public boolean validerPlacement(int valeur, int ligne, int colonne) {
		boolean placementValide = true;
		// Si une des trois validation est vrai le placement n'est pas valide
		if (estDansLeBloc(valeur, ligne, colonne) || estSurLigne(valeur, ligne)
				|| estSurColonne(valeur, colonne)) {
			placementValide = false;
		}
		return placementValide;
	}

}
