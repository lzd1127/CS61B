/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import dict.*;
import list.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g) {

  	// new graph with no edges
  	Object[] vertices = g.getVertices();
  	WUGraph newGraph = new WUGraph();
  	for (int i = 0; i < vertices.length; i++) {
  		newGraph.addVertex(vertices[i]);
  	}

  	// get all the edges, add to a LinkedQueue
  	LinkedQueue edgeQ = new LinkedQueue();
  	for (int i = 0; i < vertices.length; i++) {
  		Neighbors neighbor = g.getNeighbors(vertices[i]);
  		for (int j = 0; j < neighbor.neighborList.length; j++) {
  			Object v2 = neighbor.neighborList[j];
  			int w = neighbor.weightList[j];
  			KEdge newEdge = new KEdge(vertices[i], v2, w);
  			edgeQ.enqueue(newEdge);
  		}
  	}

  	// quicksort the edges
  	ListSorts.quickSort(edgeQ);

  	// add all the edges
  	HashTableChained mappedInts = new HashTableChained();
  	DisjointSets set = new DisjointSets(vertices.length);
  	for (int i = 0; i < vertices.length; i++) {
  		mappedInts.insert(vertices[i], i);
  	}

  	try {
      while (!edgeQ.isEmpty()) {
  		  KEdge currKedge = (KEdge) edgeQ.dequeue();
  		  Object v1 = currKedge.v1;
  		  Object v2 = currKedge.v2;
  		  int int1 = (int) mappedInts.find(v1).value();
  		  int int2 = (int) mappedInts.find(v2).value();

  		  if (set.find(int1) != set.find(int2)) {
  			 set.union(set.find(int1), set.find(int2));
  			 newGraph.addEdge(v1, v2, currKedge.w);
  		  }
      }
    } catch (QueueEmptyException e) {
      System.out.print("error in mappedInts");
    }
  	return newGraph;

  }


}