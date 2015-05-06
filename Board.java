/**
 * \file Board.java
 * \brief Bandeau de mesures du système physique
 * \author Romain Mekarni
 */

import java.math.BigDecimal;

/**
 * \class Indicator
 * \brief Mesure numérique d'une grandeur physique du système physique
 * \author Romain Mekarni
 */
class Indicator
{
    String  name;
    String  unite;
    double  value;
    int     precision = 2;
    Rect    size; ///< Position dans le bandeau

    /**
     * \brief Constructor
     * @author Romain Mekarni
     */
    static Indicator make(String name, String unite, int precision, Rect size)
    {
        Indicator i = new Indicator();
        i.name = name;
        i.unite = unite;
        i.size = size;
        i.precision = precision;
        return i;
    }
}

/**
 * \class Graph
 * \brief Mesure graphique d'une grandeur physique du système physique
 * \author Romain Mekarni
 */
class Graph
{
    Indicator   indicator;///<Grandeur physique mesurée
    int         scale;///<Echelle d'affichage
    double      moy = 0;///<Valeur moyenne
    Rect        size;///<Taille et zone d'affichage
    int[][]     graph;///<Tableau d'entier représentant le graphique

    /**
     * \brief Constructeur
     * @param scale Echelle
     * @param size Taille et zone d'affichage
     * @author Romain Mekarni
     */
    static Graph make(String name, String unite, int precision, int scale, Rect size)
    {
        Graph g = new Graph();
        g.size = size;
        g.indicator = Indicator.make(name, unite, precision, Rect.make(size.x, size.y + size.h + 25, 230, 20));
        g.scale = scale;
        g.graph = new int[size.h][size.w];
        for (int i = 0; i < size.h; i++)
        {
            for (int j = 0; j < size.w; j++)
                g.graph[i][j] = 0;
        }
        return g;
    }
}

/**
 * \class Board
 * \brief Bandeau d'affichage des grandeurs physique du système
 * \author Romain Mekarni
 */
public class Board
{
    static int nb;
    static int height = 200;

    Rect        size;
    Indicator   indicators[];
    Graph       graphs[];

    static Board make(Rect size)
    {
        Board b = new Board();
        b.size = size;
        b.indicators = new Indicator[7];
        b.indicators[0] = Indicator.make("Boule : ", "", 0, Rect.make(size.x + 550, size.y + 20, 50, 30));
        b.indicators[1] = Indicator.make("Vitesse : ", " m/s", 2, Rect.make(size.x + 550, size.y + 50, 50, 30));
        b.indicators[2] = Indicator.make("Angle : ", " degres", 0, Rect.make(size.x + 550, size.y + 80, 50, 30));
        b.indicators[3] = Indicator.make("Position.x : ", " m", 2, Rect.make(size.x + 550, size.y + 110, 50, 30));
        b.indicators[4] = Indicator.make("Position.y : ", " m", 2, Rect.make(size.x + 550, size.y + 140, 50, 30));
        b.indicators[5] = Indicator.make("Rayon : ", " m", 2, Rect.make(size.x + 550, size.y + 170, 50, 30));
        b.indicators[6] = Indicator.make("FPS : ", "", 0, Rect.make(size.x + 690, size.y + 20, 50, 30));
        b.graphs = new Graph[2];
        b.graphs[0] = Graph.make("Nombre de chocs : ", " par dt", 0, 10, Rect.make(size.x + 15, size.y + 10, 240, 150));
        b.graphs[1] = Graph.make("Vitesse moyenne : ", " m/s", 2, 100, Rect.make(size.x + 280, size.y + 10, 240, 150));

        return b;
    }

    static void render(Graph g)
    {
        g.moy = (g.moy + g.indicator.value) / (double)g.size.w;
        for (int i = 0; i < g.size.w; i++)
        {
            for (int j = 0; j < g.size.h; j++)
            {
                g.graph[j][i] = 0;
                if (i < g.size.w - 1)
                {
                    if (g.graph[j][i+1] == 1)
                    {
                        g.graph[j][i] = 1;
                        EcranGraphique.setColor(255, 255, 255);
                        EcranGraphique.drawPixel(g.size.x + i, g.size.y + g.size.h - j);
                    }
                }
            }
        }
        for (int i = 0; i < (int)Math.min(g.size.h - 1, g.indicator.value * g.scale); i++)
        {
            g.graph[i][g.size.w - 1] = 1;
            EcranGraphique.setColor(255, 255, 255);
            EcranGraphique.drawPixel(g.size.x + g.size.w - 1, g.size.y + g.size.h - i);
        }
        render(g.indicator);
    }

    static void render(Indicator i)
    {
        EcranGraphique.setColor(255, 255, 255);
        EcranGraphique.drawString(i.size.x, i.size.y, 3, i.name + new BigDecimal(i.value).setScale(i.precision, Box.r).toString() + i.unite);
    }

    static void render(Board b)
    {
        b.indicators[0].value = Box.ballFocus.id;
        b.indicators[1].value = Box.ballFocus.v.m;
        b.indicators[2].value = Box.ballFocus.v.a * 180 / Math.PI;
        b.indicators[3].value = Box.ballFocus.p.x;
        b.indicators[4].value = Box.ballFocus.p.y;
        b.indicators[5].value = Box.ballFocus.r;
        b.indicators[6].value = Window.fps;
        b.graphs[0].indicator.value = Box.nbChoc;
        Box.nbChoc = 0;
        b.graphs[1].indicator.value = Box.vmoy;
        render(b.indicators[0]);
        render(b.indicators[1]);
        render(b.indicators[2]);
        render(b.indicators[3]);
        render(b.indicators[4]);
        render(b.indicators[5]);
        render(b.indicators[6]);
        render(b.graphs[0]);
        render(b.graphs[1]);
    }
}


