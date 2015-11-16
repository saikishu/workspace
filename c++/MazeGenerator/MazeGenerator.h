/*
 * MazeGenerator.h
 *
 * Author: Venkata Sai Kishore Modalavalasa
 */

#ifndef MAZEGENERATOR_H_
#define MAZEGENERATOR_H_

#include <iostream>
#include <time.h>
#include <vector>
using namespace std;

//Prototypes

//Class Cell which models each cell
//A cell has four walls
class Cell {

public:
	Cell(int, int);
	void setVisited();
	bool isCellVisited();
	void breakWall(const char& direction);
	void PrintCell();
	inline int getxPos() const {
		return xPos;
	}
	inline int getyPos() const {
		return yPos;
	}

	inline bool hasNorthWall() const {
		return northWall;
	}

	inline bool hasSouthWall() const {
		return southWall;
	}

	inline bool hasEastWall() const {
		return eastWall;
	}
	inline bool hasWestWall() const {
		return westWall;
	}
	inline bool areAllWallsIntact() const {
		return (eastWall && westWall && northWall && southWall);
	}

private:
	bool eastWall;
	bool westWall;
	bool northWall;
	bool southWall;
	bool isVisited;
	int xPos;
	int yPos;
};

#endif /* MAZEGENERATOR_H_ */
