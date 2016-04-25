package logic;

public class Moves {

	private Board board;
	
	public Board getBoard(){
		return board;
	}
	
	public void setBoard(Board board){
		this.board=board;
	}
	
	public Boolean placePiece(int x,int y,int player,int direction){

		/*if (!board.slidable(player))
			return false;*/
		
		if (board.isUsed(x,y))
			return false;
		System.out.println("cena1");
		if (!board.checkValidPlace(x,y,player,direction))
			return false;

		System.out.println("cena1");
		return true;
	}
	
	public Boolean slidePiece(int x, int y,int x1,int y1, int player,int direction){
		
		if (!board.checkActivePiece(x,y,player))
			return false;
		
		//board.pivotPiece(x,y,player); //facultativo
		
		if (!board.checkValidSlide(x,y,x1,y1,player))
			return false;

		if (!board.checkValidPlace(x,y,player,direction))
			return false;
		
		board.erasePiece(x,y);
				
		return true;
		
	}
}
