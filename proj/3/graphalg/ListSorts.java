/* ListSorts.java */
package graphalg;

import list.*;

public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
    LinkedQueue queueOfQueues = new LinkedQueue();
    while (q.size() > 0) {
      try {
        Comparable item = (Comparable) q.dequeue();
        LinkedQueue queue = new LinkedQueue();
        queue.enqueue(item);
        queueOfQueues.enqueue(queue);
      } catch (QueueEmptyException e) {
        System.out.println("Empty Queue!");
      }
    }
    return queueOfQueues;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
    LinkedQueue q = new LinkedQueue();
    while (q1.size() > 0 && q2.size() > 0) {
      try {
        Comparable item1 = (Comparable) q1.front();
        Comparable item2 = (Comparable) q2.front();
        int c = item1.compareTo(item2);
        if (c < 0) {
          q.enqueue(q1.dequeue());
        } else if (c > 0) {
          q.enqueue(q2.dequeue());
        } else {
          q.enqueue(q1.dequeue());
          q.enqueue(q2.dequeue());
        }
      } catch (QueueEmptyException e) {
        System.out.println("q1 or q2 is empty.");
      }
    }

    if (q1.size() > 0) {
      q.append(q1);
    } else if (q2.size() > 0) {
      q.append(q2);
    }

    return q;
  }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
    while (qIn.size() > 0) {
      try {
        Comparable item = (Comparable) qIn.dequeue();
        int c = item.compareTo(pivot);
        if (c < 0){
          qSmall.enqueue(item);
        } else if (c > 0) {
          qLarge.enqueue(item);
        } else {
          qEquals.enqueue(item);
        }
      } catch (QueueEmptyException e) {
        System.out.println("qIn is empty!");
      }
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
    LinkedQueue queueOfQueues = makeQueueOfQueues(q);
    while (queueOfQueues.size() > 1) {
      LinkedQueue first = new LinkedQueue();
      LinkedQueue second = new LinkedQueue();
      try {
        first = (LinkedQueue) queueOfQueues.dequeue();
        second = (LinkedQueue) queueOfQueues.dequeue();
      } catch (QueueEmptyException e) {
        System.out.println("Queue of queues doesn't have two queues.");
      }
      LinkedQueue merged = mergeSortedQueues(first,second);
      queueOfQueues.enqueue(merged);
    }
    if (queueOfQueues.size() == 1) {
      try {
        q.append((LinkedQueue) queueOfQueues.dequeue());
      } catch (QueueEmptyException e) {
        System.out.println("Queue of queues is empty.");
      }
    }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    // Your solution here.
    if (q.size() == 1 || q.size() == 0) {
      return;
    }
    Comparable pivot = (Comparable) q.nth((int) ((q.size() - 1) * Math.random()) + 1);
    LinkedQueue qSmall = new LinkedQueue();
    LinkedQueue qEquals = new LinkedQueue();
    LinkedQueue qLarge = new LinkedQueue();
    partition(q,pivot,qSmall,qEquals,qLarge);
    quickSort(qSmall);
    quickSort(qLarge);
    q.append(qSmall);
    q.append(qEquals);
    q.append(qLarge);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

}
