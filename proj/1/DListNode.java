public class DListNode {

	public int[] item;
	public DListNode prev;
	public DListNode next;


	DListNode() {
		item = new int[4];
		prev = null;
		next = null;
	}

	DListNode(int[] i) {
		item = i;
		prev = null;
		next = null;
	}
}