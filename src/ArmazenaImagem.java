import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ArmazenaImagem {
	
	private static final String IMAGE_FORMAT = "PNG";

	public static BufferedImage carregaImagem(File arquivoImagem) {
		try {
			BufferedImage imagem = ImageIO.read(arquivoImagem);
			if (imagem == null || (imagem.getWidth() < 0)) {
				return null;
			}
			return imagem;
		} catch (IOException e) {
			return null;
		}
	}

	public static void salvaImagem(BufferedImage imagem, File arquivo) {
		try {
			ImageIO.write(imagem, IMAGE_FORMAT, arquivo);
		} catch (IOException e) {
			
		}
	}

}
