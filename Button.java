public class Button
{
	static int b = 3;

    int     type = 0;
	String	title[];
    Color   design[][];
	Rect	size;
	Rect	border;
    int     state = 0;

	static void make(Button button, int type, String title[], Rect size)
	{
        button.type = type;
		button.title = title;
		button.size = Rect.make(size.x + b, size.y + b, size.w - 2 * b, size.h - 2 * b);
        button.design = new Color[4][4];
        button.design[0][0] = Color.make(60, 60, 60);
        button.design[0][1] = Color.make(80, 80, 80);
        button.design[1][0] = button.design[0][1];
        button.design[1][1] = button.design[0][0];
        button.design[2][0] = button.design[1][0];
        button.design[2][1] = button.design[1][1];
        button.design[3][0] = button.design[2][1];
        button.design[3][1] = button.design[2][0];
		button.border = size;
	}

	static int update(Button b, Box box)
	{
        if (b.type == 2)
            b.title[0] += String.valueOf(Billard.dt);

        if (b.state % 2 == 1
        && EcranGraphique.getMouseState() == 0)
        {
            b.state = (b.state + 1) % (2 + 2 * b.type);
            return b.state;
        }
		if (Rect.isIn(b.size, EcranGraphique.getMouseX(), EcranGraphique.getMouseY())
		&& EcranGraphique.getMouseState() == 1
		&& EcranGraphique.getMouseButton() == 1
		&& b.state % 2 == 0)
        {
			b.state++;
            return b.state;
        }
        return -1;
	}

	static void render(Button b)
	{
		Color.setEcranGraphique(b.design[b.state][0]);
		Rect.fill(b.border);
		Color.setEcranGraphique(b.design[b.state][1]);
		Rect.fill(b.size);
		EcranGraphique.setColor(255, 255, 255);
		EcranGraphique.drawString(b.size.x + 10, b.size.y + b.size.h - 10, 3, b.title[(b.type % 2) * b.state / 2]);
	}
}

