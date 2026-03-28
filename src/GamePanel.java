import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    private final List<BiFunction<Integer, Integer, Fruta>> FRUITS_TYPES = List.of(
        Maca::new,
        Banana::new
    );
    ArrayList<Fruta> frutas = new ArrayList<Fruta>();
    Snake player;
    int applesEaten;
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        player = new Snake();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(player.bindControls());
        startGame();
    }

    public void generateFruit() {
        int x = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        int y = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        int i = random.nextInt(FRUITS_TYPES.size());

        Fruta novaFruta = FRUITS_TYPES.get(i).apply(x, y);
        frutas.add(novaFruta);
    }

    public void startGame() {
        generateFruit();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            if (frutas.size() != 0) {
                for(Fruta fruta : frutas) fruta.desenhar(g);
            }

            player.draw(g);
            drawScore(g);
        } else {
            gameOver(g);
        }
    }

    public void checkPlayerAndFruitCollisions() {
        for(Fruta fruta : frutas) {
            if ((player.getHeadXPos() == fruta.getX()) && (player.getHeadYPos() == fruta.getY())) {
                fruta.efeito(this);
                frutas.remove(fruta);
                generateFruit();
            }
        }
    }

    public void gameOver(Graphics g) {
        // Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.drawString(
            "Score: " + applesEaten,
            (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
            g.getFont().getSize()
        );
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(
            "Score: " + applesEaten,
            (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
            g.getFont().getSize()
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            player.update();
            checkPlayerAndFruitCollisions();
            if(player.isDead()) {
                running = false;
                timer.stop();
            }
        }
        repaint();
    }
}