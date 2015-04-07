public class Window
{
	static int width = 800;
	static int height = 600;
	static int scale = 200;
	
	RenderBall[]	renderBalls;
	Menu			menu;
	
	static void make(Window win, int nbBoules)
	{
		win.renderBalls = new RenderBall[nbBoules];
		make(win.renderBalls, nbBoules);
		
		win.menu = new Menu();
		make(win.menu, rect(300, 0, 100, width));
		
		EcranGraphique.init(50 , 50, width + 50, height + 50, width, height, "Billard");
	}

	static void renderBox()
	{
		EcranGraphique.setColor(0, 10, 0);
		EcranGraphique.fillRect(0, 0, (int)(box.length * scale + 20), (int)(box.width * scale + 20));
		EcranGraphique.setColor(0, 50, 0);
		EcranGraphique.fillRect(10, 10, length, width);
	}
	
	static void render(Window win)
	{
		render(win);
		renderBox();
		render(win.menu);
		render(win.renderBalls);
		EcranGraphique.flush();
	}
}

