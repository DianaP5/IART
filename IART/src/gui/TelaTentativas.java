package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JOptionPane;


//import Batalha.constantes.Constantes;
//import Batalha.gui.GestorSons.TipoSom;
import logic.Jogo;
import logic.Jogo.Estado;
//import Batalha.logica.Jogo.Estado;
//import Batalha.logica.Jogo.TiposTentativa;
import logic.ObservadorJogo;

@SuppressWarnings("serial")
public class TelaTentativas extends TelaQuadrada implements MouseListener, ObservadorJogo{

	private Jogo jogo = null;
	private TelaJogo telaJogo = null;
	
	/**
	 * Create the panel.
	 */
	public TelaTentativas(TelaJogo telaJogo, Jogo jogo) {
		this.addMouseListener(this);
		this.setFocusable(true);
		this.jogo = jogo;
		this.telaJogo = telaJogo;
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g.create();
		if (jogo != null)
			//Utilitarios.imprimirTabuleiro(g, g2d, null, jogo.getTentativas(), getWidth(), getWidth());
		g2d.dispose();
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.requestFocus();
		System.out.println("MousePressed");
		if (this.jogo.getEstado() != Estado.EmJogo) return;
		int tamanhoTabuleiro = 6;
		int ladoJogo = tamanhoTabuleiro + 1;
		int ladoTela = Math.min(getWidth(),getHeight());
		int dimensao = ladoTela / ladoJogo;
		
		int x = e.getX()/dimensao-1;
		int y = e.getY()/dimensao-1;
		
		System.out.println("TelaTentativa click");
		
		if (e.getButton() == MouseEvent.BUTTON1 && x >= 0 && y >= 0 && x < 6 && y < 6)
		{
			/*try {
				System.out.println("lancaTiro");
				TiposTentativa tiposTentativa = jogo.getTentativas()[y][x];
				jogo.lancaTiro(x, y);
				
				if(tiposTentativa == TiposTentativa.Vazio){
					if(jogo.getTentativas()[y][x] == TiposTentativa.Acertou){
						GestorSons.tocarSom(TipoSom.explosao);
					}
					else if(jogo.getTentativas()[y][x] == TiposTentativa.Falhou){
						//GestorSons.tocarSom(TipoSom.splash);
					}
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
				boolean msg = false;
				if (ex instanceof SocketException)
				{
					SocketException socketex = (SocketException)ex;
					if (socketex.getMessage().equals("Socket closed"))
					{
						JOptionPane.showMessageDialog(this, "Ligação com o outro jogador interrompida.");
						msg = true;
					}
				} 
				if (!msg) JOptionPane.showMessageDialog(this, "Erro inesperado ao lançar tiro.");
				this.telaJogo.sair();
			}*/
			
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}


	@Override
	public void eventoJogo() {
		this.repaint();
	}


}
