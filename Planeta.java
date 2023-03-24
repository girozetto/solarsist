import java.awt.*;

public class Planeta extends Corpo{

    private Recurso imagemRecurso;
    private double distCentro;
    private Planeta[] luas;
    public Planeta(double x, double y, int raio, double distCentro, Recurso imagem) {
        super(x, y,0,0,0,0,0);
        this.distCentro = distCentro;
        this.imagemRecurso = imagem;
        setAltura(raio);
        setLargura(raio);
    }

    public void gerarLuas(Planeta lua, int num)
    {
        luas = new Planeta[num];
        double c = 10;
        for( int i = 0 ; i < luas.length ; i++ )
        {
            Planeta l = lua;
            int raio = (int)(Math.random()*5);
            l.setAltura( raio );
            l.setLargura( raio );
            l.setDistCentro(c += 0.7);
            l.setAceleracao( Math.random()*5 );
            luas[i] = l;
        }
    }

    public double getDistCentro() {
        return distCentro;
    }

    public void setDistCentro(double centro) {
        this.distCentro = centro;
    }

    public Recurso getImagemRecurso() {
        return imagemRecurso;
    }

    public void setImagemRecurso(Recurso imagemRecurso) {
        this.imagemRecurso = imagemRecurso;
    }

    @Override
    public void desenhar(Graphics2D g) {
        g.drawImage(imagemRecurso.subImagem(), (int) getX(), (int) getY(), (int) getLargura(), (int) getAltura(), null);
        if(luas != null)
            for( Planeta p : luas )
            {
                p.orbitar(this);
                p.desenhar(g);
            }
    }
    
    public void orbitar(Planeta corpoCentral) {
        double centroX = corpoCentral.getX()+corpoCentral.getLargura()/2;
        double centroY = corpoCentral.getY()+corpoCentral.getAltura()/2;
        setRotacao(getRotacao()+getAceleracao());
        setX( centroX + this.distCentro * Math.cos(Math.toRadians(getRotacao()) ));
        setY( centroY + this.distCentro * Math.sin(Math.toRadians(getRotacao()) ));
    }
}
