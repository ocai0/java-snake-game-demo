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
    int score;
    boolean running = false;
    Timer timer;
    Random random;
    KeyAdapter playerControls;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
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
        player = new Snake();
        playerControls = player.bindControls();
        this.addKeyListener(playerControls);
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
        metrics = getFontMetrics(g.getFont());
        g.drawString(
            "Score: " + score,
            (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2,
            g.getFont().getSize() + 320
        );
        
        g.setFont(new Font("Ink Free", Font.BOLD, 32));
        g.setColor(Color.WHITE);
        metrics = getFontMetrics(g.getFont());
        String text = "Pressione ENTER para jogar";
        g.drawString(
            text,
            (SCREEN_WIDTH - metrics.stringWidth(text)) / 2,
            g.getFont().getSize() + 380
        );
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(
            "Score: " + score,
            (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2,
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
            }
        }
        else {
            if(player.isDead() && player.wantsToResetGame()) {
                timer.stop();
                frutas.clear();
                score = 0;
                this.removeKeyListener(playerControls);
                startGame();
            }
        }
        repaint();
    }
}