import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {

        initUI();
    }

    private void initUI() {

        add(new StartPage(this));

        setTitle("Pandamatik");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 650);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new Main();
            ex.setVisible(true);
        });
    }
}