/**
 * \file Board.java
 * \brief Bandeau de mesures du système physique
 * \author Romain Mekarni
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * \class Indicator
 * \brief Mesure numérique d'une grandeur physique du système physique
 * \author Romain Mekarni
 */
class Indicator
{
    String  name;
    String  unite;
    double  value = 0;
    int     precision = 2;
    Rect    size; ///< Position dans le bandeau

    /**
     * \brief Constructor
     * \author Romain Mekarni
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

    /**
     * \brief Rendu de l'indicateur dans Board
     * \author Romain Mekarni
     */
    static void render(Indicator i)
    {
        EcranGraphique.setColor(255, 255, 255);
        EcranGraphique.drawString(i.size.x, i.size.y, 3, i.name + new BigDecimal(i.value).setScale(i.precision, RoundingMode.HALF_UP).toString() + i.unite);
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
     * \param scale Echelle
     * \param size Taille et zone d'affichage
     * \author Romain Mekarni
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

    /**
     * \brief Rendu graphique dans Board
     * \author Romain Mekarni
     */
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
        Indicator.render(g.indicator);
    }
}

/**
 * \class Board
 * \brief Bandeau d'affichage des grandeurs physique du système
 * \author Romain Mekarni
 */
public class Board
{
    int         nbIndicators = 8;///<Nombre de Indicator dans le bandeau
    int         nbGraphs = 2;///<Nombre de graphiques
    Rect        size;///<Position et taille
    Indicator   indicators[];///<Tableau de Indicator
    Graph       graphs[];///<Tableau de Graph

    /**
     * \brief Constructeur
     * \param size Taille et position du Board
     * \author Romain Mekarni
     */
    static Board make(Rect size)
    {
        Board b = new Board();
        b.size = size;
        b.indicators = new Indicator[b.nbIndicators];
        b.indicators[0] = Indicator.make("Boule : ", "", 0, Rect.make(size.x + 550, size.y + 20, 50, 30));
        b.indicators[1] = Indicator.make("Vitesse : ", " m/s", 2, Rect.make(size.x + 550, size.y + 50, 50, 30));
        b.indicators[2] = Indicator.make("Angle : ", " degres", 0, Rect.make(size.x + 550, size.y + 80, 50, 30));
        b.indicators[3] = Indicator.make("Position.x : ", " m", 2, Rect.make(size.x + 550, size.y + 110, 50, 30));
        b.indicators[4] = Indicator.make("Position.y : ", " m", 2, Rect.make(size.x + 550, size.y + 140, 50, 30));
        b.indicators[5] = Indicator.make("Rayon : ", " m", 2, Rect.make(size.x + 550, size.y + 170, 50, 30));
        b.indicators[6] = Indicator.make("FPS : ", "", 0, Rect.make(size.x + 690, size.y + 20, 50, 30));
        b.indicators[7] = Indicator.make("t : ", " s", 2, Rect.make(size.x + 15, size.y - 20, 50, 30));
        b.graphs = new Graph[b.nbGraphs];
        b.graphs[0] = Graph.make("Nombre de chocs : ", " par dt", 0, 10, Rect.make(size.x + 15, size.y + 10, 240, 150));
        b.graphs[1] = Graph.make("Vitesse moyenne : ", " m/s", 2, 100, Rect.make(size.x + 280, size.y + 10, 240, 150));
        return b;
    }

    /**
     * \brief Mise à jour des informations du bandeau
     * \author Romain Mekarni
     */
    static void update(Board b, Box box, Window win)
    {
        b.indicators[0].value = box.ballFocus.id;
        b.indicators[1].value = box.ballFocus.v.m;
        b.indicators[2].value = box.ballFocus.v.a * 180 / Math.PI;
        b.indicators[3].value = box.ballFocus.p.x;
        b.indicators[4].value = box.ballFocus.p.y;
        b.indicators[5].value = box.ballFocus.r;
        b.indicators[6].value = win.fps;
        b.indicators[7].value = box.t;
        b.graphs[0].indicator.value = box.nbChoc;
        box.nbChoc = 0;
        b.graphs[1].indicator.value = box.vmoy;
    }

    /**
     * \brief Rendu du bandeau dans Menu
     * \author Romain Mekarni
     */
    static void render(Board b)
    {
        for (int i = 0; i < b.nbIndicators; i++)
            Indicator.render(b.indicators[i]);
        for (int i = 0; i < b.nbGraphs; i++)
            Graph.render(b.graphs[i]);
    }
}


