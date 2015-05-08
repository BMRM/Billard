/**
 * \file Billard.java
 * \brief Fonction main, instanciation des modules principaux
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */

/**
 * \class Billard
 * \brief Classe principale du programme
 * \authors Baptiste Minervini
 * \authors Romain Mekarni
 */
public class Billard
{
	public static void main(String[] args)
	{
        long    elapsed = 0; //Calcul du temps écoulé
		boolean run = true; //Flag exécution programme
		int		menu = 0; //Etat du menu
		Box box = Box.make(); // Moteur physique
		Window win = Window.make(box); // Moteur graphique
        Input input = new Input();

		while (run)
		{
			menu = Menu.update(win.menu, box, win);
			if (menu == 0)
				Box.posTriangle(box);
			else if (menu == 1)
				Box.posLine(box);
			else if (menu == 2)
			{
				input.queue = true;
				box.run = false;
			}
			else if (menu == 3)
				box.run = (box.run == true) ? false : true;
			else if (menu == 10)
                run = false;
            else if (menu > 3 && menu < 10)
            {
                input.kb = true;
                input.out = menu;
            }
            Input.update(input, box, win);
            Box.update(box);
            Window.update(win, box, input);
            if (win.fps > 0) // Si le blocage fps est activé
			    EcranGraphique.wait((int)Math.max(0, 1000 / (double)win.fps - (System.nanoTime() - elapsed) / 1000000));
            win.fps = (int)(1000 / (double)((System.nanoTime() - elapsed) / 1000000)); // Récupère la valeur courante du fps
            elapsed = System.nanoTime(); // Temps écoulé
			Window.render(win, box, input);
		}
		EcranGraphique.exit();
	}
}

