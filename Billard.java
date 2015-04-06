public class Billard
{
	static double dt = 0.1;

	public static void main(String[] args)
	{
		//taille de la base du triangle pour un positionnement classique
		int k = 5;

		//nombre de boule totale pour positionnement classique
		int n = (k * (k+1)/2) + 1;

		//nombre de boule pour positionnement en ligne:
		//int n = 4;

		//inititalisation des tableau de boule
		Box box = new Box();
		Window win = new 
		box.balls = new Ball[n + 1];
		
		//int [] coords = new int[2];
		Button [] Menu = new Button[8];
		RenderBall [] renderballs = new RenderBall[n + 1];
		
		//variable determinant si on a deja utilisé le tire manuel
		int a = 0;
		
		
		int output = 0;
		Vector mouse = new Vector();
		makeBalls(box.balls, n);
		ballsColoring(n, renderballs);
		/*
		for(int i = 0; i < n ; i++)
		{
			//Def du tableau de position
			balls[i] = new Ball();
			balls[i].r = 0.03;
			balls[i].v.x = 0;//(Math.random() - 0.5) * 0.001;
			balls[i].v.y = 0;//(Math.random() - 0.5) * 0.001;
			balls[i].m = 1;

			//Def du tableau de rendu
			renderballs[i] = new RenderBall();
			renderballs[i].r = (int)(balls[i].r * scale);
			renderballs[i].couleur.r = ((int)(Math.random() * 250));
			renderballs[i].couleur.v = ((int)(Math.random() * 250));
			renderballs[i].couleur.b = ((int)(Math.random() * 250));

            if (i == n - 1)
            {
            	renderballs[i].couleur.r = 255;
    			renderballs[i].couleur.v = 255;
    			renderballs[i].couleur.b = 255;
                balls[i].v.x = 0.3;
                balls[i].v.y = 0;
            }
		}
		*/
		for(int i = 0; i < Menu.length; i++ )
			Menu[i] = new Button();
		
		Menu[0].title = "Billard classique" ;
		Menu[1].title = "Conditions initale manuelle" ;
		Menu[2].title = "Vitesse" ;
		Menu[3].title = "Boule blanche uniquement" ;
		Menu[4].title = "Boules en ligne";
		Menu[5].title = "Reset" ;
		Menu[6].title = "Demarrer" ;
		Menu[7].title = "Quitter" ;
		makeMenu(50, Menu,'r');
		
		EcranGraphique.init(50,50,length + 80 + Menu[0].lengthB , width + 300, length + 30 + Menu[3].lengthB, width + 20 + 2 * Menu[0].widthB, "billard");
		iniWin();
		
		EcranGraphique.setColor(70, 70, 70);
		EcranGraphique.fillRect(length + 22, 0, length + Menu[0].lengthB, width + 20+2 * Menu[0].widthB);
		calcPosTriangle(balls , k);
		//calcPosLine(balls , n);
		EcranGraphique.flush();
		//EcranGraphique.init(50,50,length+100, width+100, length + 20, width + 20, "billard");
		//String strCoords;
		//EcranGraphique.fillRect(0, 0, length + 20, width + 20);
		
        double t;
        Menu[Menu.length-2].isClicked = true;
		while(true)
		{
			if(isClickButton(Menu, 5) == 1)
            {
				ballsConstructor(n, balls, renderballs);
				ballsColoring(n, renderballs);
            	calcPosTriangle(balls , k);
            	a = 0;
            }
			
			if(isClickButton(Menu, 4) == 1)
            {
				ballsConstructor(n, balls, renderballs);
				ballsColoring(n, renderballs);
            	calcPosLine(balls , n);
            	a = 0;
            }

			if(isClickButton(Menu, Menu.length-2) != 1 && !Menu[Menu.length-2].isClicked )
			{
				Menu[1].isClicked = false;
				a = 1;
				t = 0;
	            while (t < dt)
				    t += update(balls, n, dt - t);
			}
			
			
			renderBox(renderballs, balls,n);
			renderReturnM(returnMouse());
			//System.out.println(Menu[Menu.length-2].isClicked);
			
			if(Menu[Menu.length-2].isClicked && a !=1)
            {
				
				
				if((isClickButton(Menu, 1) == 1 || Menu[1].isClicked))
				{
					//System.out.println(isClickButton(Menu, 1));
					if(EcranGraphique.getMouseButton() == 1)
					{
						EcranGraphique.setColor(0, 0, 0);
						mouse.x = returnMouse()[0];
						mouse.y = returnMouse()[1];
						
						if(returnMouse()[0] > length)
						{
							mouse.x = length;
						}
						
						if(returnMouse()[1] > width)
						{
							mouse.y = width;
						}
						
						EcranGraphique.drawLine(renderballs[n-1].x,renderballs[n-1].y,(int)mouse.x,(int)mouse.y);
						
						
						if(EcranGraphique.getMouseState() == 1)
						{
							balls[n-1].v.x = -((double)(mouse.x - balls[n-1].p.x * scale)/scale);
							balls[n-1].v.y = -((double)((-mouse.y + width) - balls[n-1].p.y * scale)/scale);
							//balls[n-1].v.x = (mouse.x * module(mouse)/module(balls[n-1].v))/scale;
							//System.out.println(module(mouse));
							//balls[n-1].v.y = (mouse.y * module(mouse)/module(balls[n-1].v))/scale;
							//System.out.println((mouse.x - balls[n-1].p.x* scale) +" , "+ ((-mouse.y + width) - balls[n-1].p.y * scale) + " , " + balls[n-1].v.x + " , " + balls[n-1].v.y);
							//EcranGraphique.wait(4541121);
							
							a = 1;
							
							Menu[Menu.length-2].title = (Menu[Menu.length-2].title == "Pause")?"Demarrer":"Pause";
							Menu[Menu.length-2].isClicked = false;
							Menu[1].isClicked = false;
							
						}
						
					}
					
					//Menu[Menu.length-2].isClicked = true;
				}
				
            }
			//System.out.println();f
			output = stringBuilder(output);
			EcranGraphique.setColor(255, 255, 255);
			//EcranGraphique.drawString(10, width + 100, 3, output);
			renderMenu(Menu);
			EcranGraphique.flush();
			EcranGraphique.wait(10);
		}

	}
}
