package fr.unilim.iut.spaceinvaders.model;

import java.util.ArrayList;

import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class Vaisseau extends Sprite {
	
	public Vaisseau(Dimension dimension, Position positionOrigine, int vitesse) {
		super(dimension, positionOrigine, vitesse);
	}		
		
	public Missile tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {
		if(dimensionMissile.longueur()>this.dimension.longueur()){
			throw new MissileException("La taille du missile ne doit pas être supérieur à celle du vaisseau");
		}
		Position positionOrigineMissile = calculerLaPositionDeTirDuMissile(dimensionMissile);

		return new Missile(dimensionMissile, positionOrigineMissile, vitesseMissile);
	}

	public Position calculerLaPositionDeTirDuMissile(Dimension dimensionMissile) {
		int abscisseMilieuVaisseau = this.abscisseLaPlusAGauche() + (this.longueur() / 2);
		int abscisseOrigineMissile = abscisseMilieuVaisseau - (dimensionMissile.longueur() / 2);

		int ordonneeeOrigineMissile = this.ordonneeLaPlusBasse() - 1;
		Position positionOrigineMissile = new Position(abscisseOrigineMissile, ordonneeeOrigineMissile);
		return positionOrigineMissile;
	}



}