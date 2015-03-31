import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Billard
{
// ---- Definition ----

	static int width = 254;
	static int length = 508;
	static BigDecimal scale = new BigDecimal(200, Box.p);

// ---- structure -----

	static class Box
	{
        static int          s = 5; // precision scale
        static RoundingMode r = RoundingMode.HALF_UP;
        static MathContext  p = new MathContext(s, r);
	    static BigDecimal   width = new BigDecimal(1.27, p);
	    static BigDecimal   length = new BigDecimal(2.54, p);
	    static BigDecimal   dt = new BigDecimal(0.1, p);
	}

	static class Vector
	{
	    BigDecimal x;
        BigDecimal y;
	}

	static class Color
	{
	    int r, v, b;
	}

    static class Ball
    {
        int         id;
	    Vector      p = new Vector();
	    Vector      v = new Vector();
	    BigDecimal  r;
	    BigDecimal  m;
        boolean     choc = false;
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
			balls[i].p.x = multiply((n + 1) * i, Box.length);
			balls[i].p.y = multiply((double)1/2, Box.width);
		}
	}

	static void calcPosTriangle(Ball [] balls , int k)
	{
		BigDecimal decal = BigDecimal.ZERO;
		int b = 0;
		int n = (k * (k+1)/2) + 1;
		balls[n - 1].p.x = multiply((double)1/2, Box.length);
		balls[n - 1].p.y = multiply((double)1/2, Box.width);//.add(new BigDecimal(0.03, Box.p));
		for (int i = 1; i <= k; i++)
		{
			for (int j = 0; j <= i - 1; j++)
			{
				balls[b].p.x = multiply((double)2/3, Box.length).add(decal);
				balls[b].p.y = multiply((double)1/2, Box.width).add(multiply(2 * j, balls[b].r)).subtract(multiply((double)1/2, decal)).add(new BigDecimal(0.1 * j, Box.p));
                b++;
			}

			decal = decal.add(multiply(2, balls[i].r)).add(new BigDecimal(0.01, Box.p));
		}
	}

    static BigDecimal multiply(double a, BigDecimal b)
    {
        return new BigDecimal(a, Box.p).multiply(b);
    }

    static BigDecimal sqrt(BigDecimal n)
    {
        return new BigDecimal(Math.sqrt(n.doubleValue()), Box.p);
    }


    static BigDecimal module(Vector v)
    {
        return sqrt(v.x.pow(2, Box.p).add(v.y.pow(2, Box.p)));
    }

    static Vector diff(Vector v1, Vector v2)
    {
        Vector r = new Vector();
        r.x = v1.x.subtract(v2.x);
        r.y = v1.y.subtract(v2.y);
        return r;
    }

    static BigDecimal atan(BigDecimal x)
    {
        return new BigDecimal(Math.atan(x.doubleValue()), Box.p);
    }

    static BigDecimal atanDiv(BigDecimal a, BigDecimal b)
    {
        if (b.compareTo(BigDecimal.ZERO) == 0)
            return multiply((double)(a.signum()), halfPI());
        else
            return atan(a.divide(b, Box.p));
    }

    static BigDecimal tan(BigDecimal x)
    {
        return new BigDecimal(Math.tan(x.doubleValue()), Box.p);
    }

    static BigDecimal cos(BigDecimal x)
    {
        return new BigDecimal(Math.cos(x.doubleValue()), Box.p);
    }

    static BigDecimal sin(BigDecimal x)
    {
        return new BigDecimal(Math.sin(x.doubleValue()), Box.p);
    }

    static BigDecimal halfPI()
    {
        return new BigDecimal(Math.PI / 2, Box.p);
    }

	static void render(RenderBall[]renderballs, Ball []balls, int n)
	{
		iniWin();
		for (int i = 0; i < n; i++)
		{
			renderballs[i].x = balls[i].p.x.multiply(scale).intValue() + 10;
			renderballs[i].y = balls[i].p.y.multiply(scale).negate().intValue() + 10 + width;
			EcranGraphique.setColor(renderballs[i].couleur.r, renderballs[i].couleur.v, renderballs[i].couleur.b);
			EcranGraphique.fillCircle(renderballs[i].x, renderballs[i].y, renderballs[i].r);
		}
		EcranGraphique.flush();
	}

    static BigDecimal update(Ball[] balls, int n, BigDecimal dt)
    {
        BigDecimal  t = new BigDecimal(dt.doubleValue(), Box.p);
        BigDecimal  tmp = new BigDecimal(0, Box.p);

        //if (dt.compareTo(new BigDecimal(0.1, Box.p)) < 0)
        //    Ecran.afficher("update : dt = ", dt, "\n");
        for (int i = 0; i < n; i++)
        {
            if (balls[i].choc == false)
            {
                for (int j = i + 1; j < n; j++)
                {
                    //if (module(diff(balls[i].p, balls[j].p)).compareTo(balls[i].r.add(balls[j].r)) < 0)
                    //    Ecran.afficher(t, "  ", module(diff(balls[i].p, balls[j].p)), "\n");
                    tmp = dtChocBalls(balls[i], balls[j], dt).setScale(Box.s, Box.r);
                    //if (dt.compareTo(new BigDecimal(0.1, Box.p)) < 0 && balls[i].id == 0 && balls[j].id == 1)
                        //Ecran.afficher("\ntmp boule 0 vs 1 : ", tmp, "\n");
                    if (tmp.compareTo(BigDecimal.ONE.negate()) != 0 && tmp.compareTo(t) < 0)
                    {
                        t = tmp;
                        Ecran.afficher(t, "   ", i, " vs ", j, "  ", module(diff(balls[i].p, balls[j].p)), "\n");
                        if (t.compareTo(BigDecimal.ZERO) == 0)
                            chocBalls(balls[i], balls[j]);
                    }
                }
                tmp = dtChocBox(balls[i], dt).setScale(Box.s, Box.r);
                if (tmp.compareTo(BigDecimal.ONE.negate()) != 0 && tmp.compareTo(t) < 0)
                {
                    t = tmp;
                        Ecran.afficher(t, "  ", i, "\n");
                    if (t.compareTo(BigDecimal.ZERO) == 0)
                        chocBox(balls[i]);
                }
            }
            else
                Ecran.afficher("choc deja gere\n");
        }
        t = t.min(dt);
        if (t.compareTo(BigDecimal.ZERO) > 0)
            evolve(balls, n, t);
        return t;
    }

    static void evolve(Ball b, BigDecimal dt)
    {
        //if (b.id == 15)
        //    Ecran.afficher(dt, "  ", dt.multiply(b.v.x), "  ", b.v.x, "\n");
        b.p.x = b.p.x.add(dt.multiply(b.v.x));
        b.p.y = b.p.y.add(dt.multiply(b.v.y));


        //Ecran.afficher(b.id, dt * b.v.x, "\n");
        b.choc = false;
        //ball.v.module *= 0.99;
    }

    static void evolve(Ball [] balls, int n, BigDecimal dt)
    {
       // Ecran.afficher("evolve dt = ", dt, "\n");
        for (int i = 0; i < n; i++)
            evolve(balls[i], dt);
    }

    static BigDecimal dtChocBox(Ball b, BigDecimal dt)
    {
        BigDecimal t = new BigDecimal(dt.doubleValue(), Box.p);
        BigDecimal tmp = new BigDecimal(0, Box.p);

        if (b.v.x.compareTo(BigDecimal.ZERO) != 0)
        {
            tmp = b.r.subtract(b.p.x).divide(b.v.x, Box.p).setScale(Box.s, Box.r);
            if (tmp.compareTo(BigDecimal.ZERO) >= 0 && tmp.compareTo(t) < 0)
                t = tmp;
            tmp = Box.length.subtract(b.r).subtract(b.p.x).divide(b.v.x, Box.p);
            //Ecran.afficher("   ", tmp, "  ", "\n");
            if (tmp.compareTo(BigDecimal.ZERO) >= 0 && tmp.compareTo(t) < 0)
                t = tmp;
        }
        if (b.v.y.compareTo(BigDecimal.ZERO) != 0)
        {
            //Ecran.afficher(b.v.y, "\n");
            tmp = b.r.subtract(b.p.y).divide(b.v.y, Box.p).setScale(Box.s, Box.r);
            if (tmp.compareTo(BigDecimal.ZERO) >= 0 && tmp.compareTo(t) < 0)
                t = tmp;
            tmp = Box.width.subtract(b.r).subtract(b.p.y).divide(b.v.y, Box.p).setScale(Box.s, Box.r);
            if (tmp.compareTo(BigDecimal.ZERO) >= 0 && tmp.compareTo(t) < 0)
                t = tmp;
        }
        if (t.compareTo(dt) < 0)
            return t;
        return BigDecimal.ONE.negate();
    }

    static BigDecimal dtChocBalls(Ball b1, Ball b2, BigDecimal dt)
    {
        //if (b1.id == 15)
        //    Ecran.afficher("vitesse : ", b1.v.x, "\n");
        // Choc equation : a*dt*dt + b*dt + c = 0
        BigDecimal t = BigDecimal.ONE.negate();
        // b = 2 * (b1.v.x - b2.v.x) * (b1.p.x - b2.p.x) + 2 * (b1.v.y - b2.v.y) * (b1.p.y - b2.p.y)
        BigDecimal b = multiply(2, b1.v.x.subtract(b2.v.x).multiply(b1.p.x.subtract(b2.p.x))).add(multiply(2, b1.v.y.subtract(b2.v.y)).multiply(b1.p.y.subtract(b2.p.y)));
        // a = (b1.v.x - b2.v.x)^2 + (b1.v.y - b2.v.y)^2
        BigDecimal a = b1.v.x.subtract(b2.v.x).pow(2, Box.p).add(b1.v.y.subtract(b2.v.y).pow(2, Box.p));
        // c = (b1.p.x - b2.p.x)^2 + (b1.p.y - b2.p.y)^2 + (b1.r + b2.r)^2
        BigDecimal c = b1.p.x.subtract(b2.p.x).pow(2, Box.p).add(b1.p.y.subtract(b2.p.y).pow(2, Box.p)).subtract(b1.r.add(b2.r).pow(2, Box.p));
        // delta = b^2 - 4ac
        BigDecimal delta = b.pow(2, Box.p).subtract(multiply(4, a.multiply(c)));
        //if (module(diff(b1.p, b2.p)).subtract(b1.r.add(b2.r)).setScale(Box.s, Box.r).compareTo(BigDecimal.ZERO) < 0)
        //    Ecran.afficher("\nERREUR boules ", b1.id, " et ", b2.id, " encastrees\n");
        //if (b1.id == 1 && b2.id == 15)
        //    Ecran.afficher("module : ", module(diff(b1.p, b2.p)), "\n");
        //if (b1.id == 7)
                //Ecran.afficher("\n", t, "\n", a, "\n", b, "\n", c, "\n", delta, "\n", module(diff(b1.p, b2.p)), "\n");
        if (a.compareTo(BigDecimal.ZERO) == 0)
        {
            if (b.compareTo(BigDecimal.ZERO) != 0)
                t = c.negate().divide(b, Box.p);
        }
        else
        {
            if (delta.compareTo(BigDecimal.ZERO) > 0)
            {
                //Ecran.afficher("    t = ", b.negate().add(sqrt(delta)), "\n");
                // t = min((t - sqrt(delta)) / (2a), (t + sqrt(delta)) / (2a))i
                t = b.negate().add(sqrt(delta)).divide(multiply(2, a), Box.p).min(b.negate().subtract(sqrt(delta)).divide(multiply(2, a), Box.p));
            }
            else if (delta.compareTo(BigDecimal.ZERO) == 0)
                // t = -b / (2a)
                t = b.negate().divide(multiply(2, a), Box.p);
            else
                return BigDecimal.ONE.negate();
            //if (a * Math.pow(t, 2) + b * t + c == 0)
                //Ecran.afficher(t, "\n");
        }
        /*if (module(diff(b1.p, b2.p)).compareTo(b1.r.add(b2.r).add(new BigDecimal(0.01, Box.p))) < 0)
        {
                //Ecran.afficher("dtchocball : ", b1.id, " ", b2.id, " ", t, "  ", module(diff(b1.p, b2.p)), "\n");
                Ecran.afficher("\nt = ", t, "\na = ", a, "\nb = ", b.pow(2, Box.p), "\nc = ", multiply(4, c.multiply(a)), "\ndelta = ", delta, "\nmodule = ", module(diff(b1.p, b2.p)), "\n");
                //Ecran.afficher(a.multiply(dt.pow(2, Box.p)).add(b.multiply(dt)).add(c), "\n");
        }*/
        if (t.setScale(Box.s, Box.r).compareTo(BigDecimal.ZERO) >= 0 && t.compareTo(dt) < 0)
        {
            //if (t.compareTo(dt) < 0)
            //    Ecran.afficher("dtChocBalls : ", b1.id, " vs ", b2.id, "  t = ", t, "\n");
            return t;
        }
        return BigDecimal.ONE.negate();
    }

    static void chocBox(Ball b)
    {
        Ecran.afficher("ChocBox : ", b.id, "\n");
        if (b.p.x.subtract(b.r).setScale(Box.s, Box.r).compareTo(BigDecimal.ZERO) == 0)
            b.v.x = multiply(-1, b.v.x);
        else if (b.p.x.add(b.r).setScale(Box.s, Box.r).compareTo(Box.length) == 0)
            b.v.x = multiply(-1, b.v.x);
        else if (b.p.y.subtract(b.r).setScale(Box.s, Box.r).compareTo(BigDecimal.ZERO) == 0)
            b.v.y = multiply(-1, b.v.y);
        else if (b.p.y.add(b.r).setScale(Box.s, Box.r).compareTo(Box.width) == 0)
            b.v.y = multiply(-1, b.v.y);
        b.choc = true;
    }

    static void chocBalls(Ball b1, Ball b2)
    {
        Ecran.afficher("ChocBall : ", b1.id, " vs ", b2.id, "\n");
        if (module(b1.v).compareTo(module(b2.v)) < 0)
        {
            Ball tmp = b1;
            b1 = b2;
            b2 = tmp;
        }

        BigDecimal v1 = module(b1.v);
        BigDecimal v2 = module(b2.v);
        BigDecimal m1 = b1.m;
        BigDecimal m2 = b2.m;

        // alpha = atan((b1.p.y - b2.p.y) / (b1.p.x - b2.p.x)) - PI / 2
        BigDecimal alpha = atanDiv(b1.p.y.subtract(b2.p.y), b1.p.x.subtract(b2.p.x));
        alpha = (alpha.compareTo(BigDecimal.ZERO) == 0) ? multiply((double)(b1.v.x.subtract(b2.v.x).signum()), halfPI()) :
            alpha.add(multiply((double)(alpha.signum()), halfPI().negate())).negate();
        //BigDecimal alpha = atanDiv(b1.p.y.subtract(b2.p.y), b1.p.x.subtract(b2.p.x)).subtract(halfPI());
        //
        /*    (b1.p.x.compareTo(b2.p.x) == 0) ? multiply((double)(b1.p.y.subtract(b2.p.y).signum()), half
            (b1.p.y.compareTo(b2.p.y) < 0) ? BigDecimal.ZERO : multiply(2, halfPI()) :
            (b1.p.y.compareTo(b2.p.y) == 0) ?
                ((b1.p.x.compareTo(b2.p.x) < 0) ? halfPI().negate() : halfPI()).negate() :
                atan(b1.p.y.subtract(b2.p.y).divide(b1.p.x.subtract(b2.p.x), Box.p)).subtract(halfPI());*/
        BigDecimal a1 = atanDiv(b1.v.y, b1.v.x).add(alpha);
           // = (b1.v.x.compareTo(BigDecimal.ZERO) == 0) ? multiply((double)(b1.v.y.signum()), halfPI()).add(alpha) :
           // atan(b1.v.y.divide(b1.v.x, Box.p)).add(alpha);
        BigDecimal a2 = atanDiv(b2.v.y, b2.v.x).add(alpha);
            //(b2.v.x.compareTo(BigDecimal.ZERO) == 0) ? multiply((double)(b2.v.y.signum()), halfPI()).add(alpha) :
            //atan(b2.v.y.divide(b2.v.x, Box.p)).add(alpha);

        // atan(((m1 - m2) / (m1 + m2)) * ()
        BigDecimal th1 = (v1.multiply(cos(a1)).compareTo(BigDecimal.ZERO) == 0) ? multiply((double)(v2.multiply(sin(a2)).signum()), halfPI()) :
            atan(m1.subtract(m2).divide(m1.add(m2), Box.p).multiply(tan(a1)).add(multiply(2, m2).divide(m1.add(m2), Box.p).multiply(v2.divide(v1, Box.p)).multiply(sin(a2).divide(cos(a1), Box.p))));
        BigDecimal th2 = (v2.multiply(cos(a2)).compareTo(BigDecimal.ZERO) == 0) ? multiply((double)(v1.multiply(sin(a1)).signum()), halfPI()) :
            atan(m2.subtract(m1).divide(m1.add(m2), Box.p).multiply(tan(a2)).add(multiply(2, m1).divide(m1.add(m2), Box.p).multiply(v1.divide(v2, Box.p)).multiply(sin(a1).divide(cos(a2), Box.p))));

        BigDecimal newV1 = sqrt(m1.subtract(m2).divide(m1.add(m2), Box.p).multiply(v1).multiply(sin(a1)).add(multiply(2, m2).divide(m1.add(m2), Box.p).multiply(v2).multiply(sin(a2)).pow(2, Box.p).add(v1.multiply(cos(a1)).pow(2, Box.p)))).setScale(Box.s, Box.r);
        BigDecimal newV2 = sqrt(m2.subtract(m1).divide(m1.add(m2), Box.p).multiply(v2).multiply(sin(a2)).add(multiply(2, m1).divide(m1.add(m2), Box.p).multiply(v1).multiply(sin(a1)).pow(2, Box.p).add(v2.multiply(cos(a2)).pow(2, Box.p)))).setScale(Box.s, Box.r);

        b1.v.x = newV1.multiply(cos(th1.subtract(alpha)));
        b1.v.y = newV1.multiply(sin(th1.subtract(alpha)));
        b2.v.x = newV2.multiply(cos(th2.subtract(alpha)));
        b2.v.y = newV2.multiply(sin(th2.subtract(alpha)));

        b1.choc = true;

        Ecran.afficher("\nalpha : ", alpha, "\n");
        Ecran.afficher("a1 : ", a1, "\n");
        Ecran.afficher("a2 : ", a2, "\n");
        Ecran.afficher("th1 : ", th1, "\n");
        Ecran.afficher("th2 : ", th2, "\n");
        Ecran.afficher("newV1 : ", newV1, "\n");
        Ecran.afficher("newV2 : ", newV2, "\n");
        Ecran.afficher("newTh1 : ", th1.add(alpha), "\n");
        Ecran.afficher("newTh2 : ", th2.add(alpha), "\n");
        //Ecran.afficher("angle vecteur vitesse repere carthesien : ", (th1 + alpha)* 180 / Math.PI, "  ", (th2 + alpha) * 180 / Math.PI, "\n");
        Ecran.afficher("b1.v.x : ", b1.v.x, "\n");
        Ecran.afficher("b1.v.y : ", b1.v.y, "\n");
        Ecran.afficher("b2.v.x : ", b2.v.x, "\n");
        Ecran.afficher("b2.v.y : ", b2.v.y, "\n");
    }

