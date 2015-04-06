public class Input
{
	static int returnButtonM(boolean reset)
	{
		int buttonM = -1;
		if (reset == false)
			buttonM = EcranGraphique.getMouseButton();
		return buttonM;
	}
	static int[] returnMouse()
	{
		int [] coords = new int[2];
		coords[0] = EcranGraphique.getMouseX();
		coords[1] = EcranGraphique.getMouseY();
		return coords;
	}
	
	static char kbInput()
	{
		char input = EcranGraphique.getKey();
		return input;
	}
	
	static int stringBuilder(int output)
	{
		if((int)EcranGraphique.getKey() == 7)
		{
			output = 0;
		}
		else
		{
			output += (int)kbInput();
		}
		//EcranGraphique.drawString(10, width + 100, 3, output);
		return output;
	}
}