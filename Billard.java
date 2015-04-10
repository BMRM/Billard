public class Billard
{
	static double dt = 0.05;
    static int k = 5;

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
			switch (Menu.update(win.menu, box))
			{
				case 0 :
					Box.posTriangle(box.balls, k);
					break;
				case 1 :
					Box.posLine(box.balls, n);
					break;
				case 2 :
					box.run = (box.run == true) ? false : true;
					break;
				case 3 :
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


