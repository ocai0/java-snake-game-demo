import java.awt.Graphics;
import java.awt.Color;

public class Banana extends Fruta {

    public Banana(int x, int y) {
        super(x, y, 10);
    }
    
    @Override
    public void efeito(GamePanel game) {
        game.bodyParts++;
        game.applesEaten++;
        System.out.println("Banana consumida: +1 no tamanho da cobra");
    }

    @Override
    public void desenhar(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(getX(), getY(), GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
    }
}
