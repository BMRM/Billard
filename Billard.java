/**
 * \file Billard.java
 * \brief Fonction main, instanciation des modules principaux
 * \author Baptiste Minervini, Romain Mekarni
 */

/**
 * \class Billard
 * \brief Classe principale du programme
 * \author Baptiste Minervini, Romain Mekarni
 */
public class Billard
{
	public static void main(String[] args)
	{
        int baseBoules = 5; //Taille de la base du triangle des boules
		int nbBoules = (baseBoules * (baseBoules + 1) / 2) + 1;
        long    elapsed = 0; //Calcul du temps écoulé
		boolean run = true; //Flag exécution programme
		int		menu = 0; //Etat du menu
		Box box = Box.make(baseBoules, nbBoules); // Moteur physique
		Window win = Window.make(nbBoules); // Moteur graphique

		while (run)
		{
			menu = Menu.update(win.menu, box);
			if (menu == 0)
				Box.posTriangle(box.balls, baseBoules);
			else if (menu == 1)
				Box.posLine(box.balls, nbBoules);
			else if (menu == 2)
			{
				Input.queue = true;
				box.run = false;
			}
			else if (menu == 3)
				box.run = (Box.run == true) ? false : true;
			else if (menu == 10)
                run = false;
            else if (menu > 3 && menu < 10)
            {
                Input.kb = true;
                Input.out = menu;
            }
            Input.update();
            Box.update(box);
            if (Window.fps > 0) // Si le blocage fps est activé
			    EcranGraphique.wait((int)Math.max(0, 1000 / (double)Window.fps - (System.nanoTime() - elapsed) / 1000000));
            Window.fps = (int)(1000 / (double)((System.nanoTime() - elapsed) / 1000000)); // Récupère la valeur courante du fps
            elapsed = System.nanoTime(); // Temps écoulé
			Window.render(win, box);
		}
		EcranGraphique.exit();
	}
}


