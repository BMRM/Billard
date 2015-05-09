/**
 * \file Button.java
 * \brief Boutton du Menu
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */

/**
 * \class Button
 * \brief Boutton du Menu
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
public class Button
{
	int b = 3;///<Taille bordure
	String	title[];///<Libellés bouton
    Color   design[];///<Couleurs du boutons
	Rect	size;///<Taille et position
	Rect	border;///<Format de la bordure
    int     state = 0;///<Etats du bouton
/**
 * \brief Constructeur
 * \param title Tableau de chaines contenant le libellé du bouton
 * \param size Taille et position du bouton
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static Button make(String[] title, Rect size)
	{
        Button button = new Button();
		button.title = title;
		button.size = Rect.make(size.x + button.b, size.y + button.b, size.w - 2 * button.b, size.h - 2 * button.b);
        button.design = new Color[2];
        button.design[0] = Color.make(60, 60, 60);
        button.design[1] = Color.make(80, 80, 80);
		button.border = size;
        return button;
	}
/**
 * \brief Détermine l'état du bouton
 * \return Clique enfoncé sur bouton = true
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static boolean update(Button b)
	{
        if (b.state == 1
        && EcranGraphique.getMouseState() == 0)
            b.state = 0;
		if (Rect.isIn(b.size, EcranGraphique.getMouseX(), EcranGraphique.getMouseY())
		&& EcranGraphique.getMouseState() == 1
		&& EcranGraphique.getMouseButton() == 1
		&& b.state == 0)
        {
			b.state = 1;
            return true;
        }
        return false;
	}
/**
 * \brief Rendu dans Window
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static void render(Button b)
	{
		Color.set(b.design[0]);
		Rect.fill(b.border);
		Color.set(b.design[1]);
		Rect.fill(b.size);
		EcranGraphique.setColor(255, 255, 255);
		EcranGraphique.drawString(b.size.x + 10, b.size.y + b.size.h - 10, 3, b.title[0] + b.title[1]);
	}
}

