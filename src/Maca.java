import java.awt.Graphics;
import java.awt.Color;

public class Maca extends Fruta {

    public Maca(int x, int y) {
        super(x, y, 10);
    }
    
    @Override
    public void efeito(GamePanel game) {
        game.bodyParts++;
        game.applesEaten++;
        System.out.println("Maçã consumida: +1 no tamanho da cobra");
    }

    @Override
    public void desenhar(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(getX(), getY(),50,50);
    }
}
