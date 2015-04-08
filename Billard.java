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

		Window.make(win, n);
		Box.make(box, n);

        double t;
		boolean run = true;
		while (run)
		{
			switch (Menu.update(win.menu))
			{
				case 0 :
					Box.posTriangle(box.balls, 5);
					break;
				case 1 :
					Box.posLine(box.balls, n);
					break;
				case 2 :
					box.run = false;
					break;
				case 3 :
					box.run = true;
					break;
				case 4 :
					run = false;
					break;
				default :
					break;
			}
			if (box.run)
			{
				t = 0;
	            while (t < dt)
				    t += Box.update(box, dt - t);
            }
			Window.render(win, box);
			EcranGraphique.flush();
			EcranGraphique.wait(10);
		}
		EcranGraphique.exit();
	}
}


