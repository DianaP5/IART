package gui;

import gui.GestorImagens.TipoImagem;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import logic.Board;

public class Utilitarios {
	static void imprimirPeca(Graphics2D g2d, int piece,int x1,int y1, int dimensao)
	{
		int x = dimensao*(x1+1);
		int y = dimensao*(y1+1);
		int player=-1;
		
		System.out.println("piece: " + piece);

		if (piece == 1 || piece == 2 || piece == 3 || piece == 4)
			player=0;
		else if (piece == -1 || piece == -2 || piece == -3 || piece == -4)
			player=0;
		else player=1;
			
		BufferedImage imagem = GestorImagens.getImage(player);
		AffineTransform tx = new AffineTransform();
			
		tx.translate(x, y);

		tx.scale(1.0*dimensao/imagem.getHeight(), 1.0*dimensao/imagem.getWidth());
		
		/*if (peca.getAtiva())
			tx.scale(1.0*height/imagem.getHeight(), 1.0*width/imagem.getWidth());
		else
			tx.scale(1.0*width/imagem.getWidth(), 1.0*height/imagem.getHeight());*/
		
		if (piece > 0 && piece < 9){
			//tx.translate(imagem.getHeight() / 2, - (imagem.getWidth() / 4));
			if (piece == 1 || piece == 5){
				tx.rotate(-Math.PI / 2, 0, 0);
				tx.translate(imagem.getHeight() / 2, - (imagem.getWidth() / 4));
			}
			else if (piece == 3 || piece == 7)
				tx.rotate(Math.PI / 2, 0, 0);
			else if (piece == 4 || piece == 8)
				tx.rotate(Math.PI, 0, 0);
		}else if (piece < 0){
			if (piece == -1 || piece == -5)
				tx.rotate(-Math.PI / 4, 0, 0);
			else if (piece == -2 || piece == -6)
				tx.rotate(Math.PI / 4, 0, 0);
			else if (piece == -3 || piece == -7)
				tx.rotate(3*Math.PI / 4, 0, 0);
			else if (piece == -4 || piece == -8)
				tx.rotate(-3*Math.PI/4, 0, 0);
		}
		
	    g2d.drawImage(imagem, tx, null);
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

	static void imprimirTabuleiro(Graphics g, Graphics2D g2d,Board board, int largura, int altura) {
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
		
		for (int i = 0; i < board.getBoardSize(); i++)
			for (int j = 0; j < board.getBoardSize(); j++)
				if (board.getBoard()[j][i] != 0)
					imprimirPeca(g2d,board.getBoard()[j][i],j,i,dimensao);
	}
}
