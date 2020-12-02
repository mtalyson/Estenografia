import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

@SuppressWarnings("serial")
public class ManipulaPainel extends JComponent {

	private int largura;
	private int altura;

	private BufferedImage imagemPainel;

	public ManipulaPainel() {
		imagemPainel = null;
	}

	// Seleciona a imagem que deve ser exibida no painel
	public void setImagem(BufferedImage image) {
		if (image != null) {
			largura = image.getWidth();
			altura = image.getHeight();
			imagemPainel = image;
			repaint();
		}
	}

	public BufferedImage getImagem() {
		return imagemPainel;
	}

	// Limpa a imagem no painel
	public void clearImage() {
		Graphics imageGraphics = imagemPainel.getGraphics();
		imageGraphics.setColor(Color.LIGHT_GRAY);
		imageGraphics.fillRect(0, 0, largura, altura);
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(largura, altura);
	}
	
	// Chamado toda vez que a imagem precisa ser exibida no painel
	public void paintComponent(Graphics g) {
		Dimension size = getSize();
		g.clearRect(0, 0, size.width, size.height);
		if (imagemPainel != null) {
			g.drawImage(imagemPainel, 0, 0, null);
		}
	}
}
