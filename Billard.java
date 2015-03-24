
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
		double x;
        double y;
	}

	static class Color
	{
		int r, v, b;
	}

	static class Ball
	{
		double r;
		Vector p = new Vector();
		Vector v = new Vector();
		double m;
	}

	static class RenderBall
	{
		int r;
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

	static void calcPosLine(Ball [] balls, int n)
	{
		for (int i = 0; i <= n - 1; i++)
		{
			balls[i].p.x = (Box.length/(n+1)) * i;
			balls[i].p.y = Box.width/2;
		}
	}

	static void calcPosTriangle(Ball [] balls , int k)
	{
		double decal = 0;
		int b = 0;
		int n = (k * (k+1)/2) + 1;
		balls[n - 1].p.x = Box.length/3;
		balls[n - 1].p.y = Box.width/2 - 0.02;
		for (int i = 1; i <= k; i++)
		{

			for (int j = 0; j <= i - 1; j++)
			{
				balls[b].p.x = (2 * Box.length / 3) + decal;
				balls[b].p.y = (Box.width/2) + (2 * (balls[b].r) * j) - ((decal)/2);
				b++;
			}

			decal += (2 * balls[i].r);
		}
	}

    static double module(Vector v)
    {
        return Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
    }

    static Vector diff(Vector v1, Vector v2)
    {
        Vector r = new Vector();
        r.x = v1.x - v2.x;
        r.y = v1.y - v2.y;
        return r;
    }

    static double tan(double x)
    {
        if (x < -Math.PI / 2)
            x = -Math.PI / 2;
        else if (x > Math.PI / 2)
            x = Math.PI / 2;
        return Math.tan(x);
    }

	static void render(RenderBall[]renderballs, Ball []balls, int n)
	{
		iniWin();
		for (int i = 0; i < n; i++)
		{
			renderballs[i].x = (int)(balls[i].p.x * scale) + 10;
			renderballs[i].y = (int)(-balls[i].p.y * scale) + 10 + width;
			EcranGraphique.setColor(renderballs[i].couleur.r, renderballs[i].couleur.v, renderballs[i].couleur.b);
			EcranGraphique.fillCircle(renderballs[i].x, renderballs[i].y, renderballs[i].r);
		}
		EcranGraphique.flush();
	}

    static double update(Ball[] balls, int n, double dt)
    {
        double t = dt;
        double tmp;
        int k = 0;
        int l = 0;

        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                tmp = dtChocBalls(balls[i], balls[j], dt);
                if (tmp != -1 && tmp < t)
                {
                    // Get the first choc
                    t = tmp;
                    k = i;
                    l = j;
                }
            }
            tmp = dtChocBox(balls[i], dt);
            if (tmp != -1 && tmp < t)
            {
                k = i;
                t = tmp;
                l = 0;
            }
        }
        if (t < dt)
        {
            evolve(balls, n, t);
            if (l > 0)
                chocBalls(balls[k], balls[l]);
            else
                chocBox(balls[k]);
            evolve(balls, n, 0.00001);
            return t;
        }
        else
            evolve(balls, n, dt);
        return dt;
    }

    static void evolve(Ball b, double dt)
    {
        b.p.x += dt * b.v.x;
        b.p.y += dt * b.v.y;
        //ball.v.module *= 0.99;
    }

    static void evolve(Ball [] balls, int n, double dt)
    {
        for (int i = 0; i < n; i++)
            evolve(balls[i], dt);
    }

    static double dtChocBox(Ball b, double dt)
    {
        double t = dt;
        double tmp;

        tmp = (b.r - b.p.x) / b.v.x;
        if (tmp >= 0 && tmp < t)
            t = tmp;
        tmp = (Box.length - b.r - b.p.x) / b.v.x;
        if (tmp >= 0 && tmp < t)
            t = tmp;
        tmp = (b.r - b.p.y) / b.v.y;
        if (tmp >= 0 && tmp < t)
            t = tmp;
        tmp = (Box.width - b.r - b.p.y) / b.v.y;
        if (tmp >= 0 && tmp < t)
            t = tmp;
        if (t < dt)
            return t;
        return -1;
    }

    static double dtChocBalls(Ball b1, Ball b2, double dt)
    {
        // Choc equation : a * dt * dt + b * dt + c = 0
        double t;
        double b = 2 * (b1.v.x - b2.v.x) * (b1.p.x - b2.p.x) + 2 * (b1.v.y - b2.v.y) * (b1.p.y - b2.p.y);
        double a = Math.pow(b1.v.x - b2.v.x, 2) + Math.pow(b1.v.y - b2.v.y, 2);
        double c = Math.pow(b1.p.x - b2.p.x, 2) + Math.pow(b1.p.y - b2.p.y, 2) - Math.pow(b1.r + b2.r, 2);
        double delta = Math.pow(b, 2) - 4 * a * c;

        if (delta > 0)
            t = Math.min((-b + Math.sqrt(delta)) / (2 * a), (-b - Math.sqrt(delta)) / (2 * a));
        else if (delta == 0)
            t = (-b) / (2 * a);
        else
            return -1;
        if (t >= 0 && t < dt)
            return t;
        return -1;
    }

    static void chocBox(Ball b)
    {
        Ecran.afficher("ChocBox\n");
        if (b.p.x - b.r == 0)
            b.v.x *= -1;
        else if (b.p.x + b.r == Box.length)
            b.v.x *= -1;
        else if (b.p.y - b.r == 0)
            b.v.y *= -1;
        else if (b.p.y + b.r == Box.width)
            b.v.y *= -1;
    }

    static void chocBalls(Ball b1, Ball b2)
    {
        Ecran.afficher("ChocBall\n");
        if (module(b1.v) < module(b2.v))
        {
            Ball tmp = b1;
            b1 = b2;
            b2 = tmp;
        }
        Ecran.afficher("avant -> v1.x : ", b1.v.x, ", v2.x : ", b2.v.x, "\n");

        double v1 = module(b1.v);
        double v2 = module(b2.v);
        double m1 = b1.m;
        double m2 = b2.m;

        double alpha = Math.atan((b1.p.y - b2.p.y) / (b1.p.x - b2.p.x)) - Math.PI / 2;
        Ecran.afficher("tan a = ", (b1.p.y - b2.p.y) / (b1.p.x - b2.p.x), "\n");
        double a1 = (b1.v.x == 0) ? -alpha : Math.atan(b1.v.y / b1.v.x) - alpha;
        double a2 = (b2.v.x == 0) ? -alpha : Math.atan(b2.v.y / b2.v.x) - alpha;

        double th1 = (b1.v.x == 0) ? Math.PI / 2 :
        Math.atan((m1 - m2) / (m1 + m2) * Math.tan(a1) + ((2 * m2) / (m1 + m2)) * (v2 / v1) * (Math.sin(a2) / Math.cos(a1)));
        double th2 = (b2.v.x == 0) ? Math.PI / 2 :
        Math.atan((b2.m - b1.m) / (b1.m + b2.m) * Math.tan(a2) + ((2 * b1.m) / (b1.m + b2.m)) * (v1 / v2) * (Math.sin(a1) / Math.cos(a2)));

        double newV1 = Math.sqrt(Math.pow((m1 - m2) / (m1 + m2) * (v1 * Math.sin(a1)) + (2 * m2) / (m1 + m2) * (v2 * Math.sin(a2)), 2) + Math.pow(v1 * Math.cos(a1), 2));
        double newV2 = Math.sqrt(Math.pow((m2 - m1) / (m1 + m2) * (v2 * Math.sin(a2)) + (2 * m1) / (m1 + m2) * (v1 * Math.sin(a1)), 2) + Math.pow(v2 * Math.cos(a2), 2));
        //double v1 = Math.sqrt(Math.pow(b2.v.y, 2) + Math.pow(b1.v.x, 2));
        //double v2 = Math.sqrt(Math.pow(b1.v.y, 2) + Math.pow(b2.v.x, 2));

        Ecran.afficher("alpha : ", alpha, "\n");
        Ecran.afficher("a : ", a1, "  ", a2, "\n");
        Ecran.afficher("th : ", th1, "  ", th2, "\n");
        Ecran.afficher("new v1, v2 : ", newV1, "  ", newV2, "\n");

        b1.v.x = newV1 * Math.cos(th1 + alpha + Math.PI);
        b1.v.y = newV1 * Math.sin(th1 + alpha + Math.PI);
        Ecran.afficher("th - alpha : ", th1 + alpha + Math.PI, "  ", th2 + alpha, "\n");
        b2.v.x = newV2 * Math.cos(th2 + alpha);
        b2.v.y = newV2 * Math.sin(th2 + alpha);
        Ecran.afficher("---- cos : ", Math.cos(th2 + alpha), "  sin : ", Math.sin(th2 + alpha), "\n");



        /*double vgx = (b1.m * b1.v.x + b2.m * b2.v.x) / (b1.m + b2.m);
        double vgy = (b1.m * b1.v.y + b2.m * b2.v.y) / (b1.m + b2.m);i

       Ecran.afficher("centre gravite v : ", 2 * vgx - b2.v.x, "\n");
        b1.v.x = (2 * vgx - module(b1.v)) * ;
        b1.v.y = 2 * vgy - b1.v.y;
        b2.v.x = 2 * vgx - b2.v.x;
        b2.v.y = 2 * vgy - b2.v.y;*/

        /*double mu = b1.m / b2.m;
        double cos = (b1.x - b2.x) / (b1.r + b2.r);
        double sin = (b1.y - b2.y) / (b1.r + b2.r);
        EcranGraphique.wait(1000);
        b2.v.x = b1.v.module * (mu - 1 + 2 * Math.pow(cos, 2)) / (mu + 1);
        b2.v.y = b1.v.module * (2 * sin * cos) / (mu + 1);
        b1.v.x = b1.v.module * (2 * mu * Math.pow(sin, 2)) / (mu + 1);
        b1.v.y = b1.v.module * (-2 * mu * sin * cos) / (mu + 1);*/
        Ecran.afficher("apres -> v1.x : ", b1.v.x, ", v2.x : ", b2.v.x, "\n");
        Ecran.afficher("apres -> v1.y : ", b1.v.y, ", v2.y : ", b2.v.y, "\n");
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
		//int n = 4;

		//inititalisation des tableau de boule
		Ball [] balls = new Ball[n + 1];
		RenderBall [] renderballs = new RenderBall[n + 1];

		for(int i = 0; i < n ; i++)
		{
			//Def du tableau de position
			balls[i] = new Ball();
			balls[i].r = 0.03;
			balls[i].v.x = 0;//(Math.random() - 0.5) * 0.001;
			balls[i].v.y = 0;//(Math.random() - 0.5) * 0.001;
			balls[i].m = 1;

			//Def du tableau de rendu
			renderballs[i] = new RenderBall();
			renderballs[i].r = (int)(balls[i].r * scale);
			renderballs[i].couleur.r = ((int)(Math.random() * 250));
			renderballs[i].couleur.v = ((int)(Math.random() * 250));
			renderballs[i].couleur.b = ((int)(Math.random() * 250));

            if (i == n - 1)
            {
                balls[i].v.x = 0.05;
                balls[i].v.y = 0;
            }
		}

		EcranGraphique.init(50,50,length+100, width+100, length + 20, width + 20, "billard");
		iniWin();
		calcPosTriangle(balls , k);
		//calcPosLine(balls , n);
		render(renderballs, balls, n);
		EcranGraphique.flush();

        double t;
		while(true)
		{
            t = 0;
            while (t < dt)
			    t += update(balls, n, dt - t);
			render(renderballs, balls,n);
			EcranGraphique.wait(10);
		}

	}
}
