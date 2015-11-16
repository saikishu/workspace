/*
 * MazeGenerator.cpp
 * Generates a simple maze (simple maze has only one solution)
 * Entry is set to top-left and Exit is set to bottom-right.
 * Author: Sai Kishore
 * Usage: ./MazeGenerator r
 *	 r = no of rows = no of columns
 */

#include "MazeGenerator.h"
#include "Graph.h"

//Class Definitions

//Constructor - Cell : Building block of maze
Cell::Cell(int xPos, int yPos) {
	//Every cell has position and all walls and not visited by default
	this->xPos = xPos;
	this->yPos = yPos;
	westWall = eastWall = northWall = southWall = true;
	isVisited = false;
}

//Sets a cell visited
void Cell::setVisited() {
	this->isVisited = true;
}

//Checks if a cell is visited
bool Cell::isCellVisited() {
	return isVisited;
}

//Debug - Helper
void Cell::PrintCell() {
	if (northWall)
		std::cout << "+-+";
	std::cout << std::endl;
	if (westWall)
		std::cout << "|";
	std::cout << " ";
	if (eastWall)
		std::cout << "|";
	std::cout << "\n";
	if (southWall)
		std::cout << "+-+";
	std::cout << "\n";
}

//Break wall on a given cell
void Cell::breakWall(const char& direction) {

	switch (direction) {
	case 'n':
		northWall = false;
		break;
	case 'e':
		eastWall = false;
		break;
	case 'w':
		westWall = false;
		break;
	case 's':
		southWall = false;
		break;
	default:
		break;
	}
}

// End of Class Definitions

//struct to hold positions
struct Point {
	int x;
	int y;
};

void decorator() {
	cout << "\n***********************************\n" << endl;
}

