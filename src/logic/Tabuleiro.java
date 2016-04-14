package logic;
import java.util.ArrayList;
import java.util.Arrays;

import logic.Peca;
//import Batalha.logica.Jogo.TiposTentativa;
import logic.Peca;


public class Tabuleiro {
	
	private ArrayList<Peca> pecas;
	//private TiposTentativa[][] tentativasInimigo;
	private int tamanho;
		
	/*
	private char[][] tab = {
				{ '=', '=', '=', '=', '=', '=' },
				{ 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X' },
				{ '=', '=', '=', '=', '=', '=' } };
		//labirinto.setLab(lab);
	
	
	public void displayTabuleiro() {
		for (int i = 0; i < tab.length; i++) {
			System.out.println(tab[i]);
		}
	} 
	*/
	
	public Tabuleiro(){
		
	}
	
	public int getTamanho() {
		return tamanho;
	}
	
	public Tabuleiro(ArrayList<Peca> pecas, int tamanho) {
		super();
		this.pecas = pecas;
		this.tamanho = tamanho;
		//tentativasInimigo = new TiposTentativa[tamanho][tamanho];
		//for(TiposTentativa[] linha : tentativasInimigo)
		//	Arrays.fill(linha, TiposTentativa.Vazio);
	}

	public ArrayList<Peca> getPecas() {
		return pecas;
	}

	public boolean novaPosicao(int posX, int posY)
	{
		for (Peca peca: pecas)
		{
			if (peca.getPosX() == posX && posY >= peca.getPosY() && posY < peca.getPosY() + peca.getComprimento())
			{
				//peca.setDestruido(posY - peca.getPosY());
				//tentativasInimigo[posY][posX] = TiposTentativa.Acertou;
				return true;
			}
			else if (peca.getPosY() == posY && posX >= peca.getPosX() && posX < peca.getPosX() + peca.getComprimento())
			{
				//navio.setDestruido(posX - navio.getPosX());
				//tentativasInimigo[posY][posX] = TiposTentativa.Acertou;
				return true;
			}
		}
		//tentativasInimigo[posY][posX] = TiposTentativa.Falhou;
		return false;
	}

}
