/*
 * Graph.h
 *
 * Author: Sai Kishore
 */

 #ifndef GRAPH_H_
 #define GRAPH_H_

class Graph {
public:
	Graph(int size = 2); //constructor
	~Graph();
	bool isConnected(int, int);
	void addEdge(int, int);

private:
	int nEdges; //number of vertices
	int **vertices; // 2d array of edges

};

 #endif
