/**
 * \file Color.java
 * \brief Gestion des couleurs pour l'API EcranGraphique
 * \author Romain Mekarni, Baptiste Minervini
 */

 /**
 * \brief Classe représentant une couleur
 */
public class Color
{
	int r; ///<Rouge
	int v; ///<Vert
	int b; ///<Bleu
	
/**
 * Créé une couleur de type RVB
 * @param r
 * @param v
 * @param b
 * @return
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
 * Defini la couleur qu'utilisera l'API graphique
 * @param c
 */
    static void set(Color c)
    {
        EcranGraphique.setColor(c.r, c.v, c.b);
    }
}

