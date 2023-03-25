import javax.swing.*;
import java.awt.*;

public class Tela extends JPanel implements Runnable
{
    private Animacao anima;
    private boolean bugs;
    public Tela()
    {
        bugs = true;
        this.anima = new Animacao();
        new Thread(this).start();
    }
    private boolean rodarFrames()
    {
        this.repaint();
        try {Thread.sleep(1000/60);} catch (InterruptedException e) {JOptionPane.showMessageDialog(this.getParent(), "Houve um erro de interrupção da Thread");}
        return true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        long inicio= System.nanoTime();
        this.setBackground(Color.BLACK);
        anima.dimTela = new int[]{getWidth(),getHeight()};
        anima.desenhar(g2d);
        long fim= System.nanoTime();
        g2d.setColor(Color.WHITE);
        if(bugs){
            g2d.drawString("Tempo de Desenho: "+(fim-inicio)/1000000000.0+" segundos",0,0);
        }
    }

    @Override
    public void run() {
        while(rodarFrames());
    }

    public static void main(String[] args)
    {
        JFrame jogo = new JFrame("Sistema Solar");
        Tela t = new Tela();
        jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jogo.add(t);
        jogo.setSize(800, 600);
        jogo.setMinimumSize(jogo.getSize());
        jogo.setLocationRelativeTo(null);
        jogo.setVisible(true);
    }
    
}