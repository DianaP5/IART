package logic;

public class Moves {

	private Board board;
	private Boolean blackFirstMove=true;
	private Boolean whiteFirstMove=true;
	public int[] pieces={10,10};
	public int bonus=-1;
	
	public Board getBoard(){
		return board;
	}
	
	public void setBoard(Board board){
		this.board=board;
	}
	
	public Boolean placePiece(int x,int y,int player,int direction,int piece){
		
		
		//checks first move for each player
		if ((!blackFirstMove && player == 0 ) || (!whiteFirstMove && player == 1)){
			if (!board.slidable(player))
				return false;
		}else if (player == 0)
				blackFirstMove=false;
		else if (player == 1)
				whiteFirstMove=false;
		
		if (board.isUsed(x,y))
			return false;
		
		if (!board.checkValidPlace(x,y,player,direction,piece,0))
			return false;
		
		getBoard().setBonusMove(-1);
		
		return true;
	}
	
	public Boolean slidePiece(int x, int y,int x1,int y1, int player,int direction,int piece){
		System.out.println("SLIDING");
		
		int tempBonus=getBoard().getBonusMove();;
		/*if (!board.checkActivePiece(x,y,player))
			return false;
		System.out.println("Active");*/
		
		//board.pivotPiece(x,y,player); //facultativo
		
		if (x == x1 && y == y1)
			return false;
		
		int[][] tempBoard = new int[6][6];
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				tempBoard[i][j]=board.getBoard()[i][j];
		
		if (!board.checkValidSlide(x,y,x1,y1,player))
			return false;

		System.out.println("Valid slide"+x+" "+y+" X "+x1+" "+y1);
		
		if (!board.checkValidPlace(x1,y1,player,direction,piece,1)){
			board.setBoard(tempBoard);
			return false;
		}
		
		if (tempBonus > -1)
			getBoard().setBonusMove(-1);
		
		System.out.println("ValidPlace");
		
		return true;
		
	}

	public Boolean removePiece(int x, int y, int player) {
		
		if (!board.checkPlayer(x, y, player))
			return false;

		board.erasePiece(x,y);
		
		getBoard().setBonusMove(-1);
		
		return true;
	}
	
	public Boolean pickUpPiece(int x, int y, int player) {
		
		if (!board.checkPlayer(x, y, player))
			return false;

		board.erasePiece(x,y);
		
		this.pieces[player]++;
		
		getBoard().setBonusMove(-1);
		
		return true;
	}
	
	public Boolean activatePiece(int x,int y, int player,int direction,int piece){
		/*if (!board.checkPlayer(x, y, player))
			return false;
		
		if (board.checkActivePiece(x,y,player))
			return false;
		*/
		if (!board.checkValidPlace(x,y,player,direction,piece,0))
			return false;
		
		System.out.println("valid place");
		
		if (!board.checkActivePiece(x,y,player))
			return false;
		
		System.out.println("active place");
		
		getBoard().setBonusMove(-1);
		
		return true;
	}
	 
	public Boolean pivotPiece(int x,int y, int player,int direction,int piece){
		
		System.out.println("pivoting");
		
		if (!board.checkValidPlace(x,y,player,direction,piece,0))
			return false;
		
		System.out.println("valid place");
		
		if (!board.checkActivePiece(x,y,player))
			return false;
		
		System.out.println("active place");
		
		getBoard().setBonusMove(-1);
		
		return true;
	}
	
	public Boolean placePieceInit(int x,int y,int player,int direction,int piece){

		if (board.isUsed(x,y))
			return false;
		
		if (!board.checkValidPlace(x,y,player,direction,piece,0))
			return false;
		
		getBoard().setBonusMove(-1);
		
		return true;
	}

	public Boolean slidePieceHeu(int x, int y,int x1,int y1, int player,int direction,int piece){
		System.out.println("SLIDING_HEU");
		
		int tempBonus=getBoard().getBonusMove();;
		/*if (!board.checkActivePiece(x,y,player))
			return false;
		System.out.println("Active");*/
		
		//board.pivotPiece(x,y,player); //facultativo
		
		if (x == x1 && y == y1)
			return false;
		
		int[][] tempBoard = new int[6][6];
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				tempBoard[i][j]=board.getBoard()[i][j];
		
		if (!board.checkValidSlide(x,y,x1,y1,player))
			return false;
	
		System.out.println("Valid slide"+x+" "+y+" X "+x1+" "+y1);
		
		if (!board.checkValidPlace(x1,y1,player,direction,piece,1)){
			board.setBoard(tempBoard);
			return false;
		}
		board.setBoard(tempBoard);
		
		if (tempBonus > -1)
			getBoard().setBonusMove(-1);
		
		System.out.println("ValidPlace");
		
		return true;
		
	}


}
