package logic;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Tabuleiro\n");
		
		Tabuleiro tab = null;
		
		Jogo novo_jogo = new Jogo(tab);
		
		novo_jogo.iniciar();
	}
}

//https://www.youtube.com/watch?v=GljtsYkYOKA