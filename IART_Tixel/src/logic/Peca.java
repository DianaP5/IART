package logic;

import java.util.EnumMap;


public class Peca {
	
	private int posX;
	private int posY;
	private boolean ativa;
	private TipoPeca tipo;
	private int comprimento;
	
	public static enum TipoPeca{
		pecaJogador1,
		pecaJogador2
	} 
	
	private static EnumMap<TipoPeca, Integer> comprimentos = new EnumMap<TipoPeca, Integer>(TipoPeca.class);
	
	static
	{
		//TODO compor - tamanhos e nomes ja nao estao corretos
		comprimentos.put(TipoPeca.pecaJogador1,1);
		comprimentos.put(TipoPeca.pecaJogador2,1);
	}
	
	
	public Peca(TipoPeca tipo, int posX, int posY, boolean ativa){
		this.posX = posX;
		this.posY = posY;
		this.ativa = ativa;  
		
	}
	
	public int getComprimento() {
		return comprimento;
	}
	
	public TipoPeca getTipo() {
		return tipo;
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

	
	public boolean getAtiva(){
		return ativa;
	}

	public void setAtiva(boolean ativa){
		this.ativa = ativa;
	}
}
