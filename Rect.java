public class Rect
{
	int x;
	int y;
	int w;
	int h;

	static Rect make(int x, int y, int w, int h)
	{
		Rect rect = new Rect();
		rect.x = x;
		rect.y = y;
		rect.w = w;
		rect.h = h;
		return rect;
	}

	static void fill(Rect rect)
	{
		EcranGraphique.fillRect(rect.x, rect.y, rect.w, rect.h);
	}

	static boolean isIn(Rect rect, int x, int y)
	{
		return ((x >= rect.x && x <= rect.x + rect.w) && (y >= rect.y && y <= rect.y + rect.h));
	}
}

