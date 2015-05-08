/**
 * \file RenderBall.java
 * \brief Rendu des boules
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */

/**
 * \class RenderBall
 * \brief Rendu des boules
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
public class RenderBall
{
	int r;///<Rayon en pixels
	int x;///<Position x
	int y;///<Position y
	Color c = new Color();///<Couleur d'affichage
/**
 * \brief Constructeur
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static RenderBall[] make(Box box)
	{
        RenderBall[] rB = new RenderBall[box.nbBalls];
		for (int i = 0; i < box.nbBalls; i++)
		{
			rB[i] = new RenderBall();
			rB[i].c.r = (int)(Math.random() * 195 + 30);
			rB[i].c.v = (int)(Math.random() * 195 + 30);
			rB[i].c.b = (int)(Math.random() * 195 + 30);
			if (i == box.nbBalls - 1)
			{
				rB[i].c.r = 255;
				rB[i].c.v = 255;
				rB[i].c.b = 255;
			}
		}
        return rB;
	}

    /**
     * \brief Synchronisation graphique à partir du moteur physique
     * \authors Baptiste Minervini
     * \authors Romain Mekarni
     */
    static void update(Window win, RenderBall rB, Ball b)
    {
		rB.x = (int)(b.p.x * win.scale) + 10;
		rB.y = (int)(-b.p.y * win.scale) - 10 + win.height - win.sizeBoard;
		rB.r = (int)(b.r * win.scale);
    }
/**
 * \brief Rendu d'une boule
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
	static void render(RenderBall rB)
	{
		EcranGraphique.setColor(rB.c.r, rB.c.v, rB.c.b);
		EcranGraphique.fillCircle(rB.x, rB.y, rB.r);
	}
}

