package fr.unilim.iut.spaceinvaders;

import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;

public class SpaceInvaders implements Jeu{
	 	private static final char MARQUE_VIDE = '.';
		private static final char MARQUE_VAISSEAU = 'V';
		int longueur;
	    int hauteur;
	    Vaisseau vaisseau;

	    public SpaceInvaders(int longueur, int hauteur) {
		   this.longueur = longueur;
		   this.hauteur = hauteur;
	   }

	    @Override
		public boolean etreFini() {
			// le jeu n'est jamais fini
			return false;
		}
	    
	    @Override
	    public void evoluer(Commande commande) {
	    	if (commande.gauche)
			{
				this.vaisseau.seDeplacerVersLaGauche();;
			}

			if (commande.droite)
			{
				this.vaisseau.seDeplacerVersLaDroite();
			}
		}
	    
	    
	    
	    
	    
	    
		public String recupererEspaceJeuDansChaineASCII() {
			StringBuilder espaceDeJeu = new StringBuilder();
			for (int y = 0; y < hauteur; y++) {
				for (int x = 0; x < longueur; x++) {
				    espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
				}
				espaceDeJeu.append('\n');
			}
			return espaceDeJeu.toString();
		}
	    
	    
	    
	    
	    
		private char recupererMarqueDeLaPosition(int x, int y) {
			char marque;
			if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			      marque=MARQUE_VAISSEAU;
			else
			      marque=MARQUE_VIDE;
			return marque;
		}
		private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
			return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
		}
		public boolean aUnVaisseau() {
			return vaisseau!=null;
		}
		public Vaisseau recupererVaisseau() {
			return this.vaisseau;
		}
		
		public void initialiserJeu() {
		    Position positionVaisseau = new Position(this.longueur/2,this.hauteur-1);
		    Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		    positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau);
	    }
		
		public void positionnerUnNouveauVaisseau(Dimension dimension, Position position) {
			
			int x = position.abscisse();
			int y = position.ordonnee();
			
			if (!estDansEspaceJeu(x, y))
				throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

			int longueurVaisseau = dimension.longueur();
			int hauteurVaisseau = dimension.hauteur();
			
			if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
				throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
			if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
				throw new DebordementEspaceJeuException("Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

			vaisseau = new Vaisseau(longueurVaisseau, hauteurVaisseau);
			vaisseau.positionner(x, y);
		}
		
		
		private boolean estDansEspaceJeu(int x, int y) {
			return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
		}
		
		public void deplacerVaisseauVersLaDroite() {
	        if (vaisseau.abscisseLaPlusADroite()< (longueur-1)) vaisseau.seDeplacerVersLaDroite();
		}
		
		public void deplacerVaisseauVersLaGauche() {
			if (vaisseau.abscisseLaPlusAGauche()>0) vaisseau.seDeplacerVersLaGauche();
		}

}
