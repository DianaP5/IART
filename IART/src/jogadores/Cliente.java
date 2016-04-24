package jogadores;

import java.io.IOException;

import logic.Jogo;
import logic.Jogo.ResultadoMovimento;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import net.sf.lipermi.net.IClientListener;
//import Batalha.constantes.Constantes;
//import Batalha.logica.Jogo.ResultadoTiro;

public class Cliente extends LigacaoInimigo implements InterfaceCliente, IClientListener{

	private Client client;
	private InterfaceServidor servidor = null;
	
	public Cliente(String ip) throws LipeRMIException, IOException {
		super();
		CallHandler callHandler = new CallHandler();
		callHandler.exportObject(InterfaceCliente.class, this);
		client = new Client(ip, 55661, callHandler);
		client.addClientListener(this);
		servidor = (InterfaceServidor)client.getGlobal(InterfaceServidor.class);
		servidor.recebeCliente(this);
	}

	
	@Override
	public ResultadoMovimento recebePosicao(int posX, int posY) throws IOException{
		System.out.println("Cliente recebeu tiro");
		return jogo.recebePosicao(posX, posY);
	}
	 
	@Override
	public ResultadoMovimento lancaTiro(int posX, int posY) throws IOException{
		System.out.println("Cliente envia tiro");
		return servidor.recebePosicao(posX, posY);
	}

	@Override
	public void terminar() {
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void disconnected() {
		jogo.desconectar();
	}

}
