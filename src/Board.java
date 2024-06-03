import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private Player player;
    private boolean multiplayer;
    public  boolean inGame = false;
    private int minSpeed = 1; // Minimum hız
    private int maxSpeed = 10; // Maximum hız
    private int width = 800;
    private int height = 650;
    private int sceneWidth = 800;
    private int sceneHeight = 650;
    private int characterX = sceneWidth / 2;
    private int characterY = sceneHeight - 50;
    private long lastQuestionTime;  // Son soru sorulma zamanı
    private int questionTimeout = 15000;  // Soru zaman aşımı süresi (15 saniye)

    private long startTime;  // Soru sorma başlangıç zamanı

    private int characterSpeed = 5;
    private int score = 0;
    private Random random;
    private String currentQuestion = ""; // Bu değişkeni ekleyin
    private List<Fruit> fruits;
    private int fruitSpawnCounter = 0;
    private int fruitSpawnRate = 100; // Meyve düşme sıklığı
    private JFrame calculatorFrame;
    private JTextField numField1, numField2, resultField;
    private JComboBox<String> operationComboBox;
    private JLabel questionLabel;
    private Sound sound;
    private int totalTime;

    public Board(boolean player){
        multiplayer = player;
        sound = new Sound(); // Sound nesnesini başlat
        initBoard();
        initPlayer();
        initCalculatorFrame();
        Timer compareTimer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Board.this, "Süre doldu. Yeni soru sorulacak.");
                inGame = false;
                timer.stop();
                initCalculatorFrame();
            }
        });
        compareTimer.setRepeats(false);
    }

    private void initBoard() {
        sound = new Sound();
        addKeyListener(new TAdapter());
        setFocusable(true);
        timer = new Timer(16, this);

        random = new Random();
        fruits = new ArrayList<>();

        questionLabel = new JLabel("Soru: ");
        setLayout(null); // Düzeni null olarak ayarla
        questionLabel.setBounds(20, 10, 200, 30); // x=30, y=40, genişlik=200, yükseklik=30
        add(questionLabel);

    }



    public void initPlayer(){
        player = new Player(
                100,
                height-190,
                characterSpeed,
                new ImageIcon("./resources/player-1.png").getImage());
    }

    private void initCalculatorFrame() {
        calculatorFrame = new JFrame("Aritmetik Hesap Makinesi");
        calculatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculatorFrame.setSize(400, 200);
        calculatorFrame.setLayout(new FlowLayout());

        numField1 = new JTextField(10);
        numField2 = new JTextField(10);
        operationComboBox = new JComboBox<>(new String[]{"+", "-", "*", "/"});
        resultField = new JTextField(10);

        JButton calculateButton = new JButton("Soru Sor");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResult();
            }
        });

        calculatorFrame.add(numField1);
        calculatorFrame.add(operationComboBox);
        calculatorFrame.add(numField2);
        calculatorFrame.add(new JLabel("="));
        calculatorFrame.add(resultField);
        calculatorFrame.add(calculateButton);

        calculatorFrame.setVisible(true);
    }
    private void calculateResult() {
        sound.playBackgroundSound(true);
        try {
            int num1 = Integer.parseInt(numField1.getText());
            int num2 = Integer.parseInt(numField2.getText());
            String operation = (String) operationComboBox.getSelectedItem();

            switch (operation) {
                case "+":
                    break;
                case "-":
                    break;
                case "*":
                    break;
                case "/":
                    break;
                default:
            }
            Font questionFont = new Font("Arial", Font.BOLD, 21);
            questionLabel.setFont(questionFont);

            String currentQuestionText = String.format("Soru: %d %s %d", num1, operation, num2);
            questionLabel.setBounds(20, 10, 400, 30);
            questionLabel.setText(currentQuestionText);

            inGame = true;
            timer.start();
            score=0;
            calculatorFrame.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçerli sayılar giriniz.");
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(this, "Sıfıra bölme hatası.");
        }
    }
    private void checkAnswer() {
        int userAnswer;
        try {
            userAnswer = Integer.parseInt(resultField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçerli bir sayı giriniz.");
            return;
        }

        if (score == userAnswer) {
            if (inGame) {
                long totalTime = System.currentTimeMillis() - startTime; // long olarak değiştirildi
                double totalTimeSeconds = totalTime / 1000.0;
                JOptionPane.showMessageDialog(this, "Tebrikler"+ totalTimeSeconds + " milisaniyede tamamlandı, Yeni soru sorulacak.");
                inGame = false;
                timer.stop();
                initCalculatorFrame();
                startTime = System.currentTimeMillis(); // Soru sorma başlangıç zamanını güncelle
                return;
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground((Graphics2D) g);
        doDrawing(g);
    }

    private void drawBackground(Graphics2D g2d) {
        ImageIcon backgroundImage = new ImageIcon("./resources/background.png");
        g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    private void dropFruits() {
        fruitSpawnCounter++;

        if (fruitSpawnCounter >= fruitSpawnRate) {
            // Rastgele bir meyve türü seç
            String[] fruitTypes = {"Elma", "Armut", "Muz", "Çilek"};
            String selectedFruitType = fruitTypes[random.nextInt(4)];

            // Rastgele bir x konumu seç (sahne içinde)
            int fruitX = random.nextInt(sceneWidth - 60);

            // Rastgele bir hız seç
            int fruitSpeed = random.nextInt(5) + 1; // 1 ile 5 arasında rastgele bir hız

            // Meyve oluştur ve listeye ekle
            Fruit fruit = new Fruit(fruitX, 0, fruitSpeed, selectedFruitType);
            fruits.add(fruit);

            fruitSpawnCounter = 0; // Sayaçı sıfırla
        }

        // Meyveleri düşür
        for (Fruit f : fruits) {
            f.setY(f.getY() + f.getSpeed());
            // Meyve sahneden çıkarsa yukarı çıkart
            if (f.getY() > sceneHeight) {
                f.setY(0);
            }
        }
    }

    private void moveCharacter() {
        // Karakteri yatay yönde hareket ettir
        player.setX(player.getX() + player.getSpeed() * player.getDx());

        // Karakterin sahneden çıkmasını kontrol et
        if (player.getX() < 0) {
            player.setX(0);
        } else if (player.getX() + 180 > sceneWidth) {
            player.setX(sceneWidth - 180);
        }
    }

    private void checkCollisions() {
        // Meyve ve karakter çarpışma kontrolü
        for (Fruit fruit : new ArrayList<>(fruits)) {
            if (fruit.intersects(player.getX(), player.getY(), 200, 200)) {
                sound.playEatingFruitSound(); // Meyve toplandığında ses efekti çal
                sound.adjustBackgroundVolume(0.3f); // Arka plan müziğinin sesini kıs
                score += fruit.getPoints();
                fruits.remove(fruit);

                Timer restoreVolumeTimer = new Timer(2000, e -> sound.adjustBackgroundVolume(0.7f));
                restoreVolumeTimer.setRepeats(false);
                restoreVolumeTimer.start();
            }
        }
    }

    private void increaseSpeed() {
        if (player.getSpeed() < maxSpeed) {
            player.setSpeed(player.getSpeed() + 1);
        }
    }
    private void decreaseSpeed() {
        if (player.getSpeed() > minSpeed) {
            player.setSpeed(player.getSpeed() - 1);
        }
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Arka planı çiz
        drawBackground(g2d);

        // Oyun karakterini çiz
        g2d.drawImage(player.getImage(),player.getX(),player.getY(),this);

        // Meyveleri çiz
        for (Fruit fruit : fruits) {
            g2d.drawImage(fruit.getImage(), fruit.getX(), fruit.getY(), null);
//            g2d.fillRect(fruit.getX(), fruit.getY(), 30, 30);
        }

        // Skor tablosunu göster
        g2d.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 24); // Yazı tipi boyutunu 24 olarak ayarladık
        g2d.setFont(font);
        g2d.drawString("Skor: " + score, sceneWidth - 150, 30); // Sağ üst köşede skoru göster


        g2d.setColor(Color.RED);
        g2d.drawString("Hız: " + player.getSpeed(), 350, 30);


        g2d.setColor(Color.GRAY); // Metin rengi gri
        g2d.setFont(font);
    }

    public void setCurrentQuestion(String question) {
        this.currentQuestion = question;
    }

    class TAdapter extends KeyAdapter {
        private Sound sound;
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_LEFT:
                    player.setdX(-1);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setdX(1);
                    break;
                case KeyEvent.VK_UP:
                    player.setSpeed(player.getSpeed()+1);
                    increaseSpeed(); // Hızı artır
                    break;
                case KeyEvent.VK_DOWN:
                    player.setSpeed(player.getSpeed()-1);
                    decreaseSpeed(); // Hızı azalt
                    break;
            }
            if (inGame) {
                timer.start(); // inGame durumu true ise timer'ı başlat
//                sound.playBackgroundSound(true);

            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                    player.setdX(0);
                    break;
            }
        }

    }

    public void startPage() {
        requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Meyveleri düşür
        dropFruits();

        // Karakteri hareket ettir
        moveCharacter();

        // Meyve ve karakter çarpışma kontrolü
        checkCollisions();
        checkAnswer();
        repaint();
    }
}
