package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class FrameJogo extends JFrame{
	
	JPanel tela;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameJogo window = new FrameJogo();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public FrameJogo() throws IOException {
		super("TIXEL");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				if (tela instanceof TelaJogo)
				{
					try {
						((TelaJogo) tela).sair();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0,0));

		trocarTela(new TelaMenuInicial(this));
	}
	
	public void trocarTela(JPanel tela)
	{
		this.getContentPane().removeAll();
		this.getContentPane().add(tela, null);
		this.tela = tela;
		tela.requestFocus();
		tela.revalidate();
		tela.repaint();
	}
}
