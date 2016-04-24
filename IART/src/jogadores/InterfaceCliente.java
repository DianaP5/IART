package jogadores;

import java.io.IOException;

import logic.Jogo.ResultadoMovimento;

public interface InterfaceCliente {
	ResultadoMovimento recebePosicao(int posX, int posY) throws IOException;
	ResultadoMovimento lancaTiro(int posX, int posY) throws IOException;
}
