import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartPage extends JPanel {

    private JFrame mainFrame;
    private boolean playernum = false; // false: single player, true: multiplayer
    private JButton singlePlayerButton;
    private Image backgroundImage;

    public StartPage(JFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        initUI();
    }

    public static ImageIcon resizeIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

//    private void initUI() {
//        playernum = false;
//
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//
//        ImageIcon backgroundImage = new ImageIcon("./resources/start-background.png"); // Arka plan resmi dosyasının yolunu verin
//        JLabel backgroundLabel = new JLabel(backgroundImage);
//        backgroundLabel.setLayout(new BorderLayout());
//        add(backgroundLabel, BorderLayout.CENTER);
//
//        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);
//
//        // backgroundLabel'ı CENTER konumuna ekleyerek, ortada yerleştiriyoruz
//        add(backgroundLabel, BorderLayout.CENTER);
//
//    }

    private void initUI() {
        playernum = false;

        ImageIcon singlePlayerIcon = resizeIcon("./resources/1.png", 150, 150);

        singlePlayerButton = new JButton(singlePlayerIcon);

        customizeButton(singlePlayerButton);

        singlePlayerButton.addActionListener((ActionEvent event) -> {
            playernum = false;
            mainFrame.getContentPane().removeAll();
            mainFrame.add(new Board(playernum));
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(singlePlayerButton);
        add(bottomPanel, BorderLayout.CENTER);

        ImageIcon backgroundImage = new ImageIcon("./resources/start-background.png"); // Arka plan resmi dosyasının yolunu verin
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel, BorderLayout.CENTER);

        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        // backgroundLabel'ı CENTER konumuna ekleyerek, ortada yerleştiriyoruz
        add(backgroundLabel, BorderLayout.CENTER);

    }

    private void customizeButton(JButton button) {
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
    }

}
