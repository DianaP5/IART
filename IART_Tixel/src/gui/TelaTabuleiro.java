package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import logic.Jogo;

@SuppressWarnings("serial")
public class TelaTabuleiro extends TelaQuadrada {

	private Jogo jogo = null;
	
	/**
	 * Create the panel.
	 */
	public TelaTabuleiro(Jogo jogo) {
		this.jogo = jogo;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		if (jogo != null && jogo.getTabuleiro() != null)
			//Utilitarios.imprimirTabuleiro(g, g2d, jogo.getTabuleiro().getNavios(), jogo.getTentativasInimigo(), getWidth(), getHeight());
		//else Utilitarios.imprimirTabuleiro(g, g2d, null, null, getWidth(), getHeight());
		g2d.dispose();
	}
}
