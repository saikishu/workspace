#include "Graph.h"

Graph::Graph(int size) {

	if(size < 2) nEdges = 2;
	else nEdges = size;

	vertices = new int* [size];

	for(int i = 0; i < size; ++i) {
		vertices[i] = new int[size];
	}

	for(int i=0; i < size; ++i) {
		for (int j=0; j < size; ++j) {
			vertices[i][j] = 0;
		}
	}

}

//Cleanup memory
Graph::~Graph() {
	for(int i = 0; i < nEdges; ++i) {
		delete[] vertices[i];
	}
}

//Check if two vertices are connected.
bool Graph::isConnected(int u, int v) {
	return (vertices[u][v] == 1);
}

//Adds an Edge  to Graph
//Populates adjacency matrix
void Graph::addEdge(int u, int v) {
	vertices[u][v] = vertices[v][u] = 1;
}
