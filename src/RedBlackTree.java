/*
 * CS 3345
 * Project 4
 * Hongyun Du
 * hxd171530
 */

public class RedBlackTree<E extends Comparable<E>> {
	private static boolean RED = false;
	private static boolean BLACK = true;
	public Node<E> root;
	int size = 0;

	public boolean insert(E element) throws NullPointerException {

		if (element == null)
			throw new NullPointerException("null input");
		Node<E> parent = null;
		Node<E> current = root;
		if (contains(element) == true)
			return false;
		// if the root is empty, then initialize it.
		if (root == null) {
			root = createNode(element, null, RED);
			size++;
			Rebalancing(root);
			return true;
		} else {
			while (current != null) {
				// find the location that the new node should be
				if (element.compareTo(current.element) == 0) {
					return false;
				} else if (element.compareTo(current.element) < 0) {
					parent = current;
					current = parent.leftChild;
				} else {
					parent = current;
					current = parent.rightChild;
				}
			}
			// depends on the Key value to create the left or right node.
			if (current == null) {
				if (element.compareTo(parent.element) < 0) {
					parent.leftChild = createNode(element, parent, RED);
					size++;
					Rebalancing(parent.leftChild);
					return true;
				} else {
					parent.rightChild = createNode(element, parent, RED);
					size++;
					Rebalancing(parent.rightChild);
					return true;
				}

			}
		}
		return false;
	}

	private void Rebalancing(Node<E> current) {

		if (current == root) {
			current.color = BLACK;
			return;
		}
		Node<E> grandpa = current.parent.parent;
		if (getColor(current.parent) == RED) {
			// parent is RED
			if (grandpa.rightChild == current.parent) {
				// its parent is its grandpa's right child
				if (getColor(grandpa.leftChild) == RED) {
					// its parent's sibling is RED
					// need re-color
					// re-color
					current.parent.parent.leftChild.color = BLACK;
					current.parent.parent.rightChild.color = BLACK;
					current.parent.parent.color = RED;
					if (current.parent.parent != root)
						if (current.parent.parent.parent.color == RED)
							Rebalancing(current.parent.parent);
						else
							;
					else
						Rebalancing(current.parent.parent);
				} else {
					// its parent's sibling is BLACK
					// needs re-balancing
					// R-L
					// Do right rotation
					if (current == current.parent.leftChild) {
						// current node is its parent's left child, and its parent is the grandpa's
						// right child
						// BLACK uncle
						// re-balancing
						Node<E> tempParent = new Node<E>(current.parent);
						Node<E> tempGrandpa = new Node<E>(current.parent.parent);
						current.parent.leftChild = current.rightChild;
						if (current.rightChild != null)
							current.rightChild.parent = current.parent;
						current.rightChild = current.parent;
						current.parent.parent.rightChild = current;
						current.parent.parent = current;
						current.parent = tempParent.parent;
						// After right rotation
						current.parent.rightChild = current.leftChild;
						if (current.leftChild != null)
							current.leftChild.parent = current.parent;
						current.leftChild = current.parent;
						current.parent.parent = current;
						current.parent = tempGrandpa.parent;

						if (tempGrandpa.parent != null) {
							if (tempGrandpa.element.compareTo(tempGrandpa.parent.leftChild.element) == 0)
								current.parent.leftChild = current;
							else
								current.parent.rightChild = current;
						}
						current.color = BLACK;
						current.leftChild.color = RED;
						current.rightChild.color = RED;
						if (current.parent == null)
							root = current;
					} else {
						// current node is its parent's right child, and its parent is the grandpa's
						// right child
						// re-balancing
						// L
						Node<E> tempGrandpa = new Node<E>(current.parent.parent);
						current.parent.parent.rightChild = current.parent.leftChild;
						if (current.parent.leftChild != null)
							current.parent.leftChild.parent = current.parent.parent;
						current.parent.leftChild = current.parent.parent;
						current.parent.parent.parent = current.parent;
						current.parent.parent = tempGrandpa.parent;

						if (tempGrandpa.parent != null) {
							if (tempGrandpa.parent.leftChild.element.compareTo(tempGrandpa.element) == 0)
								current.parent.parent.leftChild = current.parent;
							else
								current.parent.parent.rightChild = current.parent;
						}
						current.parent.color = BLACK;
						current.parent.leftChild.color = RED;
						current.parent.rightChild.color = RED;
						if (current.parent.parent == null)
							root = current.parent;
					}

				}
			} else {
				// it's parent is its grandpa's left child
				if (getColor(grandpa.rightChild) == RED) {
					// its parent's sibling is RED
					// need re-color
					// current node is its parent's left child, and its parent is the grandpa's
					// right child
					// re-color
					current.parent.parent.leftChild.color = BLACK;
					current.parent.parent.rightChild.color = BLACK;
					current.parent.parent.color = RED;
					if (current.parent.parent != root)
						if (current.parent.parent.parent.color == RED)
							Rebalancing(current.parent.parent);
						else
							;
					else
						Rebalancing(current.parent.parent);
				} else {
					// its parent's sibling is BLACK
					// needs re-balancing
					if (current == current.parent.leftChild) {
						// current node is its parent's left child, and its parent is the grandpa's
						// left child
						// BLACK uncle
						// re-balancing
						// R
						Node<E> tempGrandpa = new Node<E>(current.parent.parent);
						current.parent.parent.leftChild = current.parent.rightChild;
						if (current.parent.rightChild != null)
							current.parent.rightChild.parent = current.parent.parent;
						current.parent.rightChild = current.parent.parent;
						current.parent.parent.parent = current.parent;
						current.parent.parent = tempGrandpa.parent;

						if (tempGrandpa.parent != null) {
							if (tempGrandpa.parent.leftChild.element.compareTo(tempGrandpa.element) == 0)
								current.parent.parent.leftChild = current.parent;
							else
								current.parent.parent.rightChild = current.parent;
						}
						current.parent.color = BLACK;
						current.parent.leftChild.color = RED;
						current.parent.rightChild.color = RED;
						if (current.parent.parent == null)
							root = current.parent;

					} else {
						// current node is its parent's right child, and its parent is the grandpa's
						// left child
						// re-balancing
						// L-R
						Node<E> tempParent = new Node<E>(current.parent);
						Node<E> tempGrandpa = new Node<E>(current.parent.parent);
						current.parent.rightChild = current.leftChild;
						if (current.leftChild != null)
							current.leftChild.parent = current.parent;
						current.leftChild = current.parent;
						current.parent.parent.leftChild = current;
						current.parent.parent = current;
						current.parent = tempParent.parent;
						// After right rotation
						current.parent.leftChild = current.rightChild;
						if (current.rightChild != null)
							current.rightChild.parent = current.parent;
						current.rightChild = current.parent;
						current.parent.parent = current;
						current.parent = tempGrandpa.parent;

						if (tempGrandpa.parent != null) {
							if (tempGrandpa.parent.leftChild == tempGrandpa)
								current.parent.leftChild = current;
							else
								current.parent.rightChild = current;
						}
						current.color = BLACK;
						current.leftChild.color = RED;
						current.rightChild.color = RED;
						if (current.parent == null)
							root = current;
					}
				}
			}

		} else
			return;// doing nothing
	}

