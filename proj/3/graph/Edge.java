/* Edge.java */

package graph;
import list.*;

/**
 * The Edge class represents an edge in a weighted, undirected graph.
 */

public class Edge {
	protected Vertex v1;
	protected Vertex v2;
	protected int weight;
	protected DListNode v1Node;
	protected DListNode v2Node;


	public Edge(Vertex u, Vertex v, int w) {
		v1 = u;
		v2 = v;
		weight = w;
	}
}