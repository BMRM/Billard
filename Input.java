public class Input
{
    static boolean  kb = false;
    static String   buffer = "";

	static void update()
	{
        if (kb)
        {
            char key = EcranGraphique.getKey();
            if (key == 10)
            {
                Box.dt = Double.valueOf(buffer);
                buffer = "";
                kb = false;
            }
            else if (key == 8)
                buffer = buffer.substring(0, buffer.length() - 1);
            else if (key > 0)
                buffer += key;
        }
	}
}


