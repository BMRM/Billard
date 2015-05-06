public class Billard
{
    static int k = 5;

	public static void main(String[] args)
	{
		int n = (k * (k + 1) / 2) + 1;

		Box box = Box.make(box, n);
		Window win = Window.make(win, n);

        long    elapsed = 0;
		boolean run = true;
		int		menu = 0;
		while (run)
		{
			menu = Menu.update(win.menu, box);
			if (menu == 0)
				Box.posTriangle(box.balls, k);
			else if (menu == 1)
				Box.posLine(box.balls, n);
			else if (menu == 2)
			{
				Input.queue = true;
				Box.run = false;
			}
			else if (menu == 3)
				Box.run = (Box.run == true) ? false : true;
			else if (menu == 10)
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


