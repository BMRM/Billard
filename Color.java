public class Color
{
	int r;
	int v;
	int b;

    static Color make(int r, int v, int b)
    {
        Color c = new Color();
        c.r = r;
        c.v = v;
        c.b = b;
        return c;
    }

    static void setEcranGraphique(Color c)
    {
        EcranGraphique.setColor(c.r, c.v, c.b);
    }
}


