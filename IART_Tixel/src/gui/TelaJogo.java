package gui;

import logic.Jogo;
import logic.ObservadorJogo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


@SuppressWarnings("serial")
public class TelaJogo extends JPanel implements ObservadorJogo{
	
	private FrameJogo frameJogo = null;
	private Jogo jogo = null;
	
	private TelaTentativas panelTentativas;
	private TelaTabuleiro panelTabuleiro;
	private JLabel lblEstado;
	
	private int acabou = 0;

	/**
	 * Create the panel.
	 * @param jogo 
	 */
	public TelaJogo(FrameJogo frameJogo, Jogo jogo) throws IOException {
		this.setPreferredSize(new Dimension(1000,600));
		setLayout(new BorderLayout(0, 0));
		this.frameJogo = frameJogo;
		this.jogo = jogo;
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		splitPane.setResizeWeight(0.5);
		
		JPanel panel1 = new JPanel(new GridBagLayout());
		panelTabuleiro = new TelaTabuleiro(jogo);
		panel1.add(panelTabuleiro);
		splitPane.setLeftComponent(panel1);
		
		JPanel panel2 = new JPanel(new GridBagLayout());
		panelTentativas = new TelaTentativas(this,jogo);
		panel2.add(panelTentativas);
		splitPane.setRightComponent(panel2);
		
		
		JPanel panelBotoes = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotoes.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panelBotoes, BorderLayout.SOUTH);
		
		JButton btnRecomecar = new JButton("Menu Inicial");
		btnRecomecar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resultado = JOptionPane.showConfirmDialog(frameJogo,"Tem a certeza que deseja voltar ao Menu Inicial?", "Voltar ao Menu Inicial",JOptionPane.YES_NO_OPTION);
				if (resultado == JOptionPane.YES_OPTION) {
					sair();
				}
			}
		});
		
		lblEstado = new JLabel("Estado: Em Espera");
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelBotoes.add(lblEstado);
		
		Component horizontalStrut = Box.createHorizontalStrut(50);
		panelBotoes.add(horizontalStrut);
		panelBotoes.add(btnRecomecar);
		
		jogo.adicionarObservador(this);
	}
	
	
	@Override
	public void eventoJogo() {
		this.repaint();
	}


	public void sair() {
		jogo.terminar();
		frameJogo.trocarTela(new TelaInicioJogo(frameJogo));
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch (jogo.getEstado())
		{
		case Espera:
			lblEstado.setText("Estado: Em Espera");
			break;
		case EmJogo:
			lblEstado.setText("Estado: Lançar tiro");
			break;
		case Ganho:
			lblEstado.setText("Estado: Jogo Ganho :)");
			if (acabou == 2) {
				JOptionPane.showMessageDialog(frameJogo, "Parabéns! Irá voltar ao menu inicial");
				sair();
			}
			else {
				acabou++;
				this.repaint();
			}
			break;
		case Perdido:
			lblEstado.setText("Estado: Jogo Perdido :(");
			if (acabou == 2) {
				JOptionPane.showMessageDialog(frameJogo, "Perdeu o jogo. Irá voltar ao menu inicial");
				sair();
			}
			else {
				acabou++;
				this.repaint();
			}
			break;
		case Disconectado:
			JOptionPane.showMessageDialog(frameJogo, "Conecção perdida!");
			sair();
		default:
			break;
		}
	}
}
