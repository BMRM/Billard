public class Menu
{
	static int nbButtons = 5;
	static int width = 200;
    static int type[] = {0, 0, 1, 2, 0};
	static String title[][] = {
        {"Billard classique"},
        {"Boules en ligne"},
        {"Demarrer", "Pause"},
        {"dt : ", ""},
        {"Quitter"}};

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
			Button.make(menu.buttons[i], type[i], title[i], size);
		}
	}

	static int update(Menu menu, Box box)
	{
		for (int i = 0; i < nbButtons; i++)
		{
			if (Button.update(menu.buttons[i], box) != -1
			&& menu.buttons[i].state % 2 == 1)
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

