/**
 * \file Color.java
 * \brief Gestion des couleurs pour l'API EcranGraphique
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */

 /**
 * \brief Classe représentant une couleur
 * \author Baptiste Minervini
 */
public class Color
{
	int r; ///<Rouge
	int v; ///<Vert
	int b; ///<Bleu

/**
 * \brief Constructeur
 * \author Baptiste Minervini
 */
    static Color make(int r, int v, int b)
    {
        Color c = new Color();
        c.r = r;
        c.v = v;
        c.b = b;
        return c;
    }

/**
 * \brief Paramètre la couleur de EcranGraphique
 * \author Romain Mekarni
 */
    static void set(Color c)
    {
        EcranGraphique.setColor(c.r, c.v, c.b);
    }
}

