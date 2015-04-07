public class Window
{
	static int width = 800;
	static int height = 600;
	static int scale = 200;

    int             nbBalls;
	RenderBall[]	renderBalls;
	Menu			menu;

	static void make(Window win, int nbBalls)
	{
        win.nbBalls = nbBalls;
		win.renderBalls = new RenderBall[nbBalls];
		RenderBall.make(win.renderBalls, nbBalls);

		win.menu = new Menu();
		Menu.make(win.menu, Rect.make(300, 0, 100, width));

		EcranGraphique.init(50 , 50, width + 50, height + 50, width, height, "Billard");
	}

	static void renderBox()
	{
		EcranGraphique.setColor(0, 10, 0);
		EcranGraphique.fillRect(0, 0, (int)(Box.length * scale + 20), (int)(Box.width * scale + 20));
		EcranGraphique.setColor(0, 50, 0);
		EcranGraphique.fillRect(10, 10, width, height);
	}

	static void render(Window win, Box box)
	{
		renderBox();
		Menu.render(win.menu);
		RenderBall.render(win.renderBalls, box.balls, win.nbBalls);
		EcranGraphique.flush();
	}
}

