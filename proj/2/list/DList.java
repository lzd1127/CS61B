/* DList.java */

package list;

/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel (dummy) node at the head
 *  of the list.
 *
 *  DO NOT CHANGE ANY METHOD PROTOTYPES IN THIS FILE.
 */

public class DList {

  /**
   *  head references the sentinel node.
   *  size is the number of items in the list.  (The sentinel node does not
   *       store an item.)
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  protected DListNode head;
  protected int size;

  /* DList invariants:
   *  1)  head != null.
   *  2)  For any DListNode x in a DList, x.next != null.
   *  3)  For any DListNode x in a DList, x.prev != null.
   *  4)  For any DListNode x in a DList, if x.next == y, then y.prev == x.
   *  5)  For any DListNode x in a DList, if x.prev == y, then y.next == x.
   *  6)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   */

  /**
   *  newNode() calls the DListNode constructor.  Use this class to allocate
   *  new DListNodes rather than calling the DListNode constructor directly.
   *  That way, only this method needs to be overridden if a subclass of DList
   *  wants to use a different kind of node.
   *  @param item the item to store in the node.
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   */
  protected DListNode newNode(Object item, DListNode prev, DListNode next) {
    return new DListNode(item, prev, next);
  }

  /**
   *  DList() constructor for an empty DList.
   */
  public DList() {
    //  Your solution here.
    head = newNode(null,null,null);
    head.next = head;
    head.prev = head;
    size = 0;
  }

  /**
   *  isEmpty() returns true if this DList is empty, false otherwise.
   *  @return true if this DList is empty, false otherwise. 
   *  Performance:  runs in O(1) time.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  length() returns the length of this DList. 
   *  @return the length of this DList.
   *  Performance:  runs in O(1) time.
   */
  public int length() {
    return size;
  }

  /**
   *  insertFront() inserts an item at the front of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertFront(Object item) {
    // Your solution here.
    DListNode newFront = newNode(item,head,head.next);
    head.next.prev = newFront;
    head.next = newFront;
    size++;
  }

  /**
   *  insertBack() inserts an item at the back of this DList.
   *  @param item is the item to be inserted.
   *  Performance:  runs in O(1) time.
   */
  public void insertBack(Object item) {
    // Your solution here.
    DListNode newBack = newNode(item,head.prev,head);
    head.prev.next = newBack;
    head.prev = newBack;
    size++;
  }

  /**
   *  front() returns the node at the front of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the front of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode front() {
    // Your solution here.
    if (isEmpty()) {
      return null;
    } else {
      return head.next;
    }
  }

  /**
   *  back() returns the node at the back of this DList.  If the DList is
   *  empty, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @return the node at the back of this DList.
   *  Performance:  runs in O(1) time.
   */
  public DListNode back() {
    // Your solution here.
    if (isEmpty()) {
      return null;
    } else {
      return head.prev;
    }
  }

  /**
   *  next() returns the node following "node" in this DList.  If "node" is
   *  null, or "node" is the last node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose successor is sought.
   *  @return the node following "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode next(DListNode node) {
    // Your solution here.
    if ((node == null) || (node.next == head)) {
      return null;
    } else {
      return node.next;
    }
  }

  /**
   *  prev() returns the node prior to "node" in this DList.  If "node" is
   *  null, or "node" is the first node in this DList, return null.
   *
   *  Do NOT return the sentinel under any circumstances!
   *
   *  @param node the node whose predecessor is sought.
   *  @return the node prior to "node".
   *  Performance:  runs in O(1) time.
   */
  public DListNode prev(DListNode node) {
    // Your solution here.
    if ((node == null) || (node.prev == head)) {
      return null;
    } else {
      return node.prev;
    }
  }

  /**
   *  insertAfter() inserts an item in this DList immediately following "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item after.
   *  Performance:  runs in O(1) time.
   */
  public void insertAfter(Object item, DListNode node) {
    // Your solution here.
    if (node == null) {
      return;
    } else {
      DListNode newNode = newNode(item,node,node.next);
      node.next.prev = newNode;
      node.next = newNode;
      size++;
    }
  }

  /**
   *  insertBefore() inserts an item in this DList immediately before "node".
   *  If "node" is null, do nothing.
   *  @param item the item to be inserted.
   *  @param node the node to insert the item before.
   *  Performance:  runs in O(1) time.
   */
  public void insertBefore(Object item, DListNode node) {
    // Your solution here.
    if (node == null) {
      return;
    } else {
      DListNode newNode = newNode(item,node.prev,node);
      node.prev.next = newNode;
      node.prev = newNode;
      size++;
    }
  }

  /**
   *  remove() removes "node" from this DList.  If "node" is null, do nothing.
   *  Performance:  runs in O(1) time.
   */
  public void remove(DListNode node) {
    // Your solution here.
    if (node == null) {
      return;
    } else {
      node.prev.next = node.next;
      node.next.prev = node.prev;
      node.next = null;
      node.prev = null;
      size--;
    }
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }

  public static void main(String[] args) {
    DList list1 = new DList();
    System.out.println("Front node of List 1 should be null: " + list1.front());
    System.out.println("Back node of List 1 should be null: " + list1.back());
    System.out.println("Length of List 1 should be 0: " + list1.length());
    System.out.println("List 1 should be empty: " + list1.isEmpty());
    list1.insertFront(5);
    list1.insertFront(4);
    list1.insertFront(3);
    list1.insertFront(1);
    System.out.println("List 1 should be [1 3 4 5]: " + list1);
    list1.insertAfter(2,list1.head.next);
    System.out.println("List 1 should be [1 2 3 4 5]: " + list1);
    list1.remove(list1.head.next.next);
    System.out.println("List 1 should be [1 3 4 5]: " + list1);
    list1.insertBefore(2,list1.head.next.next);
    System.out.println("List 1 should be [1 2 3 4 5]: " + list1);
    list1.insertBack(6);
    list1.insertBack(7);
    list1.insertBack(8);
    list1.insertBack(10);
    System.out.println("List 1 should be [1 2 3 4 5 6 7 8 10]: " + list1);
    list1.insertBefore(9,list1.head.prev);
    System.out.println("List 1 should be [1 2 3 4 5 6 7 8 9 10]: " + list1);
    list1.remove(list1.head.prev);
    System.out.println("List 1 should be [1 2 3 4 5 6 7 8 9]: " + list1);
    list1.insertAfter(10,list1.head.prev);
    System.out.println("List 1 should be [1 2 3 4 5 6 7 8 9 10]: " + list1);
    System.out.println("Front node of List 1 should be 1: " + list1.front().item);
    System.out.println("Back node of List 1 should be 10: " + list1.back().item);
    System.out.println("Node after 1 of List 1 should be 2: " + list1.next(list1.head.next).item);
    System.out.println("Node before 10 of List 1 should be 9: " + list1.prev(list1.head.prev).item);
    System.out.println("Length List 1 should be 10: " + list1.length());
  }
}
