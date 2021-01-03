import java.util.Arrays;

public class SudokuBoard implements Cloneable{
	
	/**
	 * actual board values
	 */
	private byte board[][];
	
	/**
	 * possible values for each square on the board
	 */
	private boolean possible[][][];
	
	/**
	 * length and height of the board
	 */
	private int size;
	
	/**
	 * length and height for a subdivision of the board
	 */
	private int square_size;
	
	/**
	 * count of remaining squares
	 */
	private int remaining;
	
	/**
	 * specific instructions for creating a deep copy of a board
	 */
	public Object clone() {
		
		//declare space for the new board
		SudokuBoard new_board = new SudokuBoard(size);
		
		//manually step through the existing board
		for(int x= 0; x<size; x++)
			for(int y= 0; y<size; y++)
				
				//fill in existing values on the new board
				if (getSquare(x, y) != 0)
					new_board.Assign(new Location(x, y, getSquare(x, y)));
		
		//return the clone
		return new_board;
	}
	
	/**
	 * constructor
	 * 
	 * @param size length and height of the board
	 */
	public SudokuBoard(int size) {
		
		//set size
		this.size = size;
		
		//test to see if size is a square number
		double sqrt_test = Math.sqrt(size);
		if (sqrt_test % 1 != 0.0) throw new
		IllegalArgumentException("must declare with a square number");
		
		//set length and height of a subdivision of the board
		square_size = (int)sqrt_test;
		
		//set number remaining to be filled
		remaining = (int)Math.pow(size, 2);
		
		//declare blank board
		board = new byte[size][size];
		
		//declare and fill list of possible values
		possible = new boolean[size][size][size];
		for (boolean[][] row:possible)
			for (boolean[] square:row)
				Arrays.fill(square, true);
	}
	
	/**
	 * console output for testing
	 * 
	 * @param b
	 */
	public static void PrintBoard(SudokuBoard b) {
		int size, square_size, x_next, y_next;
		size = b.getSize();
		square_size = b.getSquareSize();
		
		x_next = square_size;
		for (int x = 0; x < size;) {
			for (;x<x_next;x++) {
				y_next = square_size;
				for (int y = 0; y < size;) {
					for (;y<y_next;y++) {
						System.out.print(b.getSquare(x, y));
					}
					System.out.print(" ");
					y_next += square_size;
				}
				System.out.println();
			}
			System.out.println();
			x_next += square_size;
		}
	}
	
	/**
	 * console output for testing
	 * @param b
	 */
	public static void PrintPossible(SudokuBoard b) {
		int size, square_size, x_next, y_next;
		size = b.getSize();
		square_size = b.getSquareSize();
		
		x_next = square_size;
		for (int x = 0; x < size;) {
			for (;x<x_next;x++) {
				y_next = square_size;
				for (int y = 0; y < size;) {
					for (;y<y_next;y++) {
						for (byte z = 0; z < size; z++) {
							if (b.getPossible(x, y, z))
								System.out.print("1");
							else System.out.print("0");
						}
						System.out.print(" ");
					}
					System.out.print("   ");
					y_next += square_size;
				}
				System.out.println();
			}
			System.out.println();
			x_next += square_size;
		}
	}

	/**
	 * Assigns a value to the board
	 * 
	 * @param value row, column and value to be set
	 */
	public void Assign(Location value) {
		
		//local decelerations of value
		int value_index = value.value-1,
				row = value.row,
				column = value.column;
		
		//identifies subdivision location
		int s_row, s_column;
		
		//prevents assigning to a square in use
		if (board[row][column] != 0) {
			System.out.println("------------------------------------");
			SudokuBoard.PrintPossible(this);
			SudokuBoard.PrintBoard(this);
			value.Print();
			
			throw new IllegalArgumentException("square is in use");
			}
		
		//prevents assigning a value to a location where it is not possible
		if (!possible[row][column][value_index]) {
			System.out.println("------------------------------------");
			SudokuBoard.PrintPossible(this);
			SudokuBoard.PrintBoard(this);
			value.Print();
			
			throw new IllegalArgumentException("that number cannot be placed here");
		}
		
		//subdivision location calculation
		s_row = row - (row % square_size);
		s_column = column - (column % square_size);
		
		//clear out possibilities for specified value in all rows, columns and subdivision
		//clear out square possibilities for all values
		for (int i = 0; i < size; i++) {
			possible[i][column][value_index] = false;
			possible[row][i][value_index] = false;
			possible[s_row + (i/square_size)][s_column + (i%square_size)][value_index] = false;
			possible[row][column][i] = false;
		}
		
		//reset possible value in specified square
		possible[row][column][value_index] = true;
		
		//set value in board
		board[row][column] = (byte)value.value;
		
		//decrement remaining counter
		remaining--;
	}
	
	/**
	 * used locally to print the possible values
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @return
	 */
	private boolean getPossible(int row, int column, byte value) {
		return possible[row][column][value];
	}
	
	/**
	 * returns array of possible values for a square
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean[] getPossible(int row, int column) {
		return possible[row][column];
	}
	
	/**
	 * tests counter to see if board has been solved
	 * 
	 * @return
	 */
	public boolean isSolved() {return remaining==0;}
	
	/**
	 * @return length and height of the board
	 */
	public int getSize() {return size;}
	
	/**
	 * used locally to print
	 * @return
	 */
	private int getSquareSize() {return square_size;}
	
	/**
	 * Gets assigned value for a square
	 * 
	 * @param x row
	 * @param y column
	 * @return value of specified square
	 */
	public int getSquare(int x, int y) {return board[x][y];}

}
