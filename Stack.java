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

	static Stack	pull(Stack stack)
	{
		stack.first = stack.first.next;
		return stack;
	}

    static void     clear(Stack stack)
    {
        while (stack.first != null)
            pull(stack);
    }

	static boolean	isEmpty(Stack stack)
	{
		return (stack.first == null);
	}
}

