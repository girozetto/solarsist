import java.awt.*;

public class Planeta extends Corpo{

    private Recurso imagemRecurso;
    private double distCentro;
    private Planeta[] luas;
    private String nome;
    
    public Planeta(double x, double y, int raio, double distCentro, Recurso imagem) {
        super(x, y,0,0,0,0,0);
        setNome("");
        this.distCentro = distCentro;
        this.imagemRecurso = imagem;
        setAltura(raio);
        setLargura(raio);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void gerarLuas(Planeta lua, int num)
    {
        luas = new Planeta[num];
        double c = getAltura()/2;
        for( int i = 0 ; i < luas.length ; i++ )
        {
            int raio = (int)(Math.random()*5+4);
            Planeta l = new Planeta(0, 0, raio, c += 1.7, lua.getImagemRecurso());
            l.setAceleracao( (luas.length > 2 ? -1 : 1)*(Math.random() + 0.1));
            l.setNome("lua");
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
        g.drawImage(imagemRecurso.isSpritesheet()?imagemRecurso.subImagem():imagemRecurso.getImagem(), (int) getX(), (int) getY(), (int) getLargura(), (int) getAltura(), null);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(0xFF1E88E5));
        g2d.drawString(this.nome, (int)getX(), (int)getY());
        if(luas != null)
            for( Planeta p : luas )
            {
                p.orbitar(this);
                p.desenhar(g2d);
            }
        g2d.dispose();
    }
    
    public void orbitar(Planeta corpoCentral) {
        double centroX = corpoCentral.getX()+corpoCentral.getLargura()/2;
        double centroY = corpoCentral.getY()+corpoCentral.getAltura()/2;
        setRotacao( getRotacao() + getAceleracao() );
        setX( centroX + (this.distCentro + getLargura()/2) * Math.sin( Math.toRadians(getRotacao()) ));
        setY( centroY + (this.distCentro + getAltura()/2) * Math.cos( Math.toRadians(getRotacao()) ));
    }
}
