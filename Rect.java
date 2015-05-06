public class Rect
{
	int x;
	int y;
	int w;
	int h;
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
 * Verifie si le curseur de la souris se situe dans un rectangle
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

