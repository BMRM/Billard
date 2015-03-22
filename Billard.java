
public class Billard
{
// ---- Definition ----

	static int width = 254;
	static int length = 508;
	static int scale = 200;
	static double dt = 0.1;

// ---- structure -----

	static class Box
	{
		static double width = 1.27;
		static double length = 2.54;
	}

	static class Vector
	{
		double x, y;
		double module;
	}

	static class Color
	{
		int r, v, b;
	}

	static class Ball
	{
		double rayon;
		double x, y;
		Vector vitesse = new Vector();
		double weight;
	}

	static class RenderBall
	{
		int rayon;
		int x, y;
		Color couleur = new Color();
	}

// ---- Sousprog -----

	static void iniWin()
	{
		EcranGraphique.setColor(0, 10, 0);
		EcranGraphique.fillRect(0, 0, length + 20, width + 20);
		EcranGraphique.setColor(0, 50, 0);
		EcranGraphique.fillRect(10, 10, length, width);
	}

	static void calcPosLine(Box box, Ball [] balls, int n)
	{
		for (int i = 0; i <= n - 1; i++)
		{
			balls[i].x = 0.3 + (box.length/(n+1)) * i;
			balls[i].y = box.width/2;
		}
	}

	static void calcPosTriangle(Box box, Ball [] balls , int k)
	{
		double decal = 0;
		int b = 0;
		int n = (k * (k+1)/2) + 1;
		balls[n - 1].x = box.length/3;
		balls[n - 1].y = box.width/2;
		for (int i = 1; i <= k; i++)
		{

			for (int j = 0; j <= i - 1; j++)
			{
				balls[b].x = (2 * box.length / 3) + decal;
				balls[b].y = (box.width/2) + (2 * (balls[b].rayon + 0.01) * j) - ((decal)/2);
				b++;
			}

			decal += (2 * balls[i].rayon);
		}
	}

    static void formeTrigo(Vector v)
    {
        v.module = Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
        if (v.module != 0)
        {
            v.x /= v.module;
            v.y /= v.module;
        }
    }

    static void evolve(Ball ball, double dt)
    {
        ball.x += dt * ball.vitesse.module * ball.vitesse.x;
        ball.y += dt * ball.vitesse.module * ball.vitesse.y;
        //ball.vitesse.module *= 0.99;
    }

	static void evolve(Box box,Ball [] balls, int n, double dt)
	{
		for (int i = 0; i < n; i++)
            evolve(balls[i], dt);
	}

	static void render(RenderBall[]renderballs, Ball []balls, int n)
	{
		iniWin();
		for (int i = 0; i < n; i++)
		{
			renderballs[i].x = (int)(balls[i].x * scale) + 10;
			renderballs[i].y = (int)(balls[i].y * scale) + 10;
			EcranGraphique.setColor(renderballs[i].couleur.r, renderballs[i].couleur.v, renderballs[i].couleur.b);
			EcranGraphique.fillCircle(renderballs[i].x, renderballs[i].y, renderballs[i].rayon);
		}
		EcranGraphique.flush();
	}

	static boolean chocBox(Ball ball, double dt)
	{
        double t;
        double vx = ball.vitesse.module * ball.vitesse.x;
        double vy = ball.vitesse.module * ball.vitesse.y;

        t = (ball.rayon - ball.x) / vx;
        if (t >= 0 && t < dt)
        {
            evolve(ball, t);
            ball.vitesse.x *= -1;
            evolve(ball, dt - t);
            return true;
        }
        t = (Box.length - ball.rayon - ball.x) / vx;
        if (t >= 0 && t < dt)
        {
            evolve(ball, t);
            ball.vitesse.x *= -1;
            evolve(ball, dt - t);
            return true;
        }
        t = (ball.rayon - ball.y) / vy;
        if (t >= 0 && t < dt)
        {
            evolve(ball, t);
            ball.vitesse.y *= -1;
            evolve(ball, dt - t);
            return true;
        }
        t = (box.width - ball.rayon - ball.y) / vy;
        if (t >= 0 && t < dt)
        {
            evolve(ball, t);
            ball.vitesse.y *= -1;
            evolve(ball, dt - t);
            return true;
        }
        return false;
	}

