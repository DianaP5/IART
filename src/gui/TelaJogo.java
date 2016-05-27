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
	
	//private TelaTentativas panelTentativas;
	//private TelaTabuleiro panelTabuleiro;
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
	//	panelTabuleiro = new TelaTabuleiro(jogo);
		//panel1.add(panelTabuleiro);
		splitPane.setLeftComponent(panel1);
		
		/*JPanel panel2 = new JPanel(new GridBagLayout());
		panelTentativas = new TelaTentativas(this,jogo);
		panel2.add(panelTentativas);
		splitPane.setRightComponent(panel2);*/
		
		
		JPanel panelBotoes = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotoes.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panelBotoes, BorderLayout.SOUTH);
		
		JButton btnRecomecar = new JButton("Menu Inicial");
		btnRecomecar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resultado = JOptionPane.showConfirmDialog(frameJogo,"Tem a certeza que deseja voltar ao Menu Inicial?", "Voltar ao Menu Inicial",JOptionPane.YES_NO_OPTION);
				if (resultado == JOptionPane.YES_OPTION) {
					try {
						sair();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		lblEstado = new JLabel("Estado: Em Espera");
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelBotoes.add(lblEstado);
		
		Component horizontalStrut = Box.createHorizontalStrut(50);
		panelBotoes.add(horizontalStrut);
		panelBotoes.add(btnRecomecar);
		
		//jogo.adicionarObservador(this);
	}
	
	
	@Override
	public void eventoJogo() {
		this.repaint();
	}


	public void sair() throws IOException {
		//jogo.terminar();
		frameJogo.trocarTela(new TelaInicioJogo(frameJogo,-1));
	}
}
