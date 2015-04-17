import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Box
{
    static double       dt = 0.05;
    static double       rayon = 0.03;
	static double		width = 1.27;
	static double		length = 2.54;
    static int          s = 8;
    static RoundingMode r = RoundingMode.HALF_UP;
    static MathContext  p = new MathContext(s, r);
    static int          nbChoc = 0;
    static double       vmoy = 0;
    static Ball         ballFocus;
    static boolean 		run = false;
    static double		friction = 0.01;
    
    int     nbBalls;
	Ball	balls[];
	

	static void     make(Box box, int nbBalls)
	{
        box.nbBalls = nbBalls;
		box.balls = new Ball[nbBalls];
        for (int i = 0; i < nbBalls; i++)
        {
            box.balls[i] = new Ball();
            box.balls[i].id = i;
            box.balls[i].r = rayon;
            box.balls[i].m = 1;
        }
        ballFocus = box.balls[nbBalls - 1];
        posTriangle(box.balls, Billard.k);
    }

    static void     update(Box box)
    {
        if (run)
        {
            double t = 0;
            while (t < Box.dt)
                t += update(box, Box.dt);
        }
    }

	static double	update(Box box, double dt)
	{
		BigDecimal	t = new BigDecimal(dt, p);
		BigDecimal	tmp;
		Stack		stack = new Stack();

        Ball bi, bj;
		for (int i = 0; i < box.nbBalls; i++)
		{
            bi = box.balls[i];
			for (int j = i + 1; j < box.nbBalls; j++)
			{
                bj = box.balls[j];
				tmp = Ball.dtChocBalls(bi, bj, t);
				if (tmp.compareTo(BigDecimal.ONE.negate()) != 0 && tmp.compareTo(t) <= 0)
				{
					if (tmp.compareTo(t) != 0)
						Stack.clear(stack);
					Stack.push(stack, 0, bi, bj);
					t = tmp;
				}
			}
			tmp = Ball.dtChocBox(bi, t);
			if (tmp.compareTo(BigDecimal.ONE.negate()) != 0 && tmp.compareTo(t) <= 0)
			{
				if (tmp.compareTo(t) != 0)
					Stack.clear(stack);
				Stack.push(stack, 1, bi, null);
				t = tmp;
			}
		}
		evolve(box.balls, box.nbBalls, t.doubleValue());
		pollEvent(stack);
		return t.doubleValue();
	}

	static void pollEvent(Stack stack)
	{
		while (!Stack.isEmpty(stack))
		{
			switch (stack.first.event.type)
			{
				case 0 :
					Ball.chocBalls(stack.first.event.b1, stack.first.event.b2);
					break;
				case 1 :
					Ball.chocBox(stack.first.event.b1);
					break;
				default :
					break;
			}
            Stack.pull(stack);
            nbChoc++;
		}
	}

	static void evolve(Ball b, double dt)
	{
		b.p.x += dt * b.v.x;
		b.p.y += dt * b.v.y;
		b.v.x *= 1 - friction;
		b.v.y *= 1 - friction; 
        b.r = Box.rayon;
        Vector.formePol(b.v);
	}

	static void evolve(Ball[] balls, int n, double dt)
	{
        vmoy = 0;
		for (int i = 0; i < n; i++)
        {
            evolve(balls[i], dt);
            vmoy += balls[i].v.m;
        }
	}

	static void posLine(Ball [] balls, int n)
	{
		double k = length / ( n + 0.01 );
		for (int i = 0; i < n; i++)
		{
			balls[i].p.x = 0.1 + k * i;
			balls[i].p.y = width / 2;
			balls[i].v.x = Math.random() - 0.5;
	        balls[i].v.y = Math.random() - 0.5;
		}
	}

	static void posTriangle(Ball [] balls , int k)
	{
		double decal = 0;
		int b = 0;
		int n = (k * (k+1)/2) + 1;
		balls[n - 1].p.x = length/3;
		balls[n - 1].p.y = width/2;
        balls[n - 1].v.x = 1;
        balls[n - 1].v.y = 0;
        Vector.formePol(balls[n - 1].v);
		for (int i = 1; i <= k; i++)
		{
			for (int j = 0; j <= i - 1; j++)
			{
				balls[b].p.x = (2 * length / 3) + decal;
				balls[b].p.y = width / 2 + (2 * (balls[b].r + 0.003) * j) - ((decal)/2);
                balls[b].v.x = 0;
                balls[b].v.y = 0;
                Vector.formePol(balls[b].v);
				b++;
			}
			decal += (2 * balls[i].r);
		}
	}
}

