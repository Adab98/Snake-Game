import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 1200;
    private final int B_HEIGHT = 1200;
    private final int DOT_SIZE = 40;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 200;

    private final int snakeX[] = new int[ALL_DOTS];
    private final int snakeY[] = new int[ALL_DOTS];
    private final int enemyX[] = new int[ALL_DOTS];
    private final int enemyY[] = new int[ALL_DOTS];

    private int snakeLength;
    private int appleX;
    private int appleY;
    private int enemyNum;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private boolean movePossible;

    private Timer timer;
    private Image ball;
    public Image apple;
    public Image head;
    public Image enemy;

    // private int count;

    public Board() {
        intlBoard();
    }

    private void intlBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        intlGame();
    }

    private void loadImages() {

        ImageIcon iib = new ImageIcon("src/assets/SnakeBody.jpg");
        ball = iib.getImage();

        ImageIcon iia = new ImageIcon("src/assets/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/assets/SnakeEyes.jpg");
        head = iih.getImage();

        ImageIcon iie = new ImageIcon("src/assets/Spider.jpg");
        enemy = iie.getImage();
    }

    private void intlGame() {
        snakeLength = 3;
        enemyNum = 0;
        movePossible = true;

        for (int z = 0; z < snakeLength; z++) {
            snakeX[z] = 120 - z * DOT_SIZE;
            snakeY[z] = 40;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPrimary(g);
        drawGrid(g);

    }

    private void drawPrimary(Graphics g) {
        if (inGame) {

            g.drawImage(apple, appleX, appleY, this);

            // g.drawImage(enemy, enemyX[], enemyY[], this);

            for (int z = 0; z < enemyNum; z++) {
                g.drawImage(enemy, enemyX[z], enemyY[z], this);
            }

            for (int z = 0; z < snakeLength; z++) {
                if (z == 0) {
                    g.drawImage(head, snakeX[z], snakeY[z], this);
                    System.out.println(this);
                } else {
                    g.drawImage(ball, snakeX[z], snakeY[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {
            gameOver(g);
            setBackground(Color.BLACK);

        }
    }

    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Rectangle2D cell = new Rectangle2D.Double(0, 0, DOT_SIZE, DOT_SIZE);

        g2d.setColor(Color.LIGHT_GRAY);
        double x = 0;
        double y = 0;

        if (inGame) {

            while (y + DOT_SIZE < B_HEIGHT + 40) {
                x = 0;
                while (x + DOT_SIZE < B_WIDTH + 40) {
                    g2d.translate(x, y);
                    g2d.draw(cell);
                    g2d.translate(-x, -y);
                    x += DOT_SIZE;
                }
                y += DOT_SIZE;
            }
        } else {
            gameOver(g);
            setBackground(Color.BLACK);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "GAME OVER";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        if ((snakeX[0] == appleX) && (snakeY[0] == appleY)) {
            snakeLength++;
            locateApple();

            if ((snakeLength % 2 == 0) && (snakeLength > 4)) {
                enemyNum++;
            }
            locateEnemy();
        }

        for (int z = 0; z < enemyNum; z++) {
            if ((enemyX[z] == appleX) && (enemyY[z] == appleY)) {
                enemyNum = enemyNum + 5;
                // setBackground(Color.RED);
                // count = 0;
                locateEnemy();
            }
        }
    }

    private void snakeMove() {
        // count++;

        for (int z = snakeLength; z > 0; z--) {
            snakeX[z] = snakeX[(z - 1)];
            snakeY[z] = snakeY[(z - 1)];
            // Print positions
            // System.out.println(snakeX[z] + " " + snakeY[z] + " " + z);

        }

        if (leftDirection) {
            snakeX[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            snakeX[0] += DOT_SIZE;
        }

        if (upDirection) {
            snakeY[0] -= DOT_SIZE;
        }

        if (downDirection) {
            snakeY[0] += DOT_SIZE;
        }
        // movePossible = true;
    }

    private void enemyMove() {
        Random rand = new Random();
        for (int z = 0; z < enemyNum; z++) {
            int nxt = rand.nextInt(40);

            if ((nxt == 1) && (enemyX[z] - DOT_SIZE > 0)) {
                enemyX[z] -= DOT_SIZE;
            }

            if ((nxt == 2) && (enemyX[z] + DOT_SIZE < B_WIDTH)) {
                enemyX[z] += DOT_SIZE;
            }

            if ((nxt == 3) && (enemyY[z] - DOT_SIZE > 0)) {
                enemyY[z] -= DOT_SIZE;
            }

            if ((nxt == 4) && (enemyY[z] + DOT_SIZE < B_HEIGHT)) {
                enemyY[z] += DOT_SIZE;
            }
        }
    }

    private void checkCollision() {
        for (int z = 0; z < snakeLength; z++) {
            if ((snakeX[0] == snakeX[z + 1]) && (snakeY[0] == snakeY[z + 1])) {
                inGame = false;
            }
        }

        for (int z = 0; z < enemyNum; z++) {
            if ((snakeX[0] == enemyX[z]) && (snakeY[0] == enemyY[z])) {
                inGame = false;
                // Print death positions
                // System.out.println(snakeX[0] + "= " + enemyX[z] + " " + snakeY[0] + "= " +
                // enemyY[z]);
            }
        }

        if (snakeY[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (snakeY[0] < 0) {
            inGame = false;
        }

        if (snakeX[0] >= B_WIDTH) {
            inGame = false;
        }

        if (snakeX[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        appleX = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        appleY = ((r * DOT_SIZE));

    }

    private void locateEnemy() {
        for (int z = 0; z < enemyNum; z++) {

            int r1 = (int) (Math.random() * RAND_POS);
            int r2 = (int) (Math.random() * RAND_POS);

            enemyX[z] = (r1 * DOT_SIZE);
            enemyY[z] = (r2 * DOT_SIZE);
            // Rand enemy position
            // System.out.println(r1 + " " + r2);

            // dont spawn enemy close to player, or on apple
            if (((enemyX[z] == appleX) && (enemyY[z] == appleY))
                    || (((enemyX[z] >= snakeX[0] - 100) && (enemyX[z] <= snakeX[0] + 100))
                            && ((enemyY[z] >= snakeY[0] - 100) && (enemyY[z] <= snakeY[0] + 100)))) {
                z--;

                System.out.println("RANDOM POSITION RETRIGGERED");
            }
        }
    }

    // flashing color
    // private void hurtReset() {
    // if (count > 2) {
    // setBackground(Color.WHITE);
    // }
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePossible = true;

        if (inGame) {
            checkApple();
            checkCollision();
            snakeMove();
            enemyMove();
            // hurtReset();
        }
        repaint();

    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (movePossible == true) {
                if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                }

                if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                }

                if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                    upDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                }

                if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                    downDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                }
                movePossible = false;
            }
        }
    }
}
