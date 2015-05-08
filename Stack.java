/**
 * \file Stack.java
 * \brief Structure de données de type pile
 * \author Romain Mekarni
 */

/**
 * \class Event
 * \brief Représente un évènement de type choc
 * \author Romain Mekarni
 */
class Event
{
	int		type;///<Type de choc; 0 boule/boule; 1 boule/mur
	Ball	b1;
	Ball	b2;
}

/**
 * \class Item
 * \brief Element de Stack qui contient un Event
 * \author Romain Mekarni
 */
class Item
{
	Event	event;///<Evènement mémorisé
	Item	next = null;///<Référence sur l'élément suivant de la pile
}

/**
 * \class Stack
 * \brief Conteneur de Item de type pile
 * \author Romain Mekarni
 */
public class Stack
{
	Item	first = null;///<Premier élément de la pile

    /**
     * \brief Ajoute un élément à la pile
     * \param type 0 pour un choc boule/boule et 1 pour boule/mur
     * \author Romain Mekarni
     */
    static Stack	push(Stack stack, int type, Ball b1, Ball b2)
	{
		Item item = new Item();
		item.event = new Event();// Event contient l'évènement à mémoriser
        item.event.type = type;
        item.event.b1 = b1;
        item.event.b2 = b2;
		item.next = stack.first;// On fait pointer le prochain élément sur le début de la pile
		stack.first = item;// On fait pointer la pile sur ce nouvel élément
		return stack;
	}
/**
 * \brief Retire un élément
 * \author Romain Mekarni
 */
	static Stack	pull(Stack stack)
	{
        if (stack.first != null)
		    stack.first = stack.first.next;
		return stack;
	}
/**
 * \brief Vide la pile
 * \author Romain Mekarni
 */
    static void     clear(Stack stack)
    {
        while (stack.first != null)
            pull(stack);
    }
/**
 * \brief Vérifie si la pile est pleine
 */
	static boolean	isEmpty(Stack stack)
	{
		return (stack.first == null);
	}
/**
 * \brief Renvoie la taille de la pile
 * \author Romain Mekarni
 */
    static int  size(Stack stack)
    {
        int i = 0;
        Item p = stack.first;
        while (p != null)
        {
            i++;
            p = p.next;
        }
        return i;
    }
}

