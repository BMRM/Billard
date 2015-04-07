public class Ball
{
	int		id;
	double	r;
	Vector	p = new Vector();
	Vector	v = new Vector();
	double	m;
	
	static double dtChocBox(Ball b, double dt)
    {
        double t = dt;
        double tmp;

        tmp = (b.r - b.p.x) / b.v.x;
        if (tmp > 0 && tmp <= t)
            t = tmp;
        tmp = (length - b.r - b.p.x) / b.v.x;
        if (tmp > 0 && tmp <= t)
            t = tmp;
        tmp = (b.r - b.p.y) / b.v.y;
        if (tmp > 0 && tmp <= t)
            t = tmp;
        tmp = (width - b.r - b.p.y) / b.v.y;
        if (tmp > 0 && tmp <= t)
            t = tmp;
        if (t < dt)
            return t;
        return -1;
    }
	
	static void chocBox(Ball b)
    {
        if (b.p.x - b.r == 0)
            b.v.x *= -1;
        else if (b.p.x + b.r == length)
            b.v.x *= -1;
        else if (b.p.y - b.r == 0)
            b.v.y *= -1;
        else if (b.p.y + b.r == width)
            b.v.y *= -1;
		formeTrigo(b.v);
    }
	
	static double dtChocBalls(Ball b1, Ball b2, BigDecimal dt)
    {
        // Choc equation : a.dt² + b.dt + c = 0
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
        if (t > 0 && t <= dt)
            return t;
        return -1;
    }
	
	static void chocBalls(Ball b1, Ball b2)
    {
		if (b1.v.m < b2.v.m)
		{
			Ball tmp = b1;
			b1 = b2;
			b2 = tmp;
		}
		double a1 = Math.atan((b2.v.m * Math.sin(b2.v.a)) / (b1.v.m * Math.cos(b1.v.a)));
		double a2 = Math.atan((b1.v.m * Math.sin(b1.v.a)) / (b2.v.m * Math.cos(b2.v.a)));
		double v1 = Math.sqrt(Math.pow(b2.v.m * Math.sin(b2.v.a), 2) + Math.pow(b1.v.m * Math.cos(b1.v.a), 2));
		double v2 = Math.sqrt(Math.pow(b2.v.m * Math.sin(b2.v.a), 2) + Math.pow(b1.v.m * Math.cos(b1.v.a), 2));
		b1.v.m = v1;
		b1.v.a = a1;
		b2.v.m = v2;
		b2.v.a = a2;
		formeCart(b1);
		formeCart(b2);
	}
	
	static void make(Ball[] b, int n)
	{
		for(int i = 0; i < n ; i++)
		{
			b[i] = new Ball();
			b[i].r = 0.03;
			b[i].v.x = 0;
			b[i].v.y = 0;
			b[i].m = 1;		
            if (i == n - 1)
            {
                b[i].v.x = 0.3;
                b[i].v.y = 0;
            }
			formeTrigo(b[i].v);
		}
	}
}

