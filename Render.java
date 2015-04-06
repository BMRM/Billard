public class Window
{
	static int width = 254 * 2;
	static int length = 508 * 2;
	static int scale = 200 * 2;
	
	RenderBall[]	renderBalls;
	Menu			menu;
	
	class Color 
	{
		int r, v, b;
	}

	static void renderBox()
	{
		EcranGraphique.setColor(0, 10, 0);
		EcranGraphique.fillRect(0, 0, (int)(box.length * scale + 20), (int)(box.width * scale + 20));
		EcranGraphique.setColor(0, 50, 0);
		EcranGraphique.fillRect(10, 10, length, width);
	}
	
	static void render(int[] coords)
	{
		String strCoords;
		EcranGraphique.setColor(70, 70, 70);
		EcranGraphique.fillRect(10, width - 70, 80, 30);
		strCoords = Integer.toString(coords[0]) + "," + Integer.toString(coords[1]);
		//System.out.println(strCoords);
		
		EcranGraphique.setColor(0, 0, 0);
	 	EcranGraphique.drawString(10, width - 50, 1, strCoords);
		
	}
	
	static void render(Window win)
	{
		render(win);
		renderBox();
		render(win.menu);
		render(win.renderBalls);
		render(
		EcranGraphique.flush();
	}
}

