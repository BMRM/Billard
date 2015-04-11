public class Window
{
    static double fps = 60;
    static int sizeMenu = 200;
    static int sizeBoard = 200;
	static int scale = 300;
	static int width = (int)(Box.length * scale) + sizeMenu;
	static int height = (int)(Box.width * scale) + 20 + sizeBoard;

    int             nbBalls;
	RenderBall[]	renderBalls;
	Menu			menu;
    Board           board;

	static void make(Window win, int nbBalls)
	{
        win.nbBalls = nbBalls;
		win.renderBalls = new RenderBall[nbBalls];
		RenderBall.make(win.renderBalls, nbBalls);

		win.menu = new Menu();
		Menu.make(win.menu, Rect.make((int)(Box.length * scale) + 20, 0, sizeMenu, height));

        win.board = Board.make(Rect.make(0, (int)(Box.width * scale) + 20, width, sizeBoard));

		EcranGraphique.init(50 , 50, width + 100, height + 100, width, height, "Billard");
	}

	static void renderBox()
	{
		EcranGraphique.setColor(0, 10, 0);
		EcranGraphique.fillRect(0, 0, (int)(Box.length * scale) + 20, height);
		EcranGraphique.setColor(0, 50, 0);
		EcranGraphique.fillRect(10, 10, (int)(Box.length * scale), (int)(Box.width * scale));
	}

    static void renderInput()
    {
        EcranGraphique.setColor(255, 255, 255);
        EcranGraphique.fillRect(0, 50, width, 50);
        EcranGraphique.setColor(0, 0, 0);
        EcranGraphique.drawString(10, 80, 3, "> " + Input.buffer);
    }

	static void render(Window win, Box box)
	{
		renderBox();
		Menu.render(win.menu);
        Board.render(win.board);
		RenderBall.render(win.renderBalls, box.balls, win.nbBalls);
        if (Input.kb)
            renderInput();
        EcranGraphique.flush();
	}
}

