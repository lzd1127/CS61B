/* Vertex.java */

package graph;
import list.*;

/**
 * The Vertex class represents a vertex in a weighted, undirected graph.
 */

public class Vertex {
	protected Object entry;
	protected int degree;
	protected DList adjacencyList;
	protected DListNode vertexNode;


	public Vertex(Object entry, DList adjacencyList) {
		this.entry = entry;
		this.degree = 0;
		this.adjacencyList = adjacencyList;
		this.vertexNode = null;
	}
	
	public Object getEntry(){
		return this.entry;
	}
}
