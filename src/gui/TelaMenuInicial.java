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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Board;
import logic.ConstrutorTabuleiro;
import logic.Peca;
import logic.Peca.TipoPeca;

@SuppressWarnings("serial")
public class TelaMenuInicial extends JPanel implements MouseListener, KeyListener, MouseMotionListener{

	private JPanel pnlButtons;
	private boolean ativa = false;
	
	private TipoPeca PecaSelecionada = null;
	private ConstrutorTabuleiro construtorTabuleiro = null;
	Board board=null;
	
	private int ratoX, ratoY;
	
	/**
	 * Create the panel.
	 * @param frameJogo 
	 * @throws IOException 
	 */
	public TelaMenuInicial(FrameJogo frameJogo){
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);		
		this.setPreferredSize(new Dimension(1000,600));
		setLayout(new BorderLayout(0, 0));
		
		pnlButtons = new JPanel();
		this.add(pnlButtons, BorderLayout.SOUTH);
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
			
		Component verticalGlue = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue);	
		
		JPanel panel = new JPanel();
		pnlButtons.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(10, 0));
		panel.add(rigidArea);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(10, 0));
		panel.add(rigidArea_1);
		
		Component verticalGlue1 = Box.createHorizontalGlue();
		pnlButtons.add(verticalGlue1);
		
		validate();
		
		JButton botaoP1P2 = new JButton("Player1 vs Player2");
		botaoP1P2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						frameJogo.trocarTela(new TelaInicioJogo(frameJogo,0));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
		});
		
		Component verticalGlue6 = Box.createHorizontalGlue();
		pnlButtons.add(verticalGlue6);
		botaoP1P2.setAlignmentY(Component.CENTER_ALIGNMENT);
		//botaoP1P2.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(botaoP1P2);
		
		Component verticalGlue7 = Box.createHorizontalGlue();
		pnlButtons.add(verticalGlue7);
		
		JButton botaoP_PC = new JButton("Player1 vs PC");
		botaoP_PC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						frameJogo.trocarTela(new TelaInicioJogo(frameJogo,1));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
		});
		
		Component verticalGlue10 = Box.createHorizontalGlue();
		pnlButtons.add(verticalGlue10);
		botaoP_PC.setAlignmentY(Component.CENTER_ALIGNMENT);
		//botaoP_PC.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(botaoP_PC);
		
		Component verticalGlue12 = Box.createHorizontalGlue();
		pnlButtons.add(verticalGlue12);
		
		JButton botaoPC_PC = new JButton("PC vs PC");
		
		Component verticalGlue11 = Box.createHorizontalGlue();
		pnlButtons.add(verticalGlue11);
		botaoPC_PC.setAlignmentY(Component.CENTER_ALIGNMENT);
		pnlButtons.add(botaoPC_PC);
		
		Component verticalGlue13 = Box.createHorizontalGlue();
		pnlButtons.add(verticalGlue13);
		
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
		
		Component verticalGlue8 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue8);
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
		repaint();
		
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
		
		if (construtorTabuleiro == null)
		{
			g.drawImage(GestorImagens.getImage(TipoImagem.background), 0, 0, ladoTela, ladoTela, null);
			return;
		}
		else
		{
			Graphics2D g2d = (Graphics2D) g.create();
			imprimirTabuleiro(g, g2d, board, getWidth()-pnlButtons.getWidth(), getHeight());
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
		ativa = !ativa;
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
		//return Math.min(getHeight(),getWidth()-pnlButtons.getWidth());
		return Math.min(getHeight()-pnlButtons.getHeight(),getWidth());
	}
	
}
