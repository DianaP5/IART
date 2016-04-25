package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;

import javax.imageio.ImageIO;

import logic.Peca.TipoPeca;



public class GestorImagens {
	
	private static GestorImagens instance = null;
	
	public static enum TipoImagem{
		mar,
		background,
		pecaJogador1,
		pecaJogador2,
	} 
	
	
	private EnumMap<TipoImagem, BufferedImage> imagens = new EnumMap<TipoImagem, BufferedImage>(TipoImagem.class);
	
	private GestorImagens()
	{		
		try {
			imagens.put(TipoImagem.mar,ImageIO.read(getClass().getResource("/mar1.jpg")));
			imagens.put(TipoImagem.background,ImageIO.read(getClass().getResource("/fundo_tixel.png")));
			imagens.put(TipoImagem.pecaJogador1,ImageIO.read(getClass().getResource("/Jogador1.png")));
			imagens.put(TipoImagem.pecaJogador2,ImageIO.read(getClass().getResource("/Jogador2.png")));
			//imagens.put(TipoImagem.Nomejogo,ImageIO.read(getClass().getResource("/Tixel_Jogo.png")));
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	public static BufferedImage getImage(TipoImagem imagem)
	{
		return getInstance().imagens.get(imagem);
	}
	
	public static BufferedImage getImage(int player)
	{
		switch(player)
		{
			case 0:
				return getImage(TipoImagem.pecaJogador1);
			case 1:
				return getImage(TipoImagem.pecaJogador2);
			default:
				return null;
		}
	}
	
	public static GestorImagens getInstance()
	{
		if (instance == null) 
			instance = new GestorImagens();
		return instance;
	}

}
