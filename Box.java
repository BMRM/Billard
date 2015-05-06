/**
 * \file Box.java
 * \brief Moteur physique - Simulation du Billard
 * \author Baptiste Minervini, Romain Mekarni
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * \class Box
 * \brief Moteur physique
 * @author Baptiste Minervini, Romain Mekarni
 */
public class Box
{
    double          dt = 0.05; ///<Pas de temps
    double          rayon = 0.03; ///<Rayon des boules
    double          width = 1.27; ///<Largeur du billard
    double          length = 2.54; ///<Longueur du billard
    int             s = 8; ///<Précision des calculs physiques
    RoundingMode    r = RoundingMode.HALF_UP; ///<Arrondissement à 1 si 0.5
    MathContext     p = new MathContext(s, r); ///<Contexte mathématique d'arrondissement
    int             nbChoc = 0;///<Compteur nombre de choc par dt
    double          vmoy = 0;///<Compteur vitesse moyenne du système
    Ball            ballFocus;///<Boule ciblée pour interaction
    boolean         run = false;///<Flag d'exécution du système physique
    double          friction = 0.01;
    int             baseBalls = 5;///<Taille du triangle de boules

    int     nbBalls;
    Ball    balls[];

    /**
     * \brief Constructeur de Box
     * @author Baptiste Minervini, Romain Mekarni
     */
	static Box make(int nbBalls)
	{
        Box box = new Box();
        box.nbBalls = nbBalls;
		box.balls = new Ball[nbBalls];
        for (int i = 0; i < nbBalls; i++)
        {
            box.balls[i] = new Ball();
            box.balls[i].id = i;
            box.balls[i].r = box.rayon;
            box.balls[i].m = 1;
        }
        box.ballFocus = box.balls[nbBalls - 1];
        posTriangle(box);
    }
/**
 * \brief Fait evoluer le systeme physique de Box.dt
 * @author Romain Mekarni
 */
    static void update(Box box)
    {
        if (box.run)
        {
            double t = 0;
            while (t < box.dt)
                t += update(box, box.dt - t);
        }
    }

/**
 * \brief évolue jusqu'au prochain état avant dt
 * @return temps t du prochain état du système
 * @author Romain Mekarni
 */
	static double	update(Box box, double dt)
	{
		BigDecimal	t = new BigDecimal(dt, box.p);
		BigDecimal	tmp;
		Stack		stack = new Stack();

        Ball bi, bj;
		for (int i = 0; i < box.nbBalls; i++)
		{
            bi = box.balls[i];
			for (int j = i + 1; j < box.nbBalls; j++)
			{
                bj = box.balls[j];
				tmp = Ball.dtChocBalls(box, bi, bj, t); // Calcul du temps tmp du prochain choc entre bi et bj
				if (tmp.compareTo(BigDecimal.ONE.negate()) != 0 && tmp.compareTo(t) <= 0)
				{
					if (tmp.compareTo(t) != 0) // Si tmp < t, on change d'état du système
						Stack.clear(stack); // Donc on réinitialise la pile d'évènement
					Stack.push(stack, 0, bi, bj); // On ajoute l'évènement (le choc) pour une prise en compte plus tard
					t = tmp;
				}
			}
			tmp = Ball.dtChocBox(box, bi, t); // Calcul du temps tmp du prochain choc entre bi et les murs du billard
			if (tmp.compareTo(BigDecimal.ONE.negate()) != 0 && tmp.compareTo(t) <= 0)
			{
				if (tmp.compareTo(t) != 0)
					Stack.clear(stack);
				Stack.push(stack, 1, bi, null);
				t = tmp;
			}
		}
        box.vmoy = 0;
		for (int i = 0; i < box.nbBalls; i++)
        { // On fait évoluer le système vers l'état de choc le plus proche
            evolve(box, box.balls[i], t.doubleValue());
            box.vmoy += box.balls[i].v.m;
        }
		pollEvent(box, stack); // Une fois les boules placées, on peut appliquer tous les chocs mémorisés
		return t.doubleValue();
	}
/**
 * \brief Applique les événements mémorisés dans un changement d'état
 * @author Romain Mekarni
 */
	static void pollEvent(Box box, Stack stack)
	{
		while (!Stack.isEmpty(stack))
		{
			switch (stack.first.event.type)
			{
				case 0 :
					Ball.chocBalls(stack.first.event.b1, stack.first.event.b2);
					break;
				case 1 :
					Ball.chocBox(box, stack.first.event.b1);
					break;
				default :
					break;
			}
            Stack.pull(stack);
            box.nbChoc++;
		}
	}
/**
 * \brief Déplace la boule b d'un temps dt avec une friction
 * @author Baptiste Minervini, Romain Mekarni
 */
	static void evolve(Box box, Ball b, double dt)
	{
		b.p.x += dt * b.v.x;
		b.p.y += dt * b.v.y;
		b.v.x *= 1 - box.friction;
		b.v.y *= 1 - box.friction;
        b.r = box.rayon;
        Vector.formePol(b.v);
	}
/**
 * \brief Positionne les boules en ligne
 * @author Baptiste Minervini
 */
	static void posLine(Box box)
	{
		double k = box.length / (box.nbBalls + 0.01 );
		for (int i = 0; i < box.nbBalls; i++)
		{
			box.balls[i].p.x = 0.1 + k * i;
			box.balls[i].p.y = box.width / 2;
			box.balls[i].v.x = Math.random() - 0.5;
	        box.balls[i].v.y = Math.random() - 0.5;
		}
	}
/**
 * \brief Positionne les boules en triangle
 * @author Baptiste Minervini
 */
	static void posTriangle(Box box)
	{
		double decal = 0;
		int b = 0;
		box.balls[box.nbBalls - 1].p.x = box.length/3;
		box.balls[box.nbBalls - 1].p.y = box.width/2;
        box.balls[box.nbBalls - 1].v.x = 1;
        box.balls[box.nbBalls - 1].v.y = 0;
        Vector.formePol(box.balls[box.nbBalls - 1].v);
		for (int i = 1; i <= box.baseBalls; i++)
		{
			for (int j = 0; j <= i - 1; j++)
			{
				box.balls[b].p.x = (2 * box.length / 3) + decal;
				box.balls[b].p.y = box.width / 2 + (2 * (box.balls[b].r + 0.003) * j) - ((decal)/2);
                box.balls[b].v.x = 0;
                box.balls[b].v.y = 0;
                Vector.formePol(box.balls[b].v);
				b++;
			}
			decal += (2 * box.balls[i].r);
		}
	}
}

