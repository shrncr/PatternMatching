/* ***************************************************
 * Sara Hrnciar
 * 10/3/2024
 *
 * Node Class - handles any form of data
 *************************************************** */

class Node<Type>
{
	private Type data;
	private Node<Type> link;

	// constructor
	public Node()
	{
		this.data = null;
		this.link = null;
	}

	// accessor and mutator for the data component
	public Type getData()
	{
		return this.data;
	}

	public void setData(Type data)
	{
		this.data = data;
	}

	// accessor and mutator for the link component
	public Node<Type> getLink()
	{
		return this.link;
	}

	public void setLink(Node<Type> link)
	{
		this.link = link;
	}
}
