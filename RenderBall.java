public class RenderBall
{
	int r;
	int x, y;
	Color c = new Color();
	
	static void make(RenderBall[] rB, int n)
	{
		for (int i = 0; i < n; i++)
		{
			rB[i] = new RenderBall();
			rB[i].c.r = (int)(Math.random() * 250);
			rB[i].c.v = (int)(Math.random() * 250);
			rB[i].c.b = (int)(Math.random() * 250);
			if (i == n - 1)
			{
				rB[i].c.r = 255;
				rB[i].c.v = 255;
				rB[i].c.b = 255;
			}
		}
	}
	
	static void render(RenderBall rB, Ball b)
	{
		rB.x = (int)(b.p.x * scale + 10);
		rB.y = (int)(-b.p.y * scale + 10 + width);
		rB.r = (int)(b.r * scale);
		EcranGraphique.setColor(rB.c.r, rB.c.v, rB.c.b);
		EcranGraphique.fillCircle(rB.x, rB.y, rB.r);
	}

	static void render(RenderBall[] rB, Ball[] balls, int n)
	{
		for (int i = 0; i < n; i++)
			renderBall(rB[i], balls[i]);
	}
}