/**
 * \mainpage Projet Billard
 * \authors Baptiste Minervini baptiste.minervini@outlook.com
 * \authors Romain Mekarni romain.mekarni@gmail.com
 * \date Licence 1 Semestre 2
 * \section intro_sec Présentation
 * Ce programme est une simulation physique d'un billard qui prend en compte les chocs entre boules et avec les murs. Il exploite un module physique indépendant du graphique. L'affichage est réglé selon un FPS, et le moteur physique selon un dt (pas de temps). Ces deux variables sont indépendantes et permettent d'observer à plusieurs vitesses l'évolution du système physique.
 * \section install_sec Installation
 * - Clôner le dépôt git à l'url http://github.com/BMRM/Billard.git \n
 * - javac *.java pour compiler \n
 * - java Billard pour exécuter\n
 * - java -jar Billard.jar pour exécuter l'archive déjà compilée
 * \section tools_subsec Dépendances
 * - EcranGraphique
 * - Clavier
 * - Ecran
 * \section devel_sec Développement
 * \subsection devel1_subsec Premier jet graphique
 * Afin de prendre en main l'environnement, les bibliothèques, le premier jet est basé essentiellement sur l'aspect graphique. A partir de types agrégés, on affiche le billard et les Boules qui se déplacent sans prise en compte des chocs.
 * \subsection devel2_subsec Séparation physique et graphique
 * Le billard est un système physique qui possède ses propres unités de mesure de temps et de distance qui ne sont pas les mêmes que pour le moteur graphique. Par ailleurs, la fonction de l'outil graphique est d'apporter un visuel sur le système. C'est un outil que l'on peut ajouter pour mieux observer la simulation. Il devient donc évident de séparer dans le code les fonctions physiques du graphique et on obtient alors plusieurs classes aux rôles bien définies. Ainsi, la classe Box s'occupe du système physique avec la classe Ball qui représente physiquement une boule de billard. La partie graphique est gérée par les classes Window et RenderBall.
 * \subsection devel3_subsec Ajout des chocs entre boules et contre les murs
 * A l'aide de conditions, on change les vitesses et les angles des boules mais très vite on observe les limites de ce modèle trop simpliste. Premièrement, les chocs ne sont pas détectés au bon moment précis (parfois trop tôt, ou trop tard) ce qui détruit progressivement le réalisme de la simulation. Ensuite, les angles ne sont pas justes car ils dépendent de plusieurs paramètres et nécessitent des calculs plus compliqués. Enfin, le modèle ne prend pas en compte plusieurs chocs simultanés (très visible au début de la simulation quand les boules sont disposées en triangle).
 * \subsection devel4_subsec Implémentation des calculs physiques
 * Avec l'aide d'internet, on refait les calculs physiques et on les implémente dans le programme (voir Physique et Mathématiques). Les vitesses sont correctes mais les angles d'après choc entre boules ne sont pas toujours exacts : les directions s'en approchent, mais souvent les sens sont inversés. Le programme bug assez souvent car il détecte des chocs à des temps très faibles (trop faible pour faire avancer les boules) ce qui provoque une boucle infinie.
 * \subsection devel5_subsec Gestion de la précision synchronisaton avec la boucle principale
 * Pour régler ce problème de précision, on a choisit d'utiliser la classe BigDecimal fournie par Java et qui nous permet de représenter des nombres décimaux selon un contexte mathématique (nombre de chiffres significatifs et méthode d'arrondissement) On remarque qu'avec une précision de moins de 5, certains chocs sont ignorés, alors qu'au dessus de 10 ou 15, la boucle infinie peut éventuellement faire planter le programme. A 8, on a relevé aucun bug apparent. \n
 * Pour faire évoluer le système physique, on utilise l'algorithme suivent :
 *  - Pour chaque boules
 *      - Pour chaque boules
 *          - Calculer le temps de prochain choc entre les boules
 *          - S'il est positif et plus proche ou égal que le choc le plus proche
 *              - S'il est plus petit
 *                  - Vider la pile des évènements
 *                  - Mémoriser ce choc comme étant le plus proche
 *              - Ajouter le choc à la pile des évènements
 *      - Calculer le temps de prochain choc entre la boule et les murs
 *      - S'il est positif et plus proche que le choc le plus proche
 *          - S'il est plus petit
 *              - Vider la pile des évènements
 *              - Mémoriser ce choc comme étant le plus proche
 *      - Ajouter le choc à la pile des évènements
 *  - Déplacer les boules au choc le plus proche (dt si inexistant)
 *  - Décharger la pile et appliquer tous les évènements
 *  - Renvoyer le temps de choc le plus proche (dt si inexistant)
 *  .
 * Cet algorithme est appelé plusieurs fois tant qu'il n'a pas atteint le dt imposé par le programme (la boucle principale). Cela permet de garder notre système physique cohérent même si l'affichage graphique varie en fonction des performances de l'ordinateur (un dt à 500 ou 1000 réduit considérablement le FPS car les calculs sont très conséquents).
 * \section mathphys_sec Physique et Mathématiques
 * \subsection dtChocBalls_subsec Temps de choc entre deux boules
 * \f[
 * a.dt^2 + b.dt + c = 0
 * \f]
 * \f{eqnarray*}{
 * \left \{ \begin{array}{r c l}
 *  a &=& (b_{1.v.x} - b_{2.v.x})^2 + (b_{1.v.y} - b_{2.v.y})^2 \\
 *  b &=& 2(b_{1.v.x} - b_{2.v.x})(b_{1.p.x} - b_{2.p.x}) + 2(b_{1.v.y} - b_{2.v.y})(b_{1.p.y} - b_{2.p.y}) \\
 *  c &=& (b_{1.p.x} - b_{2.p.x})^2 + (b_{1.p.y} - b_{2.p.y})^2 - (b_{1.r} + b_{2.r})^2
 *  \end{array} \right .
 * \f}
 * \subsection ChocBalls_subsec Calcul des vitesses et angles après choc entre deux boules
 * \f{eqnarray*}{
 * \theta_1' &=& \arctan \left( \frac{m_1 - m_2}{m_1 + m_2} \tan \theta_1 + \frac{2 m_2}{m_1 + m_2} \frac{v_2}{v_1} \frac{\sin \theta_2}{\cos \theta_1} \right) \\
 * v_1' &=& \sqrt{\left( \frac{m_1 - m_2}{m_1 + m_2} v_1 \sin \theta_1 + \frac{2 m_2}{m_1 + m_2} v_2 \sin \theta_2 \right)^2 + (v_1 \cos \theta_1)^2} \\
 * \theta_2' &=& \arctan \left( \frac{m_2 - m_1}{m_1 + m_2} \tan \theta_2 + \frac{2 m_1}{m_1 + m_2} \frac{v_1}{v_2} \frac{\sin \theta_1}{\cos \theta_2} \right) \\
 * v_2' &=& \sqrt{\left( \frac{m_2 - m_1}{m_1 + m_2} v_1 \sin \theta_2 + \frac{2 m_1}{m_1 + m_2} v_1 \sin \theta_1 \right)^2 + (v_2 \cos \theta_2)^2} \\
 * \f}
 * \section difficulte_sec Difficultés rencontrées
 * \subsection converPolaire_subsec Conversion entre système polaire et cartésien
 * à partir de <a href="http://fr.wikipedia.org/wiki/Coordonn%C3%A9es_polaires#Conversion_entre_syst.C3.A8me_polaire_et_cart.C3.A9sien">Wikipédia</a>
 */


