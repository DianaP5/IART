package logic;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;




import logic.Peca;
import jogadores.LigacaoInimigo;
//import Batalha.constantes.Constantes;
import logic.Jogo.Estado;
//import Batalha.logica.Jogo.ResultadoTiro;
//import Batalha.logica.Jogo.TiposTentativa;
//import Batalha.logica.Jogo.Estado;
import logic.ObservadorJogo;

public class Jogo<ResultadoMovimento> {
	
	
	private Estado estado = Estado.Espera;
	private LigacaoInimigo rede;
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
	
	public static enum ResultadoMovimento
	{
		Falhou,
		Acertou,
		JogadorPerdeu
	}

	public Estado getEstado()
	{
		return estado;
	}
	
	public void primeiroJogador()
	{
		if (!iniciado)
		{
			iniciado = true;
			this.estado = Estado.EmJogo;
			notificarObservadores();
		}
		else throw new InvalidParameterException("Chamada invalida");
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
	

	public ResultadoMovimento recebePosicao(int posX, int posY) throws IOException{
		iniciado = true;
		boolean acertou = tabuleiro.novaPosicao(posX, posY);
		if (!acertou) this.estado = Estado.EmJogo;
		notificarObservadores();
		if (acertou)
		{
			if (verificaPerdeu()) return ResultadoMovimento.JogadorPerdeu;
			else return ResultadoMovimento.Acertou;
		}
		else return ResultadoMovimento.Falhou;
	}
	

	public void lancaTiro(int posX, int posY) throws IOException{
		if (this.estado != Estado.EmJogo) return;
		if (posX < 0 || posY < 0 || posX >= 6 || posY >= 6)
			throw new InvalidParameterException("Posicao invalida");
		//if (tentativas[posY][posX] != TiposTentativa.Vazio) return;
		ResultadoMovimento resultado = rede.lancaTiro(posX, posY);
		boolean acertou = (resultado != ResultadoMovimento.Falhou);
		//tentativas[posY][posX] = (acertou ? TiposTentativa.Acertou : TiposTentativa.Falhou);
		if (!acertou) this.estado = Estado.Espera;
		else if (resultado == ResultadoMovimento.JogadorPerdeu) this.estado = Estado.Ganho;
		notificarObservadores();
	}
	
	public boolean verificaPerdeu(){
		for(Peca peca : tabuleiro.getPecas()){
			//if(!peca.getDestruido()) return false;
		}
		this.estado = Estado.Perdido;
		notificarObservadores();
		return true;
	}

	
}
