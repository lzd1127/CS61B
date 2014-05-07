/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/

    protected List[] hashTable;
    protected int keyNum;

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    hashTable = new DList[checkPrime((int) (sizeEstimate/0.6))];
    keyNum = 0;
  }

  /** 
   *  Construct a new empty hash table with a default size. Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    // Your solution here.
    keyNum = 0;
    hashTable = new DList[97];
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    // Replace the following line with your solution.
    return ((127*code + 9) % 16908799) % (hashTable.length);
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return keyNum;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    if (this.size() == 0) {
      return true;
    }
    return false;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    // Replace the following line with your solution.
    Entry insert = new Entry();
    insert.key = key;
    insert.value = value;
    int slot = Math.abs(compFunction(key.hashCode()));

    if(hashTable[slot] == null) {
      hashTable[slot] = new DList();
    }

    hashTable[slot].insertBack(insert);
    keyNum++;
    return insert;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
    int slot = compFunction(key.hashCode());

    try {
      ListNode curr = hashTable[slot].front();

      for (int i = 0; i < hashTable[slot].length(); i++) {
        if (((Entry) curr.item()).key().equals(key)) {
          return ((Entry) curr.item());
        }
        curr = curr.next();
      }
    } catch (InvalidNodeException e) {
        System.out.print(e);
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    // Replace the following line with your solution.
    int slot = compFunction(key.hashCode());

    try {
      ListNode curr = hashTable[slot].front();
      
      for (int i = 0; i < hashTable[slot].length(); i++) {
        if (((Entry) curr.item()).key().equals(key)) {
          curr.remove();
          keyNum--;
          return ((Entry) curr.item());
        }
        curr = curr.next();
      }
    } catch (InvalidNodeException e) {
        System.out.print(e);
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
    keyNum = 0;
    hashTable = new DList[hashTable.length];
  }

  /**
   *  Find largest prime less than number.
   */
  private static int checkPrime(int number) {
    boolean[] isPrime = new boolean[number + 1];

    for (int i=0; i<number; i++) {
      isPrime[i] = true;
    }
    for (int d = 2; d*d <= number; d++) {
      if (isPrime[d]) {
        for (int n = 2*d; n <= number; n += d) {
          isPrime[n] = false;
        }
      }
    }
    for (int i = number; i >= 0; i--) {
      if(isPrime[i]) {
        return i;
      }
    }
    return 0;
  }

}
