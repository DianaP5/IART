package logic;

public class Moves {

	private Board board;
	private Boolean blackFirstMove=true;
	private Boolean whiteFirstMove=true;
	
	public Board getBoard(){
		return board;
	}
	
	public void setBoard(Board board){
		this.board=board;
	}
	
	public Boolean placePiece(int x,int y,int player,int direction){
		
		if ((!blackFirstMove && player == 0 ) || (!whiteFirstMove && player == 1)){
			System.out.println("olaaaaaaaaaaaaaaaaaa");
			if (!board.slidable(player))
				return false;
		}else if (player == 0)
				blackFirstMove=false;
		else if (player == 1)
				whiteFirstMove=false;
		
		System.out.println("PASOUSUSUSUSUSU");
		
		if (board.isUsed(x,y))
			return false;
		
		if (!board.checkValidPlace(x,y,player,direction,0))
			return false;

		return true;
	}
	
	public Boolean slidePiece(int x, int y,int x1,int y1, int player,int direction){
		
		if (!board.checkActivePiece(x,y,player))
			return false;
		
		//board.pivotPiece(x,y,player); //facultativo
		
		if (!board.checkValidSlide(x,y,x1,y1,player))
			return false;

		if (!board.checkValidPlace(x,y,player,direction,1))
			return false;
		
		board.erasePiece(x,y);
				
		return true;
		
	}
}
