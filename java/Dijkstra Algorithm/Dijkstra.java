import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Graph Data Structure - Dijkstra Algorithm
 *
 * @file Dijkstra.java
 * @author Sai Kishore
 * @notes: Graph is implemented as adjacency list
 *
 * */
class Vertex implements Comparable<Vertex> {

	public final String label;

	// stores distance from source node - infinite to begin with
	public double minDistance = Double.POSITIVE_INFINITY;

	public Edge[] adjacencies;
	public Vertex parent; // parent vertex or predecessor in Path

	public Vertex(String label) {
		this.label = label;
	}

	// Implement compareTo to make it Comparable
	public int compareTo(Vertex v) {
		return Double.compare(minDistance, v.minDistance);
	}
}

class Edge {
	public final Vertex target;
	public final int weight;

	public Edge(Vertex target, int weight) {
		this.target = target;
		this.weight = weight;
	}
}

class Graph {

	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();

	// adds a new vertex to Graph
	public void addVertex(Vertex v) {
		vertices.add(v);
	}

	public void addVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}

	// Dijkstra's Shortest Path Algorithm
	public void computeShortestPaths(Vertex source) {
		// Initialization
		source.minDistance = 0;
		// rest nodes have default value Infinite from Vertex Constructor
		// Predecessor vector is not used since it can be computed using Vertex.previous
		// List (L) is implemented as HashSet to pick min distance vertex faster
		// faster.
		HashSet<Vertex> L = new HashSet<Vertex>(vertices);
		while (!L.isEmpty()) {
			Vertex v = Collections.min(L); // selects min
			L.remove(v); // remove v
			if (L.isEmpty())
				break;
			for (Edge e : v.adjacencies) {
				if (L.contains(e.target)) {
					if (v.minDistance + e.weight < e.target.minDistance) {
						e.target.minDistance = v.minDistance + e.weight;
						e.target.parent = v;
					}
				}
			} // end for loop
		} // end while loop
	}

	// Print predecessor T
	public void printPredecessor() {
		System.out.print("T = (");
		for (int i = 0; i < vertices.size(); ++i) {
			Vertex v = vertices.get(i);
			if (v.parent == null) {
				System.out.print("NULL,");
			} else {
				System.out.print(v.parent.label);
				if (i != vertices.size() - 1)
					System.out.print(",");
			}
		}
		System.out.println(")");
	}

	// print Shortest Distances D
	public void printDistances() {
		System.out.print("D = (");
		for (int i = 0; i < vertices.size(); ++i) {
			Vertex v = vertices.get(i);
			System.out.print((int) v.minDistance);
			if (i != vertices.size() - 1)
				System.out.print(",");
		}
		System.out.println(")");
	}
}

public class Dijkstra {

	// Driver Program to test Dijkstra's

	public static void main(String[] args) {
		Graph assignment1 = new Graph();

		// Vertices
		Vertex v1 = new Vertex("1");
		Vertex v2 = new Vertex("2");
		Vertex v3 = new Vertex("3");
		Vertex v4 = new Vertex("4");
		Vertex v5 = new Vertex("5");
		Vertex v6 = new Vertex("6");

		// Edges
		v1.adjacencies = new Edge[] { new Edge(v2, 1), new Edge(v3, 2) };
		v2.adjacencies = new Edge[] { new Edge(v3, 5), new Edge(v4, 7) };
		v3.adjacencies = new Edge[] { new Edge(v4, 1), new Edge(v5, 2) };
		v4.adjacencies = new Edge[] { new Edge(v6, 2), };
		v5.adjacencies = new Edge[] { new Edge(v3, 8), new Edge(v4, 3), new Edge(v6, 5) };
		v6.adjacencies = new Edge[] { new Edge(v4, 9) };

		// Add to graph
		assignment1.addVertex(v1);
		assignment1.addVertex(v2);
		assignment1.addVertex(v3);
		assignment1.addVertex(v4);
		assignment1.addVertex(v5);
		assignment1.addVertex(v6);

		// Run Dijkstra's
		assignment1.computeShortestPaths(v1);

		System.out.println("Output: \n===============================");
		assignment1.printDistances();
		assignment1.printPredecessor();

	}

}
