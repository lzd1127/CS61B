public class DList {

	protected DListNode head;
	protected long size;
	protected int min = Integer.MIN_VALUE;


	public DList() {
		head = new DListNode();
		head.item = new int[] {min, min, min, min};
		head.next = head;
		head.prev = head;
		size = 0;
	}

	public DList(int[] i) {
		head = new DListNode();
		head.item = new int[] {min, min, min, min};
		head.next = new DListNode();
		head.next.item = i;
		head.prev = head.next;
		head.next.prev = head;
		head.prev.next = head;
		size = 1;
	}

	public DList(int[] x, int[] y) {
		head = new DListNode();
		head.item = new int[] {min, min, min, min};
		head.next = new DListNode();
		head.next.item = x;
		head.prev = new DListNode();
		head.prev.item = y;
		head.next.prev = head;
		head.next.next = head.prev;
		head.prev.next = head;
		head.prev.prev = head.next;
		size = 2;
	}

	public void insertFront(int[] i) {
		DListNode curr = new DListNode(i);
		if (size == 0) {
			head.next = curr;
			head.prev = curr;
			curr.next = head;
			curr.prev = head;
		} else {
			curr.next = head.next;
			head.next.prev = curr;
			head.next = curr;
			curr.prev = head;
		}
		size++;
	}

	public void insertEnd(int[] i) {
		DListNode curr = new DListNode(i);
		if (size == 0) {
			head.prev = curr;
			head.next = curr;
			curr.prev = head;
			curr.next = head;
		} else {
			curr.prev = head.prev;
			head.prev.next = curr;
			head.prev = curr;
			curr.next = head;
		}
		size++;
	}

	public void insertAfter(DListNode n1, DListNode n2){
		n1.next.prev = n2;
		n2.next = n1.next;
		n1.next = n2;
		n2.prev = n1;
		size++;
	}

	public void insertBefore(DListNode n2, DListNode n1) {
		n2.prev.next = n1;
		n1.prev = n2.prev;
		n2.prev = n1;
		n1.next = n2;
		size++;
	}

	public void removeNode(DListNode curr) {
		curr.prev.next = curr.next;
		curr.next.prev = curr.prev;
		size--;
	}

	public String toString() {
    	String result = "[  ";
    	DListNode current = head.next;
    	while (current != head) {
      		result += current.item + "  ";
      		current = current.next;
    	}
    	return result + "]";
	}
}
