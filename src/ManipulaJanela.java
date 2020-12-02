import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class ManipulaJanela {

	private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

	private BufferedImage imagemAtual;

	private ManipulaPainel imagemSelecionadaPainel;
	private ManipulaPainel imagemSecretaPainel;
	private ManipulaPainel imagemResultantePainel;

	private JFrame painel;

	// Renderiza a Janela e Inicia a Aplicação
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManipulaJanela janela = new ManipulaJanela();
					janela.painel.setLocationRelativeTo(null);
					janela.painel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Renderiza o conteudo da Janela
	public ManipulaJanela() {
		renderizarPainel();
	}
	
	// Menu de Operações
	
	public void abrirImagemSelecionada() {

		fileChooser.setSelectedFile(new File("image-source"));
		if (fileChooser.showOpenDialog(painel) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		imagemAtual = ArmazenaImagem.carregaImagem(fileChooser.getSelectedFile());

		if (imagemAtual == null) {
			JOptionPane.showMessageDialog(painel, "O arquivo não está em um formato válido.", "Erro ao carregar imagem",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		imagemSelecionadaPainel.setImagem(imagemAtual);
		painel.pack();
	}

	public void abrirImagemSecreta() {

		if (imagemSelecionadaPainel.getImagem() == null) {
			JOptionPane.showMessageDialog(painel, "Carregue uma imagem primeiro.", "Erro ao carregar imagem",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		fileChooser.setSelectedFile(new File("image-to-hide"));
		if (fileChooser.showOpenDialog(painel) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		imagemAtual = ArmazenaImagem.carregaImagem(fileChooser.getSelectedFile());

		if (imagemAtual == null) {
			JOptionPane.showMessageDialog(painel, "O arquivo não está em um formato válido.", "Erro ao carregar imagem",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Verificando se as duas imagens tem o mesmo tamanho
		if (imagemAtual.getHeight() != imagemSelecionadaPainel.getImagem().getHeight()
				|| imagemAtual.getWidth() != imagemSelecionadaPainel.getImagem().getWidth()) {
			JOptionPane.showMessageDialog(painel, "As duas imagems precisam ter o mesmo tamanho.",
					"Erro ao carregar imagem", JOptionPane.ERROR_MESSAGE);
			return;
		}

		imagemSecretaPainel.setImagem(imagemAtual);
		painel.pack();
	}

	public void esconder() {

		if (imagemSelecionadaPainel.getImagem() == null || imagemSecretaPainel.getImagem() == null) {
			JOptionPane.showMessageDialog(painel, "Carregue uma imagem e uma imagem secreta.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		ManipulaImagem s = new ManipulaImagem(imagemSelecionadaPainel.getImagem(), imagemSecretaPainel.getImagem());
		s.esconderImagem();

		imagemResultantePainel.setImagem(s.getImagemResultante());
	}

	public void revelar() {

		if (imagemSelecionadaPainel.getImagem() == null) {
			JOptionPane.showMessageDialog(painel, "Carregue uma imagem primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}

		ManipulaImagem s = new ManipulaImagem(imagemSelecionadaPainel.getImagem());
		s.revelarImagem();

		imagemResultantePainel.setImagem(s.getImagemResultante());
		painel.pack();
	}

	public void salvarResultado() {
		if (imagemResultantePainel.getImagem() == null) {
			JOptionPane.showMessageDialog(painel, "Primeiro esconda uma imagem antes de salvar.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		fileChooser.setSelectedFile(new File("resultado.jpg"));
		if (fileChooser.showSaveDialog(painel) == JFileChooser.APPROVE_OPTION) {
			ArmazenaImagem.salvaImagem(imagemResultantePainel.getImagem(), fileChooser.getSelectedFile());
		}

	}

	// Renderiza os conteudos do Painel
	private void renderizarPainel() {
		painel = new JFrame();
		painel.setTitle("Estenografia - Linguagem de Programação III");
		painel.setResizable(false);
		painel.setBounds(100, 100, 750, 550);
		painel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		painel.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("IFMA - Monte Castelo - 2020/1");
		painel.getContentPane().add(lblNewLabel, BorderLayout.SOUTH);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		painel.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		imagemSelecionadaPainel = new ManipulaPainel();
		tabbedPane.addTab("Imagem Original", imagemSelecionadaPainel);
		
		imagemSecretaPainel = new ManipulaPainel();
		tabbedPane.addTab("Imagem Secreta", imagemSecretaPainel);
		
		imagemResultantePainel = new ManipulaPainel();
		tabbedPane.addTab("Imagem Resultante", imagemResultantePainel);
	
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		painel.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Arquivo");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Carregar Imagem");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirImagemSelecionada();
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmOpenImageTo = new JMenuItem("Carregar Imagem Secreta");
		mntmOpenImageTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirImagemSecreta();
			}
		});
		mnFile.add(mntmOpenImageTo);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Esconder Imagem");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				esconder();
			}
		});
		mnFile.add(mntmNewMenuItem);
		
		JMenuItem mntmRevealImage = new JMenuItem("Revelar Imagem");
		mntmRevealImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				revelar();
			}
		});
		
		JMenuItem mntmSaveResult = new JMenuItem("Salvar Resultado");
		mntmSaveResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarResultado();
			}
		});
		
		mnFile.add(mntmRevealImage);
		mnFile.add(mntmSaveResult);
			
	}
	

}
