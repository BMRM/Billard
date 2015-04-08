import java.math.BigDecimal;

public class Ball
{
	int		id;
	double	r;
	Vector	p = new Vector();
	Vector	v = new Vector();
	double	m;

	static BigDecimal dtChocBox(Ball b, BigDecimal dt)
    {
        BigDecimal t = dt;
        BigDecimal tmp = BigDecimal.ONE.negate();

        if (b.v.x != 0)
        {
            tmp = new BigDecimal((b.r - b.p.x) / b.v.x).setScale(Box.s, Box.r);
            if (tmp.compareTo(BigDecimal.ZERO) > 0 && tmp.compareTo(t) <= 0)
                t = tmp;
            tmp = new BigDecimal((Box.length - b.r - b.p.x) / b.v.x).setScale(Box.s, Box.r);
            if (tmp.compareTo(BigDecimal.ZERO) > 0 && tmp.compareTo(t) <= 0)
                t = tmp;
        }
        if (b.v.y != 0)
        {
            tmp = new BigDecimal((b.r - b.p.y) / b.v.y).setScale(Box.s, Box.r);
            if (tmp.compareTo(BigDecimal.ZERO) > 0 && tmp.compareTo(t) <= 0)
                t = tmp;
            tmp = new BigDecimal((Box.width - b.r - b.p.y) / b.v.y).setScale(Box.s, Box.r);
            if (tmp.compareTo(BigDecimal.ZERO) > 0 && tmp.compareTo(t) <= 0)
                t = tmp;
        }
        if (t.compareTo(dt) < 0) // a prendre en compte ==
            return t;
        return BigDecimal.ONE.negate();
    }

	static void chocBox(Ball b)
    {
        if (new BigDecimal(b.p.x - b.r).setScale(Box.s, Box.r).compareTo(BigDecimal.ZERO) == 0)
            b.v.x *= -1;
        else if (new BigDecimal(b.p.x + b.r - Box.length).setScale(Box.s, Box.r).compareTo(BigDecimal.ZERO) == 0)
            b.v.x *= -1;
        else if (new BigDecimal(b.p.y - b.r).setScale(Box.s, Box.r).compareTo(BigDecimal.ZERO) == 0)
            b.v.y *= -1;
        else if (new BigDecimal(b.p.y + b.r - Box.width).setScale(Box.s, Box.r).compareTo(BigDecimal.ZERO) == 0)
            b.v.y *= -1;
        Vector.formeTrigo(b.v);
    }

    static double dtChocBalls(Ball b1, Ball b2, double dt)
    {
        // Choc equation : a.dt.dt + b.dt + c = 0
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
        Vector.dump(b1.v);
        Vector.dump(b2.v);
        double alpha = (b1.p.y - b2.p.y == 0 && b1.p.x - b2.p.x == 0) ? 0 : Math.atan((b1.p.y - b2.p.y) / (b1.p.x - b2.p.x));
        alpha = (alpha == 0) ? (b1.p.x < b2.p.x) ? -Math.PI / 2 : Math.PI / 2 :
            (alpha > 0) ? alpha -(Math.PI / 2) : alpha + Math.PI / 2;
        Ecran.afficher("alpha = ", alpha, "\n");
        b1.v.a -= alpha;
        b2.v.a -= alpha;
		double a1 = Math.atan((b2.v.m * Math.sin(b2.v.a)) / (b1.v.m * Math.cos(b1.v.a)));
		double a2 = Math.atan((b1.v.m * Math.sin(b1.v.a)) / (b2.v.m * Math.cos(b2.v.a)));
		double v1 = Math.sqrt(Math.pow(b2.v.m * Math.sin(b2.v.a), 2) + Math.pow(b1.v.m * Math.cos(b1.v.a), 2));
		double v2 = Math.sqrt(Math.pow(b1.v.m * Math.sin(b1.v.a), 2) + Math.pow(b2.v.m * Math.cos(b2.v.a), 2));
		b1.v.m = v1;
		b1.v.a = a1 + alpha;
		b2.v.m = v2;
		b2.v.a = a2 + alpha;
		Vector.formeCart(b1.v);
		Vector.formeCart(b2.v);
        Vector.dump(b1.v);
        Vector.dump(b2.v);
	}
}

