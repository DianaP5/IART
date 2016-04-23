package gui;


import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import net.sf.lipermi.exception.LipeRMIException;



//import Batalha.constantes.Constantes;
//import Batalha.gui.GestorImagens.TipoImagem;
//import Batalha.jogadores.Cliente;
//import Batalha.jogadores.Servidor;
//import Batalha.logica.ConstrutorTabuleiro;
//import Batalha.logica.Jogo;
//import Batalha.logica.Navio;

//import Batalha.logica.Tabuleiro;





import logic.ConstrutorTabuleiro;
import logic.Jogo;
import logic.Peca;
import logic.Tabuleiro;
import logic.Peca.TipoPeca;
import gui.GestorImagens.TipoImagem;

import static gui.Utilitarios.imprimirPeca;
import static gui.Utilitarios.imprimirTabuleiro;



@SuppressWarnings("serial")
public class TelaInicioJogo extends JPanel implements MouseListener, KeyListener, MouseMotionListener{

	//private FrameJogo frameJogo;
	private JPanel pnlButtons;
	private boolean vertical = false;
	private TipoPeca navioSelecionado = null;
	private ConstrutorTabuleiro construtorTabuleiro = null;
	private int ratoX, ratoY;
	
	private Jogo jogo;
	private JTextField txtIP;
	
	
	public void novoJogo(Jogo jogo){
		this.repaint();
	}
	
	
	/**
	 * Create the panel.
	 * @param frameJogo 
	 * @throws IOException 
	 */
	public TelaInicioJogo(FrameJogo frameJogo){
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
		
		JCheckBox chkboxServidor = new JCheckBox("Servidor");
		chkboxServidor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					txtIP.setText(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtIP.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
				txtIP.getParent().revalidate();
			}
		});
		
		chkboxServidor.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(chkboxServidor);
		
		JPanel panel = new JPanel();
		pnlButtons.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(10, 0));
		panel.add(rigidArea);
		
		txtIP = new JTextField();
		panel.add(txtIP);
		txtIP.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtIP.getPreferredSize().height) );
		
		try {
			txtIP.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtIP.setColumns(10);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(10, 0));
		panel.add(rigidArea_1);
		txtIP.setVisible(!chkboxServidor.isSelected());
		
		Component verticalGlue1 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue1);
		
		JButton PecaJogador1 = new JButton();
		PecaJogador1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selecionarPeca(TipoPeca.pecaJogador1);
			}
		});
		PecaJogador1.setIcon(new ImageIcon(GestorImagens.getImage(TipoPeca.pecaJogador1)));
		PecaJogador1.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(PecaJogador1);
		
		Component verticalGlue2 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue2);
		
		JButton cruzador = new JButton();
		cruzador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selecionarPeca(TipoPeca.pecaJogador2);
			}
		});
		cruzador.setIcon(new ImageIcon(GestorImagens.getImage(TipoPeca.pecaJogador2)));
		cruzador.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(cruzador);
		
		
		/*
		Component verticalGlue3 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue3);
		
		JButton couracado = new JButton();
		couracado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selecionarNavio(TipoNavio.couracado);
			}
		});
		couracado.setIcon(new ImageIcon(GestorImagens.getImage(TipoNavio.couracado)));
		couracado.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(couracado);
		
		Component verticalGlue4 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue4);
		
		JButton torpedeiro = new JButton();
		torpedeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarNavio(TipoNavio.torpedeiro);
			}
		});
		torpedeiro.setIcon(new ImageIcon(GestorImagens.getImage(TipoNavio.torpedeiro)));
		torpedeiro.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(torpedeiro);
		
		Component verticalGlue5 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue5);
		
		JButton portaAvioes = new JButton();
		portaAvioes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarNavio(TipoNavio.portaAvioes);
			}
		});
		portaAvioes.setIcon(new ImageIcon(GestorImagens.getImage(TipoNavio.portaAvioes)));
		portaAvioes.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(portaAvioes);
		
		*/
		
		validate();
		
		JButton botaoComecar = new JButton("Come\u00E7ar");
		/*botaoComecar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean sucesso = false;
				if (construtorTabuleiro == null) return;
				Tabuleiro tabuleiro = construtorTabuleiro.getTabuleiro();
				if (tabuleiro == null)
				{
					JOptionPane.showMessageDialog(frameJogo, "Adicione todos os navios.");
					return;
				}
				if (chkboxServidor.isSelected()){
				try {
						Servidor servidor = new Servidor();
						jogo = new Jogo(servidor, tabuleiro);
						botaoComecar.setEnabled(false);
						sucesso = true;
					} catch (LipeRMIException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(frameJogo, "LipeRMIException: "+ex.getMessage());
					} catch (IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(frameJogo, "IOException: "+ex.getMessage());
					}
				}
				else
				{
					try {
						Cliente cliente = new Cliente(txtIP.getText());
						jogo = new Jogo(cliente, tabuleiro);
						botaoComecar.setEnabled(false);
						sucesso = true;
					} catch (LipeRMIException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(frameJogo, "LipeRMIException: "+ex.getMessage());
					} catch (IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(frameJogo, "IOException: "+ex.getMessage());
					}
				}
				if (sucesso) {
					try {
						frameJogo.trocarTela(new TelaJogo(frameJogo, jogo));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});*/
		
		Component verticalGlue6 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue6);
		botaoComecar.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(botaoComecar);
		
		Component verticalGlue7 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue7);
		
		
		
		
		
		
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
		
		if(e.getButton() == MouseEvent.BUTTON1 && construtorTabuleiro == null){
			construtorTabuleiro = new ConstrutorTabuleiro();
			this.repaint();
		}
		else if (x >= 0 && y >= 0 && x < 6 && y < 6)
		{
			if (e.getButton() == MouseEvent.BUTTON1 && navioSelecionado != null) {
				System.out.println("Valid click");
				Peca s = new Peca(navioSelecionado, x, y, false);
				if (construtorTabuleiro.adicionarPeca(s))
				{
					System.out.println("Navio adicionado");
					this.repaint();
				}
				else
				{
					System.out.println("Navio nao adicionado");
				}
				
			}
			else if (e.getButton() == MouseEvent.BUTTON3)
			{
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
		
		if (construtorTabuleiro == null)
		{
			g.drawImage(GestorImagens.getImage(TipoImagem.background), 0, 0, ladoTela, ladoTela, null);
			return;
		}
		else
		{
			Graphics2D g2d = (Graphics2D) g.create();
			imprimirTabuleiro(g, g2d, construtorTabuleiro.getPecas(), getWidth()-pnlButtons.getWidth(), getHeight());
			if (navioSelecionado != null && !construtorTabuleiro.temPeca(navioSelecionado) 
					&& ratoX >= 0 && ratoY >= 0 && ratoX < 6 && ratoY < 6)
			{
				g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
				Peca navio = new Peca(navioSelecionado, ratoX, ratoY, false);
				imprimirPeca(g2d, navio, dimensao);
			}
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
		vertical = !vertical;
		repaint();
	}
	
	public void selecionarPeca(TipoPeca peca)
	{
		navioSelecionado = peca;
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