    static double evolve(Ball[] balls, int n, double dt)
    {
        double t = dt;
        double tmp;
        int k = 0;
        int l = 0;

        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                tmp = dtChocBall(balls[i], balls[k], dt);
                if (tmp != -1 && tmp < t)
                {
                    // Get the first choc
                    t = tmp;
                    k = i;
                    l = j;
                }
            }
            tmp = dtChocBox(ball[i], t);
            if (tmp != -1 && tmp < t)
            {
                k = i;
                t = tmp;
                l = 0;
            }
        }
        if (t < dt)
        {
            evolve(balls, t);
            if (l > 0)
            {
                (balls, n, t);
                chocElastic(balls[k], balls[j]);
            }
            else
                chocBox(balls[k]);
            return t;
        }
        else
            evolve(balls, n, dt);
        return dt;
    }

    static void chocElastic(Ball b1, Ball b2)
    {
        double mu = b1.weight / b2.weight;
        double cos = (b1.x - b2.x) / (b1.rayon + b2.rayon);
        double sin = (b1.y - b2.y) / (b1.rayon + b2.rayon);

        b1.vitesse.x = b1.vitesse.module * (mu - 1 + 2 * Math.pow(cos, 2)) / (mu + 1);
        b1.vitesse.y = b1.vitesse.module * (2 * sin * cos) / (mu + 1);
        b2.vitesse.x = b2.vitesse.module * (2 * mu * Math.pow(sin, 2)) / (mu + 1);
        b2.vitesse.y = b2.vitesse.module * (-2 * mu * sin * cos) / (mu + 1);
        formeTrigo(b1.vitesse);
        formeTrigo(b2.vitesse);
    }

    static double dtChocBall(Ball b1, Ball b2, double dt)
    {
        // Choc equation : a * dt * dt + b * dt + c = 0
        double t;
        Vector v1 = b1.vitesse;
        Vector v2 = b2.vitesse;
        double dvx = v1.module * v1.x - v2.module * v2.x;
        double dvy = v1.module * v1.y - v2.module * v2.y;
        double b = 2 * dvx * (b1.x - b2.x) + 2 * dvy * (b1.y - b2.y);
        double a = Math.pow(dvx, 2) + Math.pow(dvy, 2);
        double c = Math.pow(b1.x - b2.x, 2) + Math.pow(b1.y - b2.y, 2) - Math.pow(b1.rayon + b2.rayon, 2);
        double delta = Math.pow(b, 2) - 4 * a * c;

        if (delta > 0)
            t = Math.min((-b + Math.sqrt(delta)) / (2 * a), (-b - Math.sqrt(delta)) / (2 * a));
        else if (delta == 0)
            t = (-b) / (2 * a);
        else
            return -1;
        if (t < 0 || t >= dt)
            return -1;
        return t;
    }

// ---------------------------------------------------------------------- Main ----------------------------------------
// --- -----------------------------------------------------------------------------------------------------------------

	public static void main(String[] args)
	{

		//taille de la base du triangle pour un positionnement classique
		int k = 1;

		//nombre de boule totale pour positionnement classique
		int n = (k * (k+1)/2) + 1;

		//nombre de boule pour positionnement en ligne:
		//int nline = 4;

		//inititalisation des tableau de boule
		Ball [] balls = new Ball[n + 1];
		RenderBall [] renderballs = new RenderBall[n + 1];
		Box box = new Box();

		for(int i = 0; i < n ; i++)
		{
			//Def du tableau de position
			balls[i] = new Ball();
			balls[i].rayon = 0.03;
			balls[i].vitesse.x = 0;// (Math.random() - 0.5) * 10;
			balls[i].vitesse.y = 0; //(Math.random() - 0.5) * 10;
            balls[i].vitesse.module = Math.sqrt(Math.pow(balls[i].x, 2) + Math.pow(balls[i].y, 2));
            balls[i].x /= balls[i].vitesse.module;
            balls[i].y /= balls[i].vitesse.module;
			balls[i].vitesse.module = 0.03;
			balls[i].weight = 1;

			//Def du tableau de rendu
			renderballs[i] = new RenderBall();
			renderballs[i].rayon = (int)(balls[i].rayon * scale);
			renderballs[i].couleur.r = ((int)(Math.random() * 250));
			renderballs[i].couleur.v = ((int)(Math.random() * 250));
			renderballs[i].couleur.b = ((int)(Math.random() * 250));

            if (i == n - 1)
            {
                balls[i].vitesse.x = 1;
                balls[i].vitesse.y = 0;
            }
		}

		EcranGraphique.init(50,50,length+100, width+100, length + 20, width + 20, "billard");
		iniWin();
		calcPosTriangle(box, balls , k);
		//calcPosLine(box, balls , nline);
		render(renderballs, balls, n);
		EcranGraphique.flush();

		while(true)
		{
            t = 0;
            while (t < dt)
			    t = evolve(box, balls, n, dt);
			render(renderballs, balls,n);
			EcranGraphique.wait(5);
		}

	}
}
