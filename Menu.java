/**
 * \file Menu.java
 * \brief Menu interactif à disposition de l'utilisateur
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */

import java.math.MathContext;

/**
 * \class Menu
 * \brief menu interactif à disposition de l'utilisateur
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
public class Menu
{
	int nbButtons = 11;///<Nombre de Button du Menu
	String title[][] = {
        {"Billard classique", ""},
        {"Boules en ligne", ""},
        {"Jeu Manuel", ""},
        {"Demarrer/Pause", ""},
        {"dt : ", ""},
        {"Precision : ", ""},
        {"FPS : ", ""},
        {"Rayon : ", ""},
        {"Boule : ", ""},
        {"Frottement : ", ""},
        {"Quitter", ""}};///<Libellés des boutons
	Button	buttons[];///<Tableau de boutons
	Rect	size;///<Taille et position du menu dans Window
/**
 * \brief Constructeur
 * \param size Taille et position du Menu
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static Menu make(Window win, Box box, Rect size)
	{
        Menu menu = new Menu();
		menu.size = size;
        menu.title[4][1] = String.valueOf(box.dt);
        menu.title[5][1] = String.valueOf(box.s);
        menu.title[6][1] = String.valueOf(win.fps);
        menu.title[7][1] = String.valueOf(box.rayon);
        menu.title[8][1] = String.valueOf(box.ballFocus.id);
        menu.title[9][1] = String.valueOf(box.friction);
		menu.buttons = new Button[menu.nbButtons];
		for (int i = 0; i < menu.nbButtons; i++)
		{
			Rect sizeButton = Rect.make(menu.size.x, i * (menu.size.h / menu.nbButtons), menu.size.w, (menu.size.h / menu.nbButtons));
			menu.buttons[i] = Button.make(menu.title[i], sizeButton);
		}
        return menu;
	}
/**
 * \brief Actualise les libellés des boutons et prise en compte des clics
 * \return id du bouton cliqué, -1 si aucun
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static int update(Menu menu, Box box, Window win)
	{
        box.dt = Double.valueOf(menu.buttons[4].title[1]);
        box.s = Integer.valueOf(menu.buttons[5].title[1]);
        box.p = new MathContext(box.s, box.r);
        win.fps = Integer.valueOf(menu.buttons[6].title[1]);
        box.rayon = Double.valueOf(menu.buttons[7].title[1]);
        box.ballFocus = box.balls[Integer.valueOf(menu.buttons[8].title[1])];
        box.friction = Double.valueOf(menu.buttons[9].title[1]);
		for (int i = 0; i < menu.nbButtons; i++)
		{
			if (Button.update(menu.buttons[i]))
				return i;
		}
		return -1;
	}
/**
 * \brief Rendu du menu dans Window
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static void render(Menu menu)
	{
        EcranGraphique.setColor(0, 0, 0);
        Rect.fill(menu.size);
		for (int i = 0; i < menu.nbButtons; i++)
			Button.render(menu.buttons[i]);
	}
}

