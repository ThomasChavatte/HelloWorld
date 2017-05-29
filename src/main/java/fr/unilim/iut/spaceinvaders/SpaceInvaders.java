package fr.unilim.iut.spaceinvaders;

import fr.unilim.iut.spaceinvaders.moteurjeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurjeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;
import fr.unilim.iut.spaceinvaders.Constante;

public class SpaceInvaders implements Jeu{
		int longueur;
	    int hauteur;
	    Vaisseau vaisseau;
	    Missile missile;
		Envahisseur envahisseur;
		Direction sens = Direction.DROITE;

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
	    		vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
			}

			if (commande.droite)
			{
				vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			}
			if (commande.tir && !this.aUnMissile())
		           tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
							Constante.MISSILE_VITESSE);
			
			if(this.aUnMissile()){
	    		this.deplacerMissile();
	    	}
			if(this.aUnEnvahisseur()){
				this.deplacerEnvahisseur();
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
				marque = Constante.MARQUE_VAISSEAU;
			else if (this.aUnMissileQuiOccupeLaPosition(x, y))
					marque = Constante.MARQUE_MISSILE;
			else if (this.aUnEnvahisseurQuiOccupeLaPosition(x,y))
					marque = Constante.MARQUE_ENVAHISSEUR;
			else marque = Constante.MARQUE_VIDE;
			return marque;
		}
		
		
		
		
		
		
		
		

		private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
			return this.aUnMissile() && missile.occupeLaPosition(x, y);
			}
		

		public boolean aUnMissile() {
			// TODO Auto-generated method stub
			return missile!=null;
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
			positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
			
			Position positionEnvahisseur = new Position(this.longueur/2,Constante.ENVAHISSEUR_HAUTEUR+1);
			Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR);
			positionnerUnNouveauEnvahisseur(dimensionEnvahisseur, positionEnvahisseur, Constante.ENVAHISSEUR_VITESSE);
			
		 }
		
		public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {
			
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

			 vaisseau = new Vaisseau(dimension,position,vitesse);
		}
		
		
		private boolean estDansEspaceJeu(int x, int y) {
			return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
		}
		
		public void deplacerVaisseauVersLaDroite() {
			if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
				vaisseau.deplacerHorizontalementVers(Direction.DROITE);
				if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
					vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
				}
			}
		}
		
		public void deplacerVaisseauVersLaGauche() {
			if (0 < vaisseau.abscisseLaPlusAGauche())
				vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
			}
		}

		public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
			
			   if ((vaisseau.hauteur()+ dimensionMissile.hauteur()) > this.hauteur )
				   throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");
								
			   this.missile = this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile);
	       }

		public Missile recupererMissile() {
			// TODO Auto-generated method stub
			return this.missile;
		}

		public void deplacerMissile() {
			
			if(missile.ordonneeLaPlusBasse()>0){
			missile.deplacerVerticalementVers(Direction.HAUT_ECRAN);
			}
			else{
				this.missile=null;
			}
		}
		
		
		
		//////////////////////////////////////////////////////////

		public void positionnerUnNouveauEnvahisseur(Dimension dimension, Position position, int vitesse) {
			// TODO Auto-generated method stub
			int x = position.abscisse();
			int y = position.ordonnee();
			
			if (!estDansEspaceJeu(x, y))
				throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

			int longueurEnvahiseur = dimension.longueur();
			int hauteurEnvahisseur = dimension.hauteur();
			
			if (!estDansEspaceJeu(x + longueurEnvahiseur - 1, y))
				throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
			if (!estDansEspaceJeu(x, y - hauteurEnvahisseur+1))
				throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

			 envahisseur = new Envahisseur(dimension,position,vitesse);
		}
		
		public boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
			// TODO Auto-generated method stub
			return this.aUnEnvahisseur() && envahisseur.occupeLaPosition(x, y);
		}

		public boolean aUnEnvahisseur() {
			// TODO Auto-generated method stub
			return envahisseur!=null;
		}

		public Envahisseur recupererEnvahisseur() {
			// TODO Auto-generated method stub
			return this.envahisseur;
		}

		public void deplacerEnvahisseurVersLaGauche() {
			// TODO Auto-generated method stub
			if (0 < envahisseur.abscisseLaPlusAGauche())
				envahisseur.deplacerHorizontalementVers(Direction.GAUCHE);
			if (!estDansEspaceJeu(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusHaute())) {
				envahisseur.positionner(0, envahisseur.ordonneeLaPlusHaute());
			}			
		}

		public void deplacerEnvahisseurVersLaDroite() {
			// TODO Auto-generated method stub
			if (envahisseur.abscisseLaPlusADroite() < (longueur - 1)) {
				envahisseur.deplacerHorizontalementVers(Direction.DROITE);
				if (!estDansEspaceJeu(envahisseur.abscisseLaPlusADroite(), envahisseur.ordonneeLaPlusHaute())) {
					envahisseur.positionner(longueur - envahisseur.longueur(), envahisseur.ordonneeLaPlusHaute());
				}
			}
		}
		
		private void deplacerEnvahisseur() {
			// TODO Auto-generated method stub
			if(sens==Direction.DROITE){
				if(envahisseur.abscisseLaPlusADroite()>=longueur-1){
					sens=Direction.GAUCHE;
					this.deplacerEnvahisseurVersLaGauche();
				}
				else{
					this.deplacerEnvahisseurVersLaDroite();
				}
				
			}
			else{
				if(envahisseur.abscisseLaPlusAGauche()<=0){
					this.deplacerEnvahisseurVersLaDroite();
					sens=Direction.DROITE;
				}
				else{
					this.deplacerEnvahisseurVersLaGauche();
				}				
			}
			
		}

}
