public class Menu
{
	static int nbButtons = 8;
	static int width = 200;
	static String title[] = {
		"Billard classique",
		"Boules en ligne",
		"Pause",
		"Demarrer",
		"Quitter"};

	Button	buttons[];
	Rect	size;

	static void make(Menu menu, Rect sizeMenu)
	{
		menu.buttons = new Button[nbButtons];
		menu.size = sizeMenu;

		for (int i = 0; i < nbButtons; i++)
		{
			Rect size = Rect.make(menu.size.x, i * (menu.size.h / nbButtons), menu.size.w, (menu.size.h / nbButtons));
			menu.buttons[i] = new Button();
			Button.make(menu.buttons[i], title[i], size);
		}
	}

	static int update(Menu menu)
	{
		for (int i = 0; i < nbButtons; i++)
		{
			Button.update(menu.buttons[i]);
			if (menu.buttons[i].isClicked)
				return i;
		}
		return -1;
	}

	static void render(Menu menu)
	{
		for (int i = 0; i < nbButtons; i++)
			Button.render(menu.buttons[i]);
	}
}

