public class Window
{
    static int sizeMenu = 200;
	static int scale = 300;
	static int width = (int)(Box.length * scale) + sizeMenu;
	static int height = (int)(Box.width * scale) + 20;

    int             nbBalls;
	RenderBall[]	renderBalls;
	Menu			menu;

	static void make(Window win, int nbBalls)
	{
        win.nbBalls = nbBalls;
		win.renderBalls = new RenderBall[nbBalls];
		RenderBall.make(win.renderBalls, nbBalls);

		win.menu = new Menu();
		Menu.make(win.menu, Rect.make((int)(Box.length * scale) + 20, 0, sizeMenu, height));

		EcranGraphique.init(50 , 50, width + 100, height + 100, width, height, "Billard");
	}

	static void renderBox()
	{
		EcranGraphique.setColor(0, 10, 0);
		EcranGraphique.fillRect(0, 0, (int)(Box.length * scale) + 20, height);
		EcranGraphique.setColor(0, 50, 0);
		EcranGraphique.fillRect(10, 10, (int)(Box.length * scale), (int)(Box.width * scale));
	}

	static void render(Window win, Box box)
	{
		renderBox();
		Menu.render(win.menu);
		RenderBall.render(win.renderBalls, box.balls, win.nbBalls);
	}
}

