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

	static Vector formeTrigo(Vector v)
	{
		v.m = module(v);
		v.a = (v.y == 0 && v.x == 0) ? 0 : Math.atan(v.y / v.x);
		return v;
	}

	static Vector formeCart(Vector v)
	{
		v.x = v.m * Math.cos(v.a);
		v.y = v.m * Math.sin(v.a);
		return v;
	}

    static void dump(Vector v)
    {
        Ecran.afficher("x : ", v.x, " - y : ", v.y, " - m : ", v.m, " - a : ", v.a, "\n");
    }
}

