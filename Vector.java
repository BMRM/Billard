public class Vector
{
	double x;
    double y;
	double m;
	double a;

	static double module(Vector v)
    {
        return Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
    }

    static double atan(double y, double x)
    {
        double a = 0;
        if (x > 0)
            a = Math.atan(y / x);
        if (x < 0 && y >= 0)
            a = Math.atan(y / x) + Math.PI;
        if (x < 0 && y < 0)
            a = Math.atan(y / x) - Math.PI;
        if (x == 0 && y > 0)
            a = Math.PI / 2;
        if (x == 0 && y < 0)
            a = -Math.PI / 2;
        if (x == 0 && y == 0)
            a = 0;
        return a;
    }

	static Vector formePol(Vector v)
	{
		v.m = module(v);
        v.a = atan(v.y, v.x);
		return v;
	}

	static Vector formeCart(Vector v)
	{
		v.x = v.m * Math.cos(v.a);
		v.y = v.m * Math.sin(v.a);
		return v;
	}

    static Vector newBase(Vector v, double alpha)
    {
        double x1 = v.x;
        v.x = Math.cos(alpha) * x1 - Math.sin(alpha) * v.y;
        v.y = Math.sin(alpha) * x1 + Math.cos(alpha) * v.y;
        formePol(v);
        return v;
    }

    static Vector reverseBase(Vector v, double alpha)
    {
        double x1 = v.x;
        v.x = Math.cos(alpha) * x1 + Math.sin(alpha) * v.y;
        v.y = -Math.sin(alpha) * x1 + Math.cos(alpha) * v.y;
        formePol(v);
        return v;
    }

    static void dump(Vector v)
    {
        Ecran.afficher("x : ", v.x, " - y : ", v.y, " - m : ", v.m, " - a : ", v.a, "\n");
    }
}