// ---------------------------------------------------------------------- Main ----------------------------------------
// --- -----------------------------------------------------------------------------------------------------------------

	public static void main(String[] args)
	{
		int k = 5;

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
			balls[i].v.x = new BigDecimal(0, Box.p);//(Math.random() - 0.5) * 0.001;
			balls[i].v.y = new BigDecimal(0, Box.p);//(Math.random() - 0.5) * 0.001;
			balls[i].r = new BigDecimal(0.03, Box.p);
			balls[i].m = new BigDecimal(0.1, Box.p);
            balls[i].id = i;

			//Def du tableau de rendu
			renderballs[i] = new RenderBall();
			renderballs[i].r = balls[i].r.multiply(scale).intValue();
			renderballs[i].couleur.r = ((int)(Math.random() * 250));
			renderballs[i].couleur.v = ((int)(Math.random() * 250));
			renderballs[i].couleur.b = ((int)(Math.random() * 250));

            if (i == n - 1)
            {
                balls[i].v.x = new BigDecimal(0.1, Box.p);
                balls[i].v.y = new BigDecimal(0, Box.p);
            }
		}

		EcranGraphique.init(50,50,length+100, width+100, length + 20, width + 20, "billard");
		iniWin();
		calcPosTriangle(balls , k);
		//calcPosLine(balls , n);
		render(renderballs, balls, n);
		EcranGraphique.flush();

        BigDecimal t;
        BigDecimal dt = new BigDecimal(0.1, Box.p);

		while(true)
		{
            t = BigDecimal.ZERO;
            while (t.compareTo(dt) < 0)
            {
			    t = t.add(update(balls, n, dt.subtract(t)));
                //if (t < dt)
                //Ecran.afficher("t = ", t, "\n");
            }
            //Ecran.afficher("dt ecoule\n");
			render(renderballs, balls,n);
			EcranGraphique.wait(10);
		}

	}
}
