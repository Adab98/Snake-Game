import java.awt.EventQueue;
import javax.swing.JFrame;

class Game extends JFrame {

    public Game() {
        add(new Board());

        setTitle("Snake");
        setResizable(false);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new Game();
            ex.setVisible(true);

        });
    }

}