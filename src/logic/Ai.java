package logic;

import java.util.ArrayList;
import java.util.Random;

public class Ai {
	
	private int player=1;
	private Board board;
	private int nPieces=10;
	private int mySeed=1;
	private int oppSeed=0;
	private int[] result;
	
	public Ai(Board board,int player,int nPieces) {
		this.setBoard(board);
		this.nPieces=nPieces;
		this.player=player;
		
		if (player == 0){
			this.mySeed=0;
			this.oppSeed=1;
		}
		 //level player alpha beta
		int[] vals={-1,-1,0};
		
	      result = minimax(1, getPlayer(), Integer.MIN_VALUE+1, Integer.MAX_VALUE-1,vals);
	         // depth, max-turn, alpha, beta
	      //board.printBoard();
	      
	      if (result.length == 3)
	    	  return;
	      
	      System.out.println("REPARA NISTO: "+result[0]+" "+result[1]+" "+result[2]+" "+result[4]+" "+result[5]);
	      
	      if (result[4] == -1 && result[5] == -1){
	    	  if (getnPieces() > 0){
	    		  this.getBoard().getBoard()[result[1]][result[2]] = result[3];
	    		  setnPieces(getnPieces() - 1);
	    	  }
	      	System.out.println("PLACE: "+result[1]+" "+result[2]);
	      }else{
	    	  
	    	  Moves m1=new Moves();
	    	  m1.setBoard(board);
	    	  //int piece=board.getBoard()[result[1]][result[2]];
	    	  m1.removePiece(result[1],result[2],player);
	    	  
	    	  int mul=1;
	    	  
	    	  if (result[3] > 0)
	    		  mul=1;
	    	  else mul=-1;
	    	  
	    	  int delta=5;
	    	  
	    	  if (player == 0){
	  			delta=1;
	  		}
	    	  
	    	  m1.slidePiece(result[1], result[2], result[4],result[5], player,result[3]*mul-delta, result[3]);
	    	  
	      }
	      
	     // System.out.println(this.generateSlides().size());
	      
	      //board.printBoard();
	      //this.getBoard().printBoard();
	}

	public static void main(String[] args) {
		Board b1=new Board();
		b1.initBoard();
		b1.getBoard()[0][0]=-6;
		b1.getBoard()[5][0]=-6;
		b1.getBoard()[2][1]=-2;
		b1.getBoard()[5][2]=-6;
		b1.getBoard()[2][3]=3;
		b1.getBoard()[0][5]=-2;
		b1.getBoard()[5][5]=-2;
		b1.printBoard();
		
		new Ai(b1,1,1);
		
	}
	
	 private int[] minimax(int depth, int player, int alpha, int beta,int[] move) {
	      // Generate possible next moves in a list of int[2] of {row, col}.
	      ArrayList<int[]> nextMoves = generateMoves(player);
	      
	      
	      // mySeed is maximizing; while oppSeed is minimizing
	      int score = 0;
	      int bestRow = -1;
	      int bestCol = -1;
	      int slideX = -1;
	      int slideY = -1;
	      int piece=0;
	      
	      if (nextMoves.isEmpty() || depth == 0) {
	         // Gameover or depth reached, evaluate score
	         score = evaluate(move);
	         System.out.println("           RETURN1: "+bestRow+" "+bestCol);
	         return new int[] {score, bestRow, bestCol};
	      } else {
	          for (int i=0; i < nextMoves.size();i++) {
	              // try this move for the current "player"
	              //getBoard().getBoard()[nextMoves.get(i)[0]][nextMoves.get(i)[1]] = nextMoves.get(i)[2];
	              
	              if (player == mySeed) {  // mySeed (computer) is maximizing player
	                 score = minimax(depth - 1, oppSeed, alpha, beta,nextMoves.get(i))[0];
	                 if (score > alpha) {
	                    alpha = score;
	                    bestRow = nextMoves.get(i)[0];
	                    bestCol = nextMoves.get(i)[1];
	                    piece=nextMoves.get(i)[2];
	                    if (nextMoves.get(i).length > 3){
	                    	slideX=nextMoves.get(i)[2];
	                    	slideY=nextMoves.get(i)[3];
	                    	piece=nextMoves.get(i)[4];
	                    }else{
	                    	slideX=-1;
	                    	slideY=-1;
	                    }
	                 }
	              } else {  // oppSeed is minimizing player
	                 score = minimax(depth - 1, mySeed, alpha, beta,nextMoves.get(i))[0];
	                 if (score < beta) {
	                    beta = score;
	                    bestRow = nextMoves.get(i)[0];
	                    bestCol = nextMoves.get(i)[1];
	                    piece=nextMoves.get(i)[2];
	                    if (nextMoves.get(i).length > 3){
	                    	slideX=nextMoves.get(i)[2];
	                    	slideY=nextMoves.get(i)[3];
	                    	piece=nextMoves.get(i)[4];
	                    }else{
	                    	slideX=-1;
	                    	slideY=-1;
	                    }
	                 }
	              }
	              // undo move
	              //getBoard().getBoard()[nextMoves.get(i)[0]][nextMoves.get(i)[1]] = 0;
	              // cut-off
	              if (alpha >= beta) break;
	           }
	          int a=(player == mySeed) ? alpha : beta;
	          System.out.println("           RETURN2: "+a+" "+bestRow+" "+bestCol+" "+slideX+" "+slideY);
	          
	          return new int[] {(player == mySeed) ? alpha : beta, bestRow, bestCol,piece,slideX,slideY};
	        }
	 }

