package logic;

import java.util.ArrayList;


//import Batalha.constantes.Constantes;
import logic.Peca.TipoPeca;

public class ConstrutorTabuleiro {
	
	private ArrayList<Peca> pecas;
	private boolean[][] ocupados;
	private int tamanho;
	
	public ConstrutorTabuleiro(){
		this.tamanho = 6;
		pecas = new ArrayList<Peca>();
		ocupados = new boolean[tamanho][tamanho];
	}
	
	public boolean retirarPeca(int posX, int posY){
		if (!ocupados[posY][posX]) return false;
		for (Peca peca : pecas)
		{
			boolean remover = false;
			for(int i = 0, dx = 0, dy = 0; i < peca.getComprimento(); i++){
				
				if (peca.getPosX()+dx == posX && peca.getPosY()+dy == posY)
				{
					remover = true;
					break;
				}
			}
			if (remover)
			{
				for(int i = 0, dx = 0, dy = 0; i < peca.getComprimento(); i++){
		
					ocupados[peca.getPosY()+dy][peca.getPosX()+dx] = false;
				}
				pecas.remove(peca);
				return true;
			}
		}
		return false;
	}
	
	// Se adicionado, devolve true, se já estiver ocupado, devolve false
	// Navio não pode ser repetido
	public boolean adicionarPeca(Peca peca)
	{
		for(int i = 0; i < pecas.size();i++){
			if(pecas.get(i).getTipo() == peca.getTipo()) return false;
		}
		
		boolean areaDesocupada = true;
		for(int i = 0, dx = 0, dy = 0; i < peca.getComprimento(); i++){
			
			System.out.println("Comprimento da Peca: " + peca.getComprimento());
			
			if (peca.getPosY()+dy >= 6) return false;
			if (peca.getPosX()+dx >= 6) return false;
			//if (navio.getPosX()+dx >= Constantes.tamanhoTabuleiro) return false;
			if (ocupados[peca.getPosY()+dy][peca.getPosX()+dx])
			{
				areaDesocupada = false;
				break;
			}
		}
		if (areaDesocupada)
		{
			for(int i = 0, dx = 0, dy = 0; i < peca.getComprimento(); i++){
				ocupados[peca.getPosY()+dy][peca.getPosX()+dx] = true;
			}
			pecas.add(peca);
			return true;
		}
		else return false;
	}
	
	public ArrayList<Peca> getPecas(){
		return pecas;
	}
	
	public boolean temPeca(TipoPeca tipoPeca)
	{
		for (Peca peca : pecas)
		{
			if (peca.getTipo() == tipoPeca) return true;
		}
		return false;
	}
	
	public boolean tabuleiroPreenchido(){
		if(pecas.size() == Peca.TipoPeca.values().length) return true;
		return false;
	}
	
	public Tabuleiro getTabuleiro()
	{
		if (pecas.size() == Peca.TipoPeca.values().length)
		{
			return new Tabuleiro(pecas, tamanho);
		}
		return null;
	}
}
