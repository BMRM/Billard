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
	
	static Stack	push(Stack stack, Event event)
	{
		Item item = new Item();
		item.event = event;
		item.next = stack.first;
		stack.first = item;
		return stack;
	}
	
	static Stack	pull(Stack stack)
	{
		stack.first = stack.first.next;
		return stack;
	}
	
	static boolean	isEmpty(Stack stack)
	{
		return (stack.first == null);
	}
}

