package gui;

import static gui.Utilitarios.imprimirPeca;
import static gui.Utilitarios.imprimirTabuleiro;
import gui.GestorImagens.TipoImagem;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Board;
import logic.ConstrutorTabuleiro;
import logic.Jogo;
import logic.Moves;
import logic.Peca;
import logic.Peca.TipoPeca;

@SuppressWarnings("serial")
public class TelaInicioJogo extends JPanel implements MouseListener, KeyListener, MouseMotionListener{

	private JPanel pnlButtons;
	private boolean ativa = false;
	private TipoPeca PecaSelecionada = null;
	//private ConstrutorTabuleiro construtorTabuleiro = null;
	private Board board= null;
	private int ratoX, ratoY;
	private Moves moves=new Moves();
	private int player=-1;
	private int[] rotate={0,0};
	int[] p1={2,-2,3,-3,4,-4,1,-1};
	int[] p2={6,-6,7,-7,8,-8,5,-5};
	
	
	public void novoJogo(Jogo jogo){
		this.repaint();
	}
	
	/**
	 * Create the panel.
	 * @param frameJogo 
	 * @throws IOException 
	 */
	public TelaInicioJogo(FrameJogo frameJogo) throws IOException{
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);		
		this.setPreferredSize(new Dimension(1000,600));
		setLayout(new BorderLayout(0, 0));
		
		pnlButtons = new JPanel();
		this.add(pnlButtons, BorderLayout.EAST);
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.Y_AXIS));
			
		Component verticalGlue = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue);

		Component verticalGlue1 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue1);
		
		JButton pecaPreta = new JButton();
		pecaPreta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selecionarPeca(TipoPeca.pecaJogador1);
				player=0;
			}
		});
		pecaPreta.setIcon(new ImageIcon(GestorImagens.getImage(0)));
		pecaPreta.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(pecaPreta);
		
		Component verticalGlue2 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue2);
		
		JButton pecaVermelha = new JButton();
		pecaVermelha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selecionarPeca(TipoPeca.pecaJogador2);
				player=1;
			}
		});
		
		pecaVermelha.setIcon(new ImageIcon(GestorImagens.getImage(1)));
		pecaVermelha.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(pecaVermelha);

		validate();
	
		JButton botaoSair = new JButton("Sair");
		botaoSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resultado = JOptionPane.showConfirmDialog(frameJogo,"Tem a certeza que deseja sair?", "Sair do Jogo",JOptionPane.YES_NO_OPTION);

				if (resultado == JOptionPane.YES_OPTION) {
					frameJogo.setVisible(false);
					System.exit(0);
				}
				requestFocus();
			}
		});
		botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(botaoSair);
		
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
		int tamanhoTabuleiro = 6;
		int ladoJogo = tamanhoTabuleiro + 1;
		int ladoTela = getLadoTela();
		int dimensao = ladoTela / ladoJogo;
		
		int x = e.getX()/dimensao-1;
		int y = e.getY()/dimensao-1;
		System.out.println(x+" "+y);
		if(e.getButton() == MouseEvent.BUTTON1 && board == null){
		
			board = new Board();
			moves.setBoard(board);
			System.out.println("board created");
			
			this.repaint();
		}
		else if (x >= 0 && y >= 0 && x < 6 && y < 6)
		{
			if (e.getButton() == MouseEvent.BUTTON1) {
				System.out.println("Valid click");
					
					if (player == 0)
						if (p1[rotate[player]] > 0 && p1[rotate[player]] < 5){
							moves.placePiece(x, y,player,p1[rotate[player]]);
							System.out.println(player+" "+rotate[player]+" "+p1[rotate[player]]);
						}else moves.placePiece(x, y, player, -p1[rotate[player]]);
					else if (player == 1)
						if (p2[rotate[player]] > 4 && p2[rotate[player]] < 9)
							moves.placePiece(x, y,player,p2[rotate[player]]-4);
						else moves.placePiece(x, y, player, -p2[rotate[player]]+4);

					System.out.println(player+" "+rotate[player]+" "+p1[rotate[player]]);
					moves.getBoard().printBoard();
				/*	if (construtorTabuleiro.adicionarPeca(s))
					{
						System.out.println("Peca adicionada");
						this.repaint();
					}
					else
					{
						System.out.println("Peca nao adicionado");
					}
				//}*/
			}
			else if (e.getButton() == MouseEvent.BUTTON3)
			{
				return;
				}
			/*
			}
				if (construtorTabuleiro.retirarPeca(x, y))
				{
					System.out.println("Navio retirado");
					this.repaint();
				}
				else
				{
					System.out.println("Navio nao retirado");
				}
			}
		}*/
		
	}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int ladoJogo = 6 + 1;
		int ladoTela = getLadoTela();
		int dimensao = ladoTela / ladoJogo;
		
		if (board == null)
		{
			//g.drawImage(GestorImagens.getImage(TipoImagem.background), 0, 0, ladoTela, ladoTela, null);
			return;
		}
		else
		{
			Graphics2D g2d = (Graphics2D) g.create();
			imprimirTabuleiro(g, g2d,  moves.getBoard(), getWidth()-pnlButtons.getWidth(), getHeight());
			/*if (PecaSelecionada != null && !construtorTabuleiro.temPeca(PecaSelecionada) 
					&& ratoX >= 0 && ratoY >= 0 && ratoX < 6 && ratoY < 6)
			{
				g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
				Peca p = new Peca(PecaSelecionada, ratoX, ratoY, ativa);
				imprimirPeca(g2d, p, dimensao);
			}*/
			g2d.dispose();
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) rodar();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void rodar()
	{
		
		if (rotate[player] == 7)
			rotate[player]=0;
		else rotate[player]++;
		
		repaint();
	}
	
	public void selecionarPeca(TipoPeca peca)
	{
		PecaSelecionada = peca;
		this.requestFocus();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int ladoJogo = 6 + 1;
		int dimensao = getLadoTela() / ladoJogo;
		int x = e.getX()/dimensao-1;
		int y = e.getY()/dimensao-1;
		if (ratoX != x || ratoY != y)
		{
			ratoX = x;
			ratoY = y;
			repaint();
		}
	}
	
	private int getLadoTela()
	{
		return Math.min(getHeight(),getWidth()-pnlButtons.getWidth());
	}
	
}
