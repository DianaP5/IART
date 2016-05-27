package logic;

import java.util.ArrayList;

public class Ai {
	
	private int player=1;
	private Board board;
	private int mySeed=1;
	private int oppSeed=0;
	private int[] result;
	public Ai(Board board) {
		this.setBoard(board);
		 //level player alpha beta
	      result = minimax(1, player, Integer.MIN_VALUE+1, Integer.MAX_VALUE-1,-1,-1,0);
	         // depth, max-turn, alpha, beta
	      System.out.println(result[0]+" "+result[1]+" "+result[2]);
	      
	      this.getBoard().getBoard()[result[1]][result[2]] = result[3];
	      
	      this.getBoard().printBoard();
	      
	    		  //new int[] {result[1], result[2]};   // row, col
	}

	public static void main(String[] args) {
		Board b1=new Board();
		b1.initBoard();
		new Ai(b1);
		
	}
	
	 private int[] minimax(int depth, int player, int alpha, int beta,int x,int y,int p) {
	      // Generate possible next moves in a list of int[2] of {row, col}.
	      ArrayList<int[]> nextMoves = generateMoves();
	 
	      // mySeed is maximizing; while oppSeed is minimizing
	      int score = 0;
	      int bestRow = -1;
	      int bestCol = -1;
	      int piece=0;
	 
	      if (nextMoves.isEmpty() || depth == 0) {
	         // Gameover or depth reached, evaluate score
	         score = evaluate(x,y);
	         System.out.println("           RETURN1: "+bestRow+" "+bestCol);
	         return new int[] {score, bestRow, bestCol};
	      } else {
	          for (int i=0; i < nextMoves.size();i++) {
	              // try this move for the current "player"
	              getBoard().getBoard()[nextMoves.get(i)[0]][nextMoves.get(i)[1]] = nextMoves.get(i)[2];
	              
	              if (player == mySeed) {  // mySeed (computer) is maximizing player
	                 score = minimax(depth - 1, oppSeed, alpha, beta,nextMoves.get(i)[0],nextMoves.get(i)[1],nextMoves.get(i)[2])[0];
	                 if (score > alpha) {
	                    alpha = score;
	                    bestRow = nextMoves.get(i)[0];
	                    bestCol = nextMoves.get(i)[1];
	                    piece=nextMoves.get(i)[2];
	                 }
	              } else {  // oppSeed is minimizing player
	                 score = minimax(depth - 1, mySeed, alpha, beta,nextMoves.get(i)[0],nextMoves.get(i)[1],nextMoves.get(i)[2])[0];
	                 if (score < beta) {
	                    beta = score;
	                    bestRow = nextMoves.get(i)[0];
	                    bestCol = nextMoves.get(i)[1];
	                    piece=nextMoves.get(i)[2];
	                 }
	              }
	              // undo move
	              getBoard().getBoard()[nextMoves.get(i)[0]][nextMoves.get(i)[1]] = 0;
	              // cut-off
	              if (alpha >= beta) break;
	           }
	          int a=(player == mySeed) ? alpha : beta;
	          System.out.println("           RETURN2: "+a+" "+bestRow+" "+bestCol);
	          
	          return new int[] {(player == mySeed) ? alpha : beta, bestRow, bestCol,piece};
	        }
	 }

	private int evaluate(int bestRow, int bestCol) {
		int returnValue=0;
		
		/* PLACING
		 * cantos valem ->+2 
		 * extremos de linhas ou colunas +1 
		 * restantes  posições 0;
		 * 
		 * colocar peça ativa-> +10
		 * colocar peça inativa -> -10
		2) 
		2.1)Verificar colocar um peça nova sem dar para ser desativada-> +10
		2.2) Verificar colocar peça nova e dar para ser desativada-> -10
		2.3) Verificar colocar peça nova e dar para desativar outra-> +20
		
		*/
		
		int[][] tempBoard = new int[6][6];
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				tempBoard[i][j]=board.getBoard()[i][j];
		
		returnValue+=verifyPlace(bestRow,bestCol);
		
		board.setBoard(tempBoard);
		
		//random 4
		if (bestRow == 0 && bestCol == 0)
			returnValue+= 2;
		else if (bestRow == 0 && bestCol == 5)
			returnValue+= 2;
		else if (bestRow == 5 && bestCol == 0)
			returnValue+= 2;
		else if (bestRow == 5 && bestCol == 5)
			returnValue+= 2;
		
		//random 4
		if (bestRow == 0)
			returnValue+= 1;
		else if (bestCol == 0)
			returnValue+= 1;
		else if (bestRow == 5 )
			returnValue+= 1;
		else if (bestCol == 5)
			returnValue+= 1;
		
		if (board.checkActivePiece(bestRow, bestCol, 1))
			returnValue+=10;
		else returnValue-=10;
		
		return returnValue;
	}

	private int verifyPlace(int bestRow, int bestCol) {
		int[] p1 = {-2,-3,-4,-1};
		
		Moves m1=new Moves();
		m1.setBoard(getBoard());
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++){
				for (int k=0; k < p1.length; k++)
					if (i == bestRow - 1 || i == bestRow +1 || j == bestCol -1 || j == bestCol + 1)
						if (board.getBoard()[i][j] == p1[k]){
							if (i == bestRow - 1 || i == bestRow + 1){
								int piece=m1.getBoard().getBoard()[i][j];
								
								if (bestCol + 1 < 6)
									bestCol++;
								m1.removePiece(i, j, 0);
								
								System.out.println("opt1");
								if (m1.slidePiece(i, j, i, bestCol, 0, -piece -1,piece))
									return -10;
								else if (m1.slidePiece(i, j, i,5-bestCol, 0, -piece -1,piece))
									return -10;
							}
							if (j == bestCol - 1 || j == bestCol + 1){
								int piece=m1.getBoard().getBoard()[i][j];
								
								if (bestRow + 1 < 6)
									bestRow++;
								
								m1.removePiece(i, j, 0);
								
								System.out.println("opt2"+i+" "+j+" "+" "+bestRow+" "+j+" ");
								if (m1.slidePiece(i, j, bestRow,j, 0, -piece -1,piece))
									return -10;
								else if (m1.slidePiece(i, j, 5-bestRow,j, 0, -piece -1,piece))
									return -10;
							}
						}
			}
		
		return 10;
	}

	private ArrayList<int[]> generateMoves() {
		
		ArrayList<int[]> moves=new ArrayList<int[]>();
		
		Moves m1=new Moves();
		m1.setBoard(getBoard());
		int[] p2 = { 6, -6, 7, -7, 8, -8, 5, -5 };
			
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++){
				for (int k=0; k < p2.length; k++)
					if (p2[k] > -9 && p2[k] < -4){
						if (m1.placePieceInit(i, j, 1, -p2[k]-5, p2[k])){
							System.out.println(i+" "+j);
							getBoard().getBoard()[i][j]=0;
							int[] temp={i,j,p2[k]};
							moves.add(temp);
						}
					}else if (m1.placePieceInit(i, j, 1,p2[k]-5, p2[k])){
						System.out.println(i+" "+j);	
						getBoard().getBoard()[i][j]=0;
							int[] temp={i,j,p2[k]};
							moves.add(temp);
					}
				}
		return moves;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	 
}
