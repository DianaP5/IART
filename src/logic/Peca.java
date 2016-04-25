package logic;

import java.util.EnumMap;


public class Peca {
	
	private int posX;
	private int posY;
	private boolean ativa;
	private TipoPeca tipo;
	
	public static enum TipoPeca{
		pecaJogador1,
		pecaJogador2
	} 

	public Peca(TipoPeca tipo, int posX, int posY, boolean ativa){
		this.posX = posX;
		this.posY = posY;
		this.ativa = ativa;
		this.tipo = tipo;
	}
	
	public TipoPeca getTipo() {
		return tipo;
	}
	
	public boolean getAtiva() {
		return ativa;
	}
	
	public int getPosX(){
		return posX;
	}

	public int getPosY(){
		return posY;
	}

	public void setPosX(int posX){
		this.posX = posX;
	}


	public void setPosY(int posY){
		this.posY = posY;
	}

	public void setAtiva(boolean ativa){
		this.ativa = ativa;
	}
}
