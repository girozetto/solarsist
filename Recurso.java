import java.awt.image.*;
import java.awt.Color;
import javax.imageio.*;
import java.io.*;
public class Recurso {
    final private int MAXQUADRO = 50;

    private BufferedImage imagem;
    private boolean spritesheet;
    private int indice;

    public Recurso(String imgCaminho){
        this.spritesheet = true;
        this.indice = 0;
        try {
            setImagem(ImageIO.read(new File(imgCaminho)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Recurso()
    {
        this.spritesheet = true;
        this.indice = 0;
    }

    public void mudarFundo( Color remover, int subst )
    {
        int largura = this.imagem.getWidth();
        int altura = this.imagem.getHeight();
        BufferedImage img = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        int pixels[] = new int[largura*altura];

        this.imagem.getRGB(0,0, largura, altura, pixels,0, largura);
        for(int i = 0 ; i < pixels.length ; i++ )
            if( pixels[i] == remover.getRGB() ) pixels[i] = subst;
        img.setRGB( 0, 0, largura, altura, pixels, 0, largura);

        setImagem(img);
    }
    
    public BufferedImage subImagem()
    {
        indice = ( indice + 1) % ( (imagem.getWidth() / imagem.getHeight())*MAXQUADRO - 1 );
        return imagem.getSubimage( (indice/MAXQUADRO) * imagem.getHeight(), 0, imagem.getHeight(), imagem.getHeight());
    }

    public boolean isSpritesheet() {
        return spritesheet;
    }

    public void setSpritesheet(boolean spritesheet) {
        this.spritesheet = spritesheet;
    }

    public BufferedImage getImagem() {
        return imagem;
    }

    public void setImagem(BufferedImage imagem) {
        this.imagem = imagem;
    }
}
