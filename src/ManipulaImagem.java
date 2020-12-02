import java.awt.image.BufferedImage;

public class ManipulaImagem {

	private BufferedImage imagemCarregada;
	private BufferedImage imagemSecreta;
	private BufferedImage imagemResultante;

	private int larguraImagem;
	private int alturaImagem;

	// Construtor para esconder uma imagem em outra
	public ManipulaImagem(BufferedImage imgCarregada, BufferedImage imgSecreta) {
		imagemCarregada = imgCarregada;
		imagemSecreta = imgSecreta;
		larguraImagem = imgCarregada.getWidth();
		alturaImagem = imgCarregada.getHeight();
		imagemResultante = new BufferedImage(larguraImagem, alturaImagem, BufferedImage.TYPE_INT_RGB);
	}

	// Construtor para revelar imagem
	public ManipulaImagem(BufferedImage imgOriginal) {
		this(imgOriginal, null);
	}

	// Esconde os 3 primeiros Bits mais significativos (MSB) de pixelB em pixelA
	private int escondePixel(int pixelB, int pixelA) {
		return pixelA & 0xFFF8F8F8 | (pixelB & 0x00E0E0E0) >> 5;
	}

	// Extrai os 3 ultimos Bits menos significativos (LSB)
	private int revelaPixel(int pixel) {
		return (pixel & 0xFF070707) << 5;
	}

	// Esconde uma Imagem Secreta em uma Imagem Selecionada
	public void esconderImagem() {
		for (int x = 0; x < larguraImagem; x++) {
			for (int y = 0; y < alturaImagem; y++) {
				imagemResultante.setRGB(x, y, escondePixel(imagemSecreta.getRGB(x, y), imagemCarregada.getRGB(x, y)));
			}
		}
	}

	// Revela a Imagem Secreta
	public void revelarImagem() {
		for (int x = 0; x < larguraImagem; x++) {
			for (int y = 0; y < alturaImagem; y++) {
				imagemResultante.setRGB(x, y, revelaPixel(imagemCarregada.getRGB(x, y)));
			}
		}
	}
	
	// Retorna Imagem Resultante
	public BufferedImage getImagemResultante() {
		return imagemResultante;
	}

}
