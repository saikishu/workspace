import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

//Simple pair class to hold pair of edges for MST
class Pair<X, Y> {
	public X x;
	public Y y;

	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
}

/**
 * Undirected Graph Data Structure - Prim's MST Algorithm
 *
 * @file PrimsMST.java
 * @author Sai Kishore
 * @notes: Graph is implemented as adjacency matrix
 *
 * */

class Graph {

	private int nVertices = 0;
	private double[][] graph = null;
	private double[][] cost = null;

	// maintain vertices as String array
	// Helps map vertex labels to matrix index
	private String[] vertices = null;

	// instance variables for Prim's
	// Sets are used to keep the items unique
	Set<String> Q = new HashSet<String>();
	Set<String> V = new HashSet<String>();
	Set<String> R = new HashSet<String>();
	// T is linked hash set to preserve order
	Set<Pair<String, String>> T = new LinkedHashSet<Pair<String, String>>();

	// default constructor
	public Graph(int nVertices) {
		this.nVertices = nVertices;
		graph = new double[nVertices][nVertices];
		cost = new double[nVertices][nVertices];
		vertices = new String[nVertices];
		// initialize
		initializeGraph();
		initializeCost();
		initializeVertices();
	}

	// Initializes Graph matrix with 0's
	private void initializeGraph() {
		for (double[] row : graph)
			Arrays.fill(row, 0);
	}

	// Initializes Cost matrix with infinities's
	private void initializeCost() {
		for (double[] row : cost)
			Arrays.fill(row, Double.POSITIVE_INFINITY);
	}

	// Initialize vertices - default labels are indices
	private void initializeVertices() {
		for (int i = 0; i < nVertices; ++i) {
			vertices[i] = Integer.toString(i);
		}
	}

	// Maps vertex to index
	public void addVertex(String v, int index) {
		if (index <= nVertices - 1) {
			vertices[index] = v;
		} else {
			// TODO: Throw out of bounds exception
			// Not extending for this assignment
		}

	}

	// Adds undirected edge - both sides with cost c
	public void addEdge(String vertex1, String vertex2, double c) {

		int v = getIndex(vertex1);
		int u = getIndex(vertex2);

		if (v <= nVertices - 1 && u <= nVertices - 1 && v != -1 && u != -1) {
			graph[v][u] = 1;
			graph[u][v] = 1;
			cost[v][u] = c;
			cost[u][v] = c;
		} else {
			// TODO: Throw out of bounds exception
			// TODO: Throw custom exception: Vertex not found
			// Not extending for this assignment
		}
	}

	// Overloaded method if cost is not given
	public void addEdge(String v, String u) {
		addEdge(v, u, Double.POSITIVE_INFINITY);
	}

	private int getIndex(String v) {
		// Simple Linear search : O(n) - optimize if vertices grows
		for (int i = 0; i < nVertices; ++i) {
			if (vertices[i].compareTo(v) == 0)
				return i;
		}
		return -1;
	}

	// Prim's MST
	// Input is some arbitrary vertex
	public void computeMST(String v) {
		Q.add(v);
		// Make V from existing vertices
		for (int i = 0; i < nVertices; ++i) {
			V.add(vertices[i]);
		}
		// R= V-Q
		R.addAll(V);
		R.removeAll(Q);

		for (int i = 1; i <= nVertices - 1; ++i) {
			Pair<String, String> minEdge = pickMinEdge();
			Q.add(minEdge.y); // can't be null
			R.removeAll(Q); // R = V - Q
			T.add(minEdge);
		}
	}

	public Pair<String, String> pickMinEdge() {
		// (v,u) such that it is the min cost from all edges b/w vertices of Q
		// and R
		double min = Double.POSITIVE_INFINITY;
		Pair<String, String> minEdge = new Pair<String, String>(null, null); // Other
																				// end
																				// of
																				// min
		// edge that is
		// in R
		for (String u : Q) {
			for (String v : R) {
				if (isEdge(u, v)) {
					if (min > getCost(u, v)) {
						min = getCost(u, v);
						minEdge.x = u;
						minEdge.y = v;
					}
				}
			}
		}
		return minEdge;
	}

	// checks if edge exists between two vertices
	public boolean isEdge(String u, String v) {
		return graph[getIndex(u)][getIndex(v)] == 1 ? true : false;
	}

	// get cost
	public double getCost(String u, String v) {
		return cost[getIndex(u)][getIndex(v)];
	}

	// Print MST Vertices
	public void printMST() {
		System.out.println("===================== MST =======================");
		System.out.print("{");
		int count = 0;
		for (Pair<String, String> p : T) {
			System.out.print("(" + p.x + "," + p.y + ")");
			if (count != T.size() - 1)
				System.out.print(",");
			count++;
		}
		System.out.println("}");
	}

	// print MST Cost
	public void printMSTCost() {
		int cost = 0;
		for (Pair<String, String> p : T) {
			cost += (int) getCost(p.x, p.y);
		}
		System.out.println("Weight of MST: " + cost);
	}

	// Debug methods
	public void printGraph() {
		System.out.println("===================== Graph =====================");

		// print vertex labels on top
		System.out.print("\t");
		for (int i = 0; i < nVertices; ++i) {
			System.out.print(vertices[i] + "\t");
		}
		System.out.println();
		System.out.print("\t");
		for (int i = 0; i < nVertices; ++i) {
			System.out.print("-------");
		}
		System.out.println();

		for (int i = 0; i < nVertices; ++i) {
			System.out.print(vertices[i] + "  |  \t");
			for (int j = 0; j < nVertices; ++j) {
				System.out.print((int) graph[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printCost() {
		System.out
				.println("===================== Cost =======================");
		// print vertex labels on top
		System.out.print("\t");
		for (int i = 0; i < nVertices; ++i) {
			System.out.print(vertices[i] + "\t");
		}
		System.out.println();
		System.out.print("\t");
		for (int i = 0; i < nVertices; ++i) {
			System.out.print("-------");
		}
		System.out.println();

		for (int i = 0; i < nVertices; ++i) {
			System.out.print(vertices[i] + "  |  \t");
			for (int j = 0; j < nVertices; ++j) {
				if (cost[i][j] == Double.POSITIVE_INFINITY)
					System.out.print("INF\t");
				else
					System.out.print((int) cost[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

}

// Driver class to test Prim's Algorithm
public class PrimsMST {
	public static void main(String[] args) {
		Graph assignment2 = new Graph(5);

		// Add all vertices in order
		// This helps in mapping any type of vertex labels to 0,1,2...for matrix
		// representation
		assignment2.addVertex("1", 0);
		assignment2.addVertex("2", 1);
		assignment2.addVertex("3", 2);
		assignment2.addVertex("4", 3);
		assignment2.addVertex("5", 4);

		// Add edges and costs
		assignment2.addEdge("1", "2", 6);
		assignment2.addEdge("1", "3", 3);
		assignment2.addEdge("1", "4", 4);
		assignment2.addEdge("1", "5", 5);

		assignment2.addEdge("2", "3", 3);
		assignment2.addEdge("2", "4", 5);
		assignment2.addEdge("2", "5", 7);

		assignment2.addEdge("3", "4", 3);
		assignment2.addEdge("3", "5", 5);

		assignment2.addEdge("4", "5", 3);

		assignment2.printGraph();
		assignment2.printCost();

		// Run Prim's MST
		assignment2.computeMST("1");
		assignment2.printMST();
		assignment2.printMSTCost();
	}
}
