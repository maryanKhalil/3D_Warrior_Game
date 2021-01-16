//------------------------------------------
//Written by: Maryan Khalil 
// -----------------------------------------

/*Welcome to Class Board which contains the methods necessary to print out either default game boards
 * or the boards according to player's entered size and number of levels. This class also contains the methods
 * to assigns the proper energy adjustment values at each location of the boards in the 3D Warrior game.  */

public class Board {// beginning of class board

	// the attributes that represent the data
	private int[][][] board;// represent the game board
	private static int MIN_LEVEL = 3;
	private static int MIN_SIZE = 3;
	private int level; 
	private int size; // dimensions of board game (size x size)

	// default constructor method and call createBoard method
	public Board() {
		level = MIN_LEVEL;
		size = 4;
		createBoard(level, size);
	}

	// constructor method with parameters
	public Board(int l, int x) {
		level = l;
		size = x;
		createBoard(l, x);
	}

	// private method creates 3D boards and initializes energy adjustments values
	private void createBoard(int level, int size) {
		board = new int[level][][];
		for (int i = 0; i < level; i++) {
			board[i] = new int[size][];
			for (int x = 0; x < size; x++) {
				board[i][x] = new int[size];
				for (int j = 0; j < size; j++) {
					// fill the board based on the sum of i,x and j. where i is (0, level-1), x is
					// (0,size-1), j is (0,size-1)
					if ((i + j + x) == 0)
						board[i][x][j] = 0;
					else if ((i + j + x) % 3 == 0)
						board[i][x][j] = -3;
					else if ((i + j + x) % 5 == 0)
						board[i][x][j] = -2;
					else if ((i + j + x) % 7 == 0)
						board[i][x][j] = 2;
					else
						board[i][x][j] = 0;
				}

			}
		}
	}

	// method to return the levels of board
	public int getLevel() {
		return level;
	}

	// method to return the size of the boards
	public int getSize() {
		return size;
	}

	// method to return a specific energy adjustment value on the boards
	public int getEnergyAdj(int l, int x, int j) {
		// validating the values of l,x,j
		if (l >= 0 && l < level && x >= 0 && x < size && j >= 0 && j < size)
			return board[l][x][j];
		// if values are invalid
		return -1;
	}

	// method to return the boards using string method
	public String toString() {
		String string = "";
		for (int l = 0; l < level; l++) {
			string += "Level " + (l) + "\n--------" + "\n";
			for (int x = 0; x < size; x++) {
				for (int j = 0; j < size; j++) {
					string += "     " + String.format("%2d", board[l][x][j]) + " ";
				}
				string += "\n";
			}
			string += "\n";
		}
		return string;
	}
}// End of class
