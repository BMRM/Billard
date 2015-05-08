/**
 * \file Vector.java
 * \brief Manipulation des vecteurs mathématiques pour la physique
 * \author Romain Mekarni
 */

/**
 * \class Vector
 * \brief Manipulation des vecteurs mathématiques
 * \author Romain Mekarni
 */
public class Vector
{
	double x;///<Position x
    double y;///<Position y
	double m;///<Module
	double a;///<Angle en radian
/**
 * \brief Calcule le module d'un vecteur
 * \author Romain Mekarni
 */
	static double module(Vector v)
    {
        return Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
    }
/**
 * \brief Calcule atan
 * \author Romain Mekarni
 */
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
/**
 * \brief Conversion coordonnées cartésiennes -> polaires
 * \author Romain Mekarni
 */
	static Vector formePol(Vector v)
	{
		v.m = module(v);
        v.a = atan(v.y, v.x);
		return v;
	}
/**
 * \brief Conversion coordonnées polaires -> cartésiennes
 * \author Romain Mekarni
 */
	static Vector formeCart(Vector v)
	{
		v.x = v.m * Math.cos(v.a);
		v.y = v.m * Math.sin(v.a);
		return v;
	}
/**
 * \brief Change le vecteur de base
 * \author Romain Mekarni
 */
    static Vector newBase(Vector v, double alpha)
    {
        double x1 = v.x;
        v.x = Math.cos(alpha) * x1 - Math.sin(alpha) * v.y;
        v.y = Math.sin(alpha) * x1 + Math.cos(alpha) * v.y;
        formePol(v);
        return v;
    }

    /**
     * \brief Change le vecteur de base (retour vers initial)
     * \author Romain Mekarni
     */
    static Vector reverseBase(Vector v, double alpha)
    {
        double x1 = v.x;
        v.x = Math.cos(alpha) * x1 + Math.sin(alpha) * v.y;
        v.y = -Math.sin(alpha) * x1 + Math.cos(alpha) * v.y;
        formePol(v);
        return v;
    }
/**
 * \brief Dump vecteur
 */
    static void dump(Vector v)
    {
        Ecran.afficher("x : ", v.x, " - y : ", v.y, " - m : ", v.m, " - a : ", v.a, "\n");
    }
}

