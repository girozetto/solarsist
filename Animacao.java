import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
public class Animacao{
    final private String SOL = "recursos/sol.png";
    final private String LUA = "recursos/lua.png";
    final private String FUNDO = "recursos/espaco2.png";
    final private double VELOCIDADE_TRANS = 1;
	final private int[][] pldms = new int[][]{new int[]{15, 2, 55},new int[]{20, 2, 45} ,new int[]{27, 1, 30} ,new int[]{17, 1, 40} , new int[]{50, 9, 10} , new int[]{42, 7, 25} , new int[]{35, 9, 8}, new int[]{27, 4, 15}};
	final private String[] nomes = new String[]{"Mercurio","Venus","Terra","Marte","Jupiter","Saturno","Urano","Neptuno"};
        

    public int[] dimTela;
    private Recurso fundo;
    private Planeta sol;
    private Planeta lua;
    private ArrayList<Planeta> planetas = new ArrayList<>();
    public Animacao() {

        dimTela = new int[2];
        this.fundo = new Recurso(FUNDO);
        lua = new Planeta(0, 0, 5, 10, optimizarRecursos(new Color(0xFF008080), new Recurso(LUA))[0]);
        lua.getImagemRecurso().setSpritesheet(false);
        sol = new Planeta(0, 0, 85, 0, optimizarRecursos(new Color(0xFF008080), new Recurso(SOL))[0]);
        sol.setNome("Sol");
        sol.setRotVel(0.05);
        gerarElementos( pldms, nomes);
    }
    
    private void gerarElementos( int[][] dim , String[] nomes)
    {
        double centro = (this.sol.getAltura()/2)+20;
        double rotacaoVel = 0.07;
        Recurso[] op = new Recurso[dim.length];
        for(int i=0;i<op.length;i++)
            op[i]=new Recurso("recursos/pln"+(i)+".png");
        op = optimizarRecursos( new Color(0xFF008080), op);
        
        for( int i = 0 ; i < dim.length ; i++ ){
            Planeta p = new Planeta(0, 0, dim[i][0], centro + dim[i][0]/2 , op[i]);
            p.gerarLuas( lua.getImagemRecurso(), dim[i][1] );
            p.setTransVel( ( dim[i][2] / 100.0 ) * VELOCIDADE_TRANS );
            p.setRotVel(rotacaoVel += 0.06);
            p.setNome( nomes[i] );
            planetas.add( p );
            centro += dim[i][0];
        }
    }
    
    private Recurso[] optimizarRecursos( Color fundo, Recurso ...img )
    {
        for(int i=0;i<img.length;i++){

            if( fundo != null ) img[i].mudarFundo(fundo,0);
            BufferedImage escalada = new BufferedImage( img[i].getImagem().getWidth(), img[i].getImagem().getHeight(), img[i].getImagem().getType() );
            escalada.createGraphics().drawImage(img[i].getImagem(),0,0,img[i].getImagem().getWidth(),img[i].getImagem().getHeight(),null);
            img[i].setImagem(escalada); 

        }
        return img;
    }
    
    public void desenhar(Graphics2D g)
    {
        if(dimTela[0] <= 0 || dimTela[1] <= 0) return;
        g.drawImage(fundo.getImagem(), 0, 0, dimTela[0], dimTela[1], null);

        this.sol.setX(dimTela[0]/2);
        this.sol.setY(dimTela[1]/2);
        this.sol.orbitar(this.sol);
        this.sol.desenhar(g);

        planetas.forEach( planeta -> {
            planeta.orbitar(this.sol);
            planeta.desenhar(g);
        });
    }

}
