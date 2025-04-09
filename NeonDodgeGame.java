import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class NeonDodgeGame extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int playerX = 200;
    private final int playerY = 450;
    private final int playerSize = 30;
    private final int speed = 10;
    private ArrayList<Rectangle> obstacles;
    private Random rand;
    
    public NeonDodgeGame() {
        timer = new Timer(30, this);
        timer.start();
        setFocusable(true);
        addKeyListener(this);
        obstacles = new ArrayList<>();
        rand = new Random();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Player (Neon effect)
        g2d.setColor(Color.CYAN);
        g2d.fillRoundRect(playerX, playerY, playerSize, playerSize, 10, 10);

        // Obstacles
        g2d.setColor(Color.RED);
        for (Rectangle rect : obstacles) {
            g2d.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 10, 10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move obstacles down
        for (int i = 0; i < obstacles.size(); i++) {
            Rectangle rect = obstacles.get(i);
            rect.y += 5;
            if (rect.y > getHeight()) obstacles.remove(i);
        }

        // Add new obstacles
        if (rand.nextInt(10) > 8) {
            obstacles.add(new Rectangle(rand.nextInt(getWidth() - 30), 0, 30, 30));
        }

        // Check collision
        for (Rectangle rect : obstacles) {
            if (rect.intersects(new Rectangle(playerX, playerY, playerSize, playerSize))) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over!", "Oops!", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && playerX > 0) playerX -= speed;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && playerX < getWidth() - playerSize) playerX += speed;
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Neon Dodge");
        NeonDodgeGame game = new NeonDodgeGame();
        frame.add(game);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
