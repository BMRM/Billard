/**
 * \file Rect.java
 * \brief Gestion des formes rectangulaires
 * \author Romain Mekarni
 */

 /**
 * \brief Classe représentant un rectangle soit sa taille et sa position
 */
public class Rect
{
	int x;	///<Position x
	int y;	///<Position y
	int w;	///<Longueur
	int h;	///<Largeur
/**
 * Cree un rectangle avec des dimensions donnees
 * @param x
 * @param y
 * @param w
 * @param h
 * @return
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
 * Rempli un rectangle
 * @param rect
 */
	static void fill(Rect rect)
	{
		EcranGraphique.fillRect(rect.x, rect.y, rect.w, rect.h);
	}
/**
 * Verifie si le point (x,y) est dans le rectangle rect
 * @param rect
 * @param x
 * @param y
 * @return
 */
	static boolean isIn(Rect rect, int x, int y)
	{
		return ((x >= rect.x && x <= rect.x + rect.w) && (y >= rect.y && y <= rect.y + rect.h));
	}
}

