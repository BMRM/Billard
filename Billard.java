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
					Input.queue = true;
					Box.run = false;
					break;
				case 3 :
					Box.run = (Box.run == true) ? false : true;
					break;
				case 4 :
                    Input.kb = true;
                    Input.out = 4;
					break;
                case 5 :
                    Input.kb = true;
                    Input.out = 5;
                    break;
                case 6 :
                    Input.kb = true;
                    Input.out = 6;
                    break;
                case 7 :
                    Input.kb = true;
                    Input.out = 7;
                    break;
                case 8 :
                    Input.kb = true;
                    Input.out = 8;
                    break;
                case 9 :
                    Input.kb = true;
                    Input.out = 9;
					break;
                case 10 :
                    run = false;
					break;
				default :
					break;
			}
            Input.update();
            Box.update(box);
            if (Window.fps > 0)
			    EcranGraphique.wait((int)Math.max(0, 1000 / (double)Window.fps - (System.nanoTime() - elapsed) / 1000000));
            Window.fps = (int)(1000 / (double)((System.nanoTime() - elapsed) / 1000000));
            elapsed = System.nanoTime();
			Window.render(win, box);
		}
		EcranGraphique.exit();
	}
}


