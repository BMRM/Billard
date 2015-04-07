public class Billard
{
	static double dt = 0.1;
	
	static int nbBoules(int k)
	{
		return (k * (k + 1) / 2) + 1;
	}

	public static void main(String[] args)
	{
		int n = nbBoules(5);
		Box box = new Box();
		Window win = new Window();
		
		make(win, n);
		make(box, n);
		
        double t;
		boolean run = true;
		while (run)
		{
			switch (update(win.menu))
			{
				case 0 :
					posTriangle(box.balls, n);
					break;
				case 1 :
					posLine(box.balls, n);
					break;
				case 2 :
					Box.run = false;
					break;
				case 3 :
					Box.run = true;
					break;
				case 4 :
					run = false;
					break;
				default :
					break;
			}
			if (Box.run)
			{
				t = 0;
	            while (t < dt)
				    t += update(balls, n, dt - t);
            }
			render(win);
			EcranGraphique.flush();
			EcranGraphique.wait(10);
		}
		EcranGraphique.exit();
	}
}
