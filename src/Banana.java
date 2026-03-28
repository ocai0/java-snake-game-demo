import java.awt.Graphics;
import java.awt.Color;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Banana extends Fruta {
    protected BufferedImage sprite;

    public Banana(int x, int y) {
        super(x, y, 10);
        try {
            this.sprite = ImageIO.read(new File("./src/sprites/banana.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void efeito(GamePanel game) {
        game.bodyParts++;
        game.applesEaten++;
    }

    @Override
    public void desenhar(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, null);
    }
}
