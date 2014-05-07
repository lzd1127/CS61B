/* Board.java */

package player;
import list.*;

/**
 *  An implementation of an internal game board. Keeps track of moves
 *  made by both players.  Contains various methods that checks for networks and valid moves.
 */

public class Board {
	private int[][] board;
	final static int BLACK = 0;
	final static int WHITE = 1;
	final static int EMPTY = 2;
	final static int CORNER = 3;
	protected int numWhiteChips;
	protected int numBlackChips;
	final static Location[] WHITE_GOAL_AREA = { new Location(0,1),
												new Location(0,2),
												new Location(0,3),
												new Location(0,4),
												new Location(0,5),
												new Location(0,6),
												new Location(7,1),
												new Location(7,2),
												new Location(7,3),
												new Location(7,4),
												new Location(7,5),
												new Location(7,6) };
	final static Location[] BLACK_GOAL_AREA = { new Location(1,0),
												new Location(2,0),
												new Location(3,0),
												new Location(4,0),
												new Location(5,0),
												new Location(6,0),
												new Location(1,7),
												new Location(2,7),
												new Location(3,7),
												new Location(4,7),
												new Location(5,7),
												new Location(6,7) };
	final static Location[] CORNERS = { new Location(0,0),
										new Location(7,0),
										new Location(0,7),
										new Location(7,7) };
	final static int NORTH = 10;
	final static int EAST = 20;
	final static int SOUTH = 30;
	final static int WEST = 40;
	final static int NE = 15;
	final static int SE = 25;
	final static int SW = 35;
	final static int NW = 45;
	final static int[] DIRECTIONS = { NORTH, NE, EAST, SE, SOUTH, SW, WEST, NW};

