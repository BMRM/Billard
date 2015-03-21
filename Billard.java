
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
		double width = 1.27;
		double length = 2.54;
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

	static void evolve(Box box,Ball [] balls, int n, double dt)
	{

		for(int i = 0; i < n; i++)
		{

			balls[i].x = (balls[i].x + dt * balls[i].vitesse.module * balls[i].vitesse.x);
			balls[i].y = (balls[i].y + dt * balls[i].vitesse.module * balls[i].vitesse.y);
			balls[i].vitesse.module *= 0.99;
			chocBox(box, balls[i]);
			chocBalls(balls, n);
		}
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

	static void chocBox(Box box, Ball ball)
	{
		if ((ball.y + ball.rayon > box.width ) || (ball.y - ball.rayon < 0))
		{
			if (ball.y + ball.rayon > box.width )
				ball.y = box.width - ball.rayon;
			else if (ball.y - ball.rayon < 0)
				ball.y = ball.rayon;
			ball.vitesse.y = - ball.vitesse.y;
		}

		if ((ball.x + ball.rayon > box.length ) || (ball.x - ball.rayon < 0))
		{
			if (ball.x + ball.rayon > box.length )
				ball.x = box.length - ball.rayon;
			else if (ball.x - ball.rayon < 0)
				ball.x = ball.rayon;
			ball.vitesse.x = - ball.vitesse.x;
		}
	}

	static void chocBalls(Ball[] balls, int n)
	{
		double mu;
		double cos;
		double sin;
		int a;
		int b;
		for (int i = 0; i < n; i++)
		{
			for (int j = i; j < n; j++)
			{
				if(((Math.sqrt(Math.pow(balls[i].x - balls[j].x, 2) + Math.pow(balls[i].y - balls[j].y, 2)) - balls[i].rayon - balls[j].rayon) <= 0) && i != j)
				{
					mu = balls[i].weight / balls[j].weight;
					cos = (balls[i].y - balls[j].y) / balls[i].rayon + balls[j].rayon;
					sin = (balls[i].x - balls[j].x) / balls[i].rayon + balls[j].rayon;

					balls[i].vitesse.x = (mu - 1 + 2 * Math.pow(cos, 2)) / (mu + 1);
					balls[i].vitesse.y = (2 * sin * cos) / (mu + 1);
					balls[j].vitesse.x = (2 * mu * Math.pow(sin, 2)) / (mu + 1);
					balls[j].vitesse.y = -(2 * mu * sin * cos)/ (mu + 1);
                    if (balls[i].vitesse.module > balls[j].vitesse.module)
                        balls[j].vitesse.module = balls[i].vitesse.module;
                    else
                        balls[i].vitesse.module = balls[j].vitesse.module;

					System.out.println("choc");
				}


			}
		}

	}

// ---------------------------------------------------------------------- Main ----------------------------------------
// --- -----------------------------------------------------------------------------------------------------------------

	public static void main(String[] args)
	{

		//taille de la base du triangle pour un positionnement classique
		int k = 5;

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
			balls[i].vitesse.x = (Math.random() - 0.5) * 10;
			balls[i].vitesse.y = (Math.random() - 0.5) * 10;
            balls[i].vitesse.module = Math.sqrt(Math.pow(balls[i].x, 2) + Math.pow(balls[i].y, 2));
            balls[i].x /= balls[i].vitesse.module;
            balls[i].y /= balls[i].vitesse.module;
            System.out.println(Math.sqrt(Math.pow(balls[i].x, 2) + Math.pow(balls[i].y, 2)));
			balls[i].vitesse.module = 0.4;
			balls[i].weight = 1;

			//Def du tableau de rendu
			renderballs[i] = new RenderBall();
			renderballs[i].rayon = (int)(balls[i].rayon * scale);
			renderballs[i].couleur.r = ((int)(Math.random() * 250));
			renderballs[i].couleur.v = ((int)(Math.random() * 250));
			renderballs[i].couleur.b = ((int)(Math.random() * 250));
		}

		EcranGraphique.init(50,50,length+100, width+100, length + 20, width + 20, "billard");
		iniWin();
		calcPosTriangle(box, balls , k);
		//calcPosLine(box, balls , nline);
		render(renderballs, balls, n);
		EcranGraphique.flush();

		while(true)
		{
			evolve(box, balls, n, dt);
			render(renderballs, balls,n);
			EcranGraphique.wait(5);
		}

	}
}
