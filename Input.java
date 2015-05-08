/**
 * \file Input.java
 * \brief Gestion des interactions utilisateur
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */

/**
 * \class Input
 * \brief Gestion des interactions utilisateur
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
public class Input
{
    boolean  kb = false;///<Flag d'utilisation du clavier
    boolean  queue = false;///<Flag d'utilisation de la queue de billard
    String   buffer = "";
    int      out;
/**
 * \brief Récupère l'interaction utilisateur si active
 * \param box La queue de billard agit sur la boule ciblée
 * \param win format d'affichage et libellé des boutons
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static void update(Input input, Box box, Window win)
	{
        if (input.kb)
        {
            char key = EcranGraphique.getKey();
            if (key == 10)
            {
                win.menu.title[input.out][1] = input.buffer;
                input.buffer = "";
                input.kb = false;
            }
            else if (key == 27)
            {
                input.kb = false;
                input.buffer = "";
            }
            else if (key == 8)
                input.buffer = input.buffer.substring(0, input.buffer.length() - 1);
            else if (key > 0)
                input.buffer += key;
        }
        if (input.queue)
            queuing(input, box, win);
	}
/**
 * \brief Gestion de la queue de billard
 * \param box La queue agit sur la boule ciblée et relance le moteur physique
 * \param win Format d'affichage pour l'échelle de la queue de billard
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static void queuing(Input input, Box box, Window win)
	{
		if (EcranGraphique.getMouseState() == 1 && EcranGraphique.getMouseButton() == 3)
		{
			box.ballFocus.v.x = (box.ballFocus.p.x * win.scale - EcranGraphique.getMouseX()) / win.scale;
			box.ballFocus.v.y = -(-box.ballFocus.p.y * win.scale + win.height - win.sizeBoard - EcranGraphique.getMouseY()) / win.scale;
			Vector.formePol(box.ballFocus.v);
			box.run = true;
		    input.queue = false;
		}
	}
}


