public class Color
{
	int r;
	int v;
	int b;
/**
 * Type agrege definissant une couleur R,V,B
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
    static void setEcranGraphique(Color c)
    {
        EcranGraphique.setColor(c.r, c.v, c.b);
    }
}