	private int evaluate(int[] move) {
		
		int bestRow=move[0], bestCol=move[1], piece=move[2];
		int returnValue=0;
		//board.printBoard();
		System.out.println("MOVE: "+move.length+" "+bestRow+" "+bestCol);
		
		if (move.length == 3){
		/* PLACING
		 * cantos valem ->+2 
		 * extremos de linhas ou colunas +1 
		 * restantes  posi��es 0;
		 * 
		 * colocar pe�a ativa-> +10
		 * colocar pe�a inativa -> -10
		2) 
		2.1)Verificar colocar um pe�a nova sem dar para ser desativada-> +10
		2.2) Verificar colocar pe�a nova e dar para ser desativada-> -10
		2.3) Verificar colocar pe�a nova e dar para desativar outra-> +20
		*/
		
		int[][] tempBoard = new int[6][6];
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				tempBoard[i][j]=board.getBoard()[i][j];
		
		returnValue+=evaluatePlace(bestRow,bestCol,piece,player);
		
		Random r = new Random();
		int rand = r.nextInt(4);
		
		//random 4
		if (bestRow == 0 && bestCol == 0)
			returnValue+= r.nextInt(4);
		else if (bestRow == 0 && bestCol == 5)
			returnValue+= r.nextInt(4);
		else if (bestRow == 5 && bestCol == 0)
			returnValue+= r.nextInt(4);
		else if (bestRow == 5 && bestCol == 5)
			returnValue+= r.nextInt(4);
		
		r = new Random();
		
		//random 4
		if (bestRow == 0)
			returnValue+= r.nextInt(4);
		else if (bestCol == 0)
			returnValue+= r.nextInt(4);
		else if (bestRow == 5 )
			returnValue+= r.nextInt(4);
		else if (bestCol == 5)
			returnValue+= r.nextInt(4);
		
		/*if (board.checkActivePiece(bestRow, bestCol, 1)){
			returnValue+=10;
			System.out.println("ENT�OA");
		}else returnValue-=10;*/
		
		board.setBoard(tempBoard);
		
		return returnValue;
	}else{
		int slideX=move[2],slideY=move[3];
		piece=move[4];
		
		returnValue+=evaluateSlide(bestRow,bestCol,slideX,slideY,piece,player);
		
		return returnValue;
	}
		
	}

