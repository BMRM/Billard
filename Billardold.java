public class Billard 
{
//-----------Definition des dimensions de la table
	
	static int hauteur = 360;
	static int largeur = 480;
	
//-----------Toutes les structures :------------
	
	static class Coord
	{
		int x,y;
	}
	static class Couleur
	{
		int r,v,b;
	}
	
	static class Ball
	{
		int rayon;
		Coord vitesse = new Coord();
		Coord centre = new Coord();
		Couleur couleur = new Couleur();
		
	}
	
//------------Tout les Sousprog :---------------
	
	static void IniWin()
	{
		EcranGraphique.setColor(0, 10, 0);
		EcranGraphique.fillRect(0, 0, largeur, hauteur);
		EcranGraphique.setColor(0, 50, 0);
		EcranGraphique.fillRect(10, 10, largeur-20, hauteur-20);
	}
	
	static void positionnementLigne(Ball [] balls , int n)
	{
		for(int i=0; i<= n-1; i++)
		{
			EcranGraphique.setColor(balls[i].couleur.r, balls[i].couleur.v, balls[i].couleur.b);
			balls[i].centre.x = 25 + (largeur/n) * i;
			balls[i].centre.y = hauteur/2;
			EcranGraphique.fillCircle(balls[i].centre.x, balls[i].centre.y, balls[i].rayon);
		}
	}
	
	static void positionnementTriangle(Ball [] balls , int n)
	{
		int decal = 0;
		int b = 0;
		
		for(int i=1; i<= n; i++)
		{
			
			for(int j=0; j <= i-1; j++)
			{
				EcranGraphique.setColor(balls[b].couleur.r, balls[b].couleur.v, balls[b].couleur.b);
				balls[b].centre.x = (largeur/2) + decal;
				balls[b].centre.y = (hauteur/2) + (2 * balls[b].rayon * j) - (decal/2);
				
				EcranGraphique.fillCircle(balls[b].centre.x, balls[b].centre.y, balls[b].rayon);
				b++;
			}
			
			decal += 2*balls[i].rayon;
		}
	}
	
	static void positionnementBouleBlanche(Ball [] balls ,int x, int y, int n)
	{
		EcranGraphique.setColor(255, 255, 255);
		balls[n].centre.x = x;
		balls[n].centre.y = y;
		EcranGraphique.fillCircle(balls[n].centre.x, balls[n].centre.y, balls[n].rayon);
		
	}
	static void deplacement(Ball [] balls, int n)
	{
		IniWin();
		
		for(int i = 0; i <= n; i++)
		{
			EcranGraphique.setColor(balls[i].couleur.r, balls[i].couleur.v, balls[i].couleur.b);
			balls[i].centre.x += balls[i].vitesse.x;
			balls[i].centre.y += balls[i].vitesse.y;
			EcranGraphique.fillCircle(balls[i].centre.x, balls[i].centre.y, balls[i].rayon);
		}
		
	}
	 
//------------------------------------------------------------Main----------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		
		//taille de la base du triangle pour un positionnement classique
		int k = 5;
		
		//nombre de boule totale pour positionnement classique
		int n = k * (k+1)/2;
		
		//nombre de boule pour positionnement en ligne:
		//int n = 8;
		
		//déclaration du tableau des boules
		Ball [] balls = new Ball[n + 1];
		
		//Variables diverses
		int placement = 0;
				
//-----------------Initialisation du tableau de boules------------------------
				
		for(int i=0; i<= n-1; i++)
		{
			balls[i] = new Ball();
			balls[i].rayon = 10;
			balls[i].couleur.r = ((int)(Math.random() * 255));
			balls[i].couleur.v = ((int)(Math.random() * 255));
			balls[i].couleur.b = ((int)(Math.random() * 255));
		}
		
//-------------------Initialisation de la fenêtre graphique de départ-------------------
		
		EcranGraphique.init(50,50,largeur+100,hauteur+100,largeur,hauteur,"billard");
		IniWin();
		//positionnementLigne(balls, n);
		positionnementTriangle(balls , k);
		EcranGraphique.flush();
		while(placement!=1)
		{
			if(EcranGraphique.getMouseState() == 2)
			{
				balls[n] = new Ball();
				balls[n].rayon = 10;
				balls[n].couleur.r = 255;
				balls[n].couleur.v = 255;
				balls[n].couleur.b = 255;
				balls[n].vitesse.x = 1;
				positionnementBouleBlanche(balls, EcranGraphique.getMouseX(),EcranGraphique.getMouseY(), n);
				placement = 1;
				EcranGraphique.flush();
			}
		}
//--------------------Debut de la boucle infinie--------------------------------
		
		while(1==1)
		{
			deplacement(balls, n);
			EcranGraphique.flush();
		}

	}
}	
