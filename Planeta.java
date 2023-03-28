import java.awt.*;

public class Planeta extends Corpo{
    final private int COR_NOME=0xFF1E88E5;
    private Recurso imagemRecurso;
    private double distCentro;
    private Planeta[] satelites;
    private String nome;
    
    public Planeta(double x, double y, int raio, double distCentro, Recurso imagem) {
        super(x, y,0,0,0,0,0);
        setTranslacao(0);
        setTransVel(0);
        setRotVel(0);
        setNome("");
        setDistCentro( distCentro );
        setImagemRecurso( imagem );
        setAltura( raio );
        setLargura( raio );
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void gerarLuas(Recurso rec, int num)
    {
        satelites = new Planeta[num];
        double c = getAltura()/2;
        int sentido = 1;
        for( int i = 0 ; i < satelites.length ; i++ )
        {
            int raio = (int)(Math.random()*5+4);
            Planeta l = new Planeta(0, 0, raio, c += 1.7, rec);
            l.setRotVel( (sentido*=-1) * (Math.random()*0.1 + 0.04));
            l.setTransVel( sentido * (Math.random() + 0.1) );
            l.setNome("lua");
            satelites[i] = l;
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
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(getTranslacao()), getX(), getY());
        g2d.drawImage(imagemRecurso.isSpritesheet()?imagemRecurso.subImagem():imagemRecurso.getImagem(), (int) (getX()-getLargura()/2), (int) (getY()-getAltura()/2), (int) getLargura(), (int) getAltura(), null);
        g2d.dispose();

        g.setColor(new Color(COR_NOME));
        g.drawString(this.nome, (int)getX(), (int)getY());
        if(satelites != null)
            for( Planeta sat : satelites )
            {
                sat.orbitar(this);
                sat.desenhar(g);
            }
    }
    
    public void orbitar(Planeta corpoCentral) {
        double centroX = corpoCentral.getX();
        double centroY = corpoCentral.getY();

        setRotacao( (getRotacao() >= 360) ? 0 : getRotacao() + getRotVel());
        setTranslacao( (getTranslacao() >= 360) ? 0 : getTranslacao() + getTransVel() );
        setX( centroX + this.distCentro * Math.sin( Math.toRadians( getTranslacao() ) ));
        setY( centroY + this.distCentro * Math.cos( Math.toRadians( getTranslacao() ) ));
    }
}
