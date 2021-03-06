/*
 * Modifié le 20/03/16 par Maxime Drouin
 */

/*
 * Créé le 20 sept. 2005
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fond extends JPanel {
	Image bg;
	Interface map;
	Carre[][] tableauDigits;
	int[][] grilleTemporaire;

	public Fond(Image bg, Interface map) {
		this.bg = bg;
		this.map = map;
		tableauDigits = new Carre[map.getSudoku().getN()][map.getSudoku()
				.getN()];
		creaGrille(map.getSudoku().getGrille());
		this.setLayout(null);
		this.setPreferredSize(new Dimension(295, 295));

	}

	public void chercherNouvellesGrilles() {
		int compteur = 0;
		while (true) {
			while (map.sudoku.placements != 1) {
				map.sudoku.init();
				map.sudoku.generateur();
			}
			compteur++;
			map.file.log(map.sudoku.ecrire());
			map.sudoku.init();
			map.sudoku.afficher(map.sudoku.tableauFinal);
		}
	}

	public void viderGrille() {
		// ON met la grille temporaire a null et le tableaufinal aussi

		grilleTemporaire = null;
		map.sudoku.setTableaufinal(null);
		for (int i = 0; i < map.sudoku.grille.length; i++) {
			for (int e = 0; e < map.sudoku.grille.length; e++) {
				map.sudoku.setGrille(i, e, 0);
				tableauDigits[i][e].setBg(new ImageIcon("images/nonselec.png")
						.getImage());
				tableauDigits[i][e].nonDispo = false;
				tableauDigits[i][e].setCoche(false);
			}
		}
		repaint();
		map.menu.m331.setEnabled(true);
		map.menu.m332.setEnabled(false);
	}

	public void testerSiNouvelleGrille() {
		try {
			String[] temp = map.file.reader();
			int max = 0;
			for (int i = 0; i < temp.length; i++) {
				for (int e = i + 1; e < temp.length; e++) {
					if (temp[i].equals(temp[e])) {
						System.out.println("i : " + i + " e: " + e);
					}

				}
			}
			System.out.print(max);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cacherSolution() {
		// VIDE LE TABLEAU FINAl (utile pour le presse-papier)
		map.sudoku.tableauFinal = null;
		// PERMET D'Eviter UNE ECRITURE DE CHIFFRE DANS UNE CASE NON DISPONIBLE
		decochage();
		for (int i = 0; i < tableauDigits.length; i++) {
			for (int e = 0; e < tableauDigits.length; e++) {
				if (grilleTemporaire[i][e] != 0) {
					tableauDigits[i][e]
							.setBg(map.nbPlaces[grilleTemporaire[i][e] - 1]);
					tableauDigits[i][e].nonDispo = true;
				} else {
					tableauDigits[i][e].setBg(new ImageIcon(
							"images/nonselec.png").getImage());
					tableauDigits[i][e].nonDispo = false;
				}
			}
		}

		repaint();
	}

	public boolean solutionnerGrille() {
		boolean solution = false;
		// PERMET D'Eviter UNE ECRITURE DE CHIFFRE DANS UNE CASE NON DISPONIBLE
		decochage();

		// REMET PLACEMENT ET COMPLEXITE A ZERO
		map.sudoku.placements = 0;
		map.sudoku.complexite = 0;
		// SAUVEGARDE de L'etat Actuel de la Grille
		grilleTemporaire = map.sudoku.clonage(map.sudoku.grille);

		// UNE TENTATIVE DE RESOLUTION
		if (map.sudoku.testerValidite()) {
			map.sudoku.resolution();

			if (map.sudoku.getPlacements() == 1) {
				for (int i = 0; i < tableauDigits.length; i++) {
					for (int e = 0; e < tableauDigits.length; e++) {
						if (grilleTemporaire[i][e] != 0) {
							tableauDigits[i][e]
									.setBg(map.nbPlaces[grilleTemporaire[i][e] - 1]);
							tableauDigits[i][e].nonDispo = true;
						} else {
							tableauDigits[i][e]
									.setBg(map.nbDispo[map.sudoku.tableauFinal[i][e] - 1]);
							tableauDigits[i][e].nonDispo = true;
						}
						tableauDigits[i][e].repaint();
					}
				}
				solution = true;

			} else {
				pasBon();
			}
		} else {
			pasBon();
		}
		return solution;
	}

	public void selection(int niveauDifficulte) {
		// RENITIALISATION DE LA GRILLE
		viderGrille();
		decochage();
		try {
			String[] temp = map.file.reader();
			int index = map.sudoku.random(0, temp.length);
			switch (niveauDifficulte) {
			case 1:
				while (!(Integer.parseInt(temp[index].substring(85,
						temp[index].length())) <= 5000)) {
					index = map.sudoku.random(0, temp.length);
				}
				break;
			case 2:
				while (!(Integer.parseInt(temp[index].substring(85,
						temp[index].length())) > 5000)
						&& !(Integer.parseInt(temp[index].substring(85,
								temp[index].length())) < 80000)) {
					index = map.sudoku.random(0, temp.length);
				}
				break;
			case 3:
				while (!(Integer.parseInt(temp[index].substring(85,
						temp[index].length())) >= 80000)) {
					index = map.sudoku.random(0, temp.length);
				}
				break;
			}
			System.out.println("index" + index);
			int nombreAPlacer = 0;
			for (int i = 0; i < tableauDigits.length; i++) {
				for (int e = 0; e < tableauDigits.length; e++) {
					map.sudoku.setGrille(i, e,
							temp[index].charAt(nombreAPlacer) - 48);

					if ((temp[index].charAt(nombreAPlacer) - 48) != 0) {

						tableauDigits[i][e].nonDispo = true;
						tableauDigits[i][e].setBg(map.nbPlaces[(temp[index]
								.charAt(nombreAPlacer) - 48) - 1]);
					}

					nombreAPlacer++;
				}
			}
			repaint();
			map.sudoku.afficher(map.sudoku.grille);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void decochage() {
		for (int i = 0; i < tableauDigits.length; i++) {
			for (int e = 0; e < tableauDigits.length; e++) {
				tableauDigits[i][e].coche = false;
				tableauDigits[i][e].nonDispo = false;
			}
		}
		map.ecouteur.carre = null;
	}

	public void pasBon() {
		String temp = "";
		if (map.sudoku.getPlacements() > 1) {
			temp = "Plusieurs solutions !";
		} else {
			temp = "Aucune solution !";
		}

		JOptionPane.showMessageDialog(this, temp, "Erreur",
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, null);
	}

	/**
	 * @return Renvoie bg.
	 */
	public Image getBg() {
		return bg;
	}

	private void creaGrille(int[][] tableau) {
		int position = 0;
		int positionX = 0;
		int positionY = 0;
		for (int i = 0; i < tableau.length; i++) {
			for (int e = 0; e < tableau.length; e++) {

				positionX = (i * map.carre);
				positionY = (e * map.carre);

				if (i == 0) {
					positionX = 3;
				}
				if (e == 0) {
					positionY = 3;
				}

				if (i % 3 == 0 && i != 0 && i < 3) {
					positionX = (i * map.carre) + 4;
				}
				if (i % 3 == 0 && i != 0) {
					positionX = (i * map.carre) + 4;
				}
				if (i % 3 == 0 && i != 0 && i > 3) {
					positionX = (i * map.carre) + 5;
				}

				else if (i != 0 && i < 3) {
					positionX = (i * map.carre) + 3;
				} else if (i != 0 && i > 3 && i < 6) {
					positionX = (i * map.carre) + 4;
				} else if (i != 0 && i > 6) {
					positionX = (i * map.carre) + 5;
				}

				if (e % 3 == 0 && e != 0 && e < 3) {
					positionY = (e * map.carre) + 4;
				}
				if (e % 3 == 0 && e != 0) {
					positionY = (e * map.carre) + 4;
				}
				if (e % 3 == 0 && e != 0 && e > 3) {
					positionY = (e * map.carre) + 5;
				}

				else if (e != 0 && e < 3) {
					positionY = (e * map.carre) + 3;
				} else if (e != 0 && e > 3 && e < 6) {
					positionY = (e * map.carre) + 4;
				} else if (e != 0 && e > 6) {
					positionY = (e * map.carre) + 5;
				}
				tableauDigits[i][e] = new Carre(positionY, positionX,
						map.carre, map.carre, new ImageIcon(
								"images/nonselec.png").getImage(), i, e);
				this.add(tableauDigits[i][e], position);
				position++;

			}
		}
	}

	public void impressionAPartirDeTableau(int[][] tableau) {
		for (int i = 0; i < tableau.length; i++) {
			for (int e = 0; e < tableau.length; e++) {
				if (tableau[i][e] != 0) {
					tableauDigits[i][e].setBg(map.nbDispo[tableau[i][e] - 1]);
					tableauDigits[i][e].nonDispo = true;
				} else {
					tableauDigits[i][e].setBg(new ImageIcon(
							"images/nonselec.png").getImage());
					tableauDigits[i][e].nonDispo = true;
				}
			}
		}
		repaint();
	}

	/*********************************************************************************/
	/**
	 * Méthode pour effectuer un placement dans la grille. Une nouvelle méthode
	 * a été créée en plus de celle utilisée lors de la génération de la grille,
	 * car je ne voulais pas que le joueur puisse annuler un placement généré par
	 * le programme.
	 * 
	 * @param i
	 *            - La ligne du placement
	 * @param e
	 *            - La colonne du placement
	 * @param donnee
	 *            - La nouvelle valeur du placement
	 */
	/*********************************************************************************/
	public void fairePlacement(int row, int col, int donnee) {
		// Sauvegarde la valeur actuelle au cas où le placement ne serait pas
		// valide
		int ancValeur = map.getSudoku().grille[row][col];
		// Si le placement est valide
		if (map.getSudoku().validerPlacement(donnee, row, col)) {
			// Créer une instance de Case à partir des paramètres
			Case placement = new Case(row, col,
					map.getSudoku().grille[row][col], donnee);
			// Ajoute le placement dans l'ArrayList
			map.getSudoku().getHistoriquePlacements().add(placement);
			// Change la valeur de la grille pour la nouvelle valeur.
			map.getSudoku().grille[row][col] = donnee;

		} else {
			// Si le placement n'est pas valide
			remettreAncienneValeur(ancValeur, row, col);
		}
	}

	/*********************************************************************************/
	/**
	 * Méthode utilisée pour annuler le dernier placement.
	 */
	/********************************************************************************/
	public void annulerPlacement() {
		ArrayList<Case> al = map.getSudoku().getHistoriquePlacements();
		// Vérifie s'il y a un placement à annuler
		if (!al.isEmpty()) {
			// Va chercher le dernier placement effectué
			Case placement = al.get(al.size() - 1);
			int row = placement.getRow();
			int col = placement.getCol();
			int ancValeur = placement.getAncValeur();
			// Réassigne la case à son ancienne valeur
			map.getSudoku().getGrille()[row][col] = ancValeur;
			al.remove(al.size() - 1);
			// Imprime la grille
			map.getSudoku().afficher(map.getSudoku().getGrille());
			// Remet l'ancienne valeur dans la case
			remettreAncienneValeur(ancValeur, row, col);

		} else {
			// S'il n'y a pas de placements à annuler affiche un message.
			JOptionPane.showMessageDialog(null,
					"Il n'y a aucun placement à annuler");
		}

	}

	/*********************************************************************************/
	/**
	 * Méthode utilisée pour remettre une valeur dans le carre au niveau de
	 * l'affichage
	 * 
	 * @param valeur
	 *            - La valeur
	 * @param row
	 *            - La ligne ou se trouve le carré
	 * @param col
	 *            - La colonne ou se trouve le carré
	 */
	/********************************************************************************/
	public void remettreAncienneValeur(int valeur, int row, int col) {
		if (valeur != 0) {
			tableauDigits[row][col].setBg(map.nbDispo[map.getSudoku()
					.getGrille()[row][col] - 1]);
		} else {
			tableauDigits[row][col].setBg(new ImageIcon("images/nonselec.png")
					.getImage());
		}
		tableauDigits[row][col].nonDispo = false;
		tableauDigits[row][col].setCoche(false);
		map.repaint();
	}
}
