package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collection;

import gui.GestorImagens.TipoImagem;
import logic.Peca;

public class Utilitarios {
	static void imprimirPeca(Graphics2D g2d, Peca peca, int dimensao)
	{
		int x = dimensao*(peca.getPosX()+1)+dimensao/8;
		int y = dimensao*(peca.getPosY()+1)+dimensao/8;
		int height = dimensao-dimensao/4;
		int width = dimensao*peca.getComprimento()-dimensao/4;
		
		BufferedImage imagem = GestorImagens.getImage(peca.getTipo());
		
		AffineTransform tx = new AffineTransform();

		tx.translate(x, y);
		/*if (navio.getVertical())
			tx.scale(1.0*height/imagem.getHeight(), 1.0*width/imagem.getWidth());
		else
			tx.scale(1.0*width/imagem.getWidth(), 1.0*height/imagem.getHeight());
		if (navio.getVertical()) {
			tx.translate(imagem.getHeight(), 0);
			tx.rotate(Math.PI / 2, 0, 0);
		}
	    g2d.drawImage(imagem, tx, null);*/
	}
	
	static void imprimirStringQuadricula(Graphics g, String string, int x, int y, int dimensao)
	{
		Font font = new Font("Arial",Font.PLAIN,2*dimensao/3);
		Graphics2D g2d = (Graphics2D) g.create();
		TextLayout txt = new TextLayout(string, font, g2d.getFontRenderContext());
	    Rectangle2D bounds = txt.getBounds();
	    int xString = x*dimensao+(int)((dimensao - (int)bounds.getWidth()) / 2 );
	    int yString = y*dimensao+(int)(dimensao/2 + (int)(bounds.getHeight() / 2));
	    g.setFont(font);
	    g.drawString(string, xString, yString);
	    g2d.dispose();
	}
	

	static void imprimirTabuleiro(Graphics g, Graphics2D g2d, Collection<Peca> pecas, int largura, int altura) {
		final int ladoJogo = 6 + 1;
		final int dimensao = Math.min(largura,altura) / ladoJogo;
		
		BufferedImage mar = GestorImagens.getImage(TipoImagem.mar);
		
		
		g.drawImage(mar, dimensao, dimensao, 6*dimensao, 6*dimensao, null);
		
		for(int i = 1; i <= 6; i++){
			imprimirStringQuadricula(g,""+(char)('A'+i-1),i,0,dimensao);
			imprimirStringQuadricula(g,i+"",0,i,dimensao);
		}

		for(int i = 1; i <= ladoJogo; i++){
			g.drawLine(dimensao, i*dimensao, ladoJogo*dimensao-1, i*dimensao); //linhas
			g.drawLine(i*dimensao, dimensao,  i*dimensao, ladoJogo*dimensao-1); //colunas
		}
		if (pecas != null) {
			for (Peca peca : pecas)
			{
				imprimirPeca(g2d, peca, dimensao);
			}
		}
		/*if (tentativas != null)
		{
			for (int x = 0; x < tentativas.length; x++)
			{
				for (int y = 0; y < tentativas.length; y++)
				{
					BufferedImage imagem = null;
					if (tentativas[y][x] == TiposTentativa.Acertou) imagem = GestorImagens.getImage(TipoImagem.fogo);
					else if (tentativas[y][x] == TiposTentativa.Falhou) imagem = GestorImagens.getImage(TipoImagem.splash);
					if (imagem != null)
						g.drawImage(imagem, (x+1)*dimensao, (y+1)*dimensao, dimensao, dimensao, null);
				}
			}
		}
		g.drawImage(ceu, dimensao, dimensao, Constantes.tamanhoTabuleiro*dimensao, Constantes.tamanhoTabuleiro*dimensao, null);
		*/
	}
}
