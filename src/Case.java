/**
 * Nom du programme : TN4 
 * Fichier : Case.java
 * 
 * @author Maxime Drouin
 */

public class Case {
	// Variable pour la ligne de la case dans le sudoku. private car getter et
	// setter associé.
	private int row;
	// Variable pour la colonne de la case dans le sudoku. private car getter et
	// setter associé.
	private int col;
	// Variable pour sauvegarder l'ancienne valeur de la case. private car
	// getter et setter associé.
	private int ancValeur;
	// Variable pour indiquer la nouvelle valeur de la case. private car getter
	// et setter associé.
	private int nouValeur;

	/*********************************************************************************/
	/*
	 * Constructeur de la classe 'Case' permettant d'instancier un Objet case
	 * afin de sauvegarder tous les placements
	 * 
	 * @param row - Sur qu'elle ligne faire le placement
	 * 
	 * @param col - Dans qu'elle colonne faire le placement
	 * 
	 * @param ancValeur - Afin de sauvegarder la valeur actuelle de la case
	 * 
	 * @param nouValeur - Spécifie la nouvelle valeur de la case
	 */
	/*********************************************************************************/
	public Case(int row, int col, int ancValeur, int nouValeur) {
		this.setRow(row);
		this.setCol(col);
		this.setAncValeur(ancValeur);
		this.setNouValeur(nouValeur);
	}

	/*********************************************************************************/
	/*
	 * Getter pour l'ancienne valeur de la case Public, car doit être accessible
	 * au restant du programme
	 * 
	 * @return ancValeur - L'ancienne valeur de la case avant ce placement
	 */
	/********************************************************************************/
	public int getAncValeur() {
		return ancValeur;
	}

	/*********************************************************************************/
	/*
	 * Setter pour l'ancienne valeur de la case Public, car doit être accessible
	 * au restant du programme
	 * 
	 * @param ancValeur - L'ancienne valeur de la case avant ce placement
	 */
	/********************************************************************************/
	public void setAncValeur(int ancValeur) {
		this.ancValeur = ancValeur;
	}

	/*********************************************************************************/
	/*
	 * Getter pour la nouvelle valeur de la case Public, car doit être
	 * accessible au restant du programme
	 * 
	 * @return nouValeur - La nouvelle valeur de cette case pour le placement
	 */
	/********************************************************************************/
	public int getNouValeur() {
		return nouValeur;
	}

	/*********************************************************************************/
	/*
	 * Setter pour la nouvelle valeur de la case Public, car doit être
	 * accessible au restant du programme
	 * 
	 * @param nouValeur - La nouvelle valeur de cette case pour le placement
	 */
	/********************************************************************************/
	public void setNouValeur(int nouValeur) {
		this.nouValeur = nouValeur;
	}

	/*********************************************************************************/
	/*
	 * Getter pour le choix de la ligne du Sudoku Public, car doit être
	 * accessible au restant du programme
	 * 
	 * @return row - La ligne dans laquelle se trouve la case
	 */
	/********************************************************************************/
	public int getRow() {
		return row;
	}

	/*********************************************************************************/
	/*
	 * Setter pour le choix de la ligne du Sudoku Public, car doit être
	 * accessible au restant du programme
	 * 
	 * @param row - La ligne dans laquelle se trouve la case
	 */
	/********************************************************************************/
	public void setRow(int row) {
		this.row = row;
	}

	/*********************************************************************************/
	/*
	 * Getter pour le choix de la colonne du Sudoku Public, car doit être
	 * accessible au restant du programme
	 * 
	 * @return col - La colonne dans laquelle se trouve la case
	 */
	/********************************************************************************/
	public int getCol() {
		return col;
	}

	/*********************************************************************************/
	/*
	 * Setter pour le choix de la colonne du Sudoku Public, car doit être
	 * accessible au restant du programme
	 * 
	 * @param col - La colonne dans laquelle se trouve la case
	 */
	/********************************************************************************/
	public void setCol(int col) {
		this.col = col;
	}

}
