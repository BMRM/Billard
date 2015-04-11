class Indicator
{
    String  name;
    String  unite;
    double  value;
    int     precision = 2;
    Rect    size;
}

class Graph
{
    Indicator   indicator;
    int         scale;
    int[][]     graph;
}

public class Board
{
    static int nb;
    static int height = 200;

    Rect        size;
    Indicator   indicator[];

    static Board make(Rect size)
    {
        Board b = new Board();
        b.size = size;
        b.indicator = new Indicator[1];
        b.indicator[0] = new Indicator();
        b.indicator[0].name = "FPS : ";
        b.indicator[0].value = Window.fps;
        b.indicator[0].unite = " f/s";
        b.indicator[0].size = size;
        return b;
    }

    static void render(Indicator i)
    {
        EcranGraphique.setColor(255, 255, 255);
        EcranGraphique.drawString(i.size.x, i.size.y, 3, i.name + String.valueOf(i.value) + i.precision);
    }

    static void render(Graph g)
    {

    }

    static void render(Board b)
    {
        render(b.indicator[0]);
    }
}


