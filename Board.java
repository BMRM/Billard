class Indicator
{
    String  name;
    String  unite;
    double  value;
    int     precision = 2;
    Rect    size;

    static Indicator make(String name, String unite, Rect size)
    {
        Indicator i = new Indicator();
        i.name = name;
        i.unite = unite;
        i.size = size;
        return i;
    }
}

class Graph
{
    Indicator   indicator;
    int         scale;
    double      moy = 0;
    Rect        size;
    int[][]     graph;

    static Graph make(String name, String unite, int scale, Rect size)
    {
        Graph g = new Graph();
        g.size = size;
        g.indicator = Indicator.make(name, unite, size);
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
        b.indicators = new Indicator[1];
        b.indicators[0] = Indicator.make("FPS : ", " f/s", Rect.make(size.x + 300, size.y + 20, 50, 30));
        b.graphs = new Graph[1];
        b.graphs[0] = Graph.make("Nombre de chocs : ", " par dt", 1000, Rect.make(size.x + 10, size.y + 10, 200, 100));

        return b;
    }

    static void render(Graph g)
    {
        g.moy = (g.moy + g.indicator.value) / (double)g.size.w;
        for (int i = 0; i < g.size.h; i++)
        {
            for (int j = 0; j < g.size.w - 1; j++)
            {
                g.graph[i][j] = g.graph[i][j+1];
                if (g.graph[i][j] == 1)
                {
                    EcranGraphique.setColor(255, 255, 255);
                    EcranGraphique.drawPixel(g.size.x + j, g.size.y + g.size.h - i);
                }
            }
            g.graph[i][g.size.w - 1] = 0;
        }
        g.graph[(int)Math.min(g.size.h - 1, g.moy * g.scale)][g.size.w - 1] = 1;
        EcranGraphique.setColor(255, 255, 255);
        EcranGraphique.drawPixel(g.size.x + g.size.w - 1, g.size.y + -(int)Math.min(g.size.h - 1, g.moy * g.scale) + g.size.h);
        render(g.indicator);
    }

    static void render(Indicator i)
    {
        EcranGraphique.setColor(255, 255, 255);
        EcranGraphique.drawString(i.size.x, i.size.y, 3, i.name + String.valueOf((int)i.value) + i.unite);
    }

    static void render(Board b)
    {
        b.indicators[0].value = Window.fps;
        b.graphs[0].indicator.value = Box.nbChoc;
        Box.nbChoc = 0;
        render(b.indicators[0]);
        render(b.graphs[0]);
    }
}


