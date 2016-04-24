package jogadores;

import java.io.IOException;
import java.net.Socket;


import logic.Jogo.ResultadoMovimento;
//import Batalha.logica.Jogo.ResultadoTiro;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.IServerListener;
import net.sf.lipermi.net.Server;

public class Servidor extends LigacaoInimigo implements InterfaceServidor, IServerListener{
	
	private Server server;
	private InterfaceCliente cliente = null;
	
	private int clientesConectados = 0;
	
	public Servidor() throws LipeRMIException, IOException {
		super();
		CallHandler callHandler = new CallHandler();
		callHandler.registerGlobal(InterfaceServidor.class, this);
		server = new Server();
		server.bind(55661, callHandler);
		server.addServerListener(this);
	}

	public boolean recebeCliente(InterfaceCliente cliente) throws IOException{
		if(this.cliente != null) return false;
		this.cliente = cliente;
		jogo.primeiroJogador();
		return true;
	}
	
	@Override
	public ResultadoMovimento recebePosicao(int posX, int posY) throws IOException{
		System.out.println("Servidor recebeu tiro");
		return jogo.recebePosicao(posX, posY);
	}
	
	@Override
	public ResultadoMovimento lancaTiro(int posX, int posY) throws IOException {
		System.out.println("Servidor envia tiro");
		return cliente.recebePosicao(posX, posY);
	}

	@Override
	public void clientConnected(Socket socket) {
		if (clientesConectados > 0)
		{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clientesConectados++;
	}

	@Override
	public void clientDisconnected(Socket socket) {
		clientesConectados--;
		if (clientesConectados == 0) jogo.desconectar();
	}

	@Override
	public void terminar() {
		try {
			server.close();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
