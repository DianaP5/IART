package logic;
import java.util.ArrayList;
import java.util.Arrays;

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

}
