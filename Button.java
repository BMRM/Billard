public class Button
{
	String title;
	int x, xBorder, xString;
	int y, yBorder, yString;
	int length, lengthB;
	int width, widthB;
	boolean isClicked = false;

	static void make(int buttonW, Button [] Menu, char placement)
	{
		
		int [] sizePix = new int[Menu.length];
		int max = 0;
		
		for(int i = 0; i < Menu.length; i++)
		{
			sizePix[i] = (Menu[i].title.length()) * 17; 
			if(sizePix[i] > max) max = sizePix[i];
		}
		for(int i = 0; i < Menu.length; i++)
		{
			
			
			Menu[i].y = buttonW/2  + (int)(1.5 * i * buttonW);
			System.out.println(Menu[i].y);
			
			Menu[i].length = max;
			//int position = (length - buttonL)/2 - 1;
			switch (placement)
			{
				case ('c'):	Menu[i].x = (length - max)/2 - 1; 
				break;
				case ('l'): Menu[i].x = 5;
				break;
				case ('r'): Menu[i].x = length + 25;
				break;	
			}
			Menu[i].xBorder = Menu[i].x - 1;
			Menu[i].yBorder = Menu[i].y - 1;
			Menu[i].lengthB = Menu[i].length + 2;
			Menu[i].width = buttonW;
			Menu[i].widthB = Menu[i].width + 2;
			Menu[i].xString = Menu[i].x + Menu[i].length/5 + 5; 
			Menu[i].yString = Menu[i].y + (Menu[i].width + 8)/2 ;
			System.out.println(Menu[i].width);	
		}
	}
	
	static int isClickButton(Button [] Menu, int button)
	{
		if(button == -1)
		{
			if((isOnButton(Menu) != -1) && EcranGraphique.getMouseState() == 1)
			{
				return 1;
			}
		}
		else if((isOnButton(Menu) == button) && EcranGraphique.getMouseState() == 1 )
		{
			
			while(EcranGraphique.getMouseState() != 0)
			{
				EcranGraphique.setColor(60, 60, 60);
				EcranGraphique.fillRect(Menu[button].xBorder, Menu[button].yBorder, Menu[button].lengthB, Menu[button].widthB);
				EcranGraphique.setColor(80, 80, 80);
				EcranGraphique.fillRect(Menu[button].x, Menu[button].y, Menu[button].length, Menu[button].width );
				EcranGraphique.setColor(255, 255, 255);
				EcranGraphique.drawString(Menu[button].xString, Menu[button].yString, 3, Menu[button].title);
				EcranGraphique.flush();
			}
				
			Menu[button].isClicked = (Menu[button].isClicked) ? false : true;
			
			if(button == Menu.length-2)
				Menu[Menu.length-2].title = (Menu[Menu.length-2].title == "Pause")?"Demarrer":"Pause";
			return 1;
		}
		return -1;
	}
	
	static int isOnButton(Button [] Menu)
	{
		int [] coord = returnMouse();
		for(int i = 0; i < Menu.length; i++)
		{
			if(coord[0] <= (Menu[i].x + Menu[i].length) && coord[0] >= Menu[i].x)
			{
				if(coord[1] <= Menu[i].y + Menu[i].width && coord[1] >= Menu[i].y)
				{
					return i;
				}
			}
		}
		return -1;
	}
}