class Event
{
	int		type;
	Ball	b1;
	Ball	b2;
}

class Item
{
	Event	event;
	Item	next = null;
}

public class Stack
{
	Item	first = null;
/**
 * Remplis la pile
 * @param stack
 * @param type
 * @param b1
 * @param b2
 * @return
 */
	static Stack	push(Stack stack, int type, Ball b1, Ball b2)
	{
		Item item = new Item();
		item.event = new Event();
        item.event.type = type;
        item.event.b1 = b1;
        item.event.b2 = b2;
		item.next = stack.first;
		stack.first = item;
		return stack;
	}
/**
 * Retire un evenement de la pile
 * @param stack
 * @return
 */
	static Stack	pull(Stack stack)
	{
        if (stack.first != null)
		    stack.first = stack.first.next;
		return stack;
	}
/**
 * Vide la pile
 * @param stack
 */
    static void     clear(Stack stack)
    {
        while (stack.first != null)
            pull(stack);
    }
/**
 * Verifie si la pile est pleine
 * @param stack
 * @return
 */
	static boolean	isEmpty(Stack stack)
	{
		return (stack.first == null);
	}
/**
 * Renvoie la taille de la pile
 * @param stack
 * @return
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

