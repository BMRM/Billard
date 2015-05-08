/**
 * \file Window.java
 * \brief Moteur graphique
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */

/**
 * \class Window
 * \brief Moteur graphique
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
public class Window
{
    int             fps = 60;///<Taux de rafraichissement
    int             sizeMenu = 200;///<Taille du menu en largeur
    int             sizeBoard = 200;///<Taille du bandeau en hauteur
	int             scale = 300;///<Echelle de conversion mètre/pixels
	int             width;///<Largeur totale de l'intérieur de la fenêtre
    int             height;///<Hauteur totale de l'intérieur de la fenêtre
    int             nbBalls;///<Nombre de boules
	RenderBall[]	renderBalls;///<Tableau de boules à dessiner
	Menu			menu;///<Menu de l'interface graphique
    Board           board;///<Bandeau moniteur

    /**
     * \brief Constructeur
     * \authors Baptiste Minervini
     * \authors Romain Mekarni
     */
	static Window make(Box box)
	{
        Window win = new Window();
        win.nbBalls = box.nbBalls;
	    win.width = (int)(box.length * win.scale) + win.sizeMenu;
	    win.height = (int)(box.width * win.scale) + 20 + win.sizeBoard;
		win.renderBalls = RenderBall.make(box);
		win.menu = Menu.make(win, box, Rect.make((int)(box.length * win.scale) + 20, 0, win.sizeMenu, win.height));
        win.board = Board.make(Rect.make(0, (int)(box.width * win.scale) + 20, win.width, win.sizeBoard));
		EcranGraphique.init(50 , 50, win.width + 100, win.height + 100, win.width, win.height, "Billard");
        return win;
	}

    /**
     * \brief Rendu de la table de billard
     * \author Baptiste Minervini
     */
	static void renderBox(Window win, Box box)
	{
		EcranGraphique.setColor(0, 10, 0);
		EcranGraphique.fillRect(0, 0, (int)(box.length * win.scale) + 20, win.height);
		EcranGraphique.setColor(0, 50, 0);
		EcranGraphique.fillRect(10, 10, (int)(box.length * win.scale), (int)(box.width * win.scale));
	}

    /**
     * \brief Rendu de l'entrée clavier
     * \author Romain Mekarni
     */
    static void renderInput(Window win, Input input)
    {
        EcranGraphique.setColor(255, 255, 255);
        EcranGraphique.fillRect(0, 50, win.width, 50);
        EcranGraphique.setColor(0, 0, 0);
        EcranGraphique.drawString(10, 80, 3, "> " + input.buffer);
    }

    /**
     * \brief Rendu de la canne de jeu
     * \author Baptiste Minervini
     */
    static void renderQueue(RenderBall rB)
	{
		EcranGraphique.drawLine(rB.x, rB.y, EcranGraphique.getMouseX(), EcranGraphique.getMouseY());
	}

    /**
     * \brief Synchronisation avec le système physique
     * \author Romain Mekarni
     */
    static void update(Window win, Box box, Input input)
    {
         Board.update(win.board, box, win);
         for (int i = 0; i < win.nbBalls; i++)
             RenderBall.update(win, win.renderBalls[i], box.balls[i]);
    }

    /**
     * \brief Rendu de la fenêtre
     * \authors Baptiste Minervini
     * \authors Romain Mekarni
     */
	static void render(Window win, Box box, Input input)
	{
		renderBox(win, box);
		Menu.render(win.menu);
        Board.render(win.board);
        for (int i = 0; i < win.nbBalls; i++)
            RenderBall.render(win.renderBalls[i]);
		if(input.queue)
			renderQueue(win.renderBalls[box.ballFocus.id]);
        if (input.kb)
            renderInput(win, input);
        EcranGraphique.flush();
	}
}

