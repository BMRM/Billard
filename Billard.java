/**
 * \file Billard.java
 * \brief Fonction main, instanciation des modules principaux
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */

/**
 * \class Billard
 * \brief Classe principale du programme
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
public class Billard
{
	public static void main(String[] args)
	{
        long    elapsed = 0; //Calcul du temps écoulé
		boolean run = true; //Flag exécution programme
		int		menu = 0; //Etat du menu
		Box box = Box.make(); // Moteur physique
		Window win = Window.make(box); // Moteur graphique
        Input input = new Input();

		while (run)
		{
			menu = Menu.update(win.menu, box, win);
			if (menu == 0)
				Box.posTriangle(box);
			else if (menu == 1)
				Box.posLine(box);
			else if (menu == 2)
			{
				input.queue = true;
				box.run = false;
			}
			else if (menu == 3)
				box.run = (box.run == true) ? false : true;
			else if (menu == 10)
                run = false;
            else if (menu > 3 && menu < 10)
            {
                input.kb = true;
                input.out = menu;
            }
            Input.update(input, box, win);
            Box.update(box);
            if (win.fps > 0) // Si le blocage fps est activé
			    EcranGraphique.wait((int)Math.max(0, 1000 / (double)win.fps - (System.nanoTime() - elapsed) / 1000000));
            win.fps = (int)(1000 / (double)((System.nanoTime() - elapsed) / 1000000)); // Récupère la valeur courante du fps
            elapsed = System.nanoTime(); // Temps écoulé
            Window.update(win, box, input);
			Window.render(win, box, input);
		}
		EcranGraphique.exit();
	}
}


