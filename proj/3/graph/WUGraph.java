/* WUGraph.java */

package graph;
import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
  private DList vertices;
  private HashTableChained vertexHash;
  private HashTableChained edgeHash;
  private int numVertices;
  private int numEdges;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
    vertices = new DList();
    vertexHash = new HashTableChained();
    edgeHash = new HashTableChained();
    numVertices = 0;
    numEdges = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
    return numVertices;
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
    return numEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    Object[] v = new Object[numVertices];
    DListNode curr = vertices.front();
    int index = 0;
    while (curr != null) {
      v[index] = ((Vertex) curr.item).getEntry();
      index++;
      curr = vertices.next(curr);
    }
    return v;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
    if (isVertex(vertex)) {
      return;
    } else {
      Vertex newVertex = new Vertex(vertex, new DList());
      vertices.insertBack(newVertex);
      newVertex.vertexNode = vertices.back();
      vertexHash.insert(vertex,newVertex);
      numVertices++;
    }
  
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    if (!isVertex(vertex)) {
      return;
    } else {
      DListNode vNode= ((Vertex) vertexHash.find(vertex).value()).vertexNode;
      vertices.remove(vNode);
      Vertex v = (Vertex) vNode.item;
      DList vAdjList = v.adjacencyList;
      DListNode curr = vAdjList.front();
      DListNode next = vAdjList.next(curr);
      while (curr != null) {
        Edge e = (Edge) curr.item;
        Vertex v1 = ((Edge) curr.item).v1;
        Vertex v2 = ((Edge) curr.item).v2;
        removeEdge(v1.entry,v2.entry);
        curr = next;
        if (next != null) {
          next = vAdjList.next(next);
        }
      }
      vertexHash.remove(vertex);
      numVertices--;

    }
  }


  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
    return vertexHash.find(vertex) != null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
    if (!isVertex(vertex)) {
      return 0;
    }
    return ((Vertex) vertexHash.find(vertex).value()).degree;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
    if (!isVertex(vertex)) {
      return null;
    } else if (((Vertex) vertexHash.find(vertex).value()).degree == 0) {
      return null;
    } else {
      Vertex v = (Vertex) vertexHash.find(vertex).value();
      int degree = v.degree;
      Neighbors n = new Neighbors();
      n.neighborList = new Object[degree];
      n.weightList = new int[degree];
      DListNode curr = v.adjacencyList.front();
      int index = 0;
      while (curr != null) {
        Edge e = (Edge) curr.item;
        n.weightList[index] = e.weight;
        if (e.v1.equals(v)) {
          n.neighborList[index] = e.v2.entry;
        } else {
          n.neighborList[index] = e.v1.entry;
        }
        index++;
        curr = v.adjacencyList.next(curr);
      }
      return n;
    }
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
    if (!isVertex(u) || !isVertex(v)) {
      return;
    } else {
      VertexPair vp = new VertexPair(u,v);
      Vertex uVertex = (Vertex) vertexHash.find(u).value();
      Vertex vVertex = (Vertex) vertexHash.find(v).value();
      if (isEdge(u,v)) {
        Edge e = (Edge) edgeHash.find(vp).value();
        e.weight = weight;
      } else {
        Edge e = new Edge(uVertex,vVertex,weight);
        edgeHash.insert(vp,e);
        uVertex.adjacencyList.insertBack(e);
        e.v1Node = uVertex.adjacencyList.back();
        uVertex.degree = uVertex.degree + 1;
        if (uVertex != vVertex) {
          vVertex.adjacencyList.insertBack(e);
          e.v2Node = vVertex.adjacencyList.back();
          vVertex.degree = vVertex.degree + 1;
        }
        numEdges++;
      }
    }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
    if (!isEdge(u,v)) {
      return;
    } else {
      VertexPair vp = new VertexPair(u,v);
      Edge e = (Edge) edgeHash.find(vp).value();
      Vertex v1 = e.v1;
      DListNode v1Node = e.v1Node;
      v1.adjacencyList.remove(v1Node);
      v1.degree = v1.degree - 1;
      Vertex v2 = e.v2;
      DListNode v2Node = e.v2Node;
      if (v1 != v2) {
        v2.adjacencyList.remove(v2Node);
        v2.degree = v2.degree - 1;
      }
      edgeHash.remove(vp);
      numEdges--;
    }
  }
  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
    if (vertexHash.find(u) == null || vertexHash.find(v) == null) {
      return false;
    } else {
      VertexPair vp = new VertexPair(u,v);
      if (edgeHash.find(vp) == null) {
        return false;
      } else {
        return true;
      }
    }
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    if (!isEdge(u,v)) {
      return 0;
    } else {
      VertexPair vp = new VertexPair(u,v);
      Edge e = (Edge) edgeHash.find(vp).value();
      return e.weight;
    }
  }

}
