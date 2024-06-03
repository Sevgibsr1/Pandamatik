import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Fruit {
    private Image image;
    private Image elma;
    private Image armut;
    private Image muz;
    private Image cilek;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int point;
    private int speed;
    private String type;

    public Fruit(int x, int y,  int point, String type) {
        this.x = x;
        this.y = y;
        this.point = point;
        this.type = type;
        this.speed = new Random().nextInt(3) + 1; // Meyve düşüş hızı

        elma = new ImageIcon("./resources/elma.png").getImage();
        armut = new ImageIcon("./resources/armut.png").getImage();
        muz = new ImageIcon("./resources/muz.png").getImage();
        cilek = new ImageIcon("./resources/cilek.png").getImage();
    }

    public int getX(){
        return  this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getDx(){
        return this.dx;
    }
    public int getDy(){
        return this.dy;
    }
    public int getPoint(){
        return this.point;
    }
    public Image getImage(){
        imageChanged();
        return this.image;
    }
    public int getSpeed(){
        return this.speed;
    }
    public String getType() {
        return type;
    }

    public int getPoints() {
        // Meyve türüne göre puan hesapla
        switch (type) {
            case "Elma":
                return 2;
            case "Armut":
                return 1;
            case "Muz":
                return -1;
            case "Çilek":
                return -2;
            default:
                return 0;
        }
    }

    public boolean intersects(int otherX, int otherY, int otherWidth, int otherHeight) {
        // Çarpışma kontrolü
        return x < otherX + otherWidth &&
                x + 30 > otherX &&
                y < otherY + otherHeight &&
                y + 30 > otherY;
    }
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setDx(int dx){
        this.dx = dx;
    }
    public void setDy(int dy){
        this.dy = dy;
    }
    public void setPoint(int point){
        this.point = point;
    }
    public void setElma(Image elma){
        this.elma = elma;
    }
    public void setArmut(Image armut) {
        this.armut = armut;
    }
    public void setCilek(Image cilek) {
        this.cilek = cilek;
    }
    public void setMuz(Image muz) {
        this.muz = muz;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void imageChanged(){
        if (type == "Elma")
            image = elma;
        else if (type == "Armut")
            image = armut;
        else if (type == "Muz")
            image = muz;
        else
            image = cilek;
    }

}
