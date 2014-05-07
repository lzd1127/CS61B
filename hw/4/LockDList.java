/* LockDList.java */

package list;

public class LockDList extends DList {

	protected LockDListNode newNode(Object i, DListNode p, DListNode n) {
		return new LockDListNode(i, p, n);
	}

	public void lockNode(DListNode node) {
		((LockDListNode) node).locked = true;
	}

	public void remove(DListNode node) {
		if (((LockDListNode) node).locked) return;
		super.remove(node);
	}

	public static void main(String[] args) {
		System.out.println("Tests Start!");
		LockDList test = new LockDList();
		System.out.println("\nTest empty. true: " + test.isEmpty());
		
		test.insertFront(3);
		test.insertFront(2);
		test.insertBack(5);
		test.insertBack(7);
		System.out.println("\nPrint list. [2 3 5 7]: " + test);

		test.lockNode(test.front());
		test.lockNode(test.back());
		test.remove(test.front());
		test.remove(test.back());
		System.out.println("\nPrint list. [2 3 5 7]: " + test);
		
		DListNode three = test.next(test.front());
		DListNode five = test.prev(test.back());
		test.remove(three);
		test.remove(five);
		System.out.println("\nPrint list. [2 7]: " + test);
	}
}