package jogadores;

import java.io.IOException;

import logic.Jogo;
//import Batalha.logica.Jogo.ResultadoTiro;
import logic.Jogo.ResultadoMovimento;

public abstract class LigacaoInimigo {
	
	Jogo jogo = null;
	
	public void setJogo(Jogo jogo){
		this.jogo = jogo;
	}
	
	//public abstract ResultadoTiro lancaTiro(int posX, int posY) throws IOException;
	
	public abstract void terminar();

	public abstract ResultadoMovimento lancaTiro(int posX, int posY) throws IOException;
}
