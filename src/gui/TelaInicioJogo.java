package gui;

import static gui.Utilitarios.imprimirPeca;
import static gui.Utilitarios.imprimirTabuleiro;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
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

import logic.Ai;
import logic.Board;
import logic.Jogo;
import logic.Moves;
import logic.Peca.TipoPeca;

@SuppressWarnings("serial")
public class TelaInicioJogo extends JPanel implements MouseListener,
		KeyListener, MouseMotionListener {
	
	private int mode;
	private Boolean pc=true;
	//private Boolean opp=true;
	
	private JPanel pnlButtons;
	private boolean ativa = false;
	private TipoPeca PecaSelecionada = null;
	// private ConstrutorTabuleiro construtorTabuleiro = null;
	private Board board = null;
	private int ratoX, ratoY;
	private Moves moves = new Moves();
	private int player = -1;
	private int[] rotate = { 1, 1 };
	private int[] pickPos={-1,-1};
	
	int[] p1 = { 2, -2, 3, -3, 4, -4, 1, -1 };
	int[] p2 = { 6, -6, 7, -7, 8, -8, 5, -5 };
	
	private boolean isSliding=false;
	private boolean activating=false;
	private boolean pivoting=false;
	int[] sliding={-1,-1,0,-1};
	String bonus="";
	FrameJogo frameJogo;
	
	public void novoJogo(Jogo jogo) {
		this.repaint();
	}

	/**
	 * Create the panel.
	 * 
	 * @param frameJogo
	 * @throws IOException
	 */
	public TelaInicioJogo(FrameJogo frameJogo,int mode) throws IOException {
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(1000, 600));
		setLayout(new BorderLayout(0, 0));
		this.frameJogo=frameJogo;
		this.mode=mode;
		
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
				//if (!opp)
					player = 0;
					
					int[][] tempBoard = new int[6][6];
					
					for (int i = 0; i < 6; i++)
						for (int j = 0; j < 6; j++)
							tempBoard[i][j]=board.getBoard()[i][j];
					
					moves.checkEndOfGame(1);
					//board.checkEndOfGame(1);
					moves.setBoard(board);
					if (mode == 0){
						pc=false;
					}else if (mode == 1){
						//MouseEvent e=new MouseEvent();
						
						//while(true){
							if (pc){
								Ai ai1=new Ai(board);
								moves.pieces[1]=ai1.getnPieces();
								board=ai1.getBoard();
								
								repaint();
								pc=false;
							}
						//}			
					}else{
						
					}
			}
		});
		pecaPreta.setIcon(new ImageIcon(GestorImagens.getImage(0)));
		pecaPreta.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(pecaPreta);

		Component verticalGlue2 = Box.createVerticalGlue();
		pnlButtons.add(verticalGlue2);

		JButton pecaBranca = new JButton();
		pecaBranca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selecionarPeca(TipoPeca.pecaJogador2);
				
				/*if (opp)
					player = 1;*/
				
				if (mode == 0){
					player=1;
					pc=false;
				}else if (mode == 1){
					//MouseEvent e=new MouseEvent();
					
					//while(true){
						if (pc){
							Ai ai1=new Ai(board);
							moves.pieces[1]=ai1.getnPieces();
							
							board=ai1.getBoard();
							
							repaint();
							pc=false;
						}
					//}			
				}else{
					
				}
			}
		});

		pecaBranca.setIcon(new ImageIcon(GestorImagens.getImage(1)));
		pecaBranca.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(pecaBranca);

		validate();

		JButton botaoSair = new JButton("Sair");
		botaoSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resultado = JOptionPane.showConfirmDialog(frameJogo,
						"Tem a certeza que deseja sair?", "Sair do Jogo",
						JOptionPane.YES_NO_OPTION);

				if (resultado == JOptionPane.YES_OPTION) {
					frameJogo.setVisible(false);
					System.exit(0);
				}
				requestFocus();
			}
		});
		botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlButtons.add(botaoSair);
		
		if ( board == null) {

			board = new Board();
			moves.setBoard(board);
			System.out.println("board created");

			this.repaint();
		}
			
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
		
		if (pc)
			return;
		
		System.out.println("MousePressed");
		int tamanhoTabuleiro = 6;
		int ladoJogo = tamanhoTabuleiro + 1;
		int ladoTela = getLadoTela();
		int dimensao = ladoTela / ladoJogo;

		int x = e.getX() / dimensao - 1;
		int y = e.getY() / dimensao - 1;
		System.out.println(x + " " + y);
		
		if (x >= 0 && y >= 0 && x < 6 && y < 6) {
			System.out.println("N_PIECES: "+this.moves.pieces[0]+" "+this.moves.pieces[1]);
			System.out.println("FDS"+this.isSliding+" "+this.activating+" "+this.pivoting+" "+bonus.equals("Slide"));
			if (e.getButton() == MouseEvent.BUTTON1 && !this.isSliding && !this.activating && !this.pivoting && !bonus.equals("Slide")) {
				if (player == 0 && this.moves.pieces[player] > 0) {
					if (p1[rotate[player]] > -5 && p1[rotate[player]] < 0) {
						
						if (moves.placePiece(x, y, player, -p1[rotate[player]] - 1,p1[rotate[player]])){
							this.moves.pieces[player]--;
							if (mode == 0)
								pc=false;
							else pc=true;
							bonus="";
						}
					}else if (p1[rotate[player]] > 0 && p1[rotate[player]] < 5){
						if(moves.placePiece(x, y, player, p1[rotate[player]]- 1,p1[rotate[player]])){
							this.moves.pieces[player]--;
							board.printBoard();
							if (mode == 0)
								pc=false;
							else pc=true;
							bonus="";
						}
					}
				} else if (player == 1 && this.moves.pieces[player] > 0)
					if (p2[rotate[player]] > -9 && p2[rotate[player]] < -4){
						if(moves.placePiece(x, y, player, -p2[rotate[player]] - 5,p2[rotate[player]]))
							this.moves.pieces[player]--;
					}else if (p2[rotate[player]] > 4 && p2[rotate[player]] < 9){
						if(moves.placePiece(x, y, player, p2[rotate[player]] - 5,p2[rotate[player]]))
							this.moves.pieces[player]--;
					}
				} else if (e.getButton() == MouseEvent.BUTTON1 && this.isSliding) {
				if (player == 0) {
					if (p1[rotate[player]] > -5 && p1[rotate[player]] < 0) {
						if (moves.slidePiece(this.sliding[0], this.sliding[1], x, y, player, -p1[rotate[player]] - 1,p1[rotate[player]]))
							this.isSliding=false;
					}else if (p1[rotate[player]] > 0 && p1[rotate[player]] < 5)
						if (moves.slidePiece(this.sliding[0], this.sliding[1], x, y, player, p1[rotate[player]] - 1,p1[rotate[player]]))
							this.isSliding=false;
					bonus="";
					
					if (moves.getBoard().getBonusMove() > -1){
						Object[] possibilities = {"Place", "Slide", "Activate","Pick up","Pivot","End turn"};
						String s = (String)JOptionPane.showInputDialog(
						                    frameJogo,
						                    "Possible bonus move:\n",
						                    "Optional Bonus Move!",
						                    JOptionPane.PLAIN_MESSAGE  ,
						                    null,
						                    possibilities,
						                    "End turn");
						if (s != null)
							bonus=s;
						else s="End turn";
						
						if (s.equals("Pick up")){
							pickPos[0]=x;
							pickPos[1]=y;
						}
						
						if (bonus.equals("End turn")){
							bonus="";
							moves.getBoard().setBonusMove(-1);
							if (mode == 0)
								pc=false;
							else pc=true;
						}
					}else{
						if (mode == 0)
							pc=false;
						else pc=true;
					}
				} else if (player == 1){
					if (p2[rotate[player]] > -9 && p2[rotate[player]] < -4){
						if (moves.slidePiece(this.sliding[0], this.sliding[1], x, y, player, -p2[rotate[player]] - 5,p2[rotate[player]]))
							this.isSliding=false;
					}else if (p2[rotate[player]] > 4 && p2[rotate[player]] < 9)
						if (moves.slidePiece(this.sliding[0], this.sliding[1], x, y, player, p2[rotate[player]] - 5,p2[rotate[player]]))
							this.isSliding=false;
					
					bonus="";
					
					if (moves.getBoard().getBonusMove() > -1){
						Object[] possibilities = {"Place", "Slide", "Activate","Pick up","Pivot","End turn"};
						String s = (String)JOptionPane.showInputDialog(
						                    frameJogo,
						                    "Possible bonus move:\n",
						                    "Optional Bonus Move!",
						                    JOptionPane.PLAIN_MESSAGE,
						                    null,
						                    possibilities,
						                    "End turn");
						if (s != null)
							bonus=s;
						else s="End turn";
						
						if (s.equals("Pick up")){
							pickPos[0]=x;
							pickPos[1]=y;
						}
						
						if (bonus.equals("End turn")){
							bonus="";
							moves.getBoard().setBonusMove(-1);
							if (mode == 0)
								pc=false;
							else pc=true;
						}
					}else{
						if (mode == 0)
							pc=false;
						else pc=true;
					}
					
				}}else  if (e.getButton() == MouseEvent.BUTTON3 && moves.getBoard().getBonusMove() > -1 && bonus.equals("Pick up")){
					if (pickPos[0] != x || pickPos[1] != y){
						moves.pickUpPiece(x, y, player);
						bonus="";
						pickPos[0]=-1;
						pickPos[1]=-1;
						
						if (mode == 0)
							pc=false;
						else pc=true;
					}
			}else if (e.getButton() == MouseEvent.BUTTON3 && moves.getBoard().getBonusMove() > -1 && bonus.equals("Pivot")){
				this.sliding[0]=x;
				this.sliding[1]=y;
				this.sliding[2]=board.getBoard()[x][y];
				
				if (!board.checkPlayer(x, y, player))
					return;
				
				if (!board.checkActivePiece(x,y,player))
					return;
				
				moves.removePiece(x, y, player);
				this.pivoting=true;
				
				bonus="";
				
			}else if (e.getButton() == MouseEvent.BUTTON1 && this.pivoting) {
				if (player == 0) {
					if (p1[rotate[player]] > -5 && p1[rotate[player]] < 0){
						 if(!moves.pivotPiece(x, y, player, -p1[rotate[player]] - 1,p1[rotate[player]]))
							 board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
					}else if (p1[rotate[player]] > 0 && p1[rotate[player]] < 5){
						 if (!moves.pivotPiece(x, y, player, p1[rotate[player]]- 1,p1[rotate[player]]))
						 	board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
					}
				} else if (player == 1)
					if (p2[rotate[player]] > -9 && p2[rotate[player]] < -4){
						 if (!moves.pivotPiece(x, y, player, -p2[rotate[player]] - 5,p2[rotate[player]]))
							 board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
					}else if (p2[rotate[player]] > 4 && p2[rotate[player]] < 9)
						 if(!moves.pivotPiece(x, y, player, p2[rotate[player]] - 5,p2[rotate[player]]))
							 board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
				
				this.sliding[0]=-1;
				this.sliding[1]=-1;
				this.sliding[2]=0;
				this.sliding[3]=-1;
				this.pivoting=false;
				
				if (mode == 0)
					pc=false;
				else pc=true;
			}else if (e.getButton() == MouseEvent.BUTTON3 && moves.getBoard().getBonusMove() > -1 && !this.activating && bonus.equals("Activate")){
				this.sliding[0]=x;
				this.sliding[1]=y;
				this.sliding[2]=board.getBoard()[x][y];
				
				if (!board.checkPlayer(x, y, player))
					return;
				
				if (board.checkActivePiece(x,y,player))
					return;
				
				moves.removePiece(x, y, player);
				this.activating=true;
				
				bonus="";
				
			}else if (e.getButton() == MouseEvent.BUTTON1 && this.activating) {
				if (player == 0) {
					if (p1[rotate[player]] > -5 && p1[rotate[player]] < 0){
						 if(!moves.activatePiece(x, y, player, -p1[rotate[player]] - 1,p1[rotate[player]]))
							 board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
						 System.out.println("cenas2");
					}else if (p1[rotate[player]] > 0 && p1[rotate[player]] < 5){
						 if (!moves.activatePiece(x, y, player, p1[rotate[player]]- 1,p1[rotate[player]])){
						 	board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
						 System.out.println("cenas3");
						 }
					}
				} else if (player == 1)
					if (p2[rotate[player]] > -9 && p2[rotate[player]] < -4)
						 if (!moves.activatePiece(x, y, player, -p2[rotate[player]] - 5,p2[rotate[player]]))
							 board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
					else if (p2[rotate[player]] > 4 && p2[rotate[player]] < 9)
						 if(!moves.activatePiece(x, y, player, p2[rotate[player]] - 5,p2[rotate[player]]))
							 board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
				
				this.sliding[0]=-1;
				this.sliding[1]=-1;
				this.sliding[2]=0;
				this.sliding[3]=-1;
				this.activating=false;
				
				if (mode == 0)
					pc=false;
				else pc=true;
				
			} else if (e.getButton() == MouseEvent.BUTTON3 && !this.isSliding) {
				this.isSliding=true;
				if (board.checkActivePiece(x,y,player)){
					this.sliding[0]=x;
					this.sliding[1]=y;
					this.sliding[2]=board.getBoard()[x][y];
					moves.removePiece(x, y, player);
				}else this.isSliding=false;
			}else if (e.getButton() == MouseEvent.BUTTON3 && this.isSliding) {
				board.getBoard()[this.sliding[0]][this.sliding[1]]=this.sliding[2];
				this.sliding[0]=-1;
				this.sliding[1]=-1;
				this.sliding[2]=0;
				this.sliding[3]=-1;
				this.isSliding=false;
			}
		}

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

		Graphics2D g2d = (Graphics2D) g.create();
		imprimirTabuleiro(g, g2d, moves.getBoard(),
				getWidth() - pnlButtons.getWidth(), getHeight());
		
		if (this.isSliding || this.activating || this.pivoting){
			g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
			
			if (this.sliding[2] != 0){
				if (this.sliding[3] < 0){
					for (int i=0; i < this.p1.length; i++){
						if (this.p1[i] == this.sliding[2]){
							this.rotate[player]=i;
							this.sliding[3]=i;
						}
					}
				}
				
				if (player == 0)
					imprimirPeca(g2d,p1[this.rotate[player]],ratoX,ratoY, dimensao);
				else imprimirPeca(g2d,p2[this.rotate[player]],ratoX,ratoY, dimensao);
			}
		}else{
			if (player == 0)
				imprimirPeca(g2d,p1[this.rotate[player]],ratoX,ratoY, dimensao);
			else if (player == 1)
				imprimirPeca(g2d,p2[this.rotate[player]],ratoX,ratoY, dimensao);
		}
		
		g2d.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R)
			rodar();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void rodar() {
		int rotates = rotate[player];

		if (rotate[player] == 7)
			rotate[player] = 0;
		else
			rotate[player]++;

		System.out.println("rodar: antes: " + rotates + " depois: "
				+ rotate[player]);

		repaint();
	}

	public void selecionarPeca(TipoPeca peca) {
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
		int x = e.getX() / dimensao - 1;
		int y = e.getY() / dimensao - 1;
		if (ratoX != x || ratoY != y) {
			ratoX = x;
			ratoY = y;
			repaint();
		}

	}

	private int getLadoTela() {
		return Math.min(getHeight(), getWidth() - pnlButtons.getWidth());
	}

}
