import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class Snake {
    int applesEaten;
    char direction = 'R';
    int bodyParts = 6;
    boolean alive = true;
    final int x[] = new int[GamePanel.GAME_UNITS];
    final int y[] = new int[GamePanel.GAME_UNITS];
    boolean wantsToReset = false;

    public boolean wantsToResetGame() {
        if(wantsToReset) {
            wantsToReset = false;
            return true;
        }
        return false;
    }

    private void killPlayer() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getHeadXPos() {
        return x[0];
    }

    public int getHeadYPos() {
        return y[0];
    }

    public boolean isDead() {
        return !this.isAlive();
    }

    public void draw(Graphics g) {
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
            } else {
                g.setColor(new Color(45, 180, 0));
                g.fillRect(x[i], y[i], GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);
            }
        }
    }

    public void update() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - GamePanel.UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + GamePanel.UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - GamePanel.UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + GamePanel.UNIT_SIZE;
                break;
        }

        checkCollisions();
    }

    public void grow() {
        bodyParts++;
    }

    public void checkCollisions() {
        checkBodyCollisions();
        checkScreenCollisions();
    }

    public void checkBodyCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) killPlayer();
        }
    }

    public void checkScreenCollisions() {
        // collides w/ left border
        if (x[0] < 0) killPlayer();
        // collides w/ right border
        if (x[0] > GamePanel.SCREEN_WIDTH) killPlayer();
        // collides w/ top border
        if (y[0] < 0) killPlayer();
        // collides w/ bottom border
        if (y[0] > GamePanel.SCREEN_HEIGHT) killPlayer();
    }

    public KeyAdapter bindControls() {
        return new PlayerControls(this);
    }
}

class PlayerControls extends KeyAdapter {
    Snake player;
    PlayerControls(Snake _player) {
        this.player = _player;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (player.direction != 'L' && player.direction != 'R')
                    player.direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (player.direction != 'R' && player.direction != 'L')
                    player.direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (player.direction != 'U' && player.direction != 'D')
                    player.direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (player.direction != 'D' && player.direction != 'U')
                    player.direction = 'D';
                break;
            case KeyEvent.VK_ENTER:
                player.wantsToReset = true;
                break;
        }
    }
}