	private Node<E> createNode(E key, Node<E> P, boolean C) {
		return new Node<E>(key, P, C);
	}

	public boolean contains(Comparable<E> object) {
		Node<E> current = root;
		while (current != null) {
			if (object.compareTo(current.element) == 0) {
				return true;
			} else if (object.compareTo(current.element) < 0) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
		}
		return false;
	}

	private boolean getColor(Node<E> e) {
		if (e == null)
			return BLACK;
		else
			return e.color;
	}

	public String toString() {
		return PreOrder(root);
	}

	private String PreOrder(Node<E> e) {
		// preOder travel.
		String result = "";
		if (e == null)
			;
		else {
			if (e.color == RED)
				result = result + "*";
			result = result + e.element + " ";
			result = result + PreOrder(e.leftChild);
			result = result + PreOrder(e.rightChild);
		}
		return result;
	}

	public static class Node<E extends Comparable<E>> {

		protected E element;
		protected Node<E> leftChild;
		protected Node<E> rightChild;
		protected Node<E> parent;
		protected boolean color;

		public Node() {
			element = null;
			leftChild = null;
			rightChild = null;
			parent = null;
			color = BLACK;
		}

		public Node(E key, Node<E> P, boolean C) {
			element = key;
			leftChild = null;
			rightChild = null;
			parent = P;
			color = C;
		}

		public Node(Node<E> e) {
			element = e.element;
			leftChild = e.leftChild;
			rightChild = e.rightChild;
			parent = e.parent;
			color = e.color;
		}
	}

}
