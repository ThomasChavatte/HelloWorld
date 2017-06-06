package fr.unilim.iut.spaceinvaders.model;

public abstract class Sprite {

	protected Position origine;
	protected Dimension dimension;
	protected int vitesse;

	public Sprite(Dimension dimension, Position origine, int vitesse) {
		super();
		this.dimension = dimension;
		this.origine = origine;
		this.vitesse = vitesse;
	}

	public boolean occupeLaPosition(int x, int y) {
		return (estAbscisseCouverte(x) && estOrdonneeCouverte(y));
	}

	private boolean estOrdonneeCouverte(int y) {
		return (ordonneeLaPlusBasse() <= y) && (y <= ordonneeLaPlusHaute());
	}

	private boolean estAbscisseCouverte(int x) {
		return (abscisseLaPlusAGauche() <= x) && (x <= abscisseLaPlusADroite());
	}

	public int ordonneeLaPlusBasse() {
		return origine.ordonnee() - this.dimension.hauteur() + 1;
	}

	public int ordonneeLaPlusHaute() {
		return this.origine.ordonnee();
	}

	public int hauteur() {
		return this.dimension.hauteur();
	}

	public int longueur() {
		return this.dimension.longueur();
	}

	public int abscisseLaPlusADroite() {
		return this.origine.abscisse() + this.dimension.longueur() - 1;
	}

	public int abscisseLaPlusAGauche() {
		return this.origine.abscisse();
	}
	
	public void deplacerHorizontalementVers(Direction direction) {
		if(direction.valeur()==-1 && this.origine.abscisse() >0){
			for(int j =0 ; j<this.vitesse; j++){
			this.origine.changerAbscisse((this.origine.abscisse() - 1));
			}
		}
		if(direction.valeur()== 1 && this.origine.abscisse() < Constante.ESPACEJEU_LONGUEUR- this.dimension.longueur){
			for(int g =0; g<this.vitesse; g++){
			this.origine.changerAbscisse(this.origine.abscisse() + 1);
			}
		}
		
	}
	public void positionner(int x, int y) {
		this.origine.changerAbscisse(x);
		this.origine.changerOrdonnee(y);
	}

	public void deplacerVerticalementVers(Direction direction) {
		for(int i =0; i<this.vitesse;i++){
		this.origine.changerOrdonnee(this.origine.ordonnee() - 1);
		}
	}
}