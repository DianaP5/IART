package jogadores;

import java.io.IOException;

import logic.Jogo.ResultadoMovimento;;

public interface InterfaceServidor{
	boolean recebeCliente(InterfaceCliente cliente) throws IOException;
	ResultadoMovimento recebePosicao(int posX, int posY) throws IOException;
	ResultadoMovimento lancaTiro(int posX, int posY) throws IOException;
}
