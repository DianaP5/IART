package logic;

import java.util.ArrayList;

public class Board {

	private int boardSize;
	private int[][] board;

	public Board() {
		this.boardSize = 6;
		this.board = new int[boardSize][boardSize];
		this.initBoard();
	}

	public void initBoard() {

		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				board[i][j] = 0;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public void printBoard() {
		for (int i = 0; i < this.getBoardSize(); i++) {
			for (int j = 0; j < this.getBoardSize(); j++)
				System.out.print(this.getBoard()[j][i] + " ");
			System.out.println();
		}
	}

	public Boolean isUsed(int x, int y) {
		if (board[x][y] != 0)
			return true;
		else
			return false;
	}

	public Boolean checkActivePiece(int x, int y,int player) {
		int delta1 = 0, delta2 = 5;

		if (player == 1) {
			delta1 = 4;
			delta2 = 9;
		}
		
		if (board[x][y] > delta1 && board[x][y] < delta2)
			return true;
		
		return false;
	}

	public Boolean slidable(int player) {
		
		// player=0 preto player=1 branco
		int delta1 = -5, delta2 = 0;

		if (player == 1) {
			delta1 = -9;
			delta2 = -4;
		}

		for (int i = 0; i < this.getBoardSize(); i++) {
			for (int j = 0; j < this.getBoardSize(); j++)
				if (board[j][i] > delta1 && board[j][i] < delta2)
					if (checkOrtogonalSpaces(j, i)){
						System.out.println("iuahfoqihefoewiho");
						return true;
					}
		}

		return false;
	}

	public Boolean checkOrtogonalSpaces(int x, int y) {
		Boolean[] validaty = { true, true, true, true };

		validaty[0] = checkValidCoord(x - 1, y); // left
		validaty[1] = checkValidCoord(x + 1, y); // right
		validaty[2] = checkValidCoord(x, y + 1); // down
		validaty[3] = checkValidCoord(x, y - 1); // up

		for (int i = 0; i < validaty.length; i++)
			if (validaty[i]) {
				if (i == 0)
					if (board[x - 1][y] == 0)
						return true;
				if (i == 1)
					if (board[x + 1][y] == 0)
						return true;
				if (i == 2)
					if (board[x][y + 1] == 0)
						return true;
				if (i == 3)
					if (board[x][y - 1] == 0)
						return true;
			}
		System.out.println("TESTESTE");
		return false;
	}

	public Boolean checkValidCoord(int x, int y) {
		if (x >= 0 && y >= 0 && x < boardSize && y < boardSize)
			return true;
		else
			return false;
	}

	public Boolean checkPlayer(int x, int y, int player) {
		// player=0 branco player=1 preto
		
		//ver inativas também
		
		int delta1 = 0, delta2 = 5;

		if (player == 1) {
			delta1 = 4;
			delta2 = 9;
		}

		if (board[x][y] > delta1 && board[x][y] < delta2)
			return true;

		return false;

	}

	public Boolean checkValidSlide(int x, int y, int x1, int y1, int player) {

		if (x == x1) {
			if (y1 - y > 0) {
				ArrayList<Integer> a1 = new ArrayList<Integer>();
				for (int i = y + 1; i < y1; i++) {
					if (x - 1 >= 0) {
						a1.add(x - 1);
						a1.add(i);
					}
					if (x + 1 <= boardSize) {
						a1.add(x + 1);
						a1.add(i);
					}
					if (board[x][i] != 0)
						return false;
				}
				this.checkDeactivationSlide(a1, 0);
			} else {
				ArrayList<Integer> a1 = new ArrayList<Integer>();
				for (int i = y1; i < y - 1; i++) {
					if (x - 1 >= 0) {
						a1.add(x - 1);
						a1.add(i);
					}
					if (x + 1 <= boardSize) {
						a1.add(x + 1);
						a1.add(i);
					}
					if (board[x][i] != 0)
						return false;

					this.checkDeactivationSlide(a1, 1);
				}
			}
		} else if (y == y1) {
			if (x1 - x > 0) {
				ArrayList<Integer> a1 = new ArrayList<Integer>();

				for (int i = x + 1; x < x1; i++) {
					if (y - 1 >= 0) {
						a1.add(i);
						a1.add(y - 1);
					}
					if (y + 1 <= boardSize) {
						a1.add(i);
						a1.add(y + 1);
					}
					if (board[i][y] != 0)
						return false;

					this.checkDeactivationSlide(a1, 2);

				}
			} else {
				ArrayList<Integer> a1 = new ArrayList<Integer>();

				for (int i = x1; i < x - 1; i++) {
					if (y - 1 >= 0) {
						a1.add(i);
						a1.add(y - 1);
					}
					if (y + 1 <= boardSize) {
						a1.add(i);
						a1.add(y + 1);
					}

					if (board[i][y] != 0)
						return false;

					this.checkDeactivationSlide(a1, 3);
				}
			}
		} else
			return false;

		
		return true;
	}

	private void checkDeactivationSlide(ArrayList<Integer> a1, int direction) {

	}

	public Boolean checkValidPlace(int x, int y, int player, int direction,int slide) {

		Boolean[] validaty = { true, true, true, true };
		int[] rules = { -1, -1, -1, -1 };
		
		//check adjacents
		validaty[0] = checkValidCoord(x - 1, y); // left
		validaty[1] = checkValidCoord(x + 1, y); // right
		validaty[2] = checkValidCoord(x, y + 1); // down
		validaty[3] = checkValidCoord(x, y - 1); // up
		
		System.out.println(validaty[0]+" "+validaty[1]+" "+validaty[2]+" "+validaty[3]);
		
		for (int i = 0; i < validaty.length; i++)
			if (validaty[i]) {
				if (i == 0) {
					if (board[x - 1][y] == 0)
						rules[i] = 0;
					else if (board[x - 1][y] == 2 || board[x - 1][y] == 6)
						rules[i] = 1;
					else if (board[x - 1][y] < 0)
						rules[i] = 2;
					else
						rules[i] = 3;
				}
				if (i == 1) {
					if (board[x + 1][y] == 0)
						rules[i] = 0;
					else if (board[x + 1][y] == 4 || board[x + 1][y] == 8)
						rules[i] = 1;
					else if (board[x + 1][y] < 0)
						rules[i] = 2;
					else
						rules[i] = 3;
				}
				if (i == 2) {
					if (board[x][y + 1] == 0)
						rules[i] = 0;
					else if (board[x][y + 1] == 1 || board[x][y + 1] == 5)
						rules[i] = 1;
					else if (board[x][y + 1] < 0)
						rules[i] = 2;
					else
						rules[i] = 3;
				}
				if (i == 3) {
					if (board[x][y - 1] == 0)
						rules[i] = 0;
					else if (board[x][y - 1] == 3 || board[x][y - 1] == 7)
						rules[i] = 1;
					else if (board[x][y - 1] < 0)
						rules[i] = 2;
					else
						rules[i] = 3;
				}
			}
		
		System.out.println(rules[0]+" "+rules[1]+" "+rules[2]+" "+rules[3]);
		
		if (!validaty[0] && !validaty[1] && !validaty[2] && !validaty[3])
			return false;

		int activeCount = 0;

		for (int k = 0; k < rules.length; k++)
			if (rules[k] == 3)
				activeCount++;

		if (activeCount > 1)
			return false;
		else
			for (int k = 0; k < rules.length; k++)
				if (rules[k] == 3 && direction == k) {
					this.placePiece(x, y, false, player, direction);
					return true;
				}
		
		if (slide == 1){
			if (rules[0] <= 1 && rules[1] <= 1 && rules[2] <= 1 && rules[3] <= 1) {
				this.placePiece(x, y, false, player, direction);
				return true;
			}
		}
		
		if (rules[0] < 1 && rules[1] < 1 && rules[2] < 1 && rules[3] < 1) {
			this.placePiece(x, y, true, player, direction);
			return true;
		} else {
			for (int g = 0; g < rules.length; g++)
				if (rules[g] == 2 && slide == 1) {
					this.placePiece(x, y, false, player, direction);
					return true;
				}
		}
		
		return false;
	}

	public Boolean placePiece(int x, int y, Boolean state, int player,
			int direction) {
		System.out.println(player+" "+board[x][y]+" "+direction);
		if (player == 0) {
			if (!state)
				board[x][y] = direction + 1;
			else{
				board[x][y] = -direction+ 1;
				System.out.println("wefkjwebfwefweof: "+board[x][y]);
				}
			
		} else if (player == 1) {
			System.out.println("CENASCENASCENASCENAS: "+direction+" ");
			if (!state)
				board[x][y] = direction + 1 + 4;
			else
				board[x][y] = -(direction + 1 + 4);
		} else
			return false;

		return true;
	}

	public void erasePiece(int x, int y) {
		board[x][y]=0;
	}
}