	private int evaluateSlide(int bestRow, int bestCol, int slideX, int slideY,int piece,int player) {

		/*
		   1) 1.1)Verificar se pode mover e desativar outras pe�as e ficar ativo-> 10+x*10 x: n� de pe�as desativadas
			1.2) Verificar se pode mover e desativar e ganha bonus-> 20+x*10
			1.3)Verifica se pode mover sem desativar e fugiu de poder ser desativado-> 5
			1.4) Verifica se pode mover, desativar mas poder ser desativado -> x*10-10
			1.5) Verifica se pode mover sem desativar -> 0
			1.6) Verifica se pode mover sem desativar e poder ser desativado -> -20
		 */
		
		int count=0,returnValue=0, own=0;
		
		int delta=5;
		int[] p2 = { 6,7,8,5};
		
		if (player == 0){
			p2=new int[]{ 2, 3, 4, 1,};
			delta=1;
		}
		
		int[][] tempBoard = new int[6][6];
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				tempBoard[i][j]=board.getBoard()[i][j];
			
			board.printBoard();
		
			Moves m1=new Moves();
			m1.setBoard(board);
			//int piece=m1.getBoard().getBoard()[bestRow][bestCol];
			

			int oldPiece=board.getBoard()[bestRow][bestCol];
			
			m1.removePiece(bestRow, bestCol, player);
			int mul=1;
			
			if (piece > 0)
				mul=1;
			else mul=-1;
			
			if (m1.slidePiece(bestRow, bestCol, slideX, slideY, 1, piece*mul-delta, piece)){
				
				//board.setBoard(tempBoard);
				
				//board.printBoard();
				
				int oldPosValue=this.evaluatePlace(bestRow, bestCol, oldPiece,player);
				
				if (oldPosValue < 0)
					returnValue-=oldPosValue;
				
				System.out.println("TESTING TESTING: "+oldPosValue+" "+bestRow+" "+bestCol+" "+oldPiece);
				
				for (int n = 0; n < 6; n++)
					for (int m = 0; m < 6; m++){
						for (int g=0; g < p2.length; g++){
							if (m1.getBoard().getBoard()[n][m] > tempBoard[n][m] && m1.getBoard().getBoard()[n][m] != 0 && ( n != slideX || m != slideY))
								count++;
							if (m1.getBoard().getBoard()[n][m] == p2[g] && m1.getBoard().getBoard()[n][m] > tempBoard[n][m] && m1.getBoard().getBoard()[n][m] != 0 && ( n != slideX || m != slideY))
								own++;
						}
					}
				
				count-=own;
				
				System.out.println("prev: "+returnValue);
				if (m1.getBoard().getBoard()[slideX][slideY] > 0 && count == 0)
					returnValue-=60;
				else if (count == 0)
					returnValue+=10;

				System.out.println("after: "+returnValue);
				
				board.printBoard();
				
				board.setBoard(tempBoard);
				
				board.printBoard();
			}
			System.out.println("DIFF_: "+count);

			int a=returnValue+count*60-own*65;
			
			System.out.println("VALUE :"+a);
			
		return returnValue+count*60-own*65;
	}

