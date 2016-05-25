package logic;

public class Moves {

	private Board board;
	private Boolean blackFirstMove=true;
	private Boolean whiteFirstMove=true;
	private int bonusMove=-1;
	
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

		return true;
	}
	
	public Boolean slidePiece(int x, int y,int x1,int y1, int player,int direction,int piece){
		System.out.println("SLDIINGS");
		/*if (!board.checkActivePiece(x,y,player))
			return false;
		System.out.println("Active");*/
		
		//board.pivotPiece(x,y,player); //facultativo
		
		if (x == x1 && x1 == y1)
			return false;
		
		if (!board.checkValidSlide(x,y,x1,y1,player))
			return false;
		
		System.out.println("Valid slide");
		
		if (!board.checkValidPlace(x1,y1,player,direction,piece,1))
			return false;
		
		System.out.println("ValidPlace");
		
		return true;
		
	}

	public Boolean removePiece(int x, int y, int player) {
		
		if (!board.checkPlayer(x, y, player))
			return false;

		board.erasePiece(x,y);
		
		return true;
	}
	
	//TODO
	public Boolean activatePiece(int x,int y, int player){
		if (!board.checkPlayer(x, y, player))
			return false;
		
		
		return false;
	}
	//TODO
	public Boolean pickUpPiece(int x,int y, int player){
		if (!board.checkPlayer(x, y, player))
			return false;
		
		
		return false;
	}
	//TODO
	public Boolean pivotPiece(int x,int y, int player){
		if (!board.checkPlayer(x, y, player))
			return false;
		
		return false;
	}
}
