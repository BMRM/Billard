public class Input
{
    static boolean  kb = false;
    static String   buffer = "";
    static int      out;

	static void update()
	{
        if (kb)
        {
            char key = EcranGraphique.getKey();
            if (key == 10)
            {
                Menu.title[out][1] = buffer;
                buffer = "";
                kb = false;
            }
            else if (key == 27)
            {
                kb = false;
                buffer = "";
            }
            else if (key == 8)
                buffer = buffer.substring(0, buffer.length() - 1);
            else if (key > 0)
                buffer += key;
        }
	}
}


