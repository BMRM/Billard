public class Billard
{
	static double dt = 0.1;
    static int k = 1;

	public static void main(String[] args)
	{
		int n = (k * (k + 1) / 2) + 1;
		Box box = new Box();
		Window win = new Window();

		Box.make(box, n);
		Window.make(win, n);

        double t;
		boolean run = true;
		while (run)
		{
			switch (Menu.update(win.menu))
			{
				case 0 :
					Box.posTriangle(box.balls, k);
                    box.run = false;
					break;
				case 1 :
					Box.posLine(box.balls, n);
                    box.run = false;
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