	// Creates an empty board represented by the 2D array "board" field.
	// Corner's are marked. The number of white and black chips are set to zero.
	public Board() {
		board = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i == 0 || i == 7) && (j == 0 || j == 7)) {
					board[i][j] = CORNER;
				} else {
					board[i][j] = EMPTY;
				}
			}
		}
		numWhiteChips = 0;
		numBlackChips = 0;
	}

	// If given Move m for given player, int color, is legal returns true. If it's illegal returns
	// false. This method allows us to choose a valid move so the machine player
	// isn't accused of cheating and lose. 
	private boolean isValidMove(Move m, int color) {
		boolean valid = true;
		if ((color == BLACK && numBlackChips < 10 && m.moveKind == Move.STEP) || (color == WHITE && numWhiteChips < 10 && m.moveKind == Move.STEP)) {
			return false;
		} else if ((color == BLACK && numBlackChips == 10 && m.moveKind == Move.ADD) || (color == WHITE && numWhiteChips == 10 && m.moveKind == Move.ADD)) {
			return false;
		} else if ((color == WHITE && inBlackGoalArea(m.x1,m.y1)) || (color == BLACK && inWhiteGoalArea(m.x1,m.y1))) {
			return false;
		} else if (inCorner(m.x1,m.y1)) {
			return false;
		} else if (isOccupied(m.x1,m.y1)) {
			return false;
		} else {
			board[m.x1][m.y1] = color;
			if (m.moveKind == Move.STEP) {
				board[m.x2][m.y2] = EMPTY;;
			} 
			if (inCluster(m.x1,m.y1)) {
				valid = false;
			}
			board[m.x1][m.y1] = EMPTY;
			if (m.moveKind == Move.STEP) {
				board[m.x2][m.y2] = color;
			}
			return valid;
		}
		
	}

	// If position [int x][int y] is in one of the black goal areas returns true.
	// This method allows us to make sure we don't try to place a white chip
	// in a black goal area.
	private boolean inBlackGoalArea(int x, int y) {
		Location loc = new Location(x,y);
		for (int i = 0; i < BLACK_GOAL_AREA.length; i++) {
			if (loc.equals(BLACK_GOAL_AREA[i])) {
				return true;
			}
		}
		return false;
	}

	// If position [int x][int y] is in the top black goal area returns true.
	// This method allows us to make sure a black network starts in the
	// black goal area.
	private boolean inBlackStartGoalArea(int x, int y) {
		Location loc = new Location(x,y);
		for (int i = 0; i < BLACK_GOAL_AREA.length - 6; i++) {
			if (loc.equals(BLACK_GOAL_AREA[i])) {
				return true;
			}
		}
		return false;
	}

	// If position [int x][int y] is in the bottom black goal area returns true.
	// This method allows us to make sure a black network ends in the
	// black goal area.
	private boolean inBlackEndGoalArea(int x, int y) {
		Location loc = new Location(x,y);
		for (int i = 6; i < BLACK_GOAL_AREA.length; i++) {
			if (loc.equals(BLACK_GOAL_AREA[i])) {
				return true;
			}
		}
		return false;
	}

	// If position [int x][int y] is in one of the white goal areas returns true.
	// This method allows us to make sure we don't try to place a black chip
	// in a white goal area.
	private boolean inWhiteGoalArea(int x, int y) {
		Location loc = new Location(x,y);
		for (int i = 0; i < WHITE_GOAL_AREA.length; i++) {
			if (loc.equals(WHITE_GOAL_AREA[i])) {
				return true;
			}
		}
		return false;
	}

	// If position [int x][int y] is in the left white goal area returns true.
	// This method allows us to make sure a white network starts in the 
	// white goal area.
	private boolean inWhiteStartGoalArea(int x, int y) {
		Location loc = new Location(x,y);
		for (int i = 0; i < WHITE_GOAL_AREA.length - 6; i++) {
			if (loc.equals(WHITE_GOAL_AREA[i])) {
				return true;
			}
		}
		return false;
	}

	// If position [int x][int y] is in the right white goal area returns true.
	// This method allows us to make sure a white network ends in the 
	// white goal area.
	private boolean inWhiteEndGoalArea(int x, int y) {
		Location loc = new Location(x,y);
		for (int i = 6; i < WHITE_GOAL_AREA.length; i++) {
			if (loc.equals(WHITE_GOAL_AREA[i])) {
				return true;
			}
		}
		return false;
	}

	// If position [int x][int y] is one of the four corners, returns true.
	// This method allows us to make sure we don't try to illegally
	// place a chip in a corner.
	private boolean inCorner(int x, int y) {
		Location loc = new Location(x,y);
		for (int i = 0; i < CORNERS.length; i++) {
			if (loc.equals(CORNERS[i])) {
				return true;
			}
		}
		return false;
	}

	// If position [int x][int y] is already occupied, returns true.
	// This method allows us to make sure we don't try to illegally
	// place a chip in an occupied location.
	private boolean isOccupied(int x, int y) {
		if (board[x][y] != EMPTY) {
			return true;
		}
		return false;
	}

	// If position [int x][int y] is part of a cluster of the same colors, return true.
	// This method allows us to make sure we don't try to illegally
	// place a chip in a location that would result in a cluster.
	private boolean inCluster(int x, int y) {
		int numAdj = numAdjacent(x,y);
		Location adjacent;
		
		if (numAdj >= 2) {
			return true;
		} else if (numAdj == 1) {
			adjacent = findAdjacent(x,y);
			if (numAdjacent(adjacent.x,adjacent.y) >= 2) {
				return true;
			}
		} 
		return false;
	}

	// If position [int x][int y] has a chip of the same color adjacent to it, returns 
	// the location of the adjacent chip. 
	private Location findAdjacent(int x, int y) {
		Location adjacent = new Location();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if ( i >= 0 && i < 8 && j >= 0 && j < 8 && !(i == x && j == y) && board[i][j] == board[x][y]) {
					adjacent = new Location(i,j);
				}
			}
		}
		return adjacent;
	}

	// Returns the number of chips of the same color adjacent to position [int x][int y].
	// This method allows us to prevent clusters from occurring.
	private int numAdjacent(int x, int y) {
		int numAdjacent = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if ( i >= 0 && i < 8 && j >= 0 && j < 8 && !(i == x && j == y) && board[i][j] == board[x][y]) {
					numAdjacent++;
				}
			}
		}
		return numAdjacent;
	}

	// Returns a list of all possible legal moves that the given player, int color, can make next.
	// This method allows the minimax method in MachinePlayer to search through all valid
	// legal moves and choose the best one.
	protected DList findMoves(int color) {
		DList moves;
		if ((color == BLACK && numBlackChips < 10) || (color == WHITE && numWhiteChips < 10)) {
			moves = findAddMoves(color);
		} else {
			moves = findStepMoves(color);
		}
		return moves;
	}

	// Returns a list of all possible legal add moves that the given player, int color, can make next.
	// This method allows findMoves to return the legal add moves when the given player has 
	// less than ten chips on the board.
	private DList findAddMoves(int color) {
		DList addMoves = new DList();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Move m = new Move(i,j);
				if (isValidMove(m,color)) {
					addMoves.insertBack(m);
				}
			}
		}
		return addMoves;
	}

	// Returns a list of all possible legal step moves that the given player, int color, can make next.
	// This method allows fundMoves to return the legal step moves when the given player already
	// has ten chips on the board.
	private DList findStepMoves(int color) {
		DList stepMoves = new DList();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == color) {
					for (int x = 0; x < 8; x++) {
						for (int y = 0; y < 8; y++) {
							Move m = new Move(x,y,i,j);
							if (isValidMove(m,color)) {
								stepMoves.insertBack(m);
							}
						}
					}
				}
			}
		}
		return stepMoves;
	}
	
	// Returns a array of locations that makes a connection with the last location in DList prev.
	// This method allows us to find complete networks.
	protected Location[] findConnection(DList prev) {
		Location[] connections = new Location[8];
		Location current = (Location) prev.back().item;
		Location previous;
		Location start = (Location) prev.front().item;
		int color = board[start.x][start.y];
		if (prev.length() == 1) {
			previous = current;
		} else {
			previous = (Location) prev.prev(prev.back()).item;
		}
		int prevDir = findDirection(previous,current);
		for (int i = 0; i < DIRECTIONS.length; i++) {
			if (DIRECTIONS[i] != prevDir) {
				connections[i] = findConnectionInDirection(current,DIRECTIONS[i]);
				if (inPrev(connections[i],prev) || (color == WHITE && inWhiteStartGoalArea(connections[i].x,connections[i].y)) || (color == BLACK && inBlackStartGoalArea(connections[i].x,connections[i].y))) {
					connections[i] = null;
				}
			}
		}
		return connections;

	}

	// If the given Location next already appears in the given list of locations in DList prev, returns true.
	// This method allows us to make sure we don't make connections with previously used chips.
	private boolean inPrev(Location next, DList prev) {
		if (next != null) {
			DListNode currNode = prev.front();
			while (currNode != null) {
				if (next.equals(currNode.item)) {
					return true;
				}
				currNode = prev.next(currNode);
			}
			return false;
		} else {
			return true;
		}
		
	}

	// If there is a possible connection to be made with the given Location curren in the given int direction, returns the location.
	// This method allows us to find all possible connections to the given location.
	private Location findConnectionInDirection (Location current, int direction) {
		int color = board[current.x][current.y];
		int x;
		int y;
		if (direction == NORTH || direction == NE || direction == NW) {
			y = -1;
		} else if (direction == SOUTH || direction == SE || direction == SW) {
			y = 1;
		} else {
			y = 0;
		}
		if (direction == EAST || direction == NE || direction == SE) {
			x = 1;
		} else if (direction == WEST || direction == NW || direction == SW) {
			x = -1;
		} else {
			x = 0;
		}

		int xCurr = current.x + x;
		int yCurr = current.y + y;

		while (xCurr >= 0 && xCurr < 8 && yCurr >= 0 && yCurr < 8 && board[xCurr][yCurr] == EMPTY) {
			xCurr = xCurr + x;
			yCurr = yCurr + y;
		}
		if (!(xCurr >= 0 && xCurr < 8 && yCurr >= 0 && yCurr < 8)) {
			return null;
		} else if (board[xCurr][yCurr] == color) {
			return new Location(xCurr,yCurr);
		} else {
			return null;
		}
	}

	// Returns the int that represents the direction from Location from to Location to.
	// This method allows us to make sure we don't make an illegal connection in the same direction as the previous connection.
	private int findDirection(Location from, Location to) {
		int direction;

		if (to.y > from.y) {
			if (to.x > from.x) {
				return SE;
			} else if (to.x < from.x) {
				return SW;
			} else {
				return SOUTH;
			}
		} else if (to.y < from.y) {
			if (to.x > from.x) {
				return NE;
			} else if (to.x < from.x) {
				return NW;
			} else {
				return NORTH;
			}
		} else {
			if (to.x > from.x) {
				return EAST;
			} else if (to.x < from.x) {
				return WEST;
			} else {
				return 0;
			}
		}
	}

	// Returns the number of connections on the board for the given player, int color.
	// This method allows the eval method in MachinePlayer to score a board
	// based on the machine player's connections and it's opponents connections.	
	protected int countConnections(int color) {
		int connections = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == color) {
					Location current = new Location(i,j);
					for (int dir = 0; dir < DIRECTIONS.length; dir++) {
						if (findConnectionInDirection(current,DIRECTIONS[dir]) != null) {
							connections++;
						}
					}
				}
			}
		}
		return connections/2;
	}

	// If the given player, int color, has a network returns true. 
	// This method allows the eval method in MachinePlayer to score a board 
	// with a network. Consequently, the minimax method in MachinePlayer will choose
	// the move that will result in a network.
	protected boolean hasNetwork(int color) {
		int numInStartGoalArea = 0;
		int numInEndGoalArea = 0;
		DList[] startLocations;
		if ((color == BLACK && numBlackChips < 6) || (color == WHITE && numWhiteChips < 6)) {
			return false;
		}

		for (int i = 1; i < 7; i++) {
			if ((color == BLACK && board[i][0] == color) || (color == WHITE && board[0][i] == color)) {
				numInStartGoalArea++;
			} 
			if ((color == BLACK && board[i][7] == color) || (color == WHITE && board[7][i] == color)) {
				numInEndGoalArea++;
			}
		}

		if (numInStartGoalArea == 0 || numInEndGoalArea == 0) {
				return false;
		} else {
			int index = 0;
			startLocations = new DList[numInStartGoalArea];
			for (int i = 1; i < 7; i++) {
				if (color == BLACK && board[i][0] == color) {
					DList list = new DList();
					list.insertFront(new Location(i,0));
					startLocations[index] = list;
					index++;
				} else if (color == WHITE && board[0][i] == color) {
					DList list = new DList();
					list.insertFront(new Location(0,i));
					startLocations[index] = list;
					index++;
				}
			}
		}
		for (int i = 0; i < startLocations.length; i++) {
			if (finishNetwork(startLocations[i])) {
				return true;
			}
		}
		return false;	
	}

	// If DList prev contains a list of locations that makes a valid network, returns true.
	// If DList prev does not contain a list of locations that makes a valid network, it adds 
	// a valid continuing connection (to try and finish the network) and makes a recursive call. This method allows
	// hasNetwork to check whether a board has a valid network.
	private boolean finishNetwork(DList prev) {
		Location start = (Location) prev.front().item;
		Location end = (Location) prev.back().item;
		int color = board[start.x][start.y];
		if ((color == BLACK && inBlackEndGoalArea(end.x,end.y)) || (color == WHITE && inWhiteEndGoalArea(end.x,end.y))) {
			if (prev.length() < 6) {
				return false;
			} else if (prev.length() >= 6) {
				return true;
			}
		} else {
			Location[] next = findConnection(prev);
			for (int i = 0; i < next.length; i++) {
				if (next[i] != null) {
					prev.insertBack(next[i]);
					if (finishNetwork(prev)){
						return true;
					} else {
						prev.remove(prev.back());
					}
				}
			}
		}
		return false;
	}

	// If the given Move m is valid for the given player, int color, it updates the board.
	// This method allows us to continually update our internal game board to match
	// the actual game board.
	protected boolean makeMove(Move m, int color) {
		if (m.moveKind == Move.QUIT) {
			return false;
		}
		if (!isValidMove(m,color)) {
			return false;
		} else {
			board[m.x1][m.y1] = color;
			if (m.moveKind == Move.STEP) {
				board[m.x2][m.y2] = EMPTY;
			} else if (m.moveKind == Move.ADD) {
				if (color == WHITE) {
					numWhiteChips++;
				} else if (color == BLACK) {
					numBlackChips++;
				}
			}
			return true;
		}
	}

	// This method undoes the given Move m for the given player, int color, allowing us to undo a move
	// made in the minimax method in MachinePlayer when it tries out all valid moves to find
	// the best possible move.
	protected boolean undoMove(Move m, int color) {
		board[m.x1][m.y1] = EMPTY;
		if (m.moveKind == Move.STEP) {
			board[m.x2][m.y2] = color;
		} else if (m.moveKind == Move.ADD) {
			if (color == WHITE) {
				numWhiteChips--;
			} else if (color == BLACK) {
				numBlackChips--;
			}
		}
		return true;
	}
	
	// This method prints our internal game board. It doesn't contain code necessary
	// for our project to function so the reader can skip over it.
	protected void printBoard() {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if (board[i][j] == EMPTY) {
					System.out.print("E");
				} else if (board[i][j] == WHITE) {
					System.out.print("W");
				} else if (board[i][j] == BLACK) {
					System.out.print("B");
				} else {
					System.out.print("C");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

}