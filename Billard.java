public class Billard
{
    static int k = 5;

	public static void main(String[] args)
	{
		int n = (k * (k + 1) / 2) + 1;
		Box box = new Box();
		Window win = new Window();

		Box.make(box, n);
		Window.make(win, n);

        long    elapsed = 0;
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
                    Input.kb = true;
					break;
                case 4 :
                    run = false;
					break;
				default :
					break;
			}
            Input.update();
            Box.update(box);
			Window.render(win, box);
			EcranGraphique.wait((int)Math.max(0, 1000 / 60.0 - (System.nanoTime() - elapsed) / 1000000));
            Window.fps = (int)(1000 / (double)((System.nanoTime() - elapsed) / 1000000));
            elapsed = System.nanoTime();
		}
		EcranGraphique.exit();
	}
}


