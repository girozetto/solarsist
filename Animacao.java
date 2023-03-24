import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
public class Animacao{
    final private String SOL = "recursos/sol.png";
    final private String LUA = "recursos/lua.png";
    public int[] dim;
    Recurso[] recs;
    private Recurso fundo;
    private Planeta sol;
    private Planeta lua;
    private ArrayList<Planeta> planetas = new ArrayList<>();
    public Animacao() {

        dim = new int[2];
        int[] dim = new int[]{30, 45, 50, 53, 20, 30, 30, 45};
        this.fundo = new Recurso("recursos/espaco1.png");
        lua = new Planeta(0, 0, 5, 10, new Recurso(LUA));
        sol = new Planeta(0, 0, 300, 0, new Recurso(SOL));
        gerarElementos(dim);
    }
    
    private void gerarElementos( int ...dim )
    {
        double centro = 30;
        double accel = 0.3;
        Recurso[] op = new Recurso[10];
        for(int i=0;i<op.length;i++)
            op[i]=new Recurso("recursos/pln"+(i)+".png");
        op = optimizarRecursos( new Color(0xFF008080), op);
        for( int i = 0 ; i < dim.length ; i++ ){
            Planeta p = new Planeta(0, 0, dim[i], centro += 40 , op[i]);
            p.setAceleracao(accel += 0.7);
            planetas.add(p);
        }
    }
    
    private Recurso[] optimizarRecursos( Color fundo, Recurso ...img )
    {
        for(int i=0;i<img.length;i++){

            if( fundo != null ) img[i].mudarFundo(fundo,0);
            BufferedImage escalada = new BufferedImage( dim[i], dim[i], img[i].getImagem().getType() );
            escalada.createGraphics().drawImage(img[i].getImagem(),0,0,img[i].getImagem().getWidth(),img[i].getImagem().getHeight(),null);
            img[i].setImagem(escalada); 

        }
        return img;
    }
    
    public void desenhar(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.drawImage(fundo.getImagem(), 0, 0, dim[0], dim[1], null);

        sol.setX((dim[0]-sol.getLargura())/2);
        sol.setY((dim[1]-sol.getAltura())/2);
        sol.desenhar(g);

        planetas.forEach( planeta -> {
            planeta.orbitar(this.sol);
            planeta.desenhar(g);
        });
    }

}