	private int evaluatePlace(int bestRow, int bestCol,int p,int player) {
		int[] p1 = {-2,-3,-4,-1};
		int returnValue=0, opp=-1;
		
		int delta=1;
		
		if (player == 0){
			p1=new int[]{-5,-6,-7,-8 };
			delta=5;
		}
		
		if (player == 0)
			opp=1;
		else opp=0;
		
		
		if (p < 0)
			returnValue+=40;
		else returnValue-=30;
		
		System.out.println("PREV: "+returnValue+" "+p+" "+player);
		
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
								m1.removePiece(i, j, opp);
								
								System.out.println("opt1");
								if (m1.slidePiece(i, j, i, bestCol, opp, -piece -delta,piece))
									return returnValue+= -20;
								else if (m1.slidePiece(i, j, i,5-bestCol, opp, -piece -delta,piece))
									return returnValue+=-20;
							}
							if (j == bestCol - 1 || j == bestCol + 1){
								int piece=m1.getBoard().getBoard()[i][j];
								
								if (bestRow + 1 < 6)
									bestRow++;
								
								m1.removePiece(i, j, opp);
								
								System.out.println("opt2"+i+" "+j+" "+" "+bestRow+" "+j+" ");
								if (m1.slidePiece(i, j, bestRow,j, opp, -piece -delta,piece))
									return returnValue+= -20;
								else if (m1.slidePiece(i, j, 5-bestRow,j, opp, -piece -delta,piece))
									return returnValue+= -20;
							}
						}
			}
		if (bestRow == 0 && bestCol == 1 && player == 0)
			System.out.println("RETURNVALUEPLACE: "+returnValue);
		
		return returnValue;
	}
	
	private ArrayList<int[]> generateSlides(int player) {
		int[] p2 = {-6,-7,-8,-5};
		int[] p3 = {-6,6,-7,7,-8,8,-5,5};
		int mul=1;
		int delta=5;
		
		if (player == 0){
			p2=new int[]{-2,-3,-4,-1 };
			p3=new int[]{ 2, -2, 3, -3, 4, -4, 1, -1 };
			delta=1;
		}
		
		ArrayList<int[]> moves=new ArrayList<int[]>();

		Moves m1=new Moves();
		m1.setBoard(board);
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++){
				for (int k=0; k < p2.length; k++){
						if (m1.getBoard().getBoard()[i][j] == p2[k]){
							//int piece=m1.getBoard().getBoard()[i][j];
							int[] old={i,j,p2[k]};
							
							for (int h=0; h < 6; h++){
								m1.removePiece(i, j, player);
								for (int b=0; b < p3.length;b++){
									if (p3[b] > 0)
										mul=1;
									else mul=-1;
									
									if (m1.slidePieceHeu(i, j, 5-h, j, player, p3[b]*mul -delta,p3[b])){
										int[] temp={i, j, 5-h, j,p3[b]};
										moves.add(temp);
										//System.out.println("O1: "+temp[2]+" "+temp[3]);
										//m1.getBoard().printBoard();
										m1.removePiece(i, j, player);
									}}
							}
							
							m1.removePiece(i, j, player);
							
							for (int h=0; h < 6; h++)
								for (int b=0; b < p3.length;b++){
									if (p3[b] > 0)
										mul=1;
									else mul=-1;
								if (m1.slidePieceHeu(i, j, h, j, player, p3[b]*mul -delta,p3[b])){
									int[] temp={i, j, h, j,p3[b]};
									moves.add(temp);
									//System.out.println("O2: "+temp[2]+" "+temp[3]);
									m1.removePiece(i, j, player);
								}
								}
							m1.removePiece(i, j, player);
							
							for (int h=0; h < 6; h++)
								for (int b=0; b < p3.length;b++){
									if (p3[b] > 0)
										mul=1;
									else mul=-1;
								if (m1.slidePieceHeu(i, j, i, 5-h, player, p3[b]*mul -delta,p3[b])){
									int[] temp={i, j, i, 5-h,p3[b]};
									moves.add(temp);
									//System.out.println("O3: "+temp[2]+" "+temp[3]);
									m1.removePiece(i, j, player);
									//m1.getBoard().getBoard()[i][j]=piece;
								}}
							
							m1.removePiece(i, j, player);
							
							for (int h=0; h < 6; h++)
								for (int b=0; b < p3.length;b++){
									if (p3[b] > 0)
										mul=1;
									else mul=-1;
								if (m1.slidePieceHeu(i, j, i, h, player, p3[b]*mul -delta,p3[b])){
									int[] temp={i, j, i, h,p3[b]};
									moves.add(temp);
									//System.out.println("O4: "+temp[2]+" "+temp[3]);
									m1.removePiece(i, j, player);
									//m1.getBoard().getBoard()[i][j]=piece;
								}}
							board.getBoard()[old[0]][old[1]]=old[2];
						}
				}
			}
		
		for(int i=0; i < moves.size(); i++)
			for(int j=i+1; j < moves.size(); j++)
				if (moves.get(i)[2] == moves.get(j)[2] && moves.get(i)[3] == moves.get(j)[3])
					moves.remove(j);
		
		//for(int i=0; i < moves.size(); i++)
			//System.out.println("O: "+moves.get(i)[2]+" "+moves.get(i)[3]+" "+moves.get(i)[4]);
		
		return moves;
	}
	
	private ArrayList<int[]> generatePlaces(int player){
		ArrayList<int[]> moves=new ArrayList<int[]>();
		
		Moves m1=new Moves();
		m1.setBoard(getBoard());
		
		int delta=5;
		int[] p2 = { 6, -6, 7, -7, 8, -8, 5, -5 };
		
		if (player == 0){
			p2=new int[]{ 2, -2, 3, -3, 4, -4, 1, -1 };
			delta=1;
		}
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++){
				for (int k=0; k < p2.length; k++)
					if (p2[k] < 0){
						if (m1.placePieceInit(i, j, player, -p2[k]-delta, p2[k])){
							getBoard().getBoard()[i][j]=0;
							int[] temp={i,j,p2[k]};
							moves.add(temp);
						}
					}else if (m1.placePieceInit(i, j, player,p2[k]-delta, p2[k])){
						getBoard().getBoard()[i][j]=0;
							int[] temp={i,j,p2[k]};
							moves.add(temp);
					}
				}
		return moves;
	}
	
	public ArrayList<int[]> generateMoves(int player) {
		ArrayList<int[]> places=generatePlaces(player);
		ArrayList<int[]> slides=generateSlides(player);
		
		for (int i=0; i < places.size();i++)
			slides.add(places.get(i));
		
		return slides;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	private int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getnPieces() {
		return nPieces;
	}

	public void setnPieces(int nPieces) {
		this.nPieces = nPieces;
	}
	 
}
