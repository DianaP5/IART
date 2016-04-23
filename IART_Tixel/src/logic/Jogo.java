package logic;
import java.util.ArrayList;

import logic.ObservadorJogo;

public class Jogo {
	
	
	private Estado estado = Estado.Espera;
	//private LigacaoInimigo rede;
	private Tabuleiro tabuleiro;
	//private TiposTentativa[][] tentativas;
	private boolean iniciado = false;
	private ArrayList<ObservadorJogo> observadores = new ArrayList<ObservadorJogo>();


	public Jogo(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	public void iniciar() {

		//tabuleiro.displayTabuleiro();
		
	}
	
	public static enum Estado
	{
		Espera,
		EmJogo,
		Perdido,
		Ganho,
		Disconectado
	}

	public Estado getEstado()
	{
		return estado;
	}
	
	public void terminar() {
		//rede.terminar();
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public void adicionarObservador(ObservadorJogo observador)
	{
		observadores.add(observador);
	}
	
	
	private void notificarObservadores()
	{
		for (ObservadorJogo observador : observadores) observador.eventoJogo();
	}
	
	public void desconectar()
	{
		if(this.estado != Estado.Ganho && this.estado != Estado.Perdido){
			this.estado = Estado.Disconectado;
			notificarObservadores();	
		}
	}

	public void setEstado(Estado novoEstado) {
		this.estado = novoEstado;
	}
}
