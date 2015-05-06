public class Input
{
    static boolean  kb = false;
    static boolean  queue = false;
    static String   buffer = "";
    static int      out;
/**
 * Associe une touche du clavier a une action donnee
 */
	static void update()
	{
        if (kb)
        {
            char key = EcranGraphique.getKey();
            if (key == 10)
            {
                Menu.title[out][1] = buffer;
                buffer = "";
                kb = false;
            }
            else if (key == 27)
            {
                kb = false;
                buffer = "";
            }
            else if (key == 8)
                buffer = buffer.substring(0, buffer.length() - 1);
            else if (key > 0)
                buffer += key;
        }
        queuing(Box.ballFocus);
	}
/**
 * Determine la nouvelle direction et la nouvelle intensite d'une boule determinee par un tir manuel	
 * @param ball Boule ciblee par le tir
 */
	static void queuing(Ball ball)
	{
		if(queue && EcranGraphique.getMouseButton() == 2)
			queue = false;

		if(queue && EcranGraphique.getMouseState() == 1 && EcranGraphique.getMouseButton() == 3)
		{
			ball.v.x = (ball.p.x * Window.scale - EcranGraphique.getMouseX()) / Window.scale;
			ball.v.y = -(-ball.p.y * Window.scale + Window.height - Window.sizeBoard - EcranGraphique.getMouseY()) / Window.scale;
			Vector.formePol(ball.v);
			queue = false;
			Box.run = true;
			
		}
	}
	
	
	
}


