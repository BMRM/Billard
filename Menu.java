public class Menu
{
	static int nbButtons = 11;
	static int width = 200;
    static int type[] = {0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 0};
	static String title[][] = {
        {"Billard classique"},
        {"Boules en ligne"},
        {"Jeu Manuel"},
        {"Demarrer", "Pause"},
        {"dt : ", String.valueOf(Box.dt)},
        {"Precision : ", String.valueOf(Box.s)},
        {"FPS : ", String.valueOf(Window.fps)},
        {"Rayon : ", String.valueOf(Box.rayon)},
        {"Boule : ", String.valueOf(Box.ballFocus.id)},
        {"Frottement : ", String.valueOf(Box.friction)},
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
        Box.dt = Double.valueOf(menu.buttons[4].title[1]);
        Box.s = Integer.valueOf(menu.buttons[5].title[1]);
        Window.fps = Integer.valueOf(menu.buttons[6].title[1]);
        Box.rayon = Double.valueOf(menu.buttons[7].title[1]);
        Box.ballFocus = box.balls[Integer.valueOf(menu.buttons[8].title[1])];
        Box.friction = Double.valueOf(menu.buttons[9].title[1]);
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

