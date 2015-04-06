public class Menu
{
	Button[] buttons;
	
	
	
	static void render(Button [] Menu)
	{
		//EcranGraphique.setColor(100, 100, 100);
		//EcranGraphique.fillRect(0, 0, length + 20, width + 20);

		for(int i = 0; i < Menu.length; i++)
		{
			String title = Menu[i].title;
			EcranGraphique.setColor(60, 60, 60);
			EcranGraphique.fillRect(Menu[i].xBorder, Menu[i].yBorder, Menu[i].lengthB, Menu[i].widthB);
			EcranGraphique.setColor(120, 120, 120);
			EcranGraphique.fillRect(Menu[i].x, Menu[i].y, Menu[i].length, Menu[i].width );
			//System.out.println((length - Menu[i].length)/2);
			EcranGraphique.setColor(0, 0, 0);
			EcranGraphique.drawString(Menu[i].xString, Menu[i].yString, 3, title);
			//EcranGraphique.flush();
		}
	}
}