import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Box
{
	static double		width = 1.27;
	static double		length = 2.54;
    static MathContext  p = new MathContext(5, RoundingMode.HALF_UP);

	Ball[]	balls;
	boolean run = false;
	
	static double	make(Box box, int nbBoules)
	{
		box.balls = new Ball[nbBoules];
		make(box.balls, nbBoules);
	}

	static double	update(Ball[] balls, int n, double dt)
	{
		BigDecimal	t = new BigDecimal(dt, p);
		BigDecimal	tmp;
		Stack		stack = new Stack();
		
		for (int i = 0; i < n; i++)
		{
			for (int j = i + 1; j < n; j++)
			{
				tmp = new BigDecimal(dtChocBalls(balls[i], balls[j], t), p);
				if (tmp.compareTo(BigDecimal.ONE.negate()) != 0 && tmp.compareTo(t) <= 0)
				{
					if (tmp.compareTo(t) != 0)
						clear(stackEvent);
					push(stack, newEvent(1, balls[i], balls[j]));
					t = tmp;
				}
			}
			tmp = new BigDecimal(dtChocBox(balls[i], t), p);
			if (tmp.compareTo(BigDecimal.ONE.negate()) != 0 && tmp.compareTo(t) <= 0)
			{
				if (tmp.compareTo(t) != 0)
					clear(stackEvent);
				push(stack, newEvent(1, balls[i], balls[j]));
				t = tmp;
			}
		}
		evolve(balls, n, t);
		pollEvent(stack);
		return t.doubleValue();
	}

	static void pollEvent(Stack stack)
	{
		while (!isEmpty(stack))
		{
			Event e = stack.first;
			switch (e.type)
			{
				case 0 :
					chocBalls(e.b1, e.b2);
					break;
				case 1 :
					chocBox(e.b1);
					break;
				default :
					break;
			}
		}
	}
	
	static void evolve(Ball b, double dt)
	{
		b.p.x = dt * b.v.x;
		b.p.x = dt * b.v.y;
	}
	
	static void evolve(Ball[] balls, n, dt)
	{
		for (int i = 0; i < n; i++)
			evolve(balls[i], dt);
	}
	
	static void posLine(Ball [] balls, int n)
	{
		double k = length / ( n + 0.01 );
		for (int i = 0; i < n; i++)
		{
			balls[i].p.x = 0.1 + k * i;
			balls[i].p.y = width / 2;
		}
	}
	
	static void posTriangle(Ball [] balls , int k)
	{
		double decal = 0;
		int b = 0;
		int n = (k * (k+1)/2) + 1;
		balls[n - 1].p.x = length/3;
		balls[n - 1].p.y = width/2 + 0.02;
		for (int i = 1; i <= k; i++)
		{
			for (int j = 0; j <= i - 1; j++)
			{
				balls[b].p.x = (2 * length / 3) + decal;
				balls[b].p.y = width / 2 + (2 * (balls[b].r + 0.003) * j) - ((decal)/2);
				b++;
			}
			decal += (2 * balls[i].r);
		}
	}
}