int main(int argc, char* argv[]) {

	if (argc != 2) {
		cout << "Insufficient arguments" << endl;
		cout << "Usage: ./MazeGenerator r \nr: num of rows = num of cols"
				<< endl;
		return -1;
	}

	int r = atoi(argv[1]);

	if (r == 0) {
		cout << "Invalid size" << endl;
		return -1;
	}

	//Random number seed
	srand(time(NULL));

	Cell* maze[r][r]; // 2D array two holds cells

	//Initialization of Cell objects
	for (int i = 0; i < r; i++) {
		//i -> yPos; j -> xPos
		for (int j = 0; j < r; j++) {
			Cell* c = new Cell(j, i);
			maze[i][j] = c;
		}
	}

	//Entry point - Cell has north wall broken
	maze[0][0]->breakWall('n');
	//Exit point - Cell has south wall broken
	maze[r - 1][r - 1]->breakWall('s');

	//DFS Algorithm to create random maze
	//Initialize Cell stack
	Point p; // reusable position holder
	vector<Point> cellStack;
	int totalCells = r * r;
	Cell* currentCell = maze[0][0];
	currentCell->setVisited();
	bool unvisited = true;

	while (unvisited) {

		vector<Cell*> currentCellNeighbors;

		//find neighbours for current cell
		int xPos = currentCell->getxPos();
		int yPos = currentCell->getyPos();

		if (yPos != 0) {
			//north Cell
			if (maze[yPos - 1][xPos]->areAllWallsIntact()
					&& !maze[yPos - 1][xPos]->isCellVisited()) {
				currentCellNeighbors.push_back(maze[yPos - 1][xPos]);
			}
		}
		if (xPos != r - 1) {
			//east Cell
			if (maze[yPos][xPos + 1]->areAllWallsIntact()
					&& !maze[yPos][xPos + 1]->isCellVisited()) {
				currentCellNeighbors.push_back(maze[yPos][xPos + 1]);
			}

			//exit cell
			if ((xPos + 1 == r - 1) && (yPos == r - 1)) {
				//don't check for wall intactness
				if (!maze[yPos][xPos + 1]->isCellVisited()) {
					currentCellNeighbors.push_back(maze[yPos][xPos + 1]);
				}

			}
		}
		//west Cell
		if (xPos != 0) {
			//west cell
			if (maze[yPos][xPos - 1]->areAllWallsIntact()
					&& !maze[yPos][xPos - 1]->isCellVisited()) {
				currentCellNeighbors.push_back(maze[yPos][xPos - 1]);
			}
		}
		if (yPos != r - 1) {
			//south Cell
			if (maze[yPos + 1][xPos]->areAllWallsIntact()
					&& !maze[yPos + 1][xPos]->isCellVisited()) {
				currentCellNeighbors.push_back(maze[yPos + 1][xPos]);
			}
			//exit cell
			if ((yPos + 1 == r - 1) && (xPos == r - 1)) {
				//don't check for wall intactness
				if (!maze[yPos + 1][xPos]->isCellVisited()) {
					currentCellNeighbors.push_back(maze[yPos + 1][xPos]);
				}
			}
		}
		if (currentCellNeighbors.size() != 0) {

			//atleast 1 unvisited neighbor - pick a random cell using rand within neighbor size limit
			Cell* randomNeighbor = currentCellNeighbors.at(
					rand() % (currentCellNeighbors.size()));

			if (!randomNeighbor->isCellVisited()) {
				//knock the wall down
				if ((randomNeighbor->getxPos() - currentCell->getxPos()) == 0) {
					//common wall is north or south
					if (randomNeighbor->getyPos() > currentCell->getyPos()) {
						//neigbor is south to current cell
						currentCell->breakWall('s');
						randomNeighbor->breakWall('n');
					} else {
						//neigbor is north to current cell
						currentCell->breakWall('n');
						randomNeighbor->breakWall('s');
					}
				} else {
					//common wall is east or west
					if (randomNeighbor->getxPos() > currentCell->getxPos()) {
						//neigbor is east to current cell
						currentCell->breakWall('e');
						randomNeighbor->breakWall('w');
					} else {
						//neigbor is west to current cell
						currentCell->breakWall('w');
						randomNeighbor->breakWall('e');
					}
				}
				p.x = currentCell->getxPos();
				p.y = currentCell->getyPos();
				cellStack.push_back(p);
				currentCell = randomNeighbor;
				currentCell->setVisited();

			}
		} else if (!cellStack.empty()) {
			p = cellStack.back();
			cellStack.pop_back();
			currentCell = maze[p.y][p.x];
		} else {
			//pick a random unvisited cell
			vector<Cell*> unVisitedCells;
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < r; j++) {
					if (!maze[i][j]->isCellVisited()) {
						unVisitedCells.push_back(maze[i][j]);
					}

				}

				if (!unVisitedCells.empty()) { //check not needed
					Cell* randomCell = unVisitedCells.at(
							rand() % (unVisitedCells.size()));
					currentCell = randomCell;
					currentCell->setVisited();
				}

			}

		}
		//check for unvisited cells if any
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < r; j++) {
				if (!maze[i][j]->isCellVisited()) {
					unvisited = true;
					break;
				}
				unvisited = false;
			}
		}

	}

	decorator();
	cout << "Maze : " << endl;

	//Print maze
	for (int i = 0; i < r; i++) {
		//north walls
		for (int j = 0; j < r; j++) {
			if (maze[i][j]->hasNorthWall())
				std::cout << "+-";
			else
				std::cout << "+ ";
			if (j == r - 1)
				std::cout << "+";
		}
		std::cout << endl;
		//east and west walls
		for (int j = 0; j < r; j++) {
			if (maze[i][j]->hasWestWall())
				std::cout << "| ";
			else
				std::cout << "  ";

			if (j == r - 1) {
				if (maze[i][j]->hasEastWall())
					std::cout << "|";
				else
					std::cout << " ";
			}

		}
		std::cout << endl;

		if (i == r - 1) {
			//south walls for last row
			for (int j = 0; j < r; j++) {
				if (maze[i][j]->hasSouthWall())
					std::cout << "+-";
				else
					std::cout << "+ ";
				if (j == r - 1)
					std::cout << "+";
			}
			std::cout << std::endl;
		}
	}

	return 0;
}
