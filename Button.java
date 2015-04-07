public class Button
{
	static int b = 2;

	String	title;
	Rect	size;
	Rect	border;
	boolean isClicked = false;

	static void make(Button button, String title, Rect size)
	{
		button.title = title;
		button.size = Rect.make(size.x + b, size.x + b, size.w - 2 * b, size.h - 2 * b);
		button.border = size;
	}

	static void update(Button button)
	{
		if (Rect.isIn(button.size, EcranGraphique.getMouseX(), EcranGraphique.getMouseY())
		&& EcranGraphique.getMouseState() == 1
		&& EcranGraphique.getMouseButton() == 1
		&& button.isClicked == false)
			button.isClicked = true;
		else if (EcranGraphique.getMouseState() == 0)
			button.isClicked = false;
	}

	static void render(Button button)
	{
		EcranGraphique.setColor(60, 60, 60);
		Rect.fill(button.border);
		EcranGraphique.setColor(80, 80, 80);
		Rect.fill(button.border);
		EcranGraphique.setColor(255, 255, 255);
		EcranGraphique.drawString(button.size.x + 10, button.size.y + button.size.h - 10, 3, button.title);
	}
}
