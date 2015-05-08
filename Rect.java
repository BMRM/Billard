/**
 * \file Rect.java
 * \brief Gestion des formes rectangulaires
 * \author Romain Mekarni
 */

 /**
 * \class Rect
 * \brief Classe représentant un rectangle soit sa taille et sa position
 * \author Romain Mekarni
 */
public class Rect
{
	int x;	///<Position x
	int y;	///<Position y
	int w;	///<Longueur
	int h;	///<Largeur
/**
 * \brief Constructeur
 * \author Romain Mekarni
 */
	static Rect make(int x, int y, int w, int h)
	{
		Rect rect = new Rect();
		rect.x = x;
		rect.y = y;
		rect.w = w;
		rect.h = h;
		return rect;
	}
/**
 * \brief Rempli un rectangle avec EcranGraphique
 */
	static void fill(Rect rect)
	{
		EcranGraphique.fillRect(rect.x, rect.y, rect.w, rect.h);
	}
/**
 * \brief Vérifie si le point (x,y) est dans le rectangle rect
 * \author Romain Mekarni
 */
	static boolean isIn(Rect rect, int x, int y)
	{
		return ((x >= rect.x && x <= rect.x + rect.w) && (y >= rect.y && y <= rect.y + rect.h));
	}
}

